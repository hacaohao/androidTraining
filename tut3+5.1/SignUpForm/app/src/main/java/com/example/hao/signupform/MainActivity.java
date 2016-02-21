package com.example.hao.signupform;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CAMERA = 0;
    public static final String PREF_IMAGE = "image";
    private EditText mFirstNameEditText, mLastNameEditText, mEmailEditText, mPhoneEditText;
    private ImageView mImageView;
    private String mFirstName, mLastName, mEmail, mPhone, mCurrentImage;
    private PictureUtils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUtils = new PictureUtils(this);
        mCurrentImage = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(PREF_IMAGE, null);

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

        mImageView = (ImageView) findViewById(R.id.image_view_take_camera);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                }
            });
        }
        showImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmapImage = (Bitmap) extras.get("data");
            mCurrentImage = mUtils.saveImage(bitmapImage);
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString(PREF_IMAGE, mCurrentImage).commit();
            showImage();
        }
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
        else if (mCurrentImage == null)
            Toast.makeText(this, "You must choose an avatar", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra(Main2Activity.EXTRA_FNAME, mFirstName);
            intent.putExtra(Main2Activity.EXTRA_LNAME, mLastName);
            intent.putExtra(Main2Activity.EXTRA_EMAIL, mEmail);
            intent.putExtra(Main2Activity.EXTRA_PHONE, mPhone);
            startActivity(intent);
        }
    }

    private void showImage() {
        if (mCurrentImage != null) {
            BitmapDrawable bitmap = mUtils.getImage(mCurrentImage);
            mImageView.setImageDrawable(bitmap);
        }
    }
}
