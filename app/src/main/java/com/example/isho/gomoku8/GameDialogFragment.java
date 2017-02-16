package com.example.isho.gomoku8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * Created by Jason on 2/15/2017.
 */

public class GameDialogFragment extends DialogFragment {
    Button RematchButton;
    Button MenuButton;
    Button ExitButton;
    public GameDialogFragment() {
        // need a free constructor
    }

    public static GameDialogFragment newInstance(String title) {
        GameDialogFragment frag = new GameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_end_game_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RematchButton = (Button)view.findViewById(R.id.rematchButton);
        MenuButton = (Button)view.findViewById(R.id.MenuButton);
        ExitButton = (Button)view.findViewById(R.id.QuitButton);
        RematchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardScreen parent = (BoardScreen)getActivity();
                parent.resetMatch();
            }
        });
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardScreen parent = (BoardScreen)getActivity();
                parent.finish();
            }
        });
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardScreen parent = (BoardScreen)getActivity();
                parent.finish();
            }
        });


        // fetches title from Bundle and sets it
//        String title = getArguments().getString("title", "Game Finished");
//        getDialog().setTitle(title);
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.end)
//                .setItems()
//    }

    // return to ChooseStartOptions activity
    public void returnToMenu() {

    }


}
