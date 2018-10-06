package com.example.k.geode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class SendEmailActivity extends Activity {
    private static final String EXTRA_IMAGE_ID = "com.example.k.geode.extra_image_id";
    private static final String EXTRA_IMAGE_NAME = "com.example.k.geode.extra_image_name";
    private static final String EXTRA_IMAGE_OBJECT = "com.example.k.geode.extra_image_object";
    private static final String EXTRA_IMAGE_FILE_PATH = "com.example.k.geode.extra_image_file_path";

    private static final String TAG = "Geode";

    private ImageView mWallpaper;
    private Button mAcceptButton;
    private EditText mEmailAddress;
    private EditText mSubject;
    private EditText mCongratulations;
    private Bitmap yourSelectedImage;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_actyvity);

        Log.d(TAG, ".SendEmailActivity.class onCreate() called");

        mWallpaper = (ImageView) findViewById(R.id.wallpaper);
        filePath =  getIntent().getStringExtra(EXTRA_IMAGE_FILE_PATH);
        yourSelectedImage = BitmapFactory.decodeFile(filePath);
        mWallpaper.setImageBitmap(yourSelectedImage);

        mEmailAddress = (EditText) findViewById(R.id.editText);

        mCongratulations = (EditText) findViewById(R.id.editText2);

        mSubject = (EditText) findViewById(R.id.subject);

        mAcceptButton = (Button) findViewById(R.id.accept_button);
        mAcceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                File file = new File("app/src/main/res/drawable/");

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {mEmailAddress.getText().toString()});


                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mSubject.getText().toString());
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mCongratulations.getText().toString());


                try {

                    Uri imageUri = getImageUri(getApplicationContext(), yourSelectedImage);
                    emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
                    emailIntent.setType("image/*");

                }catch (NullPointerException npe) {
                    npe.getStackTrace();
                }

                SendEmailActivity.this.startActivity(Intent.createChooser(emailIntent, "Отправка письма..."));
            }
        });

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Log.d(TAG, "SendEmailActivity.getImageUri() called");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
