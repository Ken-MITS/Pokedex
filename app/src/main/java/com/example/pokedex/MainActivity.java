package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static Button right, left;
    public static ImageButton types;
    public static int numeroPokemon = 1, numeroType;
    public static boolean gotTypes=false;

    public static String selected="All";

    public static ArrayList<String> allTypes, oneTypeNames;


    public static ImageView[] imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allTypes = new ArrayList<>();

        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

        right = findViewById(R.id.btnRight);
        left = findViewById(R.id.btnLeft);
        types = findViewById(R.id.btnTypes);

        MainActivity.allTypes.add("All");





        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allTypes.equals("All")){
                    numeroType++;
                    fetchData a = new fetchData(oneTypeNames.get(numeroType));
                    a.execute();
                }else {
                    numeroPokemon++;
                    fetchData a = new fetchData(Integer.toString(numeroPokemon));
                    a.execute();
                }

            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeroPokemon > 1) {
                    numeroPokemon--;
                }
                if (numeroType > 1){
                    numeroType--;
                }

                if (!allTypes.equals("All")) {
                    numeroType--;
                    fetchData a = new fetchData(oneTypeNames.get(numeroType));
                    a.execute();
                }else {
                    fetchData a = new fetchData(Integer.toString(numeroPokemon));
                    a.execute();
                }


            }
        });

        types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        fetchData data = new fetchData(String.valueOf(numeroPokemon));
        data.execute();
        fetchData1 data2 = new fetchData1();
        data2.execute();

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTxtSearch();
            }
        });

        types = findViewById(R.id.btnTypes);
        types.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Types")
                        .setItems(allTypes.toArray(new String[0]), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                String a = allTypes.get(which);
                                fetchData1 fetch = new fetchData1(a);
                                Log.i("provaLog", "fetch search: "+a);
                                fetch.execute();
                                selected = a;

                                Log.i("provaLog", "size: "+oneTypeNames.size());

                            }
                        });
                builder.create();
                builder.show();
            }

        });

    }

    public void showTxtSearch() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                fetchData process = new fetchData(pokSearch);
                process.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }




}