package com.faltu.minesweeperoptimized;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n=12,m=8;
    Button[][] buttonArray=new Button[n][m];
    int[][] matrix= new int[n][m];
    int noOfBomb=n;
    int[] Bombi=new int[noOfBomb];
    int[] Bombj=new int[noOfBomb];
    Button reset;
    TextView score;
    int score_count=0;
    Boolean flagButtonClicked=false;
    ImageButton addFlag;
    int flagClickNumber=-1;
    int safeClickCount=0,safeFlagCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset=(Button)findViewById(R.id.reset);
        score=(TextView)findViewById(R.id.score);
        addFlag=(ImageButton)findViewById(R.id.addFlag);

        createMatrix();
        addButtons();
        showScore();
        addFlag.setBackgroundResource(R.drawable.ic_bomb);

        addFlag.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(flagClickNumber==0||flagClickNumber==-1) {
                    flagButtonClicked=true;
                    flagClickNumber=1;
                    addFlag.setBackgroundResource(R.drawable.ic_flag_bomb);
                    //addFlag.setBackgroundColor(Color.MAGENTA);
                }else{
                    flagButtonClicked=false;
                    addFlag.setBackgroundResource(R.drawable.ic_bomb);
                    //addFlag.setBackgroundColor(0xFF03DAC5);
                    flagClickNumber=0;
                }
            }
        });

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                final int finalI = i;
                final int finalJ = j;

                /*buttonArray[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        buttonArray[finalI][finalJ].setBackgroundResource(R.drawable.ic_flag_bomb);
                        if(matrix[finalI][finalJ]==-1)
                        {
                            score_count+=2;
                            showScore();
                            buttonArray[finalI][finalJ].setEnabled(false);
                        }
                        flagButtonClicked=true;
                        return false;
                    }
                });*/

                buttonArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flagButtonClicked){
                            buttonArray[finalI][finalJ].setText("");
                            buttonArray[finalI][finalJ].setBackgroundResource(R.drawable.ic_flag_bomb);
                            if(matrix[finalI][finalJ]==-1){
                                safeFlagCount++;
                                System.out.println(safeFlagCount);
                            }
                        }
                        else {
                            if (matrix[finalI][finalJ] != -1) {
                                buttonArray[finalI][finalJ].setBackgroundColor(Color.BLUE);
                                buttonArray[finalI][finalJ].setTextColor(Color.YELLOW);
                                buttonArray[finalI][finalJ].setText(Integer.toString(matrix[finalI][finalJ]));
                                buttonArray[finalI][finalJ].setEnabled(false);
                                score_count++;
                                showScore();
                                safeClickCount++;
                            }
                        }
                        Boolean callDialogBox=false;
                        String RESULT_IN_DIALODUE = null;
                        int idForIcon=R.id.reset;
                        if(safeFlagCount==noOfBomb&&safeClickCount==((n*m)-noOfBomb))
                            {
                                RESULT_IN_DIALODUE="You Won The Game with Score : "+Integer.toString(score_count);
                                Toast.makeText(MainActivity.this,"Winnner!!!",Toast.LENGTH_SHORT);
                                callDialogBox=true;
                            }
                        if(matrix[finalI][finalJ]==-1&&!flagButtonClicked){
                                buttonArray[finalI][finalJ].setText("");
                                for(int x=0;x<noOfBomb;x++) {
                                    buttonArray[Bombi[x]][Bombj[x]].setBackgroundColor(Color.RED);
                                    buttonArray[Bombi[x]][Bombj[x]].setBackgroundResource(R.drawable.ic_bomb);
                                }
                                RESULT_IN_DIALODUE="You Lost the Game\n Score : "+Integer.toString(score_count);
                                callDialogBox=true;
                                Toast.makeText(MainActivity.this,"You Lost -_-",Toast.LENGTH_SHORT);
                        }
                        if(callDialogBox) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setTitle(RESULT_IN_DIALODUE);
                            alertDialogBuilder.setIcon(R.drawable.ic_restart);
                            alertDialogBuilder.setMessage("Do you want to play again!!");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetGame();
                                }
                            });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setButtonsDisable();
                                }
                            });
                            alertDialogBuilder.create().show();
                        }

                    }
                });
            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void setButtonsDisable() {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                buttonArray[i][j].setEnabled(false);
            }
        }
    }

    private void showScore() {
        score.setText("  Score : "+Integer.toString(score_count));
    }

    @SuppressLint("ResourceAsColor")
    private void resetGame() {
        createMatrix();
        safeClickCount=0;
        safeFlagCount=0;
        addFlag.setBackgroundResource(R.drawable.ic_bomb);
        score.setText("  Score : 0");
        score_count=0;
        flagButtonClicked=false;
        flagClickNumber=-1;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                buttonArray[i][j].setEnabled(true);
                buttonArray[i][j].setText("*");
                buttonArray[i][j].setBackgroundColor(Color.LTGRAY);
                buttonArray[i][j].setTextColor(Color.BLACK);
            }
        }
    }

    private void addButtons() {

        GridLayout layout = (GridLayout) findViewById(R.id.rootLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(120,ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginValue=2;
        params.bottomMargin=marginValue;
        params.topMargin=marginValue;
        params.leftMargin=marginValue;
        params.rightMargin=marginValue;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                buttonArray[i][j]=new Button(this);
                buttonArray[i][j].setText("*");//Integer.toString(i)+""+Integer.toString(j)
                buttonArray[i][j].setBackgroundColor(Color.LTGRAY);
                buttonArray[i][j].setLayoutParams(params);
                layout.addView(buttonArray[i][j]);
            }
        }
        /*Button btn = new Button(this);
        btn.setText("Button1");
        layout.addView(btn);

        btn = new Button(this);
        btn.setText("Button2");
        layout.addView(btn);*/
        /*GridLayout.LayoutParams layout=new GridLayout.LayoutParams(
                GridLayout.LayoutParams.MATCH_PARENT,
                GridLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<m;j++)
            {

            }
        }*/

    }

    private void LostGame() {
        Toast.makeText(MainActivity.this,"You Lost the game",Toast.LENGTH_SHORT).show();
    }

    private void createMatrix() {
        //initially matrix is -51
        //and Bomb is where matrix[i][j]=-51
        for(int i=0;i<n;i++)
        {
            Arrays.fill(matrix[i], -51);
        }
        //Bomb is placed in matrix where matrix[i][j]=-1 && for matrix[i][j]=-51 it is empty
        placeBombs();



        //Code for locating no in the matrix;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(matrix[i][j]==-1)
                {
                    System.out.print("+");
                    continue;
                }
                else
                {
                    matrix[i][j]=noOfBombsAround(i,j);
                    System.out.print(matrix[i][j]);
                }
            }
            System.out.println("");
        }
    }

    private void placeBombs() {
        Random rand=new Random();
            /*for(int i=0;i<n;i++)
            {
                for(int j=0;j<m;j++)
                {
                    //initially matrix is -51
                    //and Bomb is where matrix[i][j]=-1
                    matrix[i][j]=-51;
                }
            }*/
        //Code for Locating Bomb;
        Arrays.fill(Bombi, -1);
        /*for(int i=0;i<noOfBomb;i++)
        {
            Bombj[i]=-1;
            Bombi[i]=-1;
        }*/
        for(int i=0;i<noOfBomb;i++)
        {
            int flag=1;
            int a=rand.nextInt(n);
            int b=rand.nextInt(m);
            for(int j=0;j<noOfBomb;j++)
            {
                if(Bombi[j]==a&&Bombj[j]==b)
                {
                    i--;
                    flag=0;
                    break;
                }
            }
            if(flag==1)
            {
                Bombi[i]=a;
                Bombj[i]=b;
                matrix[a][b]=-1;
            }
        }
        //debugging the position of bombs.
        for(int i=0;i<noOfBomb;i++)
        {
            System.out.println(Bombi[i]+"  "+Bombj[i]);
        }
    }

    private int noOfBombsAround(int i, int j) {
        int[] box={0,1,-1};
        int count=0;
        for(int x=0;x<box.length;x++)
        {
            for(int y=0;y<box.length;y++)
            {
                if(box[x]==0&&box[y]==0)
                {
                    continue;
                }
                if(isSafe(i+box[x],j+box[y]))
                {
                    if(matrix[i+box[x]][j+box[y]]==-1)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isSafe(int i,int j) {
        if(i>-1&&j>-1&&i<n&&j<m)
        {
            return true;
        }
        return false;

    }
}