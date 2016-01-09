package com.v43.runes;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawRune extends Activity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary runeLibrary;

    private TextView castTime;
    private TextView castScore;
    private TextView castEffectiveness;

    private double timeInitial;

    private int spell;
    private double score;

    Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_rune);

        runeLibrary = GestureLibraries.fromRawResource(this, R.raw.runelibrary);
        if(runeLibrary != null) {
            if(!runeLibrary.load()) {
                Log.e("GestureSample", "Gesture library was not loaded");
                finish();
            }
            else {
                GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gestures);
                gesturesView.addOnGesturePerformedListener(this);
            }
        }

        ImageView spellRune = (ImageView) findViewById(R.id.draw_rune_spell_rune);
        castTime = (TextView) findViewById(R.id.draw_rune_cast_time);
        castScore = (TextView) findViewById(R.id.draw_rune_cast_score);
        castEffectiveness = (TextView) findViewById(R.id.draw_rune_cast_effectiveness);

        // Get the spell value

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            spell = 0;
        } else {
            spell = extras.getInt("spell");
        }

        if (spell == Constants.FIREBALL) {
            spellRune.setImageResource(R.drawable.fireball);
        }
        else if (spell == Constants.PARALYZING_RAY) {
            spellRune.setImageResource(R.drawable.paralyzingray);
        }
        else if (spell == Constants.CURSE) {
            spellRune.setImageResource(R.drawable.curse);
        }
        else if (spell == Constants.ARCANE_SHIELD) {
            spellRune.setImageResource(R.drawable.arcaneshield);
        }
        else if (spell == Constants.HEAL) {
            spellRune.setImageResource(R.drawable.heal);
        }

        timeInitial = System.currentTimeMillis();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = runeLibrary.recognize(gesture);

        if (predictions.size() > 0) {
            String rune = predictions.get(0).name;
            score = predictions.get(0).score;

            if (rune.equals("fireball") && spell == Constants.FIREBALL) {
                // Fireball
                runeMatches();
            }
            else if (rune.equals("paralyzing_ray") && spell == Constants.PARALYZING_RAY) {
                // Paralyzing Ray
                runeMatches();
            }
            else if (rune.equals("curse") && spell == Constants.CURSE) {
                // Curse
                runeMatches();
            }
            else if (rune.equals("arcane_shield") && spell == Constants.ARCANE_SHIELD) {
                // Arcane Shield
                runeMatches();
            }
            else if (rune.equals("heal") && spell == Constants.HEAL) {
                // Heal
                runeMatches();
            }
        }
    }

    public void runeMatches() {
        double timeFinal = System.currentTimeMillis();
        double timeCast = (timeFinal - timeInitial) / 1000;

        double effectiveness = 100;
        double finalScore = score * 4 - timeCast;

        castTime.setText(getString(R.string.cast_time) + " " + timeCast + " s");
        castScore.setText(getString(R.string.cast_score) + " " + score);
        castEffectiveness.setText(getString(R.string.cast_effectiveness) + " " + effectiveness + "%");

        // Put the values and finish
        resultIntent.putExtra("spell", spell);
        resultIntent.putExtra("score", finalScore);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
