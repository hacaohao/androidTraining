package com.example.hao.tut31;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hao on 2/12/2016.
 */
public class UserInfoFragment extends Fragment {
    public static final String EXTRA_FNAME = "first_name";
    public static final String EXTRA_LNAME = "last_name";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_SALARY = "salary";

    private SeekBar mSeekBar;
    private TextView mTextView;
    private Button mButton;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;

    private String mFirstName, mLastName, mEmail, mPhone, mSalary;

    public static UserInfoFragment newInstance(String fName, String lName, String email, String phone) {
        Bundle agrs = new Bundle();

        agrs.putSerializable(EXTRA_FNAME, fName);
        agrs.putSerializable(EXTRA_LNAME, lName);
        agrs.putSerializable(EXTRA_EMAIL, email);
        agrs.putSerializable(EXTRA_PHONE, phone);

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(agrs);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstName = getArguments().getString(EXTRA_FNAME);
        mLastName = getArguments().getString(EXTRA_LNAME);
        mEmail = getArguments().getString(EXTRA_EMAIL);
        mPhone = getArguments().getString(EXTRA_PHONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, parent, false);

        mTextView = (TextView) view.findViewById(R.id.textViewSalary);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String salary = String.valueOf(progress / 100 * 100);
                mTextView.setText("Your salary: " + salary + " dollars");
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
        mSeekBar.incrementProgressBy(100);

        checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) view.findViewById(R.id.checkBox6);

        mButton = (Button) view.findViewById(R.id.done_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        return view;
    }

    private void validate() {
        if (checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked()
                || checkBox4.isChecked() || checkBox5.isChecked() || checkBox6.isChecked()) {
            Intent intent = new Intent(getActivity(), UserConfirmActivity.class);

            intent.putExtra(EXTRA_FNAME, mFirstName);
            intent.putExtra(EXTRA_LNAME, mLastName);
            intent.putExtra(EXTRA_EMAIL, mEmail);
            intent.putExtra(EXTRA_PHONE, mPhone);
            intent.putExtra(EXTRA_SALARY, mSalary);

            startActivity(intent);
        } else {
            Toast.makeText(
                    getActivity(),
                    "Select at least 1 sport",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
