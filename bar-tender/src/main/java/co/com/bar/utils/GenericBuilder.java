package co.com.bar.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericBuilder<T> {

  private final Supplier<T> instantiator;


  private List<Consumer<T>> instanceModifiers = new ArrayList<>();

  private GenericBuilder(final Supplier<T> instantiator) {
    this.instantiator = instantiator;
  }

  public static <T> GenericBuilder<T> of(final Supplier<T> instantiator) {
    return new GenericBuilder<>(instantiator);
  }

  public <U> GenericBuilder<T> with(final BiConsumer<T, U> consumer, final U value) {
    final Consumer<T> c = instance -> consumer.accept(instance, value);
    instanceModifiers.add(c);
    return this;
  }

  public T build() {
    final T value = instantiator.get();
    instanceModifiers.forEach(modifier -> modifier.accept(value));
    instanceModifiers.clear();
    return value;
  }
}