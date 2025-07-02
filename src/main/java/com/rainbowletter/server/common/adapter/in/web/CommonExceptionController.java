package com.rainbowletter.server.common.adapter.in.web;

import static com.rainbowletter.server.common.util.ExceptionLogUtils.error;
import static com.rainbowletter.server.common.util.ExceptionLogUtils.warn;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.application.port.in.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@WebAdapter
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
class CommonExceptionController {

    private final MessageSource messageSource;

    @ExceptionHandler({RainbowLetterException.class})
    ResponseEntity<ErrorResponse> rainbowLetter(
        final RainbowLetterException exception,
        final HttpServletRequest request
    ) {
        String message = exception.getMessage();
        if (Objects.isNull(exception.getResource())) {
            message = messageSource.getMessage(message, null, message, request.getLocale());
            warn(BAD_REQUEST, request.getRequestURI(), message);
        } else {
            message = messageSource.getMessage(message, new Object[]{exception.getResource()},
                message, request.getLocale());
            warn(
                BAD_REQUEST,
                request.getRequestURI(),
                message,
                exception.getResource()
            );
        }
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity<ErrorResponse> illegalArgument(
        final HttpServletRequest request,
        final IllegalArgumentException exception
    ) {
        final String message = messageSource.getMessage(exception.getMessage(), null,
            exception.getMessage(), request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ErrorResponse> inValid(
        final HttpServletRequest request,
        final ConstraintViolationException exception
    ) {
        final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        final ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
        String message = constraintViolation.getMessage();
        message = messageSource.getMessage(message, null, message, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<ErrorResponse> methodArgumentNotValid(
        final HttpServletRequest request,
        final MethodArgumentNotValidException exception
    ) {
        final FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
        final String field = "%s : %s".formatted(
            fieldError.getField(),
            fieldError.getRejectedValue()
        );
        String message = fieldError.getDefaultMessage();
        assert message != null;
        message = messageSource.getMessage(message, null, message, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, field);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    ResponseEntity<ErrorResponse> methodNotSupported(
        final HttpServletRequest request,
        final HttpRequestMethodNotSupportedException exception
    ) {
        final String requestURI = request.getRequestURI();
        final String message = messageSource.getMessage("not.support.method",
            new Object[]{requestURI, exception.getMethod()}, request.getLocale());
        warn(METHOD_NOT_ALLOWED, requestURI, message);
        return ResponseEntity
            .status(METHOD_NOT_ALLOWED.value())
            .body(ErrorResponse.of(message, METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    ResponseEntity<ErrorResponse> httpMessageNotReadable(
        final HttpServletRequest request,
        final HttpMessageNotReadableException exception
    ) {
        final String message = messageSource
            .getMessage("invalid.request.payload", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
    ResponseEntity<ErrorResponse> invalidPathValue(
        final HttpServletRequest request,
        final Exception exception
    ) {
        final String message = messageSource
            .getMessage("invalid.request.value", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    ResponseEntity<ErrorResponse> missingServletRequestPart(
        final HttpServletRequest request,
        final MissingServletRequestPartException exception
    ) {
        final String message = messageSource
            .getMessage("please.attach.file", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({MultipartException.class, FileUploadException.class})
    ResponseEntity<ErrorResponse> multipart(
        final HttpServletRequest request,
        final Exception exception
    ) {
        final String message = messageSource
            .getMessage("please.check.content-type.form-data", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    ResponseEntity<ErrorResponse> usernameNotFound(
        final HttpServletRequest request,
        final UsernameNotFoundException exception
    ) {
        final String message = messageSource
            .getMessage(exception.getMessage(), null, exception.getMessage(), request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({AccountExpiredException.class})
    ResponseEntity<ErrorResponse> accountExpired(
        final HttpServletRequest request,
        final AccountExpiredException exception
    ) {
        final String message = messageSource.getMessage("account.sleep", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({LockedException.class})
    ResponseEntity<ErrorResponse> locked(
        final HttpServletRequest request,
        final LockedException exception
    ) {
        final String message = messageSource.getMessage("account.locked", null,
            request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({CredentialsExpiredException.class})
    ResponseEntity<ErrorResponse> credentialsExpired(
        final HttpServletRequest request,
        final CredentialsExpiredException exception
    ) {
        final String message = messageSource
            .getMessage("need.account.authentication", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({DisabledException.class})
    ResponseEntity<ErrorResponse> disabled(
        final HttpServletRequest request,
        final DisabledException exception
    ) {
        final String message = messageSource.getMessage("account.leave", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({BadCredentialsException.class})
    ResponseEntity<ErrorResponse> badCredentials(
        final HttpServletRequest request,
        final BadCredentialsException exception
    ) {
        final String message = messageSource
            .getMessage("please.check.email.password", null, request.getLocale());
        warn(BAD_REQUEST, request.getRequestURI(), message, exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(message, BAD_REQUEST));
    }

    @ExceptionHandler({Exception.class})
    ResponseEntity<ErrorResponse> exception(
        final HttpServletRequest request,
        final Exception exception
    ) {
        final String message = messageSource
            .getMessage(exception.getMessage(), null, exception.getMessage(), request.getLocale());
        error(INTERNAL_SERVER_ERROR, request.getRequestURI(), message, exception);
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponse.of(message, INTERNAL_SERVER_ERROR));
    }

}
