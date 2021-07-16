package uk.co.crunch.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonSerialisationUnitTest {
    private XmlMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Test
    void deserializeWithUnwrap() throws JsonProcessingException {
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

        String serialised = """
                <?xml version="1.0" encoding="UTF-8"?>
                <ignored_root_element>
                    <person>
                        <first_name>John</first_name>
                        <last_name>Doe</last_name>
                    </person>
                </ignored_root_element>
                """;

        Person deserialised = objectMapper.readValue(serialised, Person.class);

        // fails with version 2.12.3, actual value is null
        assertEquals("John", deserialised.getFirstName());
        assertEquals("Doe", deserialised.getLastName());
    }

    @Test
    void deserializeWithoutUnwrap() throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);

        String serialised = """
                <?xml version="1.0" encoding="UTF-8"?>
                <person>
                    <first_name>John</first_name>
                    <last_name>Doe</last_name>
                </person>
                """;

        Person deserialised = objectMapper.readValue(serialised, Person.class);

        assertEquals("John", deserialised.getFirstName());
        assertEquals("Doe", deserialised.getLastName());
    }
}