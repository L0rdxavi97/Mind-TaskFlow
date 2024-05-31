package com.proyectomindtaskflow;

import org.json.JSONException;
import org.json.JSONObject;

public class IdeaWrapper {
    private JSONObject jsonObject;
    private String title,description;

    public IdeaWrapper(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("titulo_idea");
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
