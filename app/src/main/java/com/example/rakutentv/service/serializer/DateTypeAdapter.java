
package com.example.rakutentv.service.serializer;


import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

    final private static String[] formats = {
            "dd/MM/yyyy"
    };


    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonDate = json.getAsString();
        Date result = null;
        if(jsonDate != null && !"".equals(jsonDate))
        try {
            result = new SimpleDateFormat(formats[0]).parse(jsonDate);
        } catch (Exception e) {
            Log.d("ParseError", e.getMessage());
        }
        return result;
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        return new JsonPrimitive(format.format(src));
    }
}
