package com.example.hao.tut31;

import android.app.Fragment;

public class UserSignUpActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new UserSignUpFragment();
    }
}
