package com.example.hao.signupform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    public static final String EXTRA_FNAME = "first_name";
    public static final String EXTRA_LNAME = "last_name";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_SALARY = "salary";

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;

    private String mFirstName, mLastName, mEmail, mPhone, mSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mFirstName = getIntent().getStringExtra(EXTRA_FNAME);
        mLastName = getIntent().getStringExtra(EXTRA_LNAME);
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mPhone = getIntent().getStringExtra(EXTRA_PHONE);

        final TextView mTextView = (TextView) findViewById(R.id.textViewSalary);

        SeekBar mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String salary = String.valueOf(progress / 100 * 100);
                mTextView.setText(getString(R.string.salary_text, salary));
                mSalary = salary;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBar.setMax(100000);
        mSeekBar.setProgress(32800);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);

        Button mButton = (Button) findViewById(R.id.done_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        if (checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked()
                || checkBox4.isChecked() || checkBox5.isChecked() || checkBox6.isChecked()) {
            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);

            intent.putExtra(EXTRA_FNAME, mFirstName);
            intent.putExtra(EXTRA_LNAME, mLastName);
            intent.putExtra(EXTRA_EMAIL, mEmail);
            intent.putExtra(EXTRA_PHONE, mPhone);
            intent.putExtra(EXTRA_SALARY, mSalary);

            startActivity(intent);
        } else {
            Toast.makeText(
                    Main2Activity.this,
                    "Select at least 1 sport",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
