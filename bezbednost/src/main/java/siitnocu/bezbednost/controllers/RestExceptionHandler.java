package siitnocu.bezbednost.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import siitnocu.bezbednost.exception.ResourceConflictException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleException(RuntimeException runtimeException) {
        return new ResponseEntity(runtimeException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = ResourceConflictException.class)
    public ResponseEntity handleException(ResourceConflictException resourceConflictException) {
        return new ResponseEntity(resourceConflictException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity handleException(AccessDeniedException accessDeniedException) {
        return new ResponseEntity(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN);
    }
}
