package it.disco.unimib.GameBook.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class Game extends Application {
    SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newValue = prefs.getString(Constants.NIGHT, "1");

        switch (newValue){
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                break;
        }
    }
}
