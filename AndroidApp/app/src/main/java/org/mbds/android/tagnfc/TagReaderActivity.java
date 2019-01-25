package org.mbds.android.tagnfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class TagReaderActivity extends Activity {

    public  static String TAG_UID = "org.mbds.android.tagnfc.UID";
    public  static String NAME = "org.mbds.android.tagnfc.NAME";


    private Tag tag;
    private Ndef ndef;
    public static String tagUID;
    public static String name;

    NfcAdapter nfcAdapter = null;
    PendingIntent pendingIntent;
    Intent myIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_reader);
        myIntent = getIntent();
    }

    @Override
    public void onResume() {
        super.onResume();
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).
                addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT), 0);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        onNewIntent(myIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent) {


        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if(tag != null) {
            ndef = Ndef.get(tag);
            Toast.makeText(this, "Tag detected !", Toast.LENGTH_SHORT).show();
        }
        if ((intent.getAction() != null) &&
                ((NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())))) {

            byte[] id = tag.getId();
            this.tagUID = byteArrayToHex(id).toUpperCase();
            verify(this.tagUID);
            Bundle bundle = new Bundle();
            bundle.putString(TagReaderActivity.NAME,
                    name);
            //bundle.putString(TagReaderActivity.TAG_UID,
                    //tagUID);
            Intent userActivity = new Intent(
                    getBaseContext(),
                    UserAccountActivity.class);
            userActivity.putExtras(bundle);
            startActivity(userActivity);
             finish();
        }
    }





    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }




    public void verify(String uid){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tagID", uid);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + LoginActivity.URL + "/api/v1/location/getLocation";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences mPreferences = getSharedPreferences("session", MODE_PRIVATE);
                                try {
                                    name = response.getString("name");
                                    UserAccountActivity.points.add(name);
                                } catch (Exception e) {
                                    name = "unknow location!";
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                name = "unknow location!";
                                UserAccountActivity.points.add(name);
                                Toast.makeText(TagReaderActivity.this, "Unknow location!!", Toast.LENGTH_LONG).show();
                            }
                        }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {

                SharedPreferences mPreferences = getSharedPreferences("session", MODE_PRIVATE);
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("x-access-token", mPreferences.getString("token", ""));

                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

}


