package jp.kobeu.cs27.localEvent.application.controller.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.ConstraintViolationException;
import jp.kobeu.cs27.localEvent.configuration.exception.*;
import static jp.kobeu.cs27.localEvent.configuration.exception.ErrorCode.*;

@RestControllerAdvice("jp.kobeu.cs27.localEvent.application.controller.rest")
public class RestControllerErrorHandler {

    /**
     * ValidationExceptionをBAD_REQUEST(400)で返す
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleBadRequestException(ValidationException ex) {

        return ResponseCreator.fail(
                ex.getCode(),
                ex,
                null);
    }

    /**
     * コントローラーの引数のバリデーションエラーをBAD_REQUEST(400)で返す
     */
    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleBadRequestException(Exception ex) {

        return ResponseCreator.fail(
                CONTROLLER_VALIDATION_ERROR,
                ex,
                null);
    }

    /**
     * コントローラーで受け取れない不正なリクエストをBAD_REQUEST(400)で返す
     */
    @ExceptionHandler({ HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleControllerException(Exception ex) {

        return ResponseCreator.fail(
                CONTROLLER_REJECTED,
                ex,
                null);
    }

    /**
     * その他の例外をINTERNAL_SERVER_ERROR(500)で返す
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleOtherException(Exception ex) {

        return ResponseCreator.fail(
                OTHER_ERROR,
                ex,
                null);
    }

}
