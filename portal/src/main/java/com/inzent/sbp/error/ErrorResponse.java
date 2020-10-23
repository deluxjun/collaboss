package com.inzent.sbp.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class ErrorResponse {

    private int code;
    private Date timestamp = new Date();
    private String message;
    private String description;

    public ErrorResponse(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.value();
        this.message = errorCode.getMessage();
    }

}
