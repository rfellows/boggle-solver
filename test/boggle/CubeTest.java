package boggle;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CubeTest {

    @Test
    public void testAdjacentLocations() {
        Cube cube = new Cube(0, 0, 'A');
        List<Location> adjacentLocations = cube.getAdjacentLocations();
        assertEquals(3, adjacentLocations.size());

        cube = new Cube(0, 1, 'A');
        adjacentLocations = cube.getAdjacentLocations();
        assertEquals(5, adjacentLocations.size());

        cube = new Cube(1, 1, 'A');
        adjacentLocations = cube.getAdjacentLocations();
        assertEquals(8, adjacentLocations.size());

        cube = new Cube(3, 2, 'A');
        adjacentLocations = cube.getAdjacentLocations();
        assertEquals(5, adjacentLocations.size());
    }
}
