package com.example.tictactoe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class GameDisplayFragment extends Fragment {
    
    private TicTacToeBoard ticTacToeBoard;
    private TextView playerTurn;
    private Button homeButton, playAgainButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialising the static variables
        View view = inflater.inflate(R.layout.game_play_fragment, container, false);

        playAgainButton = view.findViewById(R.id.playAgainButton);
        homeButton = view.findViewById(R.id.homeButton);

        homeButton.setVisibility(View.GONE);
        playAgainButton.setVisibility(View.GONE);

        playerTurn = view.findViewById(R.id.playerInfoTextView);
        playerTurn.setText("Player 1's Turn");

        ticTacToeBoard = view.findViewById(R.id.ticTacToeBoard);
        ticTacToeBoard.setUpGame(homeButton, playAgainButton, playerTurn);

        playAgainButton.setOnClickListener(view12 -> {
            ticTacToeBoard.resetGame();
            ticTacToeBoard.invalidate();
        });

        homeButton.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });
        return view;
    }
}

//    Button resetButton = view.findViewById(R.id.resetGameButton);
//        resetButton.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        getFragmentManager().popBackStack();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container_view, GameDisplayFragment.class, null);
//        fragmentTransaction.setReorderingAllowed(true);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        }
//        });


//    Button gotoHomeButton = view.findViewById(R.id.gotoHomeButton);
//        gotoHomeButton.setOnClickListener(new View.OnClickListener(){
//@Override
//public void onClick(View view){
//        FragmentManager fragmentManager=getParentFragmentManager();
////        fragmentManager.popBackStack();
//        }
//        });