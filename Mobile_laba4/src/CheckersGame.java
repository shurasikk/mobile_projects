import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ScheduledExecutorService;


public class CheckersGame extends JFrame {
    private JButton[][] board;
    private char[][] pieces;
    private char currentPlayer;

    int lastrow=-1;
    int lastcol=-1;
    boolean check = true;

    public CheckersGame() {
        setTitle("Checkers Game");
        setSize(1280, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board = new JButton[8][8]; //доска
        pieces = new char[8][8]; //фигуры
        currentPlayer = 'X';

        initializeBoard();
        initializePieces();
        addButtonsToGrid();
    }

    private void initializeBoard() {
        setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new JButton();
                board[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                board[i][j].setPreferredSize(new Dimension(70, 70));

                final int row = i;
                final int col = j;

                board[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked(row, col);
                    }
                });

                add(board[i][j]);
            }
        }
    }

    private void initializePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i >= 0 && i<= 2 && j >= 0 && j<= 2) {
                    pieces[i][j] = 'X';
                    board[i][j].setText("X");
                    board[i][j].setForeground(Color.BLUE);
                } else if (i >= 5 && j >= 5) {
                    pieces[i][j] = 'O';
                    board[i][j].setText("O");
                    board[i][j].setForeground(Color.RED);
                }
                else {
                    pieces[i][j] = ' ';
                    board[i][j].setText("");
                }
            }
        }
    }

    private void addButtonsToGrid() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setBackground(Color.WHITE);
            }

        }
    }

     /*                   if (checkForWin()) {
        JOptionPane.showMessageDialog(this, "Игрок " + currentPlayer + " победил!");
        resetGame();
    }*/

    private void buttonClicked(int row, int col) {
        if (check){
            if (pieces[row][col] == currentPlayer) {
                int targetRow = row;
                int targetCol = col;
                possibleWays(targetRow, targetCol);
                lastrow = targetRow;
                lastcol = targetCol;
                check = false;
            } else{
                JOptionPane.showMessageDialog(this, "Нажмите на свою фишку.");
            }
        }
        else {
            if (pieces[row][col] == currentPlayer){
                addButtonsToGrid();
                check = true;
                buttonClicked(row, col);
            }
            else if(board[row][col].getBackground()==Color.GREEN){
                if (currentPlayer == 'X'){
                    pieces[row][col] = 'X';
                    board[row][col].setText("X");
                    board[row][col].setForeground(Color.BLUE);
                    pieces[lastrow][lastcol] = ' ';
                    board[lastrow][lastcol].setText("");
                }
                else{
                    pieces[row][col] = 'O';
                    board[row][col].setText("O");
                    board[row][col].setForeground(Color.RED);
                    pieces[lastrow][lastcol] = ' ';
                    board[lastrow][lastcol].setText("");
                }
                lastcol = -1;
                lastrow = -1;
                check = true;
                if (checkForWin()) {
                    JOptionPane.showMessageDialog(this, "Игрок " + currentPlayer + " победил!");
                    resetGame();
                }
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                addButtonsToGrid();
            }else JOptionPane.showMessageDialog(this, "Нажмите на другую клавишу.");
        }
    }

    private void possibleWays(int row, int col) {
        //[i-1][j]; [i][j+1]; [i][j-1]; [i-1][j]
        if (row != 0 && row != 7 && col != 0 && col != 7) {
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }

                }
            }
        } else if (row == 0 && col == 0) {
            for(int i = row; i<row+2; i++){
                for(int j = 0; j < col+2; j++){
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }

                }
            }
        } else if (row==7 && col == 0) {
            for(int i = row-1; i<row+1; i++){
                for(int j = col; j < col+2; j++){
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        }else if (row==0 && col==7){
            for(int i = row; i<row+2; i++){
                for(int j = col-1; j < col+1; j++){
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        } else if (row==7 && col==7) {
            for(int i = row-1; i<row+1; i++){
                for(int j = col-1; j < col+1; j++){
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        }else if (row==0) {
            for(int i = row; i<row+2; i++){
                for (int j = col - 1; j < col + 2; j++) {
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        } else if (row==7) {
            for(int i = row-1; i<row+1; i++){
                for (int j = col - 1; j < col + 2; j++) {
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        } else if (col==0){
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col; j < col + 2; j++) {
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        } else if (col==7) {
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col-1; j < col + 1; j++) {
                    if (isFree(i, j) && !isDiag(row, col, i, j)) {
                        board[i][j].setBackground(Color.GREEN);
                    } else if (!isDiag(row,col,i,j)) {
                        Jump(row,col,i,j);
                    }
                }
            }
        }
    }

    private void Jump(int backrow, int backcol, int row, int col){
        int x = row - backrow;
        int y = col - backcol;
        if (row+x>=0 && row+x<=7 && col+y>=0 && col+y<=7) {
            if (isFree(row + x, col + y)) {
                board[row + x][col + y].setBackground(Color.GREEN);
                String a = way(x,y);
                if (a == "down"){
                    if(row+x-1!=-1 && !isFree(row+x-1, col+y)){ Jump(row+x,col+y, row+x-1, col+y); }
                    if(col+y+1!=8 && !isFree(row+x,col+y+1)){ Jump(row+x,col+y, row+x, col+y+1); }
                    if(col+y-1!=-1 && !isFree(row+x,col+y-1)){ Jump(row+x,col+y, row+x, col+y-1); }
                }
                else if (a == "up"){
                    if(row+x+1!=8 && !isFree(row+x+1, col+y)){ Jump(row+x,col+y, row+x+1, col+y); }
                    if(col+y+1!=8 && !isFree(row+x,col+y+1)){ Jump(row+x,col+y, row+x, col+y+1); }
                    if(col+y-1!=-1 && !isFree(row+x,col+y-1)){ Jump(row+x,col+y, row+x, col+y-1); }
                }
                else if (a=="left"){
                    if(row+x-1!=-1 && !isFree(row+x-1, col+y)){ Jump(row+x,col+y, row+x-1, col+y); }
                    if(row+x+1!=8 && !isFree(row+x+1, col+y)){ Jump(row+x,col+y, row+x+1, col+y); }
                    if(col+y+1!=8 && !isFree(row+x,col+y+1)){ Jump(row+x,col+y, row+x, col+y+1); }
                }
                else if (a=="right"){
                    if(row+x-1!=-1 && !isFree(row+x-1, col+y)){ Jump(row+x,col+y, row+x-1, col+y); }
                    if(row+x+1!=8 && !isFree(row+x+1, col+y)){ Jump(row+x,col+y, row+x+1, col+y); }
                    if(col+y-1!=-1 && !isFree(row+x,col+y-1)){ Jump(row+x,col+y, row+x, col+y-1); }
                }
            }
        }
    }

    private String way(int x,int y){
        if(x==-1)
            return "down";
        else if (x==1) {
            return "up";
        } else if (y==-1) {
            return "right";
        }
        else return "left";
    }

    private boolean isDiag(int row1, int col1, int row2, int col2){
        int rowDifference = Math.abs(row1 - row2);
        int colDifference = Math.abs(col1 - col2);

        return rowDifference == colDifference;
    }

    private boolean isFree(int row, int col){
        if (pieces[row][col] == ' ')
            return true;
        return false;
    }

    private boolean checkForWin() {
        // Проверка, все ли фишки игрока находятся в исходной позиции противника
        if (currentPlayer == 'X'){
            for (int i = 5; i < 8; i++) {
                for (int j = 5; j < 8; j++) {
                    if (pieces[i][j]!='X') return false;
                    }
                }
        }else{
            for (int i=0; i<3; i++)
                for (int j=0; j<3; j++){
                    if (pieces[i][j] != 'O') return false;
                }
        }

        return true;
    }

    private void resetGame() {
        initializePieces();
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CheckersGame().setVisible(true);
            }
        });
    }
}
