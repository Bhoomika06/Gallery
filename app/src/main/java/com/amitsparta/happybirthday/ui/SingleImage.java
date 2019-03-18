package com.amitsparta.happybirthday.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amitsparta.happybirthday.Adapters.CutCopyAdapter;
import com.amitsparta.happybirthday.DataFiles.Image;
import com.amitsparta.happybirthday.HelperClasses.FileIO;
import com.amitsparta.happybirthday.R;

public class SingleImage extends AppCompatActivity {

    public static final String IMAGE_INTENT_EXTRA = "imageExtra";
    private Image image;
    public static int imagePos = -1;

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
            case R.id.details_image:
                Toast.makeText(getApplicationContext(), "Under Construction.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.copy_image:
                showCopyFolderList();

                return true;
            case R.id.move_image:
                showMoveFolderList();
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

    private void showCopyFolderList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.main_page, null);
        CutCopyAdapter adapter = new CutCopyAdapter(getApplicationContext(), FolderActivity.folderList);
        RecyclerView recyclerView = view.findViewById(R.id.folder_list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        builder.setView(view);
        builder.setPositiveButton(R.string.copy_menu_item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (imagePos != -1) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getApplicationContext());
                            builder2.setMessage("Are you sure?");
                            builder2.setPositiveButton("Copy to " + FolderActivity.folderList.get(imagePos),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (FileIO.copyImage(image, FolderActivity.folderList.get(imagePos).toString())) {
                                                Toast.makeText(getApplicationContext(), "Copy successful.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Copy failed.", Toast.LENGTH_SHORT).show();
                                            }
                                            onBackPressed();
                                        }
                                    });
                            builder2.setNegativeButton("Cancel", null);

                            AlertDialog dialog2 = builder2.create();
                            dialog2.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Select a folder to copy to.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMoveFolderList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.main_page, null);
        CutCopyAdapter adapter = new CutCopyAdapter(getApplicationContext(), FolderActivity.folderList);
        RecyclerView recyclerView = view.findViewById(R.id.folder_list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        builder.setView(view);
        builder.setPositiveButton(R.string.move_menu_item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (imagePos != -1) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getApplicationContext());
                            builder2.setMessage("Are you sure?");
                            builder2.setPositiveButton("Move to " + FolderActivity.folderList.get(imagePos),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (FileIO.moveImage(image, FolderActivity.folderList.get(imagePos).toString())) {
                                                Toast.makeText(getApplicationContext(), "Moving successful.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Moving failed.", Toast.LENGTH_SHORT).show();
                                            }
                                            onBackPressed();
                                        }
                                    });
                            builder2.setNegativeButton("Cancel", null);

                            AlertDialog dialog2 = builder2.create();
                            dialog2.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Select a folder to move to.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
