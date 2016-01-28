package com.v43.runes;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainMenu extends Activity implements View.OnClickListener {
    public static final String LOG_BASE = "Runes";
    private static final String LOG_TAG = LOG_BASE + "_MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        LinearLayout menuLayout = (LinearLayout) findViewById(R.id.main_menu_layout);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_screen_fadein);
        menuLayout.startAnimation(animFadeIn);

        ImageView appName = (ImageView) findViewById(R.id.main_menu_app_name);
        appName.setImageAlpha(1);

        Button playButton = (Button) findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(this);
        Button statsButton = (Button) findViewById(R.id.main_menu_stats_button);
        statsButton.setOnClickListener(this);
        Button settingsButton = (Button) findViewById(R.id.main_menu_settings_button);
        settingsButton.setOnClickListener(this);
        Button aboutButton = (Button) findViewById(R.id.main_menu_about_button);
        aboutButton.setOnClickListener(this);
        Button exitButton = (Button) findViewById(R.id.main_menu_exit_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        Log.d(LOG_TAG, "onClick");
        int id = v.getId();

        switch (id){
            case R.id.main_menu_play_button:
                Intent i = new Intent(this, PlayMenu.class);
                startActivity(i);
                break;
            case R.id.main_menu_stats_button:
                break;
            case R.id.main_menu_settings_button:
                break;
            case R.id.main_menu_about_button:
                i = new Intent(this, About.class);
                startActivity(i);
                break;
            case R.id.main_menu_exit_button:
                finish();
        }
    }
}
