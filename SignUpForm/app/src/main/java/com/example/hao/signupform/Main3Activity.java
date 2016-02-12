package com.example.hao.signupform;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {
    private String mFirstName, mLastName, mEmail, mPhone, mSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mFirstName = getIntent().getStringExtra(Main2Activity.EXTRA_FNAME);
        mLastName = getIntent().getStringExtra(Main2Activity.EXTRA_LNAME);
        mEmail = getIntent().getStringExtra(Main2Activity.EXTRA_EMAIL);
        mPhone = getIntent().getStringExtra(Main2Activity.EXTRA_PHONE);
        mSalary = getIntent().getStringExtra(Main2Activity.EXTRA_SALARY);

        Button mSendButton = (Button) findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailBody = mFirstName + "_" + mLastName + "\n"
                        + mPhone + "\n" + mSalary + "dollars";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, mEmail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "User's registration info.");
                intent.putExtra(Intent.EXTRA_TEXT, emailBody);
                startActivity(Intent.createChooser(intent, "Send via"));
            }
        });

        Button mRestartButton = (Button) findViewById(R.id.restart_button);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
