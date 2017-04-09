package com.letzunite.applabs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Custom Singleton Shared Preferences class. It exposes different types of interfaces
 * for interaction.
 *
 * @author Akash Patra
 */
public class MySharedPrefs {
    public static final String VM_AUTOMATION_PREFS = "vm_automation_prefs";
    // Keys
    public static final String ACCESS_TOKEN_EXTRA = "access_token";
    private static MySharedPrefs mySharedPrefs;
    private static Gson gson = new Gson();
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    Type typeOfObject = new TypeToken<Object>() {
    }.getType();

    private MySharedPrefs(Context context, String namePreferences, int mode) {
        if (AppUtils.isStringEmpty(namePreferences)) {
            namePreferences = VM_AUTOMATION_PREFS;
        }
        sharedPreferences = context.getSharedPreferences(namePreferences, mode);
        editor = sharedPreferences.edit();
    }

    public static MySharedPrefs getInstance(Context context, String namePreferences, int mode) {
        if (mySharedPrefs == null) {
            mySharedPrefs = new MySharedPrefs(context, namePreferences, mode);
        }
        return mySharedPrefs;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (AppUtils.isStringEmpty(key)) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putString(key, gson.toJson(object));
    }

    private void commit() {
        editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String objValue = sharedPreferences.getString(key, null);
        if (objValue == null) {
            return null;
        } else {
            try {
                return gson.fromJson(objValue, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key" + key + " is instance of other class");
            }
        }
    }

    public void putString(String key, String value) {
        if (AppUtils.isStringEmpty(key)) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        /*if (AppUtils.isStringEmpty(value)) {
            throw new IllegalArgumentException("Value is empty or null");
        }*/
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        String value = sharedPreferences.getString(key, null);
        if (AppUtils.isStringEmpty(value)) {
            return null;
        } else {
            return value;
        }
    }

    public void putBoolean(String key, boolean value) {
        if (AppUtils.isStringEmpty(key)) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putLong(String key, long value) {
        if (AppUtils.isStringEmpty(key)) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, Long.MAX_VALUE);
    }

    public void putFloat(String key, float value) {
        if (AppUtils.isStringEmpty(key)) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0.0f);
    }
}
