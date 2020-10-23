package com.inzent.sbp.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(AppException.class)
    public ErrorResponse appException(AppException e, WebRequest request, HttpServletResponse response) {
        ErrorResponse appError = new ErrorResponse(e.getErrorCode());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return appError;
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse resourceNotFoundException(NotFoundException ex, WebRequest request, HttpServletResponse response) {
        ErrorResponse message = new ErrorResponse(ErrorCode.NOT_FOUND);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return message;
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse globalExceptionHandler(RuntimeException ex, WebRequest request, HttpServletResponse response) {
        ErrorResponse message = new ErrorResponse(
                ErrorCode.GENERAL.value(),
                ex.getMessage(),
                request.getDescription(false));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(message.toString());
        return message;
    }
}
