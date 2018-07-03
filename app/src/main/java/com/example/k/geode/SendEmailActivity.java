package com.example.k.geode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SendEmailActivity extends Activity {
    private static final String EXTRA_IMAGE_ID = "com.example.k.geode.extra_image_id";
    private static final String EXTRA_IMAGE_NAME = "com.example.k.geode.extra_image_name";

    private static final String TAG = "Geode";

    private ImageView mWallpaper;
    private Button mAcceptButton;
    private EditText mEmailAddress;
    private EditText mSubject;
    private EditText mCongratulations;
    private String mImageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_actyvity);

        Log.d(TAG, ".SendEmailActivity.class onCreate() called");

        mWallpaper = (ImageView) findViewById(R.id.wallpaper);
        int firstActivityImageId = getIntent().getIntExtra(EXTRA_IMAGE_ID,0);
        mWallpaper.setImageResource(firstActivityImageId);

        mEmailAddress = (EditText) findViewById(R.id.editText);

        mCongratulations = (EditText) findViewById(R.id.editText2);

        mSubject = (EditText) findViewById(R.id.subject);

        mImageName = getIntent().getStringExtra(EXTRA_IMAGE_NAME);

        mAcceptButton = (Button) findViewById(R.id.accept_button);
        mAcceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {mEmailAddress.getText().toString()});


                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, new String[] {mSubject.getText().toString()});
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, new String[] {mCongratulations.getText().toString()});


                emailIntent.setType("image/jpg");
                Uri imageUri = Uri.parse("android.resource://com.example.k.geode/res/drawable/" + mImageName);
                emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);


                SendEmailActivity.this.startActivity(Intent.createChooser(emailIntent, "Отправка письма..."));
            }
        });

    }
}
