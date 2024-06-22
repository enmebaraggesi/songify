package com.songify.infrastructure.controller.api_validation;

import java.util.List;

record ApiValidationErrorResponseDto(List<String> messages) {

}
