package kr.ac.pnu.cse.menumapproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import kr.ac.pnu.cse.menumapproject.R;

public class SharedPreferenceUtil {

    private static SharedPreferences sharedPreferences;
    private static String SHARED_PREFERENCE_KEY = "MENU_MASTER_SHARED_PREFERENCE_KEY";
    private static String THEME_KEY = "THEME_KEY";

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static int getThemeResId(Context context) {
        int themeNum = getSharedPreferences(context).getInt(THEME_KEY, 3);

        Log.d("LOG_TAG", "theme num : " + themeNum);

        switch (themeNum) {
            case 1:
                return R.style.AppTheme;
            case 2:
                return R.style.AppThemeRed;
            case 3:
                return R.style.AppThemeOrange;
            case 4:
                return R.style.AppThemeBlue;
            case 5:
                return R.style.AppThemePurple;
        }
        return R.style.AppTheme;
    }

    public static int getPrimaryColorResId(Context context) {
        int themeNum = getSharedPreferences(context).getInt(THEME_KEY, 3);

        Log.d("LOG_TAG", "theme num : " + themeNum);

        switch (themeNum) {
            case 1:
                return R.color.colorPrimary;
            case 2:
                return R.color.colorPrimary2;
            case 3:
                return R.color.colorPrimary3;
            case 4:
                return R.color.colorPrimary4;
            case 5:
                return R.color.colorPrimary5;
        }
        return R.color.colorPrimary3;
    }
    public static void setTheme(Context context, int themeNum) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(THEME_KEY, themeNum);
        editor.apply();
    }
}
