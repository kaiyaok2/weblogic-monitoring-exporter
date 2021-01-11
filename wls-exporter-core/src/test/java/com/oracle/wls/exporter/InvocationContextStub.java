package com.oracle.wls.exporter;// Copyright (c) 2021, Oracle Corporation and/or its affiliates.
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.meterware.simplestub.Stub.createStrictStub;

abstract class InvocationContextStub implements InvocationContext {

  static final String HOST = "myhost";
  static final int PORT = 7123;
  static final int REST_PORT = 7431;
  private final ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

  private String authenticationHeader = null;
  private String contentType = "text/plain";
  private String redirectLocation = null;
  private InputStream requestStream = null;
  private int responseStatus = 0;
  private boolean secure;
  private Map<String, String> responseHeaders = new HashMap<>();

  static InvocationContextStub create() {
    return createStrictStub(InvocationContextStub.class);
  }

  InvocationContextStub withHttps() {
    secure = true;
    return this;
  }

  InvocationContextStub withConfiguration(String effect, String configuration) throws IOException {
    contentType = MultipartTestUtils.getContentType();
    requestStream = new ByteArrayInputStream(MultipartTestUtils.createEncodedForm(effect, configuration).getBytes());
    return this;
  }

  String getRedirectLocation() {
    return redirectLocation;
  }

  String getResponse() {
    return responseStream.toString();
  }

  String getResponseHeader(String name) {
    return responseHeaders.get(name);
  }

  int getResponseStatus() {
    return responseStatus;
  }

  @Override
  public void close() {
  }

  @Override
  public UrlBuilder createUrlBuilder() {
    return UrlBuilder.create(HOST, secure).withPort(PORT);
  }

  @Override
  public String getApplicationContext() {
    return "/unitTest/";
  }

  @Override
  public String getAuthenticationHeader() {
    return authenticationHeader;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public String getInstanceName() {
    return "unit test";
  }

  @Override
  public InputStream getRequestStream() throws IOException {
    return requestStream;
  }

  @Override
  public void sendError(int status, String msg) throws IOException {
    responseStatus = status;
  }

  @Override
  public void sendRedirect(String location) throws IOException {
    redirectLocation = location;
  }

  @Override
  public void setResponseHeader(String name, String value) {
    responseHeaders.put(name, value);
  }

  @Override
  public void setStatus(int status) {
    responseStatus = status;
  }

  @Override
  public PrintStream getResponseStream() throws IOException {
    return new PrintStream(responseStream);
  }
}
