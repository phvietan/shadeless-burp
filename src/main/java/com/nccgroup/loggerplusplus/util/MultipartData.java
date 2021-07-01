package com.nccgroup.loggerplusplus.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MultipartData {

    private Charset CHARSET = StandardCharsets.UTF_8;
    private String MIMETYPE = "application/octet-stream";

    private String boundary;
    private List<FileObject> files = new ArrayList<>();
    private Map<String, String> properties = new LinkedHashMap<>();

    public MultipartData() {
        this.boundary = new BigInteger(128, new Random()).toString();
    }

    public String getContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    public void addFile(FileObject newFile) {
        this.files.add(newFile);
    }
    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public ByteArrayOutputStream build() {
        MultipartData mimeMultipartData = new MultipartData();
        mimeMultipartData.boundary = this.boundary;

        var newline = "\r\n".getBytes(this.CHARSET);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (var f : files) {
                byteArrayOutputStream.write(("--" + boundary).getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + f.getName() + "\"; filename=\"" + f.getName() + "\"").getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(("Content-Type: " + this.MIMETYPE).getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(f.getBody());
                byteArrayOutputStream.write(newline);
            }
            for (var entry: properties.entrySet()) {
                byteArrayOutputStream.write(("--" + boundary).getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"").getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(newline);
                byteArrayOutputStream.write(entry.getValue().getBytes(this.CHARSET));
                byteArrayOutputStream.write(newline);
            }
            byteArrayOutputStream.write(("--" + boundary + "--").getBytes(this.CHARSET));
        } catch (IOException err) {
            // Ignore?
        }

        return byteArrayOutputStream;
    }

}