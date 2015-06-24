package com.example.mrson.facebookdemo;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.mrson.facebookdemo.apis.ApiError;
import com.example.mrson.facebookdemo.apis.ApiErrorListener;

import com.example.mrson.facebookdemo.apis.Api;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import org.json.JSONObject;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    ArrayList<Myfeed> mlist = new ArrayList<Myfeed>();
    FeedAdapter feedAdapter;
    Myfeed myfeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api.init(getApplicationContext());
       setContentView(R.layout.activity_main);
        getMyfeed();
//      Myfeed myfeed = new Myfeed();
//        Myfeed myfeed1 = new Myfeed();
//        myfeed1.setName("Nguyen van b");
//    myfeed.setName("Nguyen van a");
//     //   myfeed.setImage(R.mipmap.avar_id);
//        myfeed.setProfilePic("Day la main");
//        myfeed.setStatus("dsfsdfdsfsdfffs");
        // mlist.add(myfeed);
        // mlist.add(myfeed1);
        feedAdapter = new FeedAdapter(mlist, getBaseContext());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(feedAdapter);

    }

    public void getMyfeed() {
        HashMap<String, String> param = new HashMap<>();
        Api.getInstance().getUserInfo(param, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                final Gson gson = new Gson();

                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JsonElement je = gson.fromJson(json, JsonElement.class);
                    JsonObject jsonObjectroot = je.getAsJsonObject();

                    Log.d("obj", jsonObjectroot.toString());
                    JsonArray jsonArray = jsonObjectroot.get("feed").getAsJsonArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        //   JSONObject feedObj = (JSONObject) jsonArray.get(i);
                        JsonObject jsonMem = jsonArray.get(i).getAsJsonObject();
                        //   Log.d("jsonmem",jsonMem.toString());\
                        //   String test=jsonMem.getString("name");
                        String name = jsonMem.get("name").getAsString();
                        String status = jsonMem.get("status").getAsString();
                        //  String image=jsonMem.get("image").getAsString();
                        Log.d("name", name.toString());
                        Log.d("status", status.toString());
                        String image_c = jsonMem.get("image").isJsonNull() ? null : jsonMem.get("image").getAsString();
                        String url = jsonMem.get("url").isJsonNull() ? null : jsonMem.get("url").getAsString();
                        //        Log.d("image",image_c.toString());
                        // String status_c=jsonMem.get("status").getAsString();
                        String profilePic_c = jsonMem.get("profilePic").getAsString();

                        //    String url=jsonMem.get("url").getAsString();
                        myfeed = new Myfeed();
                        // myfeed.setStatus(status);
                        myfeed.setName(name);
                        myfeed.setStatus(status);
                        myfeed.setImage(profilePic_c);
                        myfeed.setUrl(url);
                        //  myfeed.setImage(image);

                        myfeed.setProfilePic(image_c);
                        //   myfeed.setUrl(url);
                        //    myfeed.setImage(image_c);


                        mlist.add(myfeed);


                    }


                    feedAdapter.notifyDataSetChanged();

                    //   Log.d("array",jsonArray.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new ApiErrorListener() {
            @Override

            public void onErrorResponse(ApiError error) {

            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
