package com.sso.payload.response;

public class ResponseDTO {
    private String error;
    private String message;
    private Object data;
    public ResponseDTO() {
    }

    public ResponseDTO(String error, String message, Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(Object data) {
        this.message ="success";
        this.data = data;
    }

    public ResponseDTO(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
