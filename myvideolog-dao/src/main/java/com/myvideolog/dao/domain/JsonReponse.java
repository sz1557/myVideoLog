package com.myvideolog.dao.domain;

public class JsonReponse<T> {
    private String code;
    private String msg;
    private T data;

    public JsonReponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonReponse(T data) {
        this.data = data;
        msg = "success";
        code = "0";
    }

    public static JsonReponse<String> success(){
        return new JsonReponse<>(null);
    }

    public static JsonReponse<String> success(String data){
        return new JsonReponse<>(data);
    }

    public static JsonReponse<String> fail(){
        return new JsonReponse<>("1", "fail");
    }

    public static JsonReponse<String> fail(String code, String msg){
        return new JsonReponse<>(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
