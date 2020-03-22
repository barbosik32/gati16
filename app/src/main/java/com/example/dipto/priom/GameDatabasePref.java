package com.example.dipto.priom;

import android.content.Context;
import android.content.SharedPreferences;

public class GameDatabasePref {
    private static String gut = "16gut";
    private SharedPreferences preferences;

    public GameDatabasePref(Context context){
        String NAME = "16gut";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setGut(String data){
        preferences.edit().putString(GameDatabasePref.gut, data).apply();
    }

    public String getGut(){
        return preferences.getString(gut, "");
    }
}
