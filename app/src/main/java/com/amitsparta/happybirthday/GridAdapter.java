package com.amitsparta.happybirthday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridItemHolder> {

    private ArrayList arrayList;
    private Context context;

    public GridAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GridItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GridItemHolder extends RecyclerView.ViewHolder {

        private View itemView;

        public GridItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void displayItem(DataFile item) {
            ImageView image = (ImageView)itemView.findViewById(R.id.file_image);
            TextView fileName = (TextView)itemView.findViewById(R.id.file_name);

            image.setImageBitmap(item.getImage());
            fileName.setText(item.getFilePath());
        }
    }

}