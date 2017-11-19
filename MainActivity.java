package com.example.roman.romanpset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        final List<String> list = new ArrayList<String>();
        final ListView listView = (ListView) findViewById(R.id.catList);
        String url = "https://resto.mprog.nl/categories";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray jArray = null;
                        try {
                            jArray = jsonObject.getJSONArray("categories");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < jArray.length(); i++){
                            String itemname = null;
                            try {
                                itemname = jArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            list.add(itemname);

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(
                                            MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            list);
                            listView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("choice", value);

                startActivity(intent);
                finish();
            }
        });
    }
}
