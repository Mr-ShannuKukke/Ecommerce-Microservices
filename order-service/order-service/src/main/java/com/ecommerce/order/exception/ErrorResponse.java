package com.ecommerce.order.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

//Error Response DTO

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path){
        this.timestamp=timestamp;
        this.status=status;
        this.error=error;
        this.message=message;
        this.path=path;
    }

    //Getters and setters

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp=timestamp;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setStatus(int status){
        this.status=status;
    }

    public int getStatus(){
        return status;
    }

    public void setError(String error){
        this.error=error;
    }

    public String getError(){
        return error;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }

    public void setPath(String path){
        this.path=path;
    }

    public String getPath(){
        return path;
    }
}
