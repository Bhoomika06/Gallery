package com.amitsparta.happybirthday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class gridAdapter extends ArrayAdapter {

    private ArrayList arrayList;

    public gridAdapter(@NonNull Context context, ArrayList<DataFile> arrayList) {
        super(context, 0);
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataFile dataFile =(DataFile) arrayList.get(position);

        if(convertView == null)
            convertView = (View) LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);
        imageView.setImageResource(dataFile.getImage());
        textView.setText(dataFile.getTextToDisplay());

        return convertView;

    }

}
