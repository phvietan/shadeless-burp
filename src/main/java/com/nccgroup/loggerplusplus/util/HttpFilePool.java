package com.nccgroup.loggerplusplus.util;

import com.nccgroup.loggerplusplus.logentry.LogEntry;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class HttpFilePool {
    private String project;
    private String fileUrl;
    private String fileCheckUrl;
    private ArrayList <LogEntry> entries;

    public HttpFilePool(String apiURl, String project, ArrayList <LogEntry> entries) {
        this.project = project;
        this.entries = entries;
        this.fileUrl = String.format("%s/burp/files", apiURl);
        this.fileCheckUrl = String.format("%s/files", apiURl);
    }

    private static HttpRequest.BodyPublisher createBodyNone() {
        return HttpRequest.BodyPublishers.noBody();
    }

    private URI getFileCheckUrl(String hash) {
        return URI.create(
                String.format("%s/%s/%s", this.fileCheckUrl, this.project, hash)
        );
    }

    private void upload(String fileHash, byte[] rawBody) {
        MultipartData mimeMultipartData = new MultipartData();
        mimeMultipartData.addFile(new FileObject("file", rawBody));
        mimeMultipartData.addProperty("project", this.project);
        mimeMultipartData.addProperty("id", fileHash);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofByteArray(mimeMultipartData.build().toByteArray());

        var request = HttpRequest.newBuilder()
            .header("Content-Type", mimeMultipartData.getContentType())
            .POST(body)
            .uri(URI.create(this.fileUrl))
            .build();

        var httpClient = HttpClient.newBuilder().build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkAndSend(String fileHash, byte[] rawBody) {
        URI checkUrl = this.getFileCheckUrl(fileHash);
        HttpRequest head = HttpRequest.newBuilder()
                .method("HEAD", createBodyNone())
                .uri(checkUrl)
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();
        new Thread(() -> {
            try {
                HttpResponse<Void> response = httpClient.send(head, HttpResponse.BodyHandlers.discarding());
                if (response.statusCode() == 404) {
                    upload(fileHash, rawBody);
                }
            } catch (Exception err) {}
        }).start();
    }
    private void sendOneEntry(LogEntry logEntry) {
        String requestBodyHash = logEntry.getRequestBodyHash(this.project);
        byte[] rawRequest = logEntry.rawRequestBody;
        checkAndSend(requestBodyHash, rawRequest);

        String responseBodyHash = logEntry.getResponseBodyHash(this.project);
        byte[] rawResponse = logEntry.rawResponseBody;
        checkAndSend(responseBodyHash, rawResponse);
    }

    public void sendAllEntries() {
        for (LogEntry entry: this.entries) {
            sendOneEntry(entry);
        }
    }
}
