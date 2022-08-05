package com.example.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winninLineColor;
    private boolean winningLine = false;
    private final Paint paint = new Paint();

    private int cellSize = getWidth()/3;

    private final GameLogic game;


    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);
        boardColor = typedArray.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
        XColor = typedArray.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
        OColor = typedArray.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
        winninLineColor = typedArray.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredHeight(), getMeasuredWidth());
        cellSize = dimension/3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
        if(winningLine) {
            paint.setColor(winninLineColor);
            drawWinningLine(canvas);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for(int i = 0; i<3; ++i) {
            for(int j = 0; j<3; ++j) {
                if(game.getGameBoard()[i][j] != 0) {
                    if(game.getGameBoard()[i][j] == 1) {
                        drawX(canvas, i, j);
                    }
                    else if(game.getGameBoard()[i][j] == 2){
                        drawO(canvas, i, j);
                    }
                }
            }
        }
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for(int col = 1; col<3; ++col) {
            canvas.drawLine(cellSize * col, 0, cellSize * col, canvas.getHeight(), paint);
        }
        for(int row = 1; row<3; ++row) {
            canvas.drawLine(0, cellSize * row, canvas.getWidth(), cellSize * row, paint);
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);
        canvas.drawLine(cellSize*(col + 1 -0.2f), cellSize*(row + 0.2f), cellSize*(col + 0.2f), cellSize*(row + 1 - 0.2f), paint); // draws / line
        canvas.drawLine(cellSize*(col + 0.2f), cellSize*(row + 0.2f), cellSize*(col + 1 - 0.2f), cellSize*(row + 1 - 0.2f), paint); // draws \ line
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);

        float radius = 16f;
        canvas.drawCircle(col * cellSize + cellSize/2, row * cellSize + cellSize/2, cellSize/3, paint);
        canvas.drawCircle(col * cellSize + cellSize/2, row * cellSize + cellSize/2, cellSize/3, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            int row = (int)Math.ceil(y/cellSize);
            int col = (int)Math.ceil(x/cellSize);

            if(!winningLine) {
                // updating the player's trun
                if(game.updateGameBoard(row, col)) {
                    invalidate();

                    if(game.winnerCheck()) {
                        winningLine = true;
                        invalidate();
                    }

                    if(game.getPlayer() == 1) {
                        game.setPlayer(2);
                    }
                    else {
                        game.setPlayer(1);
                    }
                }
            }

            invalidate();
            return true;
        }
        return false;
    }

    public void resetGame() {
        game.resetGame();
        winningLine = false;
    }

    public void setUpGame(Button homeButton, Button playAgainButton, TextView playerTurn) {
        game.setHomeButton(homeButton);
        game.setPlayAgainButton(playAgainButton);
        game.setPlayerTurn(playerTurn);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col, row * cellSize + cellSize/2, cellSize * 3, row * cellSize + cellSize/2, paint);
    }
    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine( col * cellSize + cellSize/2, row,col * cellSize + cellSize/2, cellSize * 3, paint);
    }
    private void drawPositiveDiagonol(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3, cellSize * 3, 0, paint);
    }
    private void drawNegativeDiagonol(Canvas canvas) {
        canvas.drawLine(0, 0, cellSize * 3, cellSize * 3, paint);
    }
    private void drawWinningLine(Canvas canvas) {
        int[] arr = game.getWinType();
        int row = arr[0];
        int col = arr[1];
        switch(arr[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawNegativeDiagonol(canvas);
                break;
            case 4:
                drawPositiveDiagonol(canvas);
                break;
        }
    }
}
