package com.example.xogame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private char currentPlayer = 'X';
    private char[][] board = new char[3][3];
    private boolean gameActive = true;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        Button resetButton = findViewById(R.id.resetButton);

        // Initialize board and set up click listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = (Button) gridLayout.getChildAt(i * 3 + j);
                final int row = i, col = j;

                button.setOnClickListener(v -> {
                    if (gameActive && button.getText().toString().isEmpty()) {
                        button.setText(String.valueOf(currentPlayer));
                        board[row][col] = currentPlayer;

                        if (checkWin()) {
                            statusTextView.setText("Player " + currentPlayer + " Wins!");
                            gameActive = false;
                        } else if (isBoardFull()) {
                            statusTextView.setText("It's a Tie!");
                            gameActive = false;
                        } else {
                            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                            statusTextView.setText("Player " + currentPlayer + "'s Turn");
                        }
                    }
                });
            }
        }

        resetButton.setOnClickListener(v -> resetGame(gridLayout));
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true;
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\0') return false;
            }
        }
        return true;
    }

    private void resetGame(GridLayout gridLayout) {
        currentPlayer = 'X';
        gameActive = true;
        statusTextView.setText("Player X's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
                Button button = (Button) gridLayout.getChildAt(i * 3 + j);
                button.setText("");
            }
        }
    }
}
