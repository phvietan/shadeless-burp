package com.nccgroup.loggerplusplus.util;

import org.apache.http.client.methods.HttpPost;

import java.util.List;

public interface IHttpRequestPool {
    Integer NUM_THREADS = 30;
    String url = null;
    List<HttpPost> requestPool = null;

    public void send();
}