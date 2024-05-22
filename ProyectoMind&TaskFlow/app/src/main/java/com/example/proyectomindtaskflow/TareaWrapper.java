package com.example.proyectomindtaskflow;

import org.json.JSONException;
import org.json.JSONObject;

public class TareaWrapper {
    private JSONObject jsonObject;
    private String title;

    public TareaWrapper(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("titulo_tarea");
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return title;
    }
}
