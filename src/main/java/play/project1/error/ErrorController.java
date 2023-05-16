package play.project1.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import play.project1.error.exception.order.PointShortageException;

@RestControllerAdvice
public class ErrorController {

    private String getSimpleName(Exception e) {
        return e.getClass().getSimpleName();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PointShortageException.class)
    public ErrorMessage handlerPointShortageException(PointShortageException e) {
        return new ErrorMessage(getSimpleName(e), e.getLocalizedMessage());
    }
}
