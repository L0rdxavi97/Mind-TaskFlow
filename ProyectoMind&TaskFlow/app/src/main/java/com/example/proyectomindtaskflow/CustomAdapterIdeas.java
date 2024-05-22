package com.example.proyectomindtaskflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterIdeas extends ArrayAdapter<IdeaWrapper> {

    private final Context context;
    private final List<IdeaWrapper> items;

    public CustomAdapterIdeas(Context context, List<IdeaWrapper> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.itemTitle);
        TextView subtitleView = convertView.findViewById(R.id.itemSubtitle);

        IdeaWrapper currentItem = items.get(position);
        titleView.setText(currentItem.getTitle());
        subtitleView.setText(currentItem.getDescription());

        return convertView;
    }
}
