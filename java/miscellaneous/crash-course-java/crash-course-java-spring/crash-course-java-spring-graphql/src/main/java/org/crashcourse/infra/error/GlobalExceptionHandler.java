package org.crashcourse.infra.error;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.crashcourse.infra.dto.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        LOGGER.error("handling ResourceNotFoundException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
    }
  
    @ExceptionHandler(value = IllegalStateException.class)
    public final ResponseEntity<GenericResponse> handleIllegalStateException(IllegalStateException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.CONFLICT.value(), LocalDateTime.now());
        LOGGER.error("handling IllegalStateException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = IOException.class)
    public final ResponseEntity<GenericResponse> handleIOException(IOException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        LOGGER.error("handling IOException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = URISyntaxException.class)
    public final ResponseEntity<GenericResponse> handleURISyntaxException(URISyntaxException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        LOGGER.error("handling URISyntaxException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
//    public final ResponseEntity<GenericResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
//        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.PAYLOAD_TOO_LARGE.value(), LocalDateTime.now());
//        LOGGER.error("handling MaxUploadSizeExceededException...");
//        LOGGER.error(exception.getMessage());
//        return new ResponseEntity<>(genericResponse, HttpStatus.PAYLOAD_TOO_LARGE);
//    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    public final ResponseEntity<GenericResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        LOGGER.error("handling IllegalArgumentException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = ParseException.class)
    public final ResponseEntity<GenericResponse> handleParseException(ParseException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), "", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        LOGGER.error("handling ParseException...");
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }
    
}
