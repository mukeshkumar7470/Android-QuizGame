/*
Author : Ashish Mohapatra (axm160031) and Akash Deo (apd160330)
Assignment : 5

This java file processes the layout to add more questions to the Database/Game.
*/

package com.example.ashishmac.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.ArrayList;
import java.util.Collections;

public class AddQuestion extends AppCompatActivity {
    public EditText newQues;
    public RadioGroup rg;
    String [] saveText = new String[2];
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Quiz";
    File folder = new File (path);
    File file =new File(path+"/Questions.txt");
    String[] lineDetail = new String[2];
    String line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        if(!folder.exists())
        {
            folder.mkdirs();
        }
    }
    //Author: Akash Deo (apd160330)
    public void btnSaveQuesOnClick(android.view.View view)
    {
        newQues= (EditText) findViewById(R.id.etNewQues); //EditText to store Question
        saveText[0] = String.valueOf(newQues.getText());
        if(saveText[0].length()==0)
        {
            newQues.setBackgroundColor(Color.MAGENTA); //Change the color if the Question field is not filled
            Toast.makeText(getApplicationContext(),"Question is Mandatory", Toast.LENGTH_LONG).show();
        }
        else
        {
            newQues.setBackgroundColor(Color.WHITE);
            Boolean isDuplicate=CheckDuplicate(file,saveText[0]);
            if(isDuplicate)
                Toast.makeText(getApplicationContext(),"Question Already Exist", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_LONG).show();
                Save(file, saveText);
            }
        }
    }
    //Author : Ashish Mohapatra (axm160031)
    public void onRadioButtonClicked(android.view.View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButtonTrue:
                if (checked)
                    saveText[1] ="True";
                    break;
            case R.id.radioButtonFalse:
                if (checked)
                    saveText[1] ="False";
                    break;
        }
    }
    //Author : Ashish Mohapatra (axm160031)
    public boolean CheckDuplicate(File file, String data) {
        BufferedReader br = null;
        int check=0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) { //Read each Line from the file
                lineDetail = line.split("\t");//Split the line by tab and store in a string array}
                if(lineDetail[0].equals(data)) {
                    check = 1;
                    break;
                }
                else
                    continue;
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(check==1)
            return true;
        else
            return false;
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
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    //Author : Ashish Mohapatra (axm160031)
    public void btnClear(android.view.View view)
    {
        newQues= (EditText) findViewById(R.id.etNewQues); //EditText to store new Ques
        newQues.setText("");
        rg=(RadioGroup) findViewById(R.id.radioGroup);
        rg.clearCheck();
    }
    //Author: Akash Deo (apd160330)
    public void btnBackToHome(android.view.View view)
    {
        Intent ToHome = new Intent(AddQuestion.this,FullscreenActivity.class); //Go back to home after saving the file
        startActivity(ToHome);
    }
}
