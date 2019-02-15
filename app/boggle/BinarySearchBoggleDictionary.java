package boggle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BinarySearchBoggleDictionary implements BoggleDictionary {
    List<String> words = Collections.emptyList();

    public BinarySearchBoggleDictionary() {
    }

    public void load(InputStream inputStream) throws IOException {
        words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // we only care about words that contain less than 16 characters
                if (line.length() >= 2 && line.length() <= 16) {
                    words.add(line);
                }
            }
        } finally {
            try {
                inputStream.close();
            } finally {
                try {
                    reader.close();
                } finally {
                    // ignore
                }
            }
        }
        // make sure the dictionary is sorted
        words = words.stream().sorted().collect(Collectors.toList());
    }

    public int getWordCount() {
        return words.size();
    }

    public boolean isWord(String word) {
        // binary search
        return find(word, 0, words.size() - 1) > -1;
    }

    public int find(String word) {
        // binary search
        return find(word, 0, words.size() - 1);
    }

    public String getWordAt(int index) {
        return words.get(index);
    }

    protected int find(String word, int start, int end) {
        int mid = (start + end) / 2;

        String w = words.get(mid);

        if (mid == start || mid == end) {
            // we are at the end of the line
            int endResult = word.toUpperCase().compareTo(words.get(end).toUpperCase());
            int startResult = word.toUpperCase().compareTo(words.get(start).toUpperCase());

            return endResult == 0 ? end : startResult == 0 ? start : -1;
        }
        int result = word.toUpperCase().compareTo(w.toUpperCase());

        if (result == 0) {
            // this is it!
            return mid;
        } else if (result < 0) {
            // look to the left
            return find(word, start, mid - 1);
        } else {
            // look to the right
            return find(word, mid + 1, end);
        }
    }
}
