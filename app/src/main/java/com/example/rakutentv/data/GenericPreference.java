package com.example.rakutentv.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class GenericPreference<T> {

    /**
     * Storage the pair key-object in preference
     *
     * @param ctx
     * @param key
     * @param objeto
     */
    public static void setPreferences(Context ctx, String key, Object objeto) {

        if (ctx != null && key != null) {

            SharedPreferences prefs = new ObscuredSharedPreferences(ctx,
                    ctx.getSharedPreferences("preferencias",
                            Context.MODE_PRIVATE));

            Editor ed = prefs.edit();
            if (objeto instanceof String) {
                ed.putString(key, (String) objeto);
            } else if (objeto instanceof Boolean) {
                ed.putBoolean(key, (Boolean) objeto);
            } else if (objeto instanceof Integer) {
                ed.putInt(key, (Integer) objeto);
            } else if (objeto instanceof Long) {
                ed.putLong(key, (Long) objeto);
            } else if (objeto instanceof Float) {
                ed.putFloat(key, (Float) objeto);
            } else {
                String json = null;
                if (objeto != null) {
                    json = gsonBuilder().create().toJson(objeto);
                }
                ed.putString(key, json);
            }

            ed.apply();
        }
    }

    // Get the stored object associate with the key string
    @SuppressWarnings("rawtypes")
    public static Object getPreferences(Context ctx, String key, Type clase) {

        if (ctx != null && key != null && clase != null) {

            SharedPreferences prefs = new ObscuredSharedPreferences(ctx,
                    ctx.getSharedPreferences("preferencias",
                            Context.MODE_PRIVATE));

            try {
                if (clase.equals(String.class)) {
                    return prefs.getString(key, "");
                } else if (clase.equals(Boolean.class)) {
                    return prefs.getBoolean(key, false);
                } else if (clase.equals(Integer.class)) {
                    return prefs.getInt(key, -1);
                } else if (clase.equals(Long.class)) {
                    return prefs.getLong(key, -1);
                } else if (clase.equals(Float.class)) {
                    return prefs.getFloat(key, -1);
                } else {

                    return gsonBuilder().create()
                            .fromJson(prefs.getString(key, ""), clase);
                }
            } catch (Exception e) {

                e.printStackTrace();

            }
        }
        return null;

    }

    /**
     * Remove stored object that it is associated with key param
     *
     * @param ctx
     * @param key
     */
    public static void removePreference(Context ctx, String key) {

        setPreferences(ctx, key, null);

    }

    private static GsonBuilder gsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonElement json, Type arg1,
                                    JsonDeserializationContext arg2) throws JsonParseException {

                if (json.getAsString() != null
                        && "".compareTo(json.getAsString()) != 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(json.getAsString()));
                    return calendar.getTime();
                } else {
                    return null;
                }
            }

        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

            @Override
            public JsonElement serialize(Date arg0, Type arg1,
                                         JsonSerializationContext arg2) {

                return new JsonPrimitive(arg0.getTime());
            }

        });

        return builder;

    }

}
