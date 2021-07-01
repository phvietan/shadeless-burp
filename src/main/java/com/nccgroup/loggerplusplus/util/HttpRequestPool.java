package com.nccgroup.loggerplusplus.util;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpRequestPool implements IHttpRequestPool {
    Integer NUM_THREADS = 10;
    Integer TIMEOUT = 1;
    String url;
    List<HttpPost> requestPool;

    HttpRequestPool(String url) {
        this.url = url;
        this.requestPool = new ArrayList<>();
    }

    private void process(HttpPost req) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            httpClient.execute(req);
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        ExecutorService pool = Executors.newFixedThreadPool(this.NUM_THREADS);
        for (final HttpPost req : this.requestPool) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    process(req);
                }
            });
        }
        pool.shutdown();
        try {
            pool.awaitTermination(this.TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}