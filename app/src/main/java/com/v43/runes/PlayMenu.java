package com.v43.runes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PlayMenu extends Activity implements View.OnClickListener {
    private static final String LOG_TAG = MainMenu.LOG_BASE + "_MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);

        Button playerVsAIButton = (Button) findViewById(R.id.play_menu_player_vs_ai_button);
        playerVsAIButton.setOnClickListener(this);
        Button sameScreenNormalButton = (Button) findViewById(R.id.play_menu_same_screen_normal_button);
        sameScreenNormalButton.setOnClickListener(this);
        Button onlineNormalButton = (Button) findViewById(R.id.play_menu_online_normal_button);
        onlineNormalButton.setOnClickListener(this);
        Button onlineHardcoreButton = (Button) findViewById(R.id.play_menu_online_hardcore_button);
        onlineHardcoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Log.d(LOG_TAG, "onClick");
        int id = v.getId();

        switch (id){
            case R.id.play_menu_player_vs_ai_button:
                Intent i = new Intent(this, PlayerVsAI.class);
                startActivity(i);
                break;
            case R.id.play_menu_same_screen_normal_button:
                break;
            case R.id.play_menu_online_normal_button:
                break;
            case R.id.play_menu_online_hardcore_button:
                break;
        }

    }
}
