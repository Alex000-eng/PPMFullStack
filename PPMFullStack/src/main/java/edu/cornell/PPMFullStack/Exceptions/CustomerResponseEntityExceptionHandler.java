package edu.cornell.PPMFullStack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectException(ProjectIdException projectException,
        WebRequest request) {

        ProjectIdExpectionResponse projectIdExpectionResponse= new ProjectIdExpectionResponse(
            projectException.getMessage());

        return new ResponseEntity<>(projectIdExpectionResponse, HttpStatus.BAD_REQUEST);
    }

}
