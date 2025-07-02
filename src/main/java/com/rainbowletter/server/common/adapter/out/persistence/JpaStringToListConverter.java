package com.rainbowletter.server.common.adapter.out.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;

@Converter
public class JpaStringToListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final List<String> personalities) {
        return String.join(DELIMITER, personalities);
    }

    @Override
    public List<String> convertToEntityAttribute(final String personalities) {
        return Arrays.stream(personalities.split(DELIMITER))
            .filter(StringUtils::hasText)
            .toList();
    }

}
