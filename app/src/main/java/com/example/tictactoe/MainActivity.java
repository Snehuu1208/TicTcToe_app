package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_NAME="com.example.tictactoe.extra.NAME";
    boolean gameActive =true;
    // Player representation
    // 0-x
    // 1-0
    int activePlayer=0;
    int []gameState={2,2,2,2,2,2,2,2,2};
    // State meanings:
    // 0-x
    // 1-0
    // 2-null
    int [][]winPositions={{0,1,2},{3,4,5},{6,7,8},
                          {0,3,6},{1,4,7},{2,5,8},
                          {0,4,8},{2,4,6}};
   @SuppressLint("SetTextI18n")
   public void PlayerTap(View view) throws InterruptedException {
       ImageView img = (ImageView)view;
       int tappedImage = Integer.parseInt(img.getTag().toString());
       if(!gameActive){
           gameReset();
       }
       if(gameState[tappedImage]==2){
           gameState[tappedImage]=activePlayer;
           img.setTranslationY(-1000f);
           if(activePlayer==0){
               img.setImageResource(R.drawable.x);
               activePlayer=1;
               TextView status= findViewById(R.id.status);
               status.setText("O's Turn - Tap to play");
           }
           else{
               img.setImageResource(R.drawable.o);
               activePlayer=0;
               TextView status= findViewById(R.id.status);
               status.setText("X's Turn - Tap to play");
           }
           img.animate().translationYBy(1000f).setDuration(300);
       }
        //Check if any player has won
        for(int[] winPosition: winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                gameState[winPosition[0]]!=2){
                //Somebody has won! - Find out who!
                 String winnerStr;
                 gameActive=false;
                 if(gameState[winPosition[0]]==0){
                     winnerStr ="X has won";
                     Intent i=new Intent(this,MainActivity2.class);
                     i.putExtra(EXTRA_NAME,winnerStr);
                     startActivity(i);
                 }
                 else{
                     winnerStr="O has won";
                     Intent i=new Intent(this,MainActivity2.class);
                     i.putExtra(EXTRA_NAME,winnerStr);
                     startActivity(i);
                 }
                 //Update the status for winner announcement
                TextView status =findViewById(R.id.status);
                 //status.setText(winnerStr);
            }
        }
       boolean emptySquare = false;
       for (int squareState : gameState) {
           if (squareState == 2) {
               emptySquare = true;
               break;
           }
       }
       if (!emptySquare && gameActive) {
           // Game is a draw
           gameActive = false;
           String winnerStr;
           winnerStr = "Match Draw! No one won";
           TextView status = findViewById(R.id.status);
           Intent i=new Intent(this,MainActivity2.class);
           i.putExtra(EXTRA_NAME,winnerStr);
           startActivity(i);
           //status.setText(winnerStr);
       }

   }

    @SuppressLint("SetTextI18n")
   public void gameReset(){
       gameActive=true;
       activePlayer=0;
       for(int i=0; i<gameState.length;i++){
           gameState[i]=2;
       }
       ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
       ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);

       TextView status= findViewById(R.id.status);
       status.setText("X's Turn - Tap to play");
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}