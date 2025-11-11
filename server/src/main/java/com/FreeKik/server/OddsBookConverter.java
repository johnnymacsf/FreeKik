package com.FreeKik.server;

import com.FreeKik.server.models.OddsBook;
import com.FreeKik.server.models.Odds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.lang.reflect.Type;
import java.util.HashMap;

@Converter
public class OddsBookConverter implements AttributeConverter<OddsBook, String> {
    private final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(OddsBook attribute) {
        if (attribute == null) return null;
        // Serialize the internal map, not the whole object
        return gson.toJson(attribute.getBook());
    }

    @Override
    public OddsBook convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return new OddsBook();
        Type type = new TypeToken<HashMap<String, Odds>>() {}.getType();
        HashMap<String, Odds> map = gson.fromJson(dbData, type);
        OddsBook book = new OddsBook();
        if (map != null) {
            map.forEach(book::addOdds);
        }
        return book;
    }
}