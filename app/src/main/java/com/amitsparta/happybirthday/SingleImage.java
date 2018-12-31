package com.amitsparta.happybirthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.amitsparta.happybirthday.DataFiles.DataFile;

public class SingleImage extends AppCompatActivity {

    public static final String IMAGE_INTENT_EXTRA = "imageExtra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        Intent intent = getIntent();
        DataFile dataFile = null;
        if (intent.hasExtra(IMAGE_INTENT_EXTRA)) {
            dataFile = (DataFile) intent.getSerializableExtra(IMAGE_INTENT_EXTRA);
        }

        ImageView imageView = findViewById(R.id.single_image);
        imageView.setImageBitmap(dataFile.createImageOriginal());
    }
}
