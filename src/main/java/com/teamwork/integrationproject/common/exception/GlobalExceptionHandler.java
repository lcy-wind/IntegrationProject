package com.teamwork.integrationproject.common.exception;

import com.teamwork.integrationproject.utils.log.LogHelper;
import com.teamwork.integrationproject.utils.resposnse.BaseResponse;
import com.teamwork.integrationproject.utils.resposnse.ResultCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Author Anyho(wuh@infoepoh.com)
 * Time   2019/12/10 16:56 星期二
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse handleError(MissingServletRequestParameterException e)
    {
        LogHelper.error(this, "Missing Request Parameter", e);
        String message = String.format("Missing Request Parameter: %s", e.getParameterName());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_MISS)
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse handleError(MethodArgumentTypeMismatchException e)
    {
        LogHelper.error(this, "Method Argument Type Mismatch", e);
        String message = String.format("Method Argument Type Mismatch: %s", e.getName());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_TYPE_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleError(MethodArgumentNotValidException e)
    {
        LogHelper.error(this, "Method Argument Not Valid", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        LogHelper.error(this, "field:{}, message:{}", error.getField(), error.getDefaultMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(error.getDefaultMessage())
                .build();
    }

//    @ExceptionHandler(FeignException.class)
//    public BaseResponse handleError(FeignException e)
//    {
//        LogHelper.error(this, "Feign Exception", e);
//        return BaseResponse
//                .builder()
//                .code(ResultCode.FAILURE)
//                .message(e.getMessage())
//                .build();
//    }

    @ExceptionHandler(BindException.class)
    public BaseResponse handleError(BindException e)
    {
        LogHelper.error(this, "Bind Exception", e);
        FieldError error = e.getFieldError();
        LogHelper.error(this, "field:{}, message:{}", error.getField(), error.getDefaultMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_BIND_ERROR)
                .message(error.getDefaultMessage())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handleError(ConstraintViolationException e)
    {
        LogHelper.error(this, "Constraint Violation", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        LogHelper.error(this, "path:{}, message:{}", path, violation.getMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(violation.getMessage())
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse handleError(NoHandlerFoundException e)
    {
        LogHelper.error(this, "404 Not Found", e);
        return BaseResponse
                .builder()
                .code(ResultCode.NOT_FOUND)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse handleError(HttpMessageNotReadableException e)
    {
        LogHelper.error(this, "Message Not Readable", e);
        return BaseResponse
                .builder()
                .code(ResultCode.MSG_NOT_READABLE)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handleError(HttpRequestMethodNotSupportedException e)
    {
        LogHelper.error(this, "Request Method Not Supported", e);
        return BaseResponse
                .builder()
                .code(ResultCode.METHOD_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse handleError(HttpMediaTypeNotSupportedException e)
    {
        LogHelper.error(this, "Media Type Not Supported", e);
        return BaseResponse
                .builder()
                .code(ResultCode.MEDIA_TYPE_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    public BaseResponse handleError(ServiceException e)
    {
        LogHelper.error(this, "Service Exception", e);
        return BaseResponse
                .builder()
                .code(e.getResultCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleError(RuntimeException e)
    {
        LogHelper.error(this, "Service Exception", e);
        return BaseResponse
                .builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = Throwable.class)
    public BaseResponse handleError(Throwable t)
    {
        LogHelper.error(this, "Internal Server Error", t);
        return BaseResponse.builder()
                .code(ResultCode.INTERNAL_SERVER_ERROR)
                .message(t.getMessage())
                .build();
    }
}
