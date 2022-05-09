package co.com.bar.model;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import co.com.adl.commons.errors.dto.Response;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarTenderInformation extends Response {

  private static final Logger LOGGER = LogManager.getLogger(BarTenderInformation.class);

  public static final String ID_SECUENCE_CUPS = "idSecuenceCups";
  public static final String SECUENCE_CUPS = "secuenceCups";
  public static final String ITERATIONS = "iterations";

  private LinkedList primeNumbersQueue = new LinkedList();
  private LinkedList iterationsQueue = new LinkedList();
  private LinkedList outputQueue = new LinkedList();
  private Stack inputStack = new Stack();

  private static final String[] FIELD_NAMES = {
      ID_SECUENCE_CUPS,
      SECUENCE_CUPS
  };

  private String idSecuenceCups;
  private String secuenceCups;
  private Integer iterations;

  public BarTenderInformation() {
  }

  public String getIdSecuenceCups() {
    return idSecuenceCups;
  }

  public void setIdSecuenceCups(final String idSecuenceCups) {
    this.idSecuenceCups = idSecuenceCups;
  }

  public String getSecuenceCups() {
    return secuenceCups;
  }

  public void setSecuenceCups(final String secuenceCups) {
    this.secuenceCups = secuenceCups;
  }

  public BarTenderInformation(final String idSecuenceCups, final String secuenceCups) {
    this.idSecuenceCups = idSecuenceCups;
    this.secuenceCups = secuenceCups;
  }

  public Integer getIterations() {
    return iterations;
  }

  public void setIterations(final Integer iterations) {
    this.iterations = iterations;
  }

  public void buildOutput() {
    buildSecuenceStack(this.secuenceCups);
    buildIterationsQueue(this.iterations);
    buildPrimeNumbersQueue(this.iterationsQueue.size());
    buildOutputSecuence(this.iterationsQueue, this.primeNumbersQueue, this.inputStack,
        this.outputQueue);
  }

  public String getOutput() {
    StringBuilder stringBuilder = new StringBuilder();
    while (this.outputQueue.peek() != null) {
      stringBuilder.append(this.outputQueue.pop()).append(" ");
    }

    LOGGER.info("Response of bar tender: {} ", stringBuilder.toString());
    return stringBuilder.toString().substring(INTEGER_ZERO,
        stringBuilder.toString().length()-1);
  }

  public Stack buildSecuenceStack(String secuenceCups) {
    Arrays.asList(secuenceCups.split(",")).stream().forEach(element -> {
      this.inputStack.push(element);
    });

    return this.inputStack;
  }

  private void buildOutputSecuence(LinkedList iterationsQueue, LinkedList primesQueue,
      Stack inputStack,
      LinkedList outputQueue) {

    Stack stackB = new Stack();
    Stack stackA = new Stack();

    LOGGER.info("Iteration in output secuence {} ");

    if (iterationsQueue.peek() != null) {
      int primeToValidate = (int) primesQueue.poll();

      while (!inputStack.empty()) {
        if (Integer.parseInt(inputStack.peek().toString()) % primeToValidate == 0) {
          LOGGER.info("Adding at stack B the value {} ",
              (Integer.parseInt(inputStack.peek().toString())));
          stackB.push(inputStack.pop());
        } else {
          LOGGER.info("Adding at stack A the value {} ",
              (Integer.parseInt(inputStack.peek().toString())));
          stackA.push(inputStack.pop());
        }
      }

      outputQueue.addAll(outputQueue.size(), stackB);
      iterationsQueue.poll();

      if (iterationsQueue.peek() != null) {
        buildOutputSecuence(iterationsQueue, primesQueue, stackA, outputQueue);
      } else {
        outputQueue.addAll(outputQueue.size(), stackA);
        LOGGER.info("All iterations was successfully executed");
        return;
      }
    }
  }


  private LinkedList buildIterationsQueue(int iterations) {
    LOGGER.info("Build queue of iterations until value {} ", iterations);
    for (int i = 1; i <= iterations; i++) {
      this.iterationsQueue.offer(i);
    }

    return iterationsQueue;
  }

  private void buildPrimeNumbersQueue(int iterations) {

    int nextInteger = 2;
    int flagDivisible = 0;

    while (this.primeNumbersQueue.size() < iterations) {

      for (int i = 2; i <= nextInteger / 2; i++) {
        if (nextInteger % i == 0) {
          flagDivisible = 1;
        }
      }

      if (flagDivisible == 0) {
        this.primeNumbersQueue.offer(nextInteger);
        LOGGER.info("Adding at queue of primes the number  : {} ", nextInteger);
      }

      flagDivisible = 0;
      nextInteger++;

    }
  }

  public static String[] getFieldNames() {
    return FIELD_NAMES;
  }
}