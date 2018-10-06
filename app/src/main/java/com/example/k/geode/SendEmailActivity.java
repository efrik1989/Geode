package com.example.k.geode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


/** Класс используется для отправки открытки по e-mail или социалки
 *
 */

public class SendEmailActivity extends Activity {
    private static final String EXTRA_IMAGE_FILE_PATH = "com.example.k.geode.extra_image_file_path";

    private static final String TAG = "Geode";

    private ImageView mWallpaper;
    private Button mAcceptButton;
    private EditText mEmailAddress;
    private EditText mSubject;
    private EditText mCongratulations;
    private Bitmap yourSelectedImage;
    private String filePath;
    private DrawView mDrawView;
    private Canvas canvas;
    private Bitmap mutableBitmap;

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

        //назначение экземляра класса прорисовки
        mDrawView = new DrawView(SendEmailActivity.this);

        mAcceptButton = (Button) findViewById(R.id.accept_button);
        mAcceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {mEmailAddress.getText().toString()});


                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mSubject.getText().toString());
                emailIntent.setType("text/plain");



                mutableBitmap = yourSelectedImage.copy(Bitmap.Config.ARGB_8888, true);

                //прорисовка начинается здесь
                canvas = new Canvas(mutableBitmap);
                mDrawView.draw(canvas);

                try {
                    Uri imageUri = getImageUri(getApplicationContext(), mutableBitmap);
                    emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
                    emailIntent.setType("image/*");

                }catch (NullPointerException npe) {
                    npe.getStackTrace();
                }

                SendEmailActivity.this.startActivity(Intent.createChooser(emailIntent, "Отправка письма..."));

                mutableBitmap.recycle();
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

    // Класс отрисовки поздравления в изображении

    public class DrawView extends View {

        Paint mPaint;

        public DrawView(Context context) {
            super(context);
            mPaint = new Paint();
        }
        @Override
        public void onDraw(Canvas canvas){
            Log.d(TAG, "onDraw() called");

            mPaint.setTextSize(100);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            canvas.drawText(mSubject.getText().toString(),canvas.getWidth() / 2,canvas.getHeight()/2 + 500, mPaint);

            canvas.drawText(mCongratulations.getText().toString(), canvas.getWidth() / 2,canvas.getHeight()/2 + 1500, mPaint);

            canvas.setBitmap(mutableBitmap);
        }
    }
}
