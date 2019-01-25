package org.mbds.android.tagnfc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AdminAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);
        setTitle("ADMIN ACCOUNT");
    }


    public void AddUser(View v) {
        Intent intent   = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    public void AddLocation(View v) {
        //Intent intent1 = new Intent(this, TagReaderActivity.class);
        //startActivity(intent1);

        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }

    public void quit(View v) {
        Intent intent   = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteLocation(View v) {
        Intent intent = new Intent(this, DeleteLocationActivity.class);
        startActivity(intent);
    }

    public void deleteUser(View v) {
        Intent intent = new Intent(this, DeleteUserActivity.class);
        startActivity(intent);
    }

    public void history(View v) {
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }
}

