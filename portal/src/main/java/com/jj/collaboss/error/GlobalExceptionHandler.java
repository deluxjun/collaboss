package com.jj.collaboss.error;

import com.jj.collaboss.utils.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler
{
//    @Bean
//    public ErrorAttributes errorAttributes() {
//        // Hide exception field in the return object
//        return new DefaultErrorAttributes() {
//            @Override
//            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
//                errorAttributes.remove("exception");
//                return errorAttributes;
//            }
//        };
//    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public ErrorResponse appException(AppException e) {
        ErrorResponse message = new ErrorResponse(e.getErrorCode());
        message.setDescription(e.getDescription());
        log.error("[AppException] " + message.toString() + ", " + e.getDescription(), e);
        return message;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse resourceNotFoundException(NotFoundException ex) {
        ErrorResponse message = new ErrorResponse(ErrorCode.NOT_FOUND);
//        response.setStatus(HttpStatus.NOT_FOUND.value());
        log.error("[NotFoundException] " + message.toString(), ex);
        return message;
    }

    // for all abnormal errors
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    public ErrorResponse globalExceptionHandler(Throwable e, WebRequest request) {
        ErrorResponse message = new ErrorResponse(
                ErrorCode.GENERAL.value(),
                ErrorCode.GENERAL.getMessage(),
                e.getMessage() + "(" + request.getDescription(false) + ")");
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (log.isErrorEnabled()) {
            log.error("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ [Not defined Exception] ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            log.error("[Throwable]" + message.toString(), e);
            log.error("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ [Not defined Exception] ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
        }
        return message;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse validationException(ConstraintViolationException e) {
        ErrorResponse message = new ErrorResponse(ErrorCode.NOT_ACCEPTABLE);
        message.setDescription(Context.getMessage("verification.InvalidValue"));
        log.error("[Validation-Exception] " + message.toString() + ", " + e.getMessage(), e);
        return message;
    }

}
