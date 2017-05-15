/*
Author : Ashish Mohapatra (axm160031) and Akash Deo (apd160330)
Assignment : 5

This java file processes the layout for playing the game. It consists of all the methods and rules on how to play the game.
Store Winner information and his score. Allows User to Restart a new Game. Exit the Game. View Score
*/
package com.example.ashishmac.quiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class PlayQuiz extends AppCompatActivity {
    String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Quiz"; //Path to the folder where the file is stored
    File file =new File(path+"/Questions.txt");
    File file2 =new File(path+"/HighScore.txt");
    String[] lineDetail = new String[2];
    String [] saveText = new String[2];
    String line;
    int count=0;
    public String [] mQuestions = new String[10000];
    public String [] mCorrectAnswers = new String[10000];


    TextView score,question;
    Button btnTrue,btnFalse;

    private String mAnswer;
    private int mScore=0;
    int mQuestionsLenght;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            while ((line = br.readLine()) != null) { //Read each Line from the file
                lineDetail = line.split("\t");//Split the line by tab and store in a string array}
                mQuestions[count]=lineDetail[0];
                mCorrectAnswers[count]=lineDetail[1];
                count++;
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mQuestionsLenght=count;

        r=new Random(); //Create a new Random Number
        btnTrue= (Button) findViewById(R.id.btnTrue); //New instance of Button
        btnFalse= (Button) findViewById(R.id.btnFalse);

        question= (TextView) findViewById(R.id.displayQuestion); //New instance of TextView to display question.
        score= (TextView) findViewById(R.id.score); //New instance of TextView to display Options (True/False).

        score.setText("Score:"+mScore*10);

        updateQuestion(r.nextInt(count)); //Update the question the textView with the next question based on the Random number.
    }
    //Author : Ashish Mohapatra (axm160031)
    public void btnTrueOnClick(android.view.View view)
    {
            if(btnTrue.getText().equals(mAnswer)) //If the true button is pressed and the answer is correct the do the following.
            {
                //btnTrue.setBackgroundColor(Color.GREEN);
                mScore++; //Increase the score
                score.setText("Score:"+mScore*10); //Display the core
                updateQuestion(r.nextInt(mQuestionsLenght)); //Update the TextView with the next question.

            }
            else
            {
                gameOver(); //Else Game is over.
            }
    }
    //Author : Ashish Mohapatra (axm160031)
    public void btnFalseOnClick(android.view.View view)
    {
        if(btnFalse.getText().equals(mAnswer)) //If the false button is pressed and the answer is correct the do the following.
        {
            mScore++; //Increase the score
            score.setText("Score:"+mScore*10); //Display the core
            updateQuestion(r.nextInt(mQuestionsLenght)); //Update the TextView with the next question.
        }
        else
        {
            gameOver(); //Else Game is over.
        }
    }
    //Author: Akash Deo (apd160330)
    private void updateQuestion(int num)
    {
        question.setText(getQuestion(num)); //Set the next question from the databse based on the random Number.
        btnTrue.setText("True"); //Set one button as True
        btnFalse.setText("False"); //Set the button as False.
        mAnswer=getCorrectAnswer(num); //Get the correct answer to the question just selected
    }
    //Author: Akash Deo (apd160330)
    public String getQuestion(int a)
    {
        String question=mQuestions[a]; //Get the question from the database
        return question;
    }
    //Author: Akash Deo (apd160330)
    public String getCorrectAnswer(int a)
    {
        String answer= mCorrectAnswers[a]; //Get the correct answer to the question selected from the database
        return answer;
    }
    //Author : Ashish Mohapatra (axm160031)
    private void gameOver(){
        BufferedReader br = null;
        final EditText editText=new EditText(this);
        int[] scoreArray = new int[5];
        int i=0;
        String[] lineDetail2 = new String[2];
        String line2;
        int isItAHighScore=0;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "Cp1252"), 100);
            while ((line2 = br.readLine()) != null) { //Read each Line from the file
                lineDetail2 = line2.split("\t");//Split the line by tab and store in a string array}
                scoreArray[i]= Integer.parseInt(lineDetail2[1]);
                i++;
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(i=0;i<scoreArray.length;i++)
        {
            if((mScore*10)>scoreArray[i]) //If the players score is better than ones in the HighScore.txt file.
            {
                isItAHighScore=1; //Initiate the variable with one so as to save his name in the file.
                break;
            }
        }
        if(isItAHighScore==1) { //If he is one of the top scorer
            AlertDialog.Builder alertDialoguilder = new AlertDialog.Builder(PlayQuiz.this); //Open a dialog box
            alertDialoguilder
                    .setTitle("Oops, That was Incorrect!") //Set the dialog box title
                    .setMessage("Congrats! You are one of the top Scorer. Your Score is : " + mScore*10 +". \nEnter Your name below to save your score.")
                    .setCancelable(false)
                    .setView(editText) //Show a EditText for the player to enter his name.
                    .setPositiveButton("New Game",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), PlayQuiz.class)); //Take to the new game activity layout
                                    finish();
                                }
                            })
                    .setNeutralButton("Save Name",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(editText.getText().toString().length()==0)
                                    {
                                        Toast.makeText(getApplicationContext(),"Name is Mandatory to Save", Toast.LENGTH_LONG).show(); //Name is manadatory to store info in file
                                    }
                                    else {
                                        saveName(editText.getText().toString()); //Save player's name
                                    }
                                }
                            })
                    .setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish(); //Finish the game.
                                }
                            });
            AlertDialog alertDialog = alertDialoguilder.create();
            alertDialog.show();
        }
        else
        {
            AlertDialog.Builder alertDialoguilder = new AlertDialog.Builder(PlayQuiz.this);
            alertDialoguilder
                    .setTitle("Oops, That was Incorrect!")
                    .setMessage("Game Over. Your Score is : " + mScore*10)
                    .setCancelable(false)
                    .setPositiveButton("New Game",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), PlayQuiz.class));
                                    finish();
                                }
                            })
                    .setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
            AlertDialog alertDialog = alertDialoguilder.create();
            alertDialog.show();
        }
    }
    //Author: Akash Deo (apd160330)
    private void saveName(String name) {
        saveText[0]=name; //Save the name of the player
        saveText[1]=Integer.toString(mScore*10); //Save the palyers score
        Save (file2,saveText); //Save the data
    }
    //Author: Akash Deo (apd160330)
    private void Save(File file, String[] data) {
        FileOutputStream fos = null;
        OutputStreamWriter myOutWriter = null;
        try
        {
            fos = new FileOutputStream(file,true); //create a new fileOutputStream
            myOutWriter = new OutputStreamWriter(fos); //Open Outputstream Writer to write into text file
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for(int i=0;i<2;i++) {
                    myOutWriter.append(data[i]); //Append all the data as a single line to the text file.
                    myOutWriter.append("\t"); //Append tab after each word
                }
                myOutWriter.append("\n");
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                myOutWriter.close(); //Close the file
                fos.close();
                Intent ToScore = new Intent(PlayQuiz.this,ViewScore.class); //Go back to home after saving the file
                ToScore.putExtra("key","Full List");//Send
                startActivity(ToScore);
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
}
