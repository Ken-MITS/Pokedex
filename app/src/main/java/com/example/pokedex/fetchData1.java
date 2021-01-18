package com.example.pokedex;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

//import com.ahmadrosid.svgloader.SvgLoader;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class fetchData1 extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String results = "";
    protected static ArrayList<String> oneTypeNames; // Create an ArrayList object
    protected String pokSearch;
    private static boolean secondConnection = false;

    public fetchData1() {
    }
    public fetchData1(String type){
        pokSearch = type;
        secondConnection = true;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url;
            //Make API connection
            if (!secondConnection){
                url = new URL("https://pokeapi.co/api/v2/type/");
            }else {
                url = new URL("https://pokeapi.co/api/v2/type/"+ pokSearch);
            }

            MainActivity.gotTypes= true;

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;

        oneTypeNames = new ArrayList<String>();

        try {

            jObject = new JSONObject(data);

            // Get all Type names
            if(!secondConnection) {
                JSONArray typeNames = new JSONArray(jObject.getString("results"));
                for (int i = 0; i < typeNames.length(); i++) {
                    JSONObject typeNameAll = new JSONObject(typeNames.getString(i));
                    MainActivity.allTypes.add(typeNameAll.getString("name"));
                    //Log.i("provaLog", "allType.get(i)): " + MainActivity.allTypes.get(i));
                }

            }else {
                Log.i("provaLog", "Pasa por secondConnection");

                JSONArray pokemon = new JSONArray(jObject.getString("pokemon"));
                Log.i("provaLog", "Json array length: "+ pokemon.length());
                for (int i = 0; i < 1; i++) {

                    JSONObject typeNameAll = new JSONObject(pokemon.toString(i));
                    Log.i("provaLog", "pokemon.toString(i): "+pokemon.toString(i));

                    JSONObject names = new JSONObject(typeNameAll.getString("pokemon"));
                    oneTypeNames.add(names.getString("name"));
                    Log.i("provaLog", "oneTypeNames.get(i)): " + oneTypeNames.get(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
