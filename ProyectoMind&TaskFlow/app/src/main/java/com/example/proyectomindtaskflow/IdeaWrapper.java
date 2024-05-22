package com.example.proyectomindtaskflow;

import org.json.JSONException;
import org.json.JSONObject;

public class IdeaWrapper {
    private JSONObject jsonObject;
    private String title;

    public IdeaWrapper(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("titulo_idea");
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return title;
    }
}
