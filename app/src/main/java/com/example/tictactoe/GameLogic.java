package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;
    private int player = 1, boardFilled = 0;
    private String[] playerNames = {"Player 1", "Player 2"};
    private Button playAgainButton, homeButton;
    private TextView playerTurn;
    // 1st --> row, 2nd --> col, 3rd --> line type
    private int[] winType = {-1, -1, -1};

    public GameLogic() {
        this.gameBoard = new int[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                gameBoard[i][j] = 0;
            }
        }
    }

    public void setPlayAgainButton(Button playAgainButton) {
        this.playAgainButton = playAgainButton;
    }


    public boolean winnerCheck() {
        boolean isWinner = false;
        // horizontal check (winType == 1)
        for (int r = 0; r < 3; ++r) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                isWinner = true;
                winType = new int[]{r, 0, 1};
                break;
            }
        }
        // vertical check (winType == 2)
        for (int c = 0; c < 3; ++c) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                isWinner = true;
                winType = new int[] {0, c, 2};
                break;
            }
        }
        // negative diagonal (winType == 3)
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }
        // positive diagonal (winType == 4)
        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[0][2] == gameBoard[2][0] && gameBoard[0][2] != 0) {
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        if (isWinner) {
            playAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            playerTurn.setText(playerNames[player - 1] + " Won !!!");
            return true;
        } else if (boardFilled == 9) {
            playAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            playerTurn.setText(" Tie Game !!!");
            return true;
        }
        return false;
    }

    public int getPlayer() {
        return player;
    }

    public void setHomeButton(Button homeButton) {
        this.homeButton = homeButton;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public boolean updateGameBoard(int row, int col) {
        if (gameBoard[row - 1][col - 1] == 0) {
            gameBoard[row - 1][col - 1] = player;
            boardFilled++;

            if (player == 1) {
                playerTurn.setText(playerNames[1] + "'s Turn");
            } else {
                playerTurn.setText(playerNames[0] + "'s Turn");
            }
            return true;
        }
        return false;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void resetGame() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                gameBoard[i][j] = 0;
            }
        }
        player = 1;
        boardFilled = 0;
        homeButton.setVisibility(View.GONE);
        playAgainButton.setVisibility(View.GONE);
        playerTurn.setText("Player 1's Turn");
        winType = new int[] {-1, -1, -1};

    }

    public int[] getWinType() {
        return this.winType;
    }
}
