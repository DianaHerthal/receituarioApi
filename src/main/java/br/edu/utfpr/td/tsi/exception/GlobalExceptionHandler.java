package br.edu.utfpr.td.tsi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException e) {
        List<String> erros = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        log.warn("Erro de validação: {}", erros);
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Validation failure",
                "details", erros
        ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleMethodValidation(HandlerMethodValidationException e) {
        List<String> erros = e.getAllErrors().stream()
                .map(err -> err.getDefaultMessage())
                .toList();
        log.warn("Erro de validação de método: {}", erros);
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Validation failure",
                "details", erros
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        log.error("Erro interno não tratado", e);
        return ResponseEntity.status(500).body(Map.of(
                "error", "Internal Server Error",
                "message", e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()
        ));
    }
}