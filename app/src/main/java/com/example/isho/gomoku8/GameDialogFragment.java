package com.example.isho.gomoku8;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Jason on 2/15/2017.
 */

public class GameDialogFragment extends DialogFragment {
    Button RematchButton;
    Button MenuButton;
    TextView Title;
    static String winner;
    public GameDialogFragment() {
        // need a free constructor
    }

    public static GameDialogFragment newInstance(String title) {
        GameDialogFragment frag = new GameDialogFragment();
        Bundle args = new Bundle();
        winner = title;
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
        Title = (TextView)view.findViewById(R.id.VictoryText);
        RematchButton = (Button)view.findViewById(R.id.rematchButton);
        MenuButton = (Button)view.findViewById(R.id.MenuButton);
        Title.setText(winner);
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

    }


}
