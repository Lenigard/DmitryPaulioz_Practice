package com.company.message;

public class Message {
    String id;
    String author;
    String timestamp;
    String message;

    public Message() {
        id = "";
        author = "";
        timestamp = "";
        message = "";
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getAuthor() + " [" + timestamp + "], " + " [" + id + "], " + "======> " + getMessage() + "\n";
    }
}

