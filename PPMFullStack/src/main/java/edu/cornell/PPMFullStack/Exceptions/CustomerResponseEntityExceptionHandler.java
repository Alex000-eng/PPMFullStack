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

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(
        ProjectNotFoundException projectNotFoundException,
        WebRequest request) {

        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse= new ProjectNotFoundExceptionResponse(
            projectNotFoundException.getMessage());

        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlreadyExistException(
        UserNameAlreadyExistsException userNameAlreadyExistsException,
        WebRequest request) {

        UserNameAlreadyExistsResponse userNameAlreadyExistsResponse= new UserNameAlreadyExistsResponse(
            userNameAlreadyExistsException.getMessage());

        return new ResponseEntity<>(userNameAlreadyExistsResponse, HttpStatus.BAD_REQUEST);
    }
}
