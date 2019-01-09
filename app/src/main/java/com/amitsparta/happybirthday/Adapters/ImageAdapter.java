package com.amitsparta.happybirthday.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitsparta.happybirthday.DataFiles.Image;
import com.amitsparta.happybirthday.R;
import com.amitsparta.happybirthday.ui.SingleImage;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.GridItemHolder> {

    private ArrayList itemList;
    private Context context;

    public ImageAdapter(Context context, ArrayList itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GridItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new GridItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemHolder holder, final int position) {
        holder.displayItem((Image) itemList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleImage.class);
                intent.putExtra(SingleImage.IMAGE_INTENT_EXTRA, (Image) itemList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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

        @SuppressLint("StaticFieldLeak")
        void displayItem(Image item) {
            final ImageView image = itemView.findViewById(R.id.file_image);
            TextView fileName = itemView.findViewById(R.id.file_name);

            fileName.setText(item.getFileName());

            new AsyncTask<Image, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Image... strings) {
                    return strings[0].createThumbnail();
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            }.execute(item);
        }
    }

}