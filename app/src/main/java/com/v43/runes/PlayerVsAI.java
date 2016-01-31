package com.v43.runes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.v43.runes.model.Wizard;

import java.text.DecimalFormat;

public class PlayerVsAI extends Activity {

    private final int NEW_TURN_TIMEOUT = 1200;

    // Players Wizards

    private Wizard player1;
    private Wizard player2;

    private Wizard attacker;
    private Wizard defender;
    private Wizard roundWinner;

    // Player LifePoints and Shield indicators

    private TextView player1LifeBar;
    private TextView player2LifeBar;

    // Player scores

    private double player1Score;
    private double player2Score;

    // Messages for state changes and scores

    private TextView player1StateMessage;
    private TextView player2StateMessage;

    private TextView player1ScoreMessage;
    private TextView player2ScoreMessage;

    // Attacking / Defending position indicator

    private TextView player1Position;
    private TextView player2Position;

    // Message for general info

    private TextView infoMessage;


    // Cast spell Button

    private Button castSpellButton;


    // Game over trigger
    private boolean victory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_vs_ai);

        player1 = new Wizard();
        player2 = new Wizard();

        victory = false;

        player1LifeBar = (TextView) findViewById(R.id.battle_player1_life);
        player2LifeBar = (TextView) findViewById(R.id.battle_player2_life);
        player1LifeBar.setText(String.valueOf(player1.getHp()) + " / " + Constants.LIFE_POINTS_MAX);
        player2LifeBar.setText(String.valueOf(player2.getHp()) + " / " + Constants.LIFE_POINTS_MAX);

        player1StateMessage = (TextView) findViewById(R.id.battle_player1_state_message);
        player2StateMessage = (TextView) findViewById(R.id.battle_player2_state_message);

        player1ScoreMessage = (TextView) findViewById(R.id.battle_player1_score);
        player2ScoreMessage = (TextView) findViewById(R.id.battle_player2_score);

        player1Position = (TextView) findViewById(R.id.battle_player1_position);
        player2Position = (TextView) findViewById(R.id.battle_player2_position);

        infoMessage = (TextView) findViewById(R.id.battle_info_message);

        castSpellButton = (Button) findViewById(R.id.CastSpellButton);
        castSpellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                castSpell();
            }
        });

        // Choose the initial attacking player
        int i = (int) (Math.random() * 2);
        if (i == 0) {
            // Player 1 will be the attacker
            attacker = player1;
            defender = player2;
            player1Position.setText(getString(R.string.attacking_position));
            player2Position.setText(getString(R.string.defending_position));
        }
        else {
            attacker = player2;
            defender = player1;
            player1Position.setText(getString(R.string.defending_position));
            player2Position.setText(getString(R.string.attacking_position));
        }

        turn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // requestCode == 0 --> A spell has been selected
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                Intent i = new Intent(this, DrawRune.class);

                // Get the spell value from the CastSpell Activity
                int resultSpell = data.getIntExtra("spell", 0);

                // Check it doesn't get out of range, put the selected spell for the next activity (drawing the rune)
                if (resultSpell >= 1 && resultSpell <= 5) {
                    i.putExtra("spell", resultSpell);
                }

                startActivityForResult(i, 1);
            }
        }

        // requestCode == 1 --> The rune has been drawn.
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                // Get the spell value from the CastSpell Activity
                int resultSpell = data.getIntExtra("spell", 0);

                // Show both players' scores
                player1Score = data.getDoubleExtra("score", 0);
                player2Score = AI.aiScore(resultSpell);

                // Check curses and stuns
                //TODO: Sustituir el playerStunned, para que se salte lo de dibujar la runa.
                if (player1.getStatus() == Constants.STUNNED) {
                    player1Score = 0;
                    player1.setStatus(Constants.NO_EFFECTS);
                    player1StateMessage.setText("");
                }
                if (player1.getStatus() == Constants.CURSED) {
                    player1Score = player1Score*3/4;
                    player1.setStatus(Constants.NO_EFFECTS);
                    player1StateMessage.setText("");
                }
                if (player2.getStatus() == Constants.STUNNED) {
                    player2Score = 0;
                    player2.setStatus(Constants.NO_EFFECTS);
                    player2StateMessage.setText("");
                }
                else if (player2.getStatus() == Constants.CURSED) {
                    player2Score = player2Score*3/4;
                    player2.setStatus(Constants.NO_EFFECTS);
                    player2StateMessage.setText("");
                }
                player1ScoreMessage.setText(getString(R.string.cast_score) + " " + new DecimalFormat("##,####").format(player1Score));
                player2ScoreMessage.setText(getString(R.string.cast_score) + " " + new DecimalFormat("##,####").format(player2Score));


                //TODO: Sacar todo lo que esta debajo fuera de esta funcion.
                // Check who wins
                if (player1Score > player2Score) {
                    roundWinner = player1;
                }
                else {
                    roundWinner = player2;
                }

                // Resolve the spell effects only if roundWinner == attacker
                if (roundWinner == attacker) {
                    resolveSpell(resultSpell, attacker, defender);
                    infoMessage.setText(getString(R.string.cast_successful));
                }
                else {
                    infoMessage.setText(getString(R.string.countercast));
                }


                // Update LifeBars

                if (player1.getShield() > 0) {
                    player1LifeBar.setText(String.valueOf(player1.getHp()) + " / " + Constants.LIFE_POINTS_MAX
                            + " (+ " + String.valueOf(player1.getShield()) + " )");
                }
                else {
                    player1LifeBar.setText(String.valueOf(player1.getHp()) + " / " + Constants.LIFE_POINTS_MAX);
                }

                if (player2.getShield() > 0) {
                    player2LifeBar.setText(String.valueOf(player2.getHp()) + " / " + Constants.LIFE_POINTS_MAX
                            + " (+ " + String.valueOf(player2.getShield()) + " )");
                }
                else {
                    player2LifeBar.setText(String.valueOf(player2.getHp()) + " / " + Constants.LIFE_POINTS_MAX);
                }


                // Check victory

                if (player1.getHp() <= 0) {
                    // Player 2 wins
                    infoMessage.setText(getString(R.string.defeat));
                    victory = true;
                }
                else if (player2.getHp() <= 0) {
                    // Player 1 wins
                    infoMessage.setText(getString(R.string.victory));
                    victory = true;
                    castSpellButton.setVisibility(View.INVISIBLE);
                }
                else {
                    // Prepare the new turn
                    attacker = roundWinner;
                    if (attacker == player1){
                        player1Position.setText(getString(R.string.attacking_position));
                        player2Position.setText(getString(R.string.defending_position));
                        defender = player2;

                        // Set the CastSpell button to Visible
                        castSpellButton.setVisibility(View.VISIBLE);
                        player1Position.setText(getString(R.string.attacking_position));
                        player2Position.setText(getString(R.string.defending_position));
                    }
                    else{
                        player1Position.setText(getString(R.string.defending_position));
                        player2Position.setText(getString(R.string.attacking_position));
                        defender = player1;

                        // Set the CastSpell button to Invisible
                        castSpellButton.setVisibility(View.INVISIBLE);
                        player1Position.setText(getString(R.string.defending_position));
                        player2Position.setText(getString(R.string.attacking_position));
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            turn();
                        }
                    }, NEW_TURN_TIMEOUT);

                }
            }
        }
    }

    public void castSpell() {
        Intent i = new Intent(this, CastSpell.class);
        startActivityForResult(i, 0);
    }

    public void turn() {

        if (!(attacker == player1)) {
            // Select a spell
            int spell = AI.aiSelectSpell(player1, player2);

            // Check if player1Stunned. In that case, he can't defend himself
            if (player1.getStatus() == Constants.STUNNED) {
                player1.setStatus(Constants.NO_EFFECTS);
                player1StateMessage.setText("");
                resolveSpell(spell, attacker, defender);
                turn();
            }
            else {
                // Create the draw spell rune activity so the player can try to defend himself
                Intent i = new Intent(this, DrawRune.class);
                i.putExtra("spell", spell);
                startActivityForResult(i, 1);
            }
        }
    }

    public void resolveSpell(int spell, Wizard attacker, Wizard defender) {

        if (spell == 1) {
            //Fireball
            defender.receiveDamage(Constants.FIREBALL_NORMAL_DAMAGE);
        }
        else if (spell == 2) {
            //Paralyzing Ray
            defender.receiveDamage(Constants.PARALYZING_RAY_NORMAL_DAMAGE);
            int stunProb = (int) (Math.random() * 100);
            if (stunProb <= Constants.PARALYZING_RAY_NORMAL_STUN_CHANCE) {
                defender.setStatus(Constants.STUNNED);
                //TODO: Cambiar la forma de check de mensaje
                if (attacker == player1){
                    player2StateMessage.setText(getString(R.string.stunned));
                }
                else{
                    player1StateMessage.setText(getString(R.string.stunned));
                }
            }
        }
        else if (spell == 3) {
            //Curse
            defender.setStatus(Constants.CURSED);
            //TODO: Cambiar la forma de check de mensaje
            if (attacker == player1){
                player2StateMessage.setText(getString(R.string.cursed));
            }
            else{
                player1StateMessage.setText(getString(R.string.cursed));
            }
        }
        else if (spell == 4) {
            //ArcaneShield
            attacker.setShield(Constants.ARCANE_SHIELD_NORMAL);
        }
        else if (spell == 5) {
            //Heal
            attacker.setLifePoints(Constants.HEAL_NORMAL);
        }
    }
}
