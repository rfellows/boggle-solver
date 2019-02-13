package boggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Boggle {
    private Cube[][] board = new Cube[4][4];

    public Boggle() {
    }

    public Boggle(Cube[][] board) {
        this.board = board;
    }

    public void setCube(int row, int column, char letter) {
        if (row < board.length && column < board[0].length) {
            board[row][column] = new Cube(row, column, letter);
        }
    }

    public void setRow(int row, char[] letters) {
        List<Character> chars = new ArrayList<>();
        for (char letter : letters) {
            chars.add(letter);
        }
        setRow(row, chars);
    }

    public void setRow(int row, List<Character> letters) {
        if (row < board.length && letters.size() == board[0].length) {
            for (int c = 0; c < letters.size(); c++) {
                char letter = letters.get(c).charValue();
                setCube(row, c, letter);
            }
        }
    }

    public Cube getCube(Location location) {
        return getCube(location.getRow(), location.getColumn());
    }

    public Cube getCube(int row, int column) {
        Location loc = new Location(row, column);
        if (loc.isValid()) {
            return board[row][column];
        }
        return null;
    }

    public List<Cube> getCubes() {
        List<Cube> cubes = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            Cube[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                Cube cube = row[j];
                cubes.add(cube);
            }
        }
        return cubes;
    }
}
