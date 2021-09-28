package com.dimelo.sampleapp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RcSourceModel {
    private String domainName;
    String apiSecret;
    String name;
    String description;
    String hostname;
    boolean isSelected = false;
    public static RcSourceModel selectedObject;
    public static ArrayList<RcSourceModel> listData;

    public RcSourceModel getSelectedObject(Context context) {
        int index = 0;
        String dataFromJson = RcConfig.readJsonFile(context);
        String rcSourceValue = RcConfig.getStringValueFromSharedPreference(context, RcConfig.RC_SOURCE_NAME);
        listData = jsonToArrayList(dataFromJson, RcSourceModel.class);

        if (rcSourceValue == null) {
            selectedObject = listData.get(index);
        } else {
            selectedObject = objectFromString(rcSourceValue);
            index = findRcSourceBySecret(selectedObject.apiSecret);
        }
        listData.get(index).isSelected = true;
        selectedObject.isSelected = true;
        return selectedObject;
    }

    int findRcSourceBySecret(String secret) {
        for (RcSourceModel rc : listData) {
            if (rc.apiSecret.equals(secret)) {
                return listData.indexOf(rc);
            }
        }
        return -1;
    }

    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();

        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    public static <T> String objectToJson(T rcModel) {
        Gson gson = new Gson();
        return gson.toJson(rcModel);
    }

    public static RcSourceModel objectFromString(String rcSourceValue) {
        Gson gson = new Gson();
        return gson.fromJson(rcSourceValue, RcSourceModel.class);
    }
}
