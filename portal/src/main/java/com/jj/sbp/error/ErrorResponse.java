package com.jj.sbp.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public ErrorResponse(AppException ex) {
        this.code = ex.getErrorCode().value();
        this.message = ex.getMessage();
        this.description = ex.getDescription();
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            sb.append("\"message\":\"" + this.message + "\"");
            sb.append("\"code\": " + this.code);
            sb.append("}");
            return sb.toString();
        }
    }

}
