package boggle;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BoggleSolverTest {

    static BinarySearchBoggleDictionary dict = new BinarySearchBoggleDictionary();
    Boggle boggle;

    @BeforeClass
    public static void init() throws Exception {
        InputStream inputStream = BinarySearchBoggleDictionaryTest.class.getClassLoader().getResourceAsStream("dictionary.txt");

        dict.load(inputStream);
    }


    @Before
    public void setUp() throws Exception {
        boggle = new Boggle();

        // from the original game'e box cover
        boggle.setRow(0, new char[]{'E', 'S', 'O', 'D'});
        boggle.setRow(1, new char[]{'T', 'I', 'N', 'T'});
        boggle.setRow(2, new char[]{'A', 'F', 'Y', 'E'});
        boggle.setRow(3, new char[]{'G', 'V', 'L', 'O'});
    }

    @Test
    public void testSolve_DictionaryWords() {
        BoggleSolver solver = new BoggleSolver(dict);
        solver.setBoggle(boggle);

        Map<String, List<Cube>> words = solver.solve();
        assertTrue(!words.isEmpty());

        words.entrySet().forEach(entry -> {
            System.out.print(entry.getKey() + " ====> ");
            entry.getValue().forEach(System.out::print);
            System.out.println("\n---------------------------");
        });

        assertEquals(287, words.size());
    }

    @Test
    public void testSolve_ScrabbleWords() throws IOException {
        InputStream inputStream = BinarySearchBoggleDictionaryTest.class.getClassLoader().getResourceAsStream("scrabble.txt");

        dict.load(inputStream);

        BoggleSolver solver = new BoggleSolver(dict);
        solver.setBoggle(boggle);

        Map<String, List<Cube>> words = solver.solve();
        assertTrue(!words.isEmpty());
        assertEquals(220, words.size());
    }
}