package com.example.foody;

import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
//    public static String getAuthToken(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("auth_token", Context.MODE_PRIVATE);
//        String authToken = sharedPreferences.getString("token", "");
//        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
//        return authToken;
//    }
//
//    public static void savePremiumStatus(Context context, boolean premium) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("premium_status", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("is_premium", premium);
//        editor.apply();
//    }
//
//    public static String getPremiumStatus(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("premium_status", Context.MODE_PRIVATE);
//        String isPremium = sharedPreferences.getString("is_premium", "");
//        Log.d("Premium", "status: " + isPremium); // Tambahkan log ini
//        return isPremium;
//    }
//
//    public static String getEmailVerifiedStatus(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("email_status", Context.MODE_PRIVATE);
//        String isVerified = sharedPreferences.getString("is_email_verified", "");
//        Log.d("Email", "verified: " + isVerified); // Tambahkan log ini
//        return isVerified;
//    }

//    public static void saveAuthToken(Context context, String token) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("auth_token", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", token);
//        editor.apply();
//    }
//
//    public static void saveLoginStatus(Context context, boolean status) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("is_logged_in", status);
//        editor.apply();
//    }
//
//    public static void saveEmailVerifiedStatus(Context context, boolean status) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("email_status", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("is_email_verified", status);
//        editor.apply();
//    }
//
//    public static boolean isLoggedIn(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean("is_logged_in", false);
//    }
}
