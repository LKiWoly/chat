package com.bignerdranch.android.chat.ViewAndLogic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.chat.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<String> messages;
    private LayoutInflater inflater;

    public DataAdapter(Context context, ArrayList<String> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.message_area, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String msg = messages.get(i);
        viewHolder.setMessage(msg);
    }

    @Override
    public int getItemCount() {
        System.out.println(messages.size());
        return messages.size();
    }
}
