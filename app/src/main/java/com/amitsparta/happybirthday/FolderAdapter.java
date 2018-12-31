package com.amitsparta.happybirthday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderHolder> {

    private Context context;
    private Folder folders[];

    FolderAdapter(Context context, HashSet<Folder> hashSet ) {
        this.context = context;
        folders = hashSet.toArray(new Folder[hashSet.size()]);
    }

    @NonNull
    @Override
    public FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent);
        return new FolderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderHolder holder, int position) {
        try {
            holder.displayItem(folders[position]);
        }catch (ArrayIndexOutOfBoundsException e) {
            holder.displayItem(folders[position - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return folders.length;
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
            image.setImageBitmap(file.getImage());
            fileName.setText(file.getFilePath());
        }
    }
}
