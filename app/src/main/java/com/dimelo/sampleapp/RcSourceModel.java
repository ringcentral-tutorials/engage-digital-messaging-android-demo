package com.dimelo.sampleapp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RcSourceModel {
    String domaineName;
    String domaineSecret;
    String name;
    String description;
    String hostname;
    boolean isSelected = false;
    public static RcSourceModel selectedObject;
    public static ArrayList<RcSourceModel> listData;

    public RcSourceModel getSelectedObject(Context context) {
        int index = 0;
        String dataFromJson = RcConfig.readJsonFile(context);
        String rcConfigValue = RcConfig.getStringValueFromSharedPreference(context, RcConfig.RC_CONF_NAME);
        listData = jsonToArrayList(dataFromJson, RcSourceModel.class);
        if (rcConfigValue == null) {
            selectedObject = listData.get(index);
        } else {
            selectedObject = objectFromString(rcConfigValue);
            index = findRcSourceBySecret(selectedObject.domaineSecret);
        }
        listData.get(index).isSelected = true;
        selectedObject.isSelected = true;
        return selectedObject;
    }

    int findRcSourceBySecret(String secret) {
        for (RcSourceModel rc : listData) {
            if (rc.domaineSecret.equals(secret)) {
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

    public static <RcConf> String objectToJson(RcConf rcConf) {
        Gson gson = new Gson();
        return gson.toJson(rcConf);
    }

    public static RcSourceModel objectFromString(String rcConfValue) {
        Gson gson = new Gson();
        return gson.fromJson(rcConfValue, RcSourceModel.class);
    }
}
