package com.songify.api_validation;

import java.util.List;

public record ApiValidationErrorResponseDto(List<String> messages) {

}
