package org.mbds.android.tagnfc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public final  static String URL = "66d538a3.ngrok.io";

    private Context context = this;
    private EditText loginBox;
    private EditText passBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("ROUND CONTROLLER");
        loginBox = findViewById(R.id.user_box);
        passBox = findViewById(R.id.pass_box);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }



    public void login(View v) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", loginBox.getText().toString());
        params.put("password", passBox.getText().toString());


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+URL+"/api/v1/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

                                try {
                                    SharedPreferences.Editor editor = mPreferences.edit();
                                    String role = response.getString("role");
                                    editor.putString("token", response.getString("token"));
                                    editor.putString("login", loginBox.getText().toString());
                                    editor.commit();
                                    if (role.equals("admin")) {
                                        Intent intent = new Intent(context, AdminAccountActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(context, UserAccountActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Log.d("login", e.getMessage());


                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Fail to connect! Verify your login and password", Toast.LENGTH_LONG).show();

                            }
                        });

        queue.add(jsonObjectRequest);

    }


    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
        }
    }

}
