package com.example.k.geode;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_IMAGE_FILE_PATH = "com.example.k.geode.extra_image_file_path";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private static final String TAG = "Geode";

    private Button mFileSelectorButton;
    private Button mSendButton;
    private ImageView mImage;
    private Bitmap yourSelectedImage;
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate(Bundle) called");

        mImage = (ImageView) findViewById(R.id.image_birthday);
        mImage.setImageResource(R.drawable.images);

        mSendButton = (Button) findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (yourSelectedImage == null){
                    mSendButton.setEnabled(false);
                }else {
                    Intent intent = new Intent(MainActivity.this, SendEmailActivity.class);
                    intent.putExtra(EXTRA_IMAGE_FILE_PATH, filePath);

                    startActivity(intent);
                }
            }
        });

        //запрос галереи по нажатию кнопки
        mFileSelectorButton = (Button) findViewById(R.id.file_selector_button);
        mFileSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Select Button Pressed");
                try {
                    // Проверка разрешения на использованине файлов из галареи
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Log.d(TAG, "Permission is not Granted");

                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        }

                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        final int ACTIVITY_SELECT_IMAGE = 1234;
                        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonEnabledChek(yourSelectedImage);
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStope called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    // получает изображение
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "onActivityForResult(int requestCode, int resultCode, Intent data) called");

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234 :
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    if(selectedImage  != null) {
                        try {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            cursor.moveToFirst();


                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            filePath = cursor.getString(columnIndex);
                            cursor.close();

                            yourSelectedImage = BitmapFactory.decodeFile(filePath);
                            mImage.setImageBitmap(yourSelectedImage);
                        } catch (NullPointerException npe) {
                            npe.printStackTrace();
                        }
                    }
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void buttonEnabledChek(Bitmap yourSelectedImage) {
        if (yourSelectedImage == null) {
            mSendButton.setEnabled(false);
            mSendButton.setTextColor(Color.DKGRAY);
        } else {
            mSendButton.setEnabled(true);
            mSendButton.setTextColor(Color.WHITE);
        }
    }

}
