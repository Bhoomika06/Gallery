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

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridItemHolder> {

    private ArrayList itemList;
    private Context context;

    public GridAdapter(Context context, ArrayList itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GridItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent);
        return new GridItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemHolder holder, int position) {
        holder.displayItem((DataFile) itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class GridItemHolder extends RecyclerView.ViewHolder {

        private View itemView;

        GridItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void displayItem(DataFile item) {
            ImageView image = itemView.findViewById(R.id.file_image);
            TextView fileName = itemView.findViewById(R.id.file_name);

            image.setImageBitmap(item.getImage());
            fileName.setText(item.getFilePath());
        }
    }

}