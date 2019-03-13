package com.amitsparta.happybirthday.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
                confirmFromUser();
                return true;
            case R.id.rename_image:
                renameFile();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmFromUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");
        builder.setPositiveButton(R.string.delete_menu_item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileIO.deleteImage(image)) {
                            Toast.makeText(getApplicationContext(), "Delete successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Delete failed.", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();
                    }
                });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void renameFile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.rename_dialog, null);

        builder.setView(view);
        builder.setPositiveButton(R.string.rename_menu_item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editView = view.findViewById(R.id.renamed_file_name);
                        String name = editView.getText().toString().trim();
                        if (!name.equals("")) {
                            if (FileIO.renameImage(image, name)) {
                                Toast.makeText(getApplicationContext(), "Rename successful.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Rename failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
