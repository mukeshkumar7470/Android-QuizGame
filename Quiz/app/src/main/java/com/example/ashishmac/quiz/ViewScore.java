/*
Author : Ashish Mohapatra (axm160031) and Akash Deo (apd160330)
Assignment : 5

This java file processes the layout in showing Leadership Board with a list of Top 5 High Score Players.
*/
package com.example.ashishmac.quiz;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
//Author: Akash Deo (apd160330)
public class ViewScore extends AppCompatActivity {
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Quiz"; //Path to the folder where the file is stored
    File folder = new File (path);
    File file =new File(path+"/HighScore.txt");
    public ListView ScorelistView;
    String[] lineDetail = new String[2];
    String line;
    private SortFile CallToSortFile= new SortFile(); //Call to SortFile.java to sort the HighScore.txt file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        try {
            CallToSortFile.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("key");
        int rank=1;
        if(value.equals("Full List")) {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            ScorelistView = (ListView) findViewById(R.id.lvHighScore);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
                ArrayList<String> lines = new ArrayList<String>();
                while ((line = br.readLine()) != null) { //Read each Line from the file
                    lineDetail = line.split("\t");//Split the line by tab and store in a string array}
                    lines.add("Rank-"+rank+"\t\t\t\t"+lineDetail[0] + ":\t" + lineDetail[1]);
                    rank++;
                }
                br.close();
                if (lines.isEmpty()) {
                    lines.add("No Records Found");
                }
                Adapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lines);
                ScorelistView.setAdapter((ListAdapter) adapter); //Show Contact List
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void btnToHomeOnClick(android.view.View view) {
        Intent ToViewScore = new Intent(ViewScore.this,FullscreenActivity.class);
        startActivity(ToViewScore);
    }
    public void btnToPlay(android.view.View view) {
        Intent ToPlay = new Intent(ViewScore.this,PlayQuiz.class);
        startActivity(ToPlay);
    }
}
