package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StringDeserializer implements JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray ja = jsonElement.getAsJsonArray();
        List<String> palabras = new ArrayList<>();
        String word = ja.get(0).getAsString();
        palabras.add(word);
        return palabras;
    }
}
