package com.example.apimap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements UserDataAdapter.OnItemClickListener {

    public  String TAG = this.getClass().getSimpleName();

    //RecyclerView setting

    private RecyclerView recyclerView;
    private UserDataAdapter adapter;
    private ArrayList<UserData> UserDataList;



    //Call API
    RequestQueue queue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this).build();

        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);

        //RecyclerView setting
        recyclerView = findViewById(R.id.rv1);
        //better performance
        recyclerView.setHasFixedSize(true);
        //grid view
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        //check Internet

        if(isNetworkConnected()) {

            //Online call API or Caching

            if(!GlobalConstants.caching) {
                //Case : Just Enter the Apps -- call API to renew the data
                retrieve_user_dataAPI();
            }else {
                //Case : By Caching during visiting the apps
                if(Hawk.get(GlobalConstants.StoreUserData) != null){

                    //Case : Have Cached Data
                    try {

                        UserDataList = new ArrayList<>();

                        String response = Hawk.get(GlobalConstants.StoreUserData);
                        //array that stores all the object in API Documentation
                        JSONArray jsonArray = new JSONArray(response);

                        //Loop all the object of hit array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject userData = jsonArray.getJSONObject(i);


                            //Extrieve what we want by Keys
                            JSONObject location = userData.getJSONObject("location");

                            double latitude = location.getDouble("latitude");
                            double longitude = location.getDouble("longitude");

                            String picture = userData.getString("picture");
                            String _id = userData.getString("_id");
                            String name = userData.getString("name");
                            String email = userData.getString("email");

                            UserDataList.add(new UserData(latitude, longitude, picture, _id, name, email));
                        }


                        adapter = new UserDataAdapter(this, UserDataList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(MainActivity.this);

                    } catch (JSONException e) {

                        Log.e(TAG, "Unexpected JSON response from  Hawk");

                        Log.i(TAG, "JSONException in Hawk");
                        e.printStackTrace();


                    }
                }else{
                    //Case : No Cached Data
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_no_internet);
                    builder.setMessage(R.string.no_internet);
                    builder.setPositiveButton(R.string.disclaimer_ok, null);
                    builder.create().show();
                }
            }

        }else{

            //Offline Check Hawk and use the Cached Data
            if(Hawk.get(GlobalConstants.StoreUserData) != null){

                //Case : Have Cached Data
                try {

                    UserDataList = new ArrayList<>();

                    String response = Hawk.get(GlobalConstants.StoreUserData);
                    //array that stores all the object in API Documentation
                    JSONArray jsonArray = new JSONArray(response);

                    //Loop all the object of hit array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userData = jsonArray.getJSONObject(i);


                        //Extrieve what we want by Keys
                        JSONObject location = userData.getJSONObject("location");

                        double latitude = location.getDouble("latitude");
                        double longitude = location.getDouble("longitude");

                        String picture = userData.getString("picture");
                        String _id = userData.getString("_id");
                        String name = userData.getString("name");
                        String email = userData.getString("email");

                        UserDataList.add(new UserData(latitude, longitude, picture, _id, name, email));
                    }


                    adapter = new UserDataAdapter(this, UserDataList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {

                    Log.e(TAG, "Unexpected JSON response from  Hawk");

                    Log.i(TAG, "JSONException in Hawk");
                    e.printStackTrace();


                }
            }else{
                //Case : No Cached Data
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_no_internet);
                builder.setMessage(R.string.no_internet);
                builder.setPositiveButton(R.string.disclaimer_ok, null);
                builder.create().show();
            }
        }



    }

    private void retrieve_user_dataAPI() {

        String url = "http://www.json-generator.com/api/json/get/cfdlYqzrfS";
        Log.i(TAG,"Call API");
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d(TAG, response);

                    try {
                        UserDataList = new ArrayList<>();
                        Log.i(TAG, "SS");
                        //array that stores all the object in API Documentation


                        Hawk.put(GlobalConstants.StoreUserData,response);
                          JSONArray jsonArray = new JSONArray(response);

                        //Loop all the object of hit array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject userData = jsonArray.getJSONObject(i);


                            //Extrieve what we want by Keys
                            JSONObject location = userData.getJSONObject("location");

                            double latitude = location.getDouble("latitude");
                            double longitude = location.getDouble("longitude");

                            String picture = userData.getString("picture");
                            String _id = userData.getString("_id");
                            String name = userData.getString("name");
                            String email = userData.getString("email");

                            UserDataList.add(new UserData(latitude,longitude,picture,_id,name,email));
                        }


                        progressDialog.dismiss();

                        GlobalConstants.caching = true;
                        adapter = new UserDataAdapter(this, UserDataList);

                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(MainActivity.this);



                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        Log.e(TAG, "Unexpected JSON response from  API");

                        Log.i(TAG, "JSONException");
                        e.printStackTrace();


                    }
                },
                error -> {
                    progressDialog.dismiss();


                    Log.e(TAG, "Login API didn't work with status code: " + ((error.networkResponse != null) ? (error.networkResponse.statusCode) : " null network response") + " " + error.getCause());

                    if (error.getMessage() != null) {
                        Log.e(TAG, error.getMessage());
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.system_error);
                    builder.setMessage(R.string.server_communication_error);

                    builder.setPositiveButton(R.string.disclaimer_ok, null);
                    builder.create().show();

                    Log.i(TAG, "error");
                }) {


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(stringRequest);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(null);
        progressDialog.setTitle("");
        progressDialog.setMessage(getString(R.string.cms_progress_dialog_msg));
        progressDialog.show();
    }


    @Override
    public void onItemClick(int position) {

        GlobalConstants.UserData = UserDataList.get(position);
        startActivity(new Intent(getBaseContext(),DetailUserActivity.class));
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
