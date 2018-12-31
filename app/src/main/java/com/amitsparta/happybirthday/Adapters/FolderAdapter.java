package com.amitsparta.happybirthday.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitsparta.happybirthday.DataFiles.DataFile;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.ImageListActivity;
import com.amitsparta.happybirthday.R;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderHolder> {

    private Context context;
    private ArrayList folders;

    public FolderAdapter(Context context, ArrayList folders) {
        this.context = context;
        this.folders = folders;
    }

    @NonNull
    @Override
    public FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new FolderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderHolder holder, final int position) {
        holder.displayItem((Folder) folders.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageListActivity.class);
                intent.putExtra(ImageListActivity.FOLDER_INTENT_EXTRA, (Folder) folders.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    class FolderHolder extends RecyclerView.ViewHolder {

        private View itemView;

        FolderHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void displayItem(Folder item) {
            ImageView image = itemView.findViewById(R.id.file_image);
            TextView fileName = itemView.findViewById(R.id.file_name);

            DataFile file = item.getImages().get(0);
            image.setImageBitmap(file.createThumbnail());
            fileName.append(item.getFolderName());
        }
    }
}
