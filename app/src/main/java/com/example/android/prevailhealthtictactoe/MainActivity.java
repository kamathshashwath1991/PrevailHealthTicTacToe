package com.example.android.prevailhealthtictactoe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int Player = 0;
    private int[] DefaultGameState={2,2,2,2,2,2,2,2,2};
    private int[][] winningPositions= {{0,1,2},{3,4,5},{6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    private boolean gameIsActive= true;

    public void dropCoin(View view){
        ImageView counter= (ImageView) view;
        int imageTapCount= Integer.parseInt(counter.getTag().toString());

        if (DefaultGameState[imageTapCount]==2 && gameIsActive){
            DefaultGameState[imageTapCount] = Player;
            counter.setTranslationY(-1000f);

            if (Player ==0){
                counter.setImageResource(R.drawable.yellow);
                Player =1;
            }
            else {
                counter.setImageResource(R.drawable.red);
                Player =0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition: winningPositions){
                if (DefaultGameState[winningPosition[0]]==DefaultGameState[winningPosition[1]] &&
                DefaultGameState[winningPosition[1]]==DefaultGameState[winningPosition[2]] &&
                DefaultGameState[winningPosition[0]]!= 2){
                    Log.d(TAG, "gameState: " +DefaultGameState[winningPosition[0]]);

                    String winner= "Red";
                    if (DefaultGameState[winningPosition[0]]==0){
                        winner= "Yellow";
                    }

                    TextView winnerMessage= (TextView) findViewById(R.id.tv_winning_message);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout= (LinearLayout) findViewById(R.id.layout_playAgain);
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    boolean gameIsOver = true;
                    for (int counterState: DefaultGameState){
                        if (counterState==2){
                            gameIsOver= false;
                        }
                    }
                    if (gameIsOver){
                        TextView winnerMessage= (TextView) findViewById(R.id.tv_winning_message);
                        winnerMessage.setText("Its a draw");
                        LinearLayout layout= (LinearLayout) findViewById(R.id.layout_playAgain);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        gameIsActive=true;
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.layout_playAgain);
        linearLayout.setVisibility(View.INVISIBLE);

        Player =0;
        for (int i=0; i< DefaultGameState.length; i++){
            DefaultGameState[i]= 2;
        }

        GridLayout gridLayout= (GridLayout) findViewById(R.id.gridLayout);
        for (int i=0; i< gridLayout.getChildCount(); i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"This is the new game!", Toast.LENGTH_SHORT).show();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
