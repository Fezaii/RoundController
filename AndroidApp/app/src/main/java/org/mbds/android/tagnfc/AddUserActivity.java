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

public class AddUserActivity extends AppCompatActivity {

    private EditText mail;
    private EditText password;
    private EditText name;
    private EditText role;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        this.context = this;
        setTitle("CREATE A NEW USER");
        name = findViewById(R.id.name_box);
        password = findViewById(R.id.password_box);
        mail = findViewById(R.id.mail_box);
        role = findViewById(R.id.role_box);
    }

    public void register(View v) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name.getText().toString());
        params.put("email", mail.getText().toString());
        params.put("password", password.getText().toString());
        params.put("role", role.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+LoginActivity.URL+"/api/v1/register";

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
                                Toast.makeText(context, "Fail to create a User", Toast.LENGTH_LONG).show();
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
        Toast.makeText(context, "User created successfully", Toast.LENGTH_LONG).show();
        Intent intent   = new Intent(context, AdminAccountActivity.class);
        startActivity(intent);

    }

}


