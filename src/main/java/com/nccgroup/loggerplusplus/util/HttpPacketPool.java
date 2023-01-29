package com.nccgroup.loggerplusplus.util;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HttpPacketPool extends HttpRequestPool {
    private HttpPost buildHttpPostRequest(JsonObject obj) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(this.url);
        StringEntity params = new StringEntity(obj.toString());
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setEntity(params);
        return request;
    }

    public HttpPacketPool(String url, ArrayList<JsonObject> entriesJson) {
        super(url);
        for  (JsonObject entryJson : entriesJson) {
            try {
                HttpPost req = buildHttpPostRequest(entryJson);
                this.requestPool.add(req);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
