package com.example.k.geode;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_IMAGE_ID = "com.example.k.geode.extra_image_id";
    private static final String EXTRA_IMAGE_NAME = "com.example.k.geode.extra_image_name";

    private static final String TAG = "Geode";

    private ImageButton mNextButton;
    private Button mSendButton;
    private ImageView mImage;
    private int mImageListIndex = 0;

    Image[] mImages = new Image[] {
            new Image(R.drawable.images, "images.jpg"),
            new Image(R.drawable.images1, "images1.jpg"),
            new Image(R.drawable.beach, "beach.jpg"),
            new Image(R.drawable.beach1, "beach1.jpg")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate(Bundle) called");

        mImage = (ImageView) findViewById(R.id.image_birthday);
        mImage.setImageResource(mImages[mImageListIndex].getResId());

        mNextButton = (ImageButton) findViewById(R.id.next_imageButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mImageListIndex != mImages.length) {
                        mImageListIndex++;
                        mImage.setImageResource(mImages[mImageListIndex].getResId());
                    }
                }catch (ArrayIndexOutOfBoundsException arrayE) {
                    mImageListIndex = 0;
                    mImage.setImageResource(mImages[mImageListIndex].getResId());
                }
            }
        });

        mSendButton = (Button) findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendEmailActivity.class);
                int currentImageIndex = mImages[mImageListIndex].getResId();
                String currentImageName = mImages[mImageListIndex].getImageName();
                intent.putExtra(EXTRA_IMAGE_ID, currentImageIndex);
                intent.putExtra(EXTRA_IMAGE_NAME, currentImageName);

                startActivity(intent);
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


}
