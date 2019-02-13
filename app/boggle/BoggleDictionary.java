package boggle;

import java.io.IOException;
import java.io.InputStream;

public interface BoggleDictionary {
    void load(InputStream inputStream) throws IOException;
    int getWordCount();
    String getWordAt(int index);
    int find(String word);
}
