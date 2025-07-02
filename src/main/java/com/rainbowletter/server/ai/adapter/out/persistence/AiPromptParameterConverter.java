package com.rainbowletter.server.ai.adapter.out.persistence;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
class AiPromptParameterConverter implements AttributeConverter<List<Parameter>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final List<Parameter> parameters) {
        return String.join(
            DELIMITER,
            parameters.stream()
                .map(Parameter::name)
                .toList()
        );
    }

    @Override
    public List<Parameter> convertToEntityAttribute(final String parameters) {
        return Arrays.stream(parameters.split(DELIMITER))
            .map(Parameter::get)
            .toList();
    }

}
