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
    protected ArrayList<String> allTypes; // Create an ArrayList object
    protected String pokSearch;

    public void fetchData1() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url;
            //Make API connection
            url = new URL("https://pokeapi.co/api/v2/type/");
            MainActivity.gotTypes= true;
            Log.i("provaLog", "Dentro fetchData1");

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
        String img = "";
        String typeName = "";
        String typeObj="";
        allTypes= new ArrayList<String>();

        try {

            jObject = new JSONObject(data);

            // Get Type names
            if (allTypes==null){
                Log.i("provaLog", "dentro if");
                JSONArray typeNames = new JSONArray(jObject.getString("results"));
                for (int i=0; i<typeNames.length(); i++){
                    JSONObject typeNameAll = new JSONObject(typeNames.getString(i));
                    //JSONObject oneType = new JSONObject(typeNameAll.getString("type"));
                    allTypes.add(typeNameAll.getString("name"));
                    Log.i("provaLog", "allType.get(i)): "+allTypes.get(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
