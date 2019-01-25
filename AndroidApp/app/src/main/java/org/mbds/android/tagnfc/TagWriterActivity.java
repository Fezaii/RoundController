package org.mbds.android.tagnfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;

public class TagWriterActivity extends Activity {

    private static String message;
    private String messageNDEF;
    private Tag tag;
    private Ndef ndef;
    private TextView response;

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    NfcAdapter nfcAdapter = null;
    PendingIntent pendingIntent;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_writer);
        // message à écrire ?
        myIntent = getIntent();
        response = findViewById(R.id.response_txt);
        try {
            message = myIntent.getStringExtra(MainActivity.MESSAGE);
        } catch (Exception e) {
        }
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
        }
        if ((intent.getAction() != null) &&
                ((NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())))) {


            write(message);

            //readNdef(tag);


            /*Bundle bundle = new Bundle();
            bundle.putString(MainActivity.MESSAGE_NDEF,
                    messageNDEF);
            Intent mainActivity = new Intent(
                    getBaseContext(),
                    MainActivity.class);
            mainActivity.putExtras(bundle);
            startActivity(mainActivity);*/

            finish();
        }
    }

    /**
     * Read the tag
     */
    /*public boolean readNdef(Tag tag) {
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            Toast.makeText(this, "Tag detected !", Toast.LENGTH_SHORT).show();
            try {
                ndef.connect();
                NdefRecord[] records = ndef.getNdefMessage().getRecords();
                messageNDEF = "";
                if (records != null) {
                    for (int i = 0; i < records.length; i++) {
                        NdefRecord record = records[i];
                        messageNDEF += new String(record.getPayload(), UTF8_CHARSET) + " ";
                    }
                }
                ndef.close();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }*/


    public boolean write(String str){
        if (ndef != null) {
            try {
                ndef.connect();
                NdefRecord mimeRecord = NdefRecord.createMime("text/plain", str.getBytes(Charset.forName("UTF-8")));
                ndef.writeNdefMessage(new NdefMessage(mimeRecord));
                Toast.makeText(this, "message writed successfly !", Toast.LENGTH_SHORT).show();
                response.setText("message writed successfly !");
                ndef.close();

            } catch (IOException | FormatException e) {
                e.printStackTrace();
                response.setText("Fail to write the message !");
                Toast.makeText(this, "Fail to write the message !", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}

