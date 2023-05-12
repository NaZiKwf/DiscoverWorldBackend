package ua.nure.akolovych.discoverworld.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.nure.akolovych.discoverworld.dto.error.ErrorResponceDto;
import ua.nure.akolovych.discoverworld.exception.EntityExistsException;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DiscoverWorldExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException) {
        log.warn(entityNotFoundException.getMessage());
        return new ResponseEntity<>(
                new ErrorResponceDto(
                        HttpServletResponse.SC_NOT_FOUND,
                        entityNotFoundException.getMessage(),
                        Instant.now().toEpochMilli()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(
            InvalidRequestException badRequestException) {
        log.warn(badRequestException.getMessage());
        return new ResponseEntity<>(
                new ErrorResponceDto(
                        HttpServletResponse.SC_BAD_REQUEST,
                        badRequestException.getMessage(),
                        Instant.now().toEpochMilli()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(
            EntityExistsException entityExistsException) {
        log.warn(entityExistsException.getMessage());
        return new ResponseEntity<>(
                new ErrorResponceDto(
                        HttpServletResponse.SC_FOUND,
                        entityExistsException.getMessage(),
                        Instant.now().toEpochMilli()),
                HttpStatus.FOUND);
    }



}
