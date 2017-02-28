package com.example.isho.gomoku8;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    static String player;
    CountDownTimer timer;
    TextView timerText;
    int initialTime = 600000, timeUnit = 1000, sec, min;
    boolean running;
    long timeLeft;

    public static final String FORMAT = "%02d:%02d";

    //private OnFragmentInteractionListener mListener;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    public static TimerFragment newInstance(String timerView) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        player = timerView;
        args.putString("Player", timerView);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View timerView;

        timerView = inflater.inflate(R.layout.fragment_timer, container, false);
        timerText = (TextView) timerView.findViewById(R.id.timer);
        timerText.setText(player);
        timer = new MyCountDownTimer(initialTime, timeUnit, timerText);

        return timerView;
    }

/*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    public static final String FORMAT = "%02d:%02d";    if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
/*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
*/
    public void start() {
        timer.start();
    }

    public void stop() {
        timer.cancel();
    }

    public void timerReset() {
        //
    }

    public class MyCountDownTimer extends CountDownTimer {
        long initial, unit;
        boolean running = true;
        long timeLeft;
        TextView countDownText;

        public MyCountDownTimer(long initial, long unit, TextView timerText) {
            super(initial, unit);
            timeLeft = initial;
            countDownText = timerText;
        }

        @Override
        //required onTick() method
        //countDownText.setText("seconds remaining: " + millisUntilFinished / 1000);
        public void onTick(long msLeft) {
            countDownText.setText("Time: " + String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toMinutes(msLeft)
            ));
        }

        public void pauseTimer() {
            running = false;
        }

        public void resumeTimer() {
            running = true;
        }

        //required onFinish() method
        public void onFinish() {
            countDownText.setText("Game Over!");
        }
    }
}

/*
public class Timer extends AppCompatActivity {

    CountDownTimer timer;
    TextView timerText;
    int initial = 600000, timeUnit = 1000, sec, min;
    boolean running;
    long timeLeft;

    // Display format for min:sec
    public static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_screen);

        timerText = (TextView) findViewById(R.id.timer);
        timer = new MyCountDownTimer(initial, timeUnit, timerText);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.cancel();
    }

    public void timerReset() {
        //
    }

    public class MyCountDownTimer extends CountDownTimer {
        long initial, unit;
        boolean running = true;
        long timeLeft;
        TextView countDownText;

        public MyCountDownTimer(long initial, long unit, TextView timerText) {
            super(initial, unit);
            timeLeft = initial;
            countDownText = timerText;
        }

        @Override
        //required onTick() method
        //countDownText.setText("seconds remaining: " + millisUntilFinished / 1000);
        public void onTick(long msLeft) {
            countDownText.setText("Time: " + String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toMinutes(msLeft)
            ));
        }

        public void pauseTimer(){
            running = false;
        }

        public void resumeTimer() {
            running = true;
        }

        //required onFinish() method
        public void onFinish() {
            countDownText.setText("Game Over!");
        }
    }
}
*/