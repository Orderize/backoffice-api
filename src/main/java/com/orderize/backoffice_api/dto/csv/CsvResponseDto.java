package com.orderize.backoffice_api.dto.csv;

import org.springframework.core.io.InputStreamResource;

import java.io.File;

public record CsvResponseDto(
        InputStreamResource resource,
        File file
) {
}
