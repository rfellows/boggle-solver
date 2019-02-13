package boggle;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BoggleSolverTest {

    static BinarySearchBoggleDictionary dict = new BinarySearchBoggleDictionary();

    @BeforeClass
    public static void init() throws Exception {
        InputStream inputStream = BinarySearchBoggleDictionaryTest.class.getClassLoader().getResourceAsStream("dictionary.txt");

        dict.load(inputStream);
    }

    @Test
    public void testSolve() {
        Boggle boggle = new Boggle();

        // from the original game'e box cover
        boggle.setRow(0, new char[]{'E', 'S', 'O', 'D'});
        boggle.setRow(1, new char[]{'T', 'I', 'N', 'T'});
        boggle.setRow(2, new char[]{'A', 'F', 'Y', 'E'});
        boggle.setRow(3, new char[]{'G', 'V', 'L', 'O'});

        BoggleSolver solver = new BoggleSolver(dict);
        solver.setBoggle(boggle);

        Map<String, List<Cube>> words = solver.solve();
        assertTrue(!words.isEmpty());
        assertEquals(287, words.size());

        words.entrySet().forEach(entry -> {
            System.out.print(entry.getKey() + " ====> ");
            entry.getValue().forEach(System.out::print);
            System.out.println("\n---------------------------");
        });
    }
}