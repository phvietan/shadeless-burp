package com.nccgroup.loggerplusplus.util;

public class FileObject {
    private String name;
    private byte[] body;

    public FileObject(String name, byte[] body) {
        this.name = name;
        this.body = body;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public byte[] getBody() { return this.body; }
    public void setBody(byte[] body) { this.body = body; }
}
