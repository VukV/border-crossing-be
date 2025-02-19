package vukv.bordercrossingbe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vukv.bordercrossingbe.exception.exceptions.AuthorizationException;
import vukv.bordercrossingbe.exception.exceptions.BadRequestException;
import vukv.bordercrossingbe.exception.exceptions.ForbiddenException;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(errorMessage));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        HttpStatus status = getResponseStatus(exception.getClass());
        return ResponseEntity.status(status).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbidden(ForbiddenException exception) {
        log.error(exception.getMessage(), exception);
        HttpStatus status = getResponseStatus(exception.getClass());
        return ResponseEntity.status(status).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException exception) {
        log.error(exception.getMessage(), exception);
        HttpStatus status = getResponseStatus(exception.getClass());
        return ResponseEntity.status(status).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorization(AuthorizationException exception) {
        log.error(exception.getMessage(), exception);
        HttpStatus status = getResponseStatus(exception.getClass());
        return ResponseEntity.status(status).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionMessage("You don't have permission to access this resource"));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException exception) {
        log.error(exception.getMessage(), exception);
        String errorMessage = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(errorMessage));
    }

    private HttpStatus getResponseStatus(Class<? extends Throwable> exceptionClass) {
        ResponseStatus responseStatus = exceptionClass.getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
