package com.bignerdranch.android.chat.ViewAndLogic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.chat.R;

class ViewHolder extends RecyclerView.ViewHolder {
    private TextView message;

    ViewHolder(View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_area);
    }

    public void setMessage(String userMessage) {
        message.setText(userMessage);
    }
}
