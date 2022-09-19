package com.lvv.ttimpex2.utils.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Department;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Vitalii Lypovetskyi
 */
public class DayOffDeserializer extends StdDeserializer<DayOff> {
    private static final long serialVersionUID = 1L;

    public DayOffDeserializer() {
        this(null);
    }

    public DayOffDeserializer(Class<DayOff> t) {
        super(t);
    }

    @Override
    public DayOff deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        DayOff dayOff = new DayOff();
        dayOff.setDate(LocalDate.parse(node.get("date").asText()));
        dayOff.setDayOff(node.get("off").asBoolean());

        return dayOff;
    }
}
