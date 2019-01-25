package org.mbds.android.tagnfc;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;


import org.json.JSONObject;

public class DeleteLocationActivity extends AppCompatActivity {

    private EditText tagID;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_location);
        this.context = this;
        setTitle("DELETE LOCATION");

        tagID = findViewById(R.id.tagdrop_box);
    }

    public void delete(View v) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tagID", tagID.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+LoginActivity.URL+"/api/v1/location/deleteLocation";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(context, "Fail to delete Location", Toast.LENGTH_LONG).show();
                            }
                        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {

                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("x-access-token", mPreferences.getString("token", ""));

                return headers;
            }
        };

        queue.add(jsonObjectRequest);
        Toast.makeText(context, "Location deleted successfully", Toast.LENGTH_LONG).show();
        Intent intent   = new Intent(context, AdminAccountActivity.class);
        startActivity(intent);

    }

}




