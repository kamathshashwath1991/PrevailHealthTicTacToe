package com.example.android.prevailhealthtictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int activePlayer= 0;
    private int[] gameState={2,2,2,2,2,2,2,2,2};
    private int[][] winningPositions= {{0,1,2},{3,4,5},{6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    private boolean gameIsActive= true;

    public void dropIn(View view){

        ImageView counter= (ImageView) view;
        int tappedCounter= Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter]==2 && gameIsActive){
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer==0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer=1;
            }
            else {
                counter.setImageResource(R.drawable.red);
                activePlayer=0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition: winningPositions){
                if (gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                gameState[winningPosition[0]]!= 2){
                    Log.d(TAG, "gameState: " +gameState[winningPosition[0]]);

                    String winner= "Red";
                    if (gameState[winningPosition[0]]==0){
                        winner= "Yellow";
                    }

                    TextView winnerMessage= (TextView) findViewById(R.id.tv_winning_message);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout= (LinearLayout) findViewById(R.id.layout_playAgain);
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    boolean gameIsOver = true;
                    for (int counterState: gameState){
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

        activePlayer=0;
        for (int i=0; i< gameState.length; i++){
            gameState[i]= 2;
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
    }
}
