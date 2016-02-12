package com.example.hao.tut31;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by hao on 2/12/2016.
 */
public class UserInfoActivity extends SingleFragmentActivity {
    private String mFirstName, mLastName, mEmail, mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstName = getIntent().getStringExtra(UserInfoFragment.EXTRA_FNAME);
        mLastName = getIntent().getStringExtra(UserInfoFragment.EXTRA_LNAME);
        mPhone = getIntent().getStringExtra(UserInfoFragment.EXTRA_PHONE);
        mEmail = getIntent().getStringExtra(UserInfoFragment.EXTRA_EMAIL);
    }

    @Override
    protected Fragment createFragment() {
        return UserInfoFragment.newInstance(mFirstName, mLastName, mEmail, mPhone);
    }
}
