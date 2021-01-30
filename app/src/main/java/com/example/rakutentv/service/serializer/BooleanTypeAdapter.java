package com.example.rakutentv.service.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BooleanTypeAdapter implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonBoolean = json.getAsString();
        Boolean res = false;
        if ("1".equals(jsonBoolean)) {
            res = true;
        }
        return res;
    }

    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
