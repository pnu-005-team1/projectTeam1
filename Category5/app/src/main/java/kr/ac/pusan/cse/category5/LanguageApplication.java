package kr.ac.pusan.cse.category5;

import android.app.Application;
import android.content.Context;

import kr.ac.pusan.cse.category5.Helpers.LocHelper;

public class LanguageApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocHelper.onAttach(base,"en"));
    }
}
