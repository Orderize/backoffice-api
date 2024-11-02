package com.orderize.backoffice_api.dto.measure;

public record MeasureUnitDto(
    Double amount,
    String unitShort,
    String unitLong
) {}
