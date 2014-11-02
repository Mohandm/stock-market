package com.misys.stockmarket.platform.web;

public class ResponseMessage {
    public enum Type {
        success, warn, error, info, danger;
    }

    private final Type type;
    private final String text;
    private String id;

    public ResponseMessage(Type type, String text) {
        this.type = type;
        this.text = text;
    }
    
    public ResponseMessage(Type type, String text, String id) {
        this.type = type;
        this.text = text;
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }
}