package com.proyectomindtaskflow;

import org.json.JSONException;
import org.json.JSONObject;

public class TareaWrapper {
    private JSONObject jsonObject;
    private String title,description;

    public TareaWrapper(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("titulo_tarea");
        this.description = jsonObject.getString("descripcion");
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
