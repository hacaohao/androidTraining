package com.hasbrain.howfastareyou;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/14/15.
 */
public class SettingsActivity extends AppCompatActivity {
    public static final String TIMER = "Timer";
    public static final String HIGH_SCORE = "High score";

    private int mTimer;
    private boolean mSwitchCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.settings_text);

        mTimer = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(TIMER, 5);
        mSwitchCheck = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(HIGH_SCORE, true);

        final TextView textView = (TextView) findViewById(R.id.text_view_time_count);
        textView.setText(getString(R.string.timer, String.valueOf(mTimer + 5)));

        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar_time_count);
        seekBar.setMax(55);
        seekBar.setProgress(mTimer);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value = mTimer;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = (progress / 5) * 5;
                textView.setText(getString(R.string.timer, String.valueOf(value + 5)));
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                        .edit().putInt(TIMER, value).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Switch highscoreSwitch = (Switch) findViewById(R.id.switch_high_score);
        highscoreSwitch.setChecked(mSwitchCheck);
        highscoreSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                        .edit().putBoolean(HIGH_SCORE, isChecked).commit();
            }
        });
    }
}
