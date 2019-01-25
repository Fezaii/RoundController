package org.mbds.android.tagnfc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {


	public final static String MESSAGE = "org.mbds.android.tagnfc.MESSAGE";

    private EditText editTextMessage;
    private Button btnWrite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  		btnWrite = findViewById(R.id.buttonWrite);
		editTextMessage = findViewById(R.id.textViewTagNFC);

		btnWrite.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String message = ((EditText) findViewById(R.id.textViewTagNFC))
						.getText().toString();
				if (message != null & !message.trim().isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage(
							"Voulez-vous encoder ce message sur le tag NFC ?")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
                                            Intent intent = new Intent( MainActivity.this, TagWriterActivity.class );
                                            intent.putExtra(MainActivity.MESSAGE, message);
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
				} else {
					editTextMessage.setError("Vous devez indiquer un message dans la zone de texte !");
				}

			}

		});
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}

