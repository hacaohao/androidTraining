package com.example.hao.signupform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mFirstNameEditText, mLastNameEditText, mEmailEditText, mPhoneEditText;
    private String mFirstName, mLastName, mEmail, mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstNameEditText = (EditText) findViewById(R.id.editTextFirstName);

        mLastNameEditText = (EditText) findViewById(R.id.editTextLastName);

        mEmailEditText = (EditText) findViewById(R.id.editTextEmail);

        mPhoneEditText = (EditText) findViewById(R.id.editTextPhone);

        Button mNextButton = (Button) findViewById(R.id.btnNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewScreen();
            }
        });
    }

    //lay input cua user
    private void getInput() {
        mFirstName = mFirstNameEditText.getText().toString();
        mLastName = mLastNameEditText.getText().toString();
        mEmail = mEmailEditText.getText().toString();
        mPhone = mPhoneEditText.getText().toString();
    }

    //Khi nhan nut NEXT, validate input va throw loi tuong duong, neu khong co loi thi tao man hinh moi
    private void startNewScreen() {
        getInput();

        boolean isfNameValid = !mFirstName.isEmpty();
        boolean islNameValid = !mLastName.isEmpty();
        boolean isEmailEmpty = !mEmail.isEmpty();
        boolean isPhoneEmpty = !mPhone.isEmpty();
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(mEmail).matches();
        boolean isPhoneValid = Patterns.PHONE.matcher(mPhone).matches();

        if (!isfNameValid) mFirstNameEditText.setError("You must enter first name");
        else if (!islNameValid) mLastNameEditText.setError("You must enter last name");
        else if (!isEmailEmpty) mEmailEditText.setError("You must enter email");
        else if (!isEmailValid) mEmailEditText.setError("Email is invalid");
        else if (!isPhoneEmpty) mPhoneEditText.setError("You must enter phone");
        else if (!isPhoneValid) mPhoneEditText.setError("PhoneNumber is invalid");
        else {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra(Main2Activity.EXTRA_FNAME, mFirstName);
            intent.putExtra(Main2Activity.EXTRA_LNAME, mLastName);
            intent.putExtra(Main2Activity.EXTRA_EMAIL, mEmail);
            intent.putExtra(Main2Activity.EXTRA_PHONE, mPhone);
            startActivity(intent);
        }
    }
}
