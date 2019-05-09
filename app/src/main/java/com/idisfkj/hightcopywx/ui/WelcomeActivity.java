package com.idisfkj.hightcopywx.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.loginandregister.LoginActivity;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.util.SPUtils;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (SPUtils.getString("userName", "") == "" &&
                        SPUtils.getString("userPhone", "") == "") {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    finish();
                } else {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    finish();
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {

    }
}
