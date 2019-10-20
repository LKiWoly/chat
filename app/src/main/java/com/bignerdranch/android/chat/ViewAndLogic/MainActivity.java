package com.bignerdranch.android.chat.ViewAndLogic;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.chat.R;
import com.bignerdranch.android.chat.Utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(Constants.DATA_NAME);

    EditText editTextUserMessage;
    Button sendMessageButton;
    RecyclerView messageRecycler;
    AlertDialog.Builder alertDialogNickName;

    ArrayList<String> messages = new ArrayList<>();
    String userNickName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInstance(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        if (userNickName.equals("")) {
            enterUserNick();
        }

        setListeners();
    }

    private void setListeners() {
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextUserMessage.getText().toString();

                if (message.length() > Constants.MAX_LENGTH_MESSAGE) {
                    Toast.makeText(getApplicationContext(), Constants.TOAST_TEXT_MAX_LENGTH_MESSAGE,Toast.LENGTH_SHORT).show();
                    return;
                }


                myRef.push().setValue(userNickName + ": " + message);
                editTextUserMessage.setText("");
            }
        });

        messageRecycler.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, messages);
        messageRecycler.setAdapter(dataAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String message = dataSnapshot.getValue(String.class);
                messages.add(message);
                dataAdapter.notifyDataSetChanged();
                messageRecycler.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void enterUserNick() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogNickName = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(Constants.ALERT_DIALOG_TITLE_TEXT)
                .setMessage(Constants.ALERT_DIALOG_MESSAGE_TEXT)
                .setView(input)
                .setNegativeButton(Constants.ALERT_DIALOG_NEGATIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setPositiveButton(Constants.ALERT_DIALOG_POSITIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userNickName = input.getText().toString().toUpperCase();
                        if (userNickName.equals("")) {
                            userNickName = Constants.DEFAULT_USER_NICK_NAME;
                        }
                    }
                });
        alertDialogNickName.show();
    }

    private void initViews() {
        editTextUserMessage = findViewById(R.id.message_input);
        sendMessageButton = findViewById(R.id.send_message_button);
        messageRecycler = findViewById(R.id.message_recycler);
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            userNickName = savedInstanceState.getString(Constants.SAVE_KEY_USER_NICK);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.SAVE_KEY_USER_NICK, userNickName);
        super.onSaveInstanceState(outState);
    }
}
