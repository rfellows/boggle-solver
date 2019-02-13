package model;

import java.util.ArrayList;

public class BoardRequest {
    private ArrayList<ArrayList<Character>> board;

    public BoardRequest() {
    }

    public ArrayList<ArrayList<Character>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<Character>> board) {
        this.board = board;
    }
}
