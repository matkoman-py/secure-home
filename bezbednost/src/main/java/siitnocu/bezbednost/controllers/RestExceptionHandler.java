package siitnocu.bezbednost.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import siitnocu.bezbednost.exception.ResourceConflictException;
import siitnocu.bezbednost.services.CustomLogger;

@ControllerAdvice
public class RestExceptionHandler {
	
	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);
	
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleException(RuntimeException runtimeException) {
		logger.error(customLogger.error(runtimeException.getMessage()));
        return new ResponseEntity(runtimeException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = ResourceConflictException.class)
    public ResponseEntity handleException(ResourceConflictException resourceConflictException) {
		logger.error(customLogger.error(resourceConflictException.getMessage()));

        return new ResponseEntity(resourceConflictException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity handleException(AccessDeniedException accessDeniedException) {
		logger.error(customLogger.error(accessDeniedException.getMessage()));
        return new ResponseEntity(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN);
    }
}
