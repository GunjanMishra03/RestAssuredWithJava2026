package org.example.tests.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class JsonDataReader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonDataReader() {
    }

    public static <T> List<T> readList(String resourcePath, TypeReference<List<T>> typeReference) {
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Test data resource not found: " + resourcePath);
            }
            List<T> values = OBJECT_MAPPER.readValue(input, typeReference);
            if (values.isEmpty()) {
                throw new IllegalStateException("Test data resource contains no records: " + resourcePath);
            }
            return values;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test data resource: " + resourcePath, exception);
        }
    }
}
