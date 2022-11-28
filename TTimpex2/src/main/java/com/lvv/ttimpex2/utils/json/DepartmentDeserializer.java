package com.lvv.ttimpex2.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lvv.ttimpex2.molel.Department;


import java.io.IOException;


public class DepartmentDeserializer extends StdDeserializer<Department> {
    private static final long serialVersionUID = 1L;

    public DepartmentDeserializer() {
        this(null);
    }

    public DepartmentDeserializer(Class<Department> t) {
        super(t);
    }

    @Override
    public Department deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Department feedback = new Department();
        feedback.setId(node.get("id").asText());
        feedback.setName(node.get("name").asText());

        return feedback;
    }
}
