package com.vegetableshoppingbe.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse{

    private Date timestamp;
    private String message;

    public ErrorResponse(String message) {
        this.timestamp = new Date();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
