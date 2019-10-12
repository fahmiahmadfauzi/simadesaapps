package com.fahmi.simadesav1.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fahmi.simadesav1.MainActivity;
import com.fahmi.simadesav1.penduduk.HomeActivity;

import java.util.HashMap;

public class SessionManager {
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String NIK = "NIK";
    public static String ID_AKUN = "ID_AKUN";
    private final String PREF_NAME = "LOGIN";
    private final String LOGIN = "IS LOGIN";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String nik, String id_akun) {
        editor.putBoolean(LOGIN, true);

        editor.putString(NIK, nik);
        editor.putString(ID_AKUN, id_akun);


        editor.apply();


    }

    public Boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLoggin()) {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NIK, sharedPreferences.getString(NIK, null));
        user.put(ID_AKUN, sharedPreferences.getString(ID_AKUN, null));

        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((HomeActivity) context).finish();
    }

}
