package com.positivehotel.nabusi_server.exception;

import com.positivehotel.nabusi_server.exception.customException.DeletedAlreadyException;
import com.positivehotel.nabusi_server.exception.customException.ExistsAlreadyException;
import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import com.positivehotel.nabusi_server.exception.customException.RestoredAlreadyException;
import com.positivehotel.nabusi_server.exception.customException.InsufficientStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestGlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ExistsAlreadyException.class)
    public ResponseEntity<String> handleExistsAlreadyException(ExistsAlreadyException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DeletedAlreadyException.class)
    public ResponseEntity<String> handleDeletedAlreadyException(DeletedAlreadyException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RestoredAlreadyException.class)
    public ResponseEntity<String> handleRestoredAlreadyException(RestoredAlreadyException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<String>
    // handleValidationExceptions(MethodArgumentNotValidException ex) {
    // StringBuilder errors = new StringBuilder();
    // ex.getBindingResult().getAllErrors().forEach(error ->
    // errors.append(error.getDefaultMessage()).append("\n"));
    // return ResponseEntity.badRequest().body(errors.toString());
    // }

    // @ExceptionHandler(IllegalStateException.class)
    // public ResponseEntity<String>
    // handleIllegalStateException(IllegalStateException ex) {
    // return ResponseEntity
    // .status(HttpStatus.CONFLICT)
    // .body("Conflict error: " + ex.getMessage());
    // }
    //
    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    // return ResponseEntity
    // .status(HttpStatus.BAD_REQUEST) // 적절한 상태 코드 선택
    // .body("Runtime error: " + ex.getMessage());
    // }

}