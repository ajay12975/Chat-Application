package com.example.mystorage.util;





import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreference mySingleton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private Context context;

    private boolean isLogin;

    public static SharedPreference getInstance(Context context) {
        if (mySingleton == null) {
            mySingleton = new SharedPreference(context);
        }
        return mySingleton;
    }

    public SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("xtream", Context.MODE_PRIVATE);
        this.context=context;

    }


    public boolean isLogin() {
        return sharedPreferences.getBoolean("ISLOGIN", false);
    }

    public void setLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean("ISLOGIN", isLogin).apply();
    }



    public void saveData(String key, String value)
    {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key)
    {
        if (sharedPreferences!= null)
        {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveInt(String key, int value)
    {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }


    public int getInt(String key)
    {
        if (sharedPreferences!= null)
        {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    public void saveBoolean(String key, boolean value)
    {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getBoolean(String key)
    {
        if (sharedPreferences!= null)
        {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }


    public void clearData()
    {
        sharedPreferences.edit().clear().apply();
    }


    public boolean isSaveActivity()
    {     sharedPreferences= context.getSharedPreferences("saveActivity", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("flag",false);
    }


    public void saveModeValues(String key,boolean mode) {
        prefsEditor = sharedPreferences.edit();

        prefsEditor.putBoolean(key, mode);
        prefsEditor.apply();
    }
    public boolean getSaveMode(String key){
        if (sharedPreferences!= null)
        {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }


}


