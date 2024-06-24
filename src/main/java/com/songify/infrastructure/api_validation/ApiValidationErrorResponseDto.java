package com.songify.infrastructure.api_validation;

import java.util.List;

record ApiValidationErrorResponseDto(List<String> messages) {

}
