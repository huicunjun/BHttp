package com.ldw.bhttp.param;


public enum Method {

    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    ;

    public boolean isGet() {
        return name().equals("GET");
    }

    public boolean isPost() {
        return name().equals("POST");
    }

    public boolean isHead() {
        return name().equals("HEAD");
    }

    public boolean isPut() {
        return name().equals("PUT");
    }

    public boolean isPatch() {
        return name().equals("PATCH");
    }

    public boolean isDelete() {
        return name().equals("DELETE");
    }
}
