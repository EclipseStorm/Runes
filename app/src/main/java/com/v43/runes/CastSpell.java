package com.v43.runes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class CastSpell extends Activity {

    private TextView spellDescription;
    private RadioButton fireball,
            paralyzingRay,
            curse,
            arcaneShield,
            heal;
    private ImageView spellRune;

    private Intent resultIntent = new Intent();
    private int spellCast = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_spell);

        spellDescription = (TextView) findViewById(R.id.cast_spell_description);

        fireball = (RadioButton) findViewById(R.id.cast_spell_button_fireball);
        paralyzingRay = (RadioButton) findViewById(R.id.cast_spell_button_paralyzing_ray);
        curse = (RadioButton) findViewById(R.id.cast_spell_button_curse);
        arcaneShield = (RadioButton) findViewById(R.id.cast_spell_button_arcane_shield);
        heal = (RadioButton) findViewById(R.id.cast_spell_button_heal);

        spellRune = (ImageView) findViewById(R.id.cast_spell_rune);
    }

    public void onRadioButtonClicked(View view) {

        String description = "";

        if (fireball.isChecked()) {
            description = getString(R.string.spell_description_fireball);
            spellRune.setImageResource(R.drawable.fireball);
            spellCast = Constants.FIREBALL;
        }
        else if (paralyzingRay.isChecked()) {
            description = getString(R.string.spell_description_paralyzing_ray);
            spellRune.setImageResource(R.drawable.paralyzingray);
            spellCast = Constants.PARALYZING_RAY;
        }
        else if (curse.isChecked()) {
            description = getString(R.string.spell_description_curse);
            spellRune.setImageResource(R.drawable.curse);
            spellCast = Constants.CURSE;
        }
        else if (arcaneShield.isChecked()) {
            description = getString(R.string.spell_description_arcane_shield);
            spellRune.setImageResource(R.drawable.arcaneshield);
            spellCast = Constants.ARCANE_SHIELD;
        }
        else if (heal.isChecked()) {
            description = getString(R.string.spell_description_heal);
            spellRune.setImageResource(R.drawable.heal);
            spellCast = Constants.HEAL;
        }

        spellDescription.setText(description);
    }

    public void onButtonClicked(View view) {
        resultIntent.putExtra("spell", spellCast);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
