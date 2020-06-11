package com.faltu.minesweeperoptimized;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n=10,m=5;
    Button[][] buttonArray=new Button[n][m];
    int[][] matrix= new int[n][m];
    int noOfBomb=5;
    Button reset;
    TextView score;
    int score_count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset=(Button)findViewById(R.id.reset);
        score=(TextView)findViewById(R.id.score);
        createMatrix();
        addButtons();

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                final int finalI = i;
                final int finalJ = j;
                buttonArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matrix[finalI][finalJ]!=-1)
                        {
                            buttonArray[finalI][finalJ].setText(Integer.toString(matrix[finalI][finalJ]));
                            buttonArray[finalI][finalJ].setEnabled(false);
                            buttonArray[finalI][finalJ].setBackgroundColor(Color.BLUE);
                            buttonArray[finalI][finalJ].setTextColor(Color.YELLOW);
                            score_count++;
                            showScore();
                        }
                        else{
                            buttonArray[finalI][finalJ].setBackgroundColor(Color.RED);
                            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setTitle("You Lost the game /*_*/\nYour Score is"+Integer.toString(score_count));
                            alertDialogBuilder.setIcon(R.drawable.ic_launcher_foreground);
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
                                    Toast.makeText(MainActivity.this, "You have clicked Exit", Toast.LENGTH_SHORT).show();
                                    finish();
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

    private void showScore() {
        score.setText("Score : "+Integer.toString(score_count));
    }

    private void resetGame() {
        createMatrix();
        score.setText("Score : 0");
        score_count=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                buttonArray[i][j].setEnabled(true);
                buttonArray[i][j].setText(Integer.toString(i)+""+Integer.toString(j));
                buttonArray[i][j].setBackgroundColor(Color.LTGRAY);
                buttonArray[i][j].setTextColor(Color.BLACK);
            }
        }
    }

    private void addButtons() {

        GridLayout layout = (GridLayout) findViewById(R.id.rootLayout);
        RelativeLayout.LayoutParams rel_bottone = new RelativeLayout.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT);
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {

                buttonArray[i][j]=new Button(this);
                buttonArray[i][j].setText(Integer.toString(i)+""+Integer.toString(j));
                buttonArray[i][j].setBackgroundColor(Color.LTGRAY);
                buttonArray[i][j].setLayoutParams(rel_bottone);
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


        /*lin_btn.setWeightSum(3f);
        for (int j = 0; j < 3; j++) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(10, 0, 0, 10);
            params1.weight = 1.0f;

            LinearLayout ll;
            ll = new LinearLayout(this);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(params1);

            final Button btn;
            btn = new Button(MainActivity.this);

            btn.setText("A"+(j+1));
            btn.setTextSize(15);
            btn.setId(j);
            btn.setPadding(10, 8, 10, 10);

            ll.addView(btn);

            lin_btn.addView(ll);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(v.getId()==0)
                    {
                        txt_text.setText("Hii");
                    }else if(v.getId()==1)
                    {
                        txt_text.setText("hello");
                    }else if(v.getId()==2)
                    {
                        txt_text.setText("how r u");
                    }
                }
            });
        }*/
    }

    private void LostGame() {
        Toast.makeText(MainActivity.this,"You Lost the game",Toast.LENGTH_SHORT).show();
    }

    private void createMatrix() {
        //initially matrix is -51
        //and Bomb is where matrix[i][j]=-1
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                matrix[i][j]=-51;
            }
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
        int[] Bombi=new int[noOfBomb];
        int[] Bombj=new int[noOfBomb];
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
        for(int x=0;x<3;x++)
        {
            for(int y=0;y<3;y++)
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