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
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Jason on 2/15/2017.
 */

public class RoundEndFragmentDialog extends DialogFragment {
    Button NextRoundButton;
    Button MenuButton;
    TextView Title;
    static String winner;
    public RoundEndFragmentDialog() {
        // need a free constructor
    }

    public static RoundEndFragmentDialog newInstance(String title) {
        RoundEndFragmentDialog frag = new RoundEndFragmentDialog();
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
        return inflater.inflate(R.layout.fragment_round_end_fragment_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Title = (TextView)view.findViewById(R.id.VictoryText);
        NextRoundButton = (Button)view.findViewById(R.id.NextButton);
        MenuButton = (Button)view.findViewById(R.id.MenuButton);
        Title.setText(winner);
        NextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardScreen parent = (BoardScreen)getActivity();
                parent.nextRound();
            }
        });
        MenuButton.setOnClickListener(new View.OnClickListener() {
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
