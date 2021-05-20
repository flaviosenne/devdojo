package com.devdojo.Anime.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    private String title;
    private Integer status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
