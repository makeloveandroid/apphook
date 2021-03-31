package com.ks.core.callaction;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

public class StartActivityParameter {


    /**
     * activityName : com.yxcorp.gifshow.corona.detail.CoronaDetailActivity
     * intentParameter : {"photoId":"33603416939","serverExpTag":"feed_photo|5225582945367688662|0|1_u/2000131441679360146_bco0"}
     */

    private String activityName;
    private String intentParameter;
    private boolean isSchema = false;

    public Map<String, String> buildIntentParameter() {
        try {
            return new Gson().fromJson(intentParameter, Map.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getIntentParameter() {
        return intentParameter;
    }

    public void setIntentParameter(String intentParameter) {
        this.intentParameter = intentParameter;
    }

    public boolean isSchema() {
        return isSchema;
    }

    public void setSchema(boolean schema) {
        isSchema = schema;
    }
}
