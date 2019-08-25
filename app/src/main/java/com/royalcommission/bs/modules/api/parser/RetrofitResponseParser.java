package com.qanawat.modules.api.parser;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RetrofitResponseParser {


    /**
     * For parsing List of object into required model class
     */
    public static <T> List<T> convertInstanceOfObjectList(List<Object> object, Class<T> desiredClass) {
        List<T> transformedList = new ArrayList<>();
        if (object != null) {
            for (Object result : object) {
                String json = new Gson().toJson(result);
                T model = new Gson().fromJson(json, desiredClass);
                transformedList.add(model);
            }
        }
        return transformedList;
    }

    /**
     * For parsing object into required model class
     */
    public static <T> T convertInstanceOfObject(Object object, Class<T> desiredClass) {
        if (object != null) {
            try {
                String json = new Gson().toJson(object);
                return new Gson().fromJson(json, desiredClass);
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

}
