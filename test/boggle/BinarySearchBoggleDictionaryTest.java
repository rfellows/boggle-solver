package boggle;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class BinarySearchBoggleDictionaryTest {
    static BinarySearchBoggleDictionary dict = new BinarySearchBoggleDictionary();

    @BeforeClass
    public static void init() throws Exception {
        InputStream inputStream = BinarySearchBoggleDictionaryTest.class.getClassLoader().getResourceAsStream("dictionary.txt");

        dict.load(inputStream);
    }

    @Test
    public void testLoad() throws Exception {
        assertEquals(364241, dict.getWordCount());
    }

    @Test
    public void testIsWord() {
        assertTrue(dict.isWord("a"));
        assertTrue(dict.isWord("aardvark"));
        assertTrue(dict.isWord("Aardvark"));
        assertTrue(dict.isWord("boggle"));
        assertTrue(dict.isWord("punctuation"));
        assertTrue(dict.isWord("zebra"));
        assertTrue(dict.isWord("SIN"));
        assertFalse(dict.isWord("babyshark"));
    }
}
