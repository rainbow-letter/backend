package com.rainbowletter.server.ai.application.port.in;

public interface UpdateAiConfigUseCase {

    void updateConfig(UpdateAiConfigCommand command);

    void updatePetInitiatedLetterConfig(UpdateAiConfigCommand command);

}
