package com.ldw.bhttp.param;

/**
 * @date 2020/5/28 19:00
 * @user 威威君
 */
public enum  ParamType {
    Query,
    Form,
    Json,
    Path,
    ;

    public boolean isQuery() {
        return name().equals("Query");
    }

    public boolean isForm() {
        return name().equals("Form");
    }

    public boolean isJson() {
        return name().equals("Json");
    }

    public boolean isPath() {
        return name().equals("Path");
    }

}
