package co.com.bar.utils;

import com.adl.bc.common.controller.lambda.ProxyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

public final class ResponseWrapper<T> {

  private T response;
  private int statusCode;

  private ResponseWrapper(final int statusCode) {
    this.statusCode = statusCode;
  }

  public static ResponseWrapper withStatusCode(final int statusCode) {
    return new ResponseWrapper(statusCode);
  }

  public ResponseWrapper<T> withResponse(final T response) {
    this.response = response;
    return this;
  }

  public ProxyResponse build() {
    final Gson gsonBuilder = new GsonBuilder().create();
    final ProxyResponse proxyResponse = new ProxyResponse();
    proxyResponse.setBase64Encoded(false);
    proxyResponse.setStatusCode(this.statusCode);
    proxyResponse.setBody(gsonBuilder.toJson(this.response));
    final Map<String, Object> headers = new HashMap<>();
    headers.put(
        "Access-Control-Allow-Headers",
        "Content-Type,X-Amz-Date,Authorization,X-Api-Key,x-adl-bank-id,x-adl-transaction-id,"
            + "x-adl-channel,x-adl-ip-addr");
    headers.put("Access-Control-Allow-Methods", "*");
    headers.put("Access-Control-Allow-Origin", "*");
    proxyResponse.setHeaders(headers);
    return proxyResponse;
  }
}
