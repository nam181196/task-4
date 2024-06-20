package com.example.demo2;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse {
    private boolean status;
    private String message;
    private int code;
    private List<User> data;

    public ApiResponse(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public ApiResponse(boolean status, String message, int code, List<User> data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    // Getters and setters
}
