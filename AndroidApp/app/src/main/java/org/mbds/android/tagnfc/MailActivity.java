package org.mbds.android.tagnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MailActivity extends AppCompatActivity {
    private TextView mEditTextTo;
    private TextView mEditTextSubject;
    private TextView mEditTextMessage;
    private String recipient;
    private  String subject;
    private  String message;
    private String[] recipients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);



        recipient = "fezai-ahmed@hotmail.fr";
        subject = "Rapport Ronde";
        message = UserAccountActivity.report(UserAccountActivity.points);
        recipients  = new String[10];
        recipients[1] = recipient;

        mEditTextTo.setText(recipient);
        mEditTextSubject.setText(subject);
        mEditTextMessage.setText(message);

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {

        UserAccountActivity.points.clear();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
