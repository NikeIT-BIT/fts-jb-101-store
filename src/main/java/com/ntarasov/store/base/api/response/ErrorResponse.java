package com.ntarasov.store.base.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter

public class ErrorResponse extends OkResponse<String> {
    private String error;
    private HttpStatus httpStatus;

    public static ErrorResponse of(String errorString, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResult(null);
        errorResponse.setStatus(Status.ERROR);
        errorResponse.setError(errorString);
        errorResponse.setHttpStatus(httpStatus);
        return errorResponse;
    }
}
