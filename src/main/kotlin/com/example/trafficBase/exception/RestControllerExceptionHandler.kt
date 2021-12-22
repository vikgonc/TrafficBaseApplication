package com.example.trafficBase.exception

import com.example.trafficBase.model.Car
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.format.DateTimeParseException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class RestControllerExceptionHandler {
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun nullFieldResponse(): ResponseEntity<Car> {
        return ResponseEntity
            .status(454)
            .body(null)
    }

    @ExceptionHandler(value = [DateTimeParseException::class])
    fun invalidDateFormatResponse(): ResponseEntity<List<Car>> {
        return ResponseEntity
            .status(455)
            .body(listOf())
    }

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun missedParamResponse(): ResponseEntity<Car> {
        return ResponseEntity
            .status(457)
            .body(null)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun invalidFieldInBodyResponse(): ResponseEntity<Car> {
        return ResponseEntity
            .status(458)
            .body(null)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun invalidFieldAsParamResponse(): ResponseEntity<Car> {
        return ResponseEntity
            .status(458)
            .body(null)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun invalidSortParamResponse(): ResponseEntity<List<Car>> {
        return ResponseEntity
            .status(458)
            .body(listOf())
    }
}