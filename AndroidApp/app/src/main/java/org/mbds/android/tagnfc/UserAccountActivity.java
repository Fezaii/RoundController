package org.mbds.android.tagnfc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccountActivity extends AppCompatActivity {

    public  static final String FILE_NAME = "example.txt";

    private TextView position;
    private TextView position10;
    public  static List<String> points = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        position = findViewById(R.id.position_txt);
        position10 = findViewById(R.id.position10_txt);
        setTitle("User ACCOUNT");
    }


    public void Scan(View v) {
        Intent intent   = new Intent(this, TagReaderActivity.class);
        startActivity(intent);
    }

    public void logout(View v) {
        Intent intent   = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void endOfround(View v) {
        points.add("Ronde faite par : "+this.getSharedPreferences("session", MODE_PRIVATE).getString("login",""));
        AlertDialog.Builder builder = new AlertDialog.Builder(
                UserAccountActivity.this);
        builder.setMessage(
                "Voulez-vous Vraiment finir votre Ronde?")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                save(report(points));
                                Intent intent = new Intent(UserAccountActivity.this,MailActivity.class );
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface arg0, int id) {
                            }

                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            Bundle bundle = this.getIntent().getExtras();
            //position10.setText(bundle.getString(TagReaderActivity.TAG_UID, ""));
            position.setText(bundle.getString(TagReaderActivity.NAME, ""));
        } catch (Exception e) {
        }
    }




    public static String report(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String m : list ){
            sb.append(m).append("\n");
        }
        return  sb.toString();
    }






    public void save(String text) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(UserAccountActivity.FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + UserAccountActivity.FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


