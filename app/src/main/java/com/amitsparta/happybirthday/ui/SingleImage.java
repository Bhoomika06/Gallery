package com.amitsparta.happybirthday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.amitsparta.happybirthday.DataFiles.Image;
import com.amitsparta.happybirthday.HelperClasses.FileIO;
import com.amitsparta.happybirthday.R;

public class SingleImage extends AppCompatActivity {

    public static final String IMAGE_INTENT_EXTRA = "imageExtra";
    private Image image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        Intent intent = getIntent();

        if (intent.hasExtra(IMAGE_INTENT_EXTRA)) {
            image = (Image) intent.getSerializableExtra(IMAGE_INTENT_EXTRA);
        }

        ImageView imageView = findViewById(R.id.single_image);
        imageView.setImageBitmap(image.createImageOriginal());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_extra_featues_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_image:
                FileIO.deleteImage(image);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
