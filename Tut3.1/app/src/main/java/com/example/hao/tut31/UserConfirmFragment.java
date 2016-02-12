package com.example.hao.tut31;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hao on 2/12/2016.
 */
public class UserConfirmFragment extends Fragment {
    private String mFirstName, mLastName, mEmail, mPhone, mSalary;

    public static UserConfirmFragment newInstance(String fName, String lName, String email, String phone, String salary) {
        Bundle agrs = new Bundle();

        agrs.putSerializable(UserInfoFragment.EXTRA_FNAME, fName);
        agrs.putSerializable(UserInfoFragment.EXTRA_LNAME, lName);
        agrs.putSerializable(UserInfoFragment.EXTRA_EMAIL, email);
        agrs.putSerializable(UserInfoFragment.EXTRA_PHONE, phone);
        agrs.putSerializable(UserInfoFragment.EXTRA_SALARY, salary);

        UserConfirmFragment fragment = new UserConfirmFragment();
        fragment.setArguments(agrs);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstName = getArguments().getString(UserInfoFragment.EXTRA_FNAME);
        mLastName = getArguments().getString(UserInfoFragment.EXTRA_LNAME);
        mEmail = getArguments().getString(UserInfoFragment.EXTRA_EMAIL);
        mPhone = getArguments().getString(UserInfoFragment.EXTRA_PHONE);
        mSalary = getArguments().getString(UserInfoFragment.EXTRA_SALARY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_confirm, parent, false);

        Button mSendButton = (Button) view.findViewById(R.id.send_button);
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

        Button mRestartButton = (Button) view.findViewById(R.id.restart_button);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserSignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }
}
