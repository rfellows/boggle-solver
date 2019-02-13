package boggle;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    private Location location;
    private char value;

    public Cube(int row, int column, char value) {
        location = new Location(row, column);
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public List<Location> getAdjacentLocations() {
        List<Location> possible = new ArrayList<>();

        for (int r = location.getRow() - 1; r <= location.getRow() + 1; r++) {
            for (int c = location.getColumn() - 1; c <= location.getColumn() + 1; c++) {

                Location loc = new Location(r, c);

                // make sure we aren't returning the same cube
                if (!location.equals(loc)) {
                    if (loc.isValid()) {
                        possible.add(loc);
                    }
                }

            }
        }
        return possible;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "location=" + location +
                ", value=" + value +
                '}';
    }
}
