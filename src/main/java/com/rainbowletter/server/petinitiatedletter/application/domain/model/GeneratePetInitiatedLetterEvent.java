package com.rainbowletter.server.petinitiatedletter.application.domain.model;

import java.util.List;

public record GeneratePetInitiatedLetterEvent(List<Long> letterIds) {
}
