package com.amitsparta.happybirthday.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.R;
import com.amitsparta.happybirthday.ui.SingleImage;

import java.util.ArrayList;

public class CutCopyAdapter extends RecyclerView.Adapter<CutCopyAdapter.FolderNameHolder> {

    private Context context;
    private ArrayList folderList;
    private int pos = 0;

    public CutCopyAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.folderList = arrayList;
    }

    @NonNull
    @Override
    public FolderNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderNameHolder(LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderNameHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = SingleImage.imagePos = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class FolderNameHolder extends RecyclerView.ViewHolder {

        TextView textView;
        private View view;

        public FolderNameHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        private void displayList(Folder folder) {
            textView = view.findViewById(R.id.file_name);
            textView.setText(folder.toString());
        }
    }
}
