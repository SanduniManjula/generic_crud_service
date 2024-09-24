package org.example.response;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Date timestamp;

    public ApiResponse() {
        this.timestamp = new Date();
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
    }
    public boolean isSuccess() {
        return success;
    }


}
