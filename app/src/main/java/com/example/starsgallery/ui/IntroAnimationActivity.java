package com.example.starsgallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.starsgallery.R;

public class IntroAnimationActivity extends AppCompatActivity {

    private ImageView animatedLogo;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_intro);

        animatedLogo = findViewById(R.id.animatedLogo);

        animatedLogo.animate().rotation(360f).setDuration(2000);
        animatedLogo.animate().scaleX(0.5f).scaleY(0.5f).setDuration(3000);
        animatedLogo.animate().translationYBy(1000f).setDuration(2000);
        animatedLogo.animate().alpha(0f).setDuration(6000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent nextScreen = new Intent(IntroAnimationActivity.this, MainGalleryActivity.class);
                    startActivity(nextScreen);
                    finish();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}