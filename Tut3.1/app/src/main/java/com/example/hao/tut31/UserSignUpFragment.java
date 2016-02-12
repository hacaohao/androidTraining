package com.example.hao.tut31;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hao on 2/12/2016.
 */
public class UserSignUpFragment extends Fragment {
    private EditText mFirstNameEditText, mLastNameEditText, mEmailEditText, mPhoneEditText;
    private String mFirstName, mLastName, mEmail, mPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_sign_up, parent, false);

        mFirstNameEditText = (EditText) view.findViewById(R.id.editTextFirstName);

        mLastNameEditText = (EditText) view.findViewById(R.id.editTextLastName);

        mEmailEditText = (EditText) view.findViewById(R.id.editTextEmail);

        mPhoneEditText = (EditText) view.findViewById(R.id.editTextPhone);

        Button mNextButton = (Button) view.findViewById(R.id.btnNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewScreen();
            }
        });

        return view;
    }

    private void getInput() {
        mFirstName = mFirstNameEditText.getText().toString();
        mLastName = mLastNameEditText.getText().toString();
        mEmail = mEmailEditText.getText().toString();
        mPhone = mPhoneEditText.getText().toString();
    }

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
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra(UserInfoFragment.EXTRA_FNAME, mFirstName);
            intent.putExtra(UserInfoFragment.EXTRA_LNAME, mLastName);
            intent.putExtra(UserInfoFragment.EXTRA_EMAIL, mEmail);
            intent.putExtra(UserInfoFragment.EXTRA_PHONE, mPhone);
            startActivity(intent);
        }
    }
}
