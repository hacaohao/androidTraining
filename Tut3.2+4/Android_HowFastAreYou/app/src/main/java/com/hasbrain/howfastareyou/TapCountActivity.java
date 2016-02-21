package com.hasbrain.howfastareyou;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TapCountActivity extends AppCompatActivity {
    private static final String COUNT = "tap count";
    private static final String TIME_USED = "time used";
    private static final String TIMER_TEXT = "timer text";

    @Bind(R.id.bt_tap)
    Button btTap;
    @Bind(R.id.bt_start)
    Button btStart;
    @Bind(R.id.tv_time)
    Chronometer tvTime;

    private HighscoreManager mManager;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private int mTimeCount;
    private boolean mIsSaveHighscore;

    private long mElapsedTime;
    private long mStartTime;
    private Date mLastHighscore = null;
    private int mHighestHighscore = 0;
    private int mTapCount = 0;
    private String mTimer = "00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ButterKnife.bind(this);

        mManager = HighscoreManager.get(this);
        mFragmentManager = getFragmentManager();

        mTimeCount = (PreferenceManager.getDefaultSharedPreferences(TapCountActivity.this)
                .getInt(SettingsActivity.TIMER, 5) + 5) * 1000;
        mIsSaveHighscore = PreferenceManager.getDefaultSharedPreferences(TapCountActivity.this)
                .getBoolean(SettingsActivity.HIGH_SCORE, true);

        mElapsedTime = mTimeCount;

        if(savedInstanceState != null){
            mTapCount = savedInstanceState.getInt(COUNT);
            mTimer = savedInstanceState.getString(TIMER_TEXT);
            mElapsedTime = savedInstanceState.getLong(TIME_USED);
        }

        setButtonStartText();

        tvTime.setText(mTimer);
        tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - mStartTime >= mTimeCount) {
                    btStart.setText(R.string.bt_start_text);
                    stopTapping();
                }
            }
        });

        mFragment = mFragmentManager.findFragmentById(R.id.fragmentContainer);
        if(mFragment == null) {
            mFragment = HighscoreFragment.newInstance(mLastHighscore);
            mFragmentManager.beginTransaction().add(R.id.fragmentContainer, mFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent showSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(showSettingsActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);

        String chronoText = tvTime.getText().toString();
        String array[] = chronoText.split(":");
        mElapsedTime = Integer.parseInt(array[1]) * 1000;

        outState.putInt(COUNT, mTapCount);
        outState.putLong(TIME_USED, mElapsedTime);
        outState.putString(TIMER_TEXT, tvTime.getText().toString());
    }

    @Override
    protected void onPause(){
        tvTime.stop();
        btTap.setEnabled(false);
        btStart.setEnabled(true);
        setButtonStartText();
        super.onPause();
    }

    @OnClick(R.id.bt_start)
    public void onStartBtnClicked(View v) {
        if(mElapsedTime >= mTimeCount || mElapsedTime == 0) {
            startTapping();
        }else{
            resumeTapping();
        }
    }

    @OnClick(R.id.bt_tap)
    public void onTapBtnClicked(View v) {
        TapCountResultFragment fragment = (TapCountResultFragment) mFragment;
        mTapCount++;
        fragment.addNewTap(mTapCount);

    }

    private void startTapping() {
        updateFragment(new TapCountResultFragment());
        mTimeCount = (PreferenceManager.getDefaultSharedPreferences(TapCountActivity.this)
                .getInt(SettingsActivity.TIMER, 5) + 5) * 1000;
        updateUI(SystemClock.elapsedRealtime());

    }

    private void stopTapping() {
        mIsSaveHighscore = PreferenceManager.getDefaultSharedPreferences(TapCountActivity.this)
                .getBoolean(SettingsActivity.HIGH_SCORE, true);
        if (mIsSaveHighscore) {
            int highestHighscore = mManager.getHighestHighscore();
            if(mTapCount > highestHighscore) Toast.makeText(this, R.string.highscore_congrat, Toast.LENGTH_LONG).show();
            mManager.insertHighscore(mTapCount);
            mLastHighscore = mManager.getLastHighscore();
        }else{
            mLastHighscore = null;
        }
        if(mTapCount > 0){
            mTapCount = 0;
        }
        updateFragment(HighscoreFragment.newInstance(mLastHighscore));
        tvTime.stop();
        btTap.setEnabled(false);
        btStart.setEnabled(true);
        mElapsedTime = mTimeCount;
        tvTime.setText("00:00");
    }

    private void resumeTapping(){
        updateUI(SystemClock.elapsedRealtime() - mElapsedTime);
    }

    private void updateUI(long start){
        mStartTime = start;
        tvTime.setBase(start);
        tvTime.start();
        btTap.setEnabled(true);
        btStart.setEnabled(false);
    }

    private void updateFragment(Fragment fragment){
        Fragment oldFragment = mFragmentManager.findFragmentById(R.id.fragmentContainer);
        mFragment = fragment;
        mFragmentManager.beginTransaction().remove(oldFragment).add(R.id.fragmentContainer, mFragment).commit();
    }

    private void setButtonStartText(){
        if(mElapsedTime < mTimeCount && mElapsedTime != 0) {
            btStart.setText(R.string.bt_resume_text);
        }else{
            btStart.setText(R.string.bt_start_text);
        }
    }
}
