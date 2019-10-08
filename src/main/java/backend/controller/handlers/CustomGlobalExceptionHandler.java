package backend.controller.handlers;

import backend.model.exception.InsufficientFundsException;
import backend.model.exception.MenuNotFoundException;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ServiceNotFoundException.class)
    public ResponseEntity<Object> exception(ServiceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MenuNotFoundException.class)
    public ResponseEntity<Object> exception(MenuNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<Object> exception(InsufficientFundsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
