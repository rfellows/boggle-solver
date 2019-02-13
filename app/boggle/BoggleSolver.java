package boggle;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BoggleSolver {
    private Boggle boggle;
    private BoggleDictionary dictionary;
    private ConcurrentMap<String, List<Cube>> words;
    private ExecutorService executorService;

    public BoggleSolver(BoggleDictionary dictionary) {
        this.dictionary = dictionary;
        executorService = Executors.newCachedThreadPool();
    }

    public Boggle getBoggle() {
        return boggle;
    }

    public void setBoggle(Boggle boggle) {
        this.boggle = boggle;
    }

    public Map<String, List<Cube>> solve() {
        words = new ConcurrentHashMap<>();

        // start at each of the 16 cubes
        List<CompletableFuture<Void>> futures = boggle.getCubes().stream().map((cube) -> {
            CompletableFuture<Void> cf = new CompletableFuture<>();
            executorService.submit(() -> {
                traverse(cube, new ArrayList<>());
                cf.complete(null);
            });
            return cf;
        }).collect(Collectors.toList());

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return words;
    }

    protected void traverse(Cube cube, List<Cube> visited) {
        visited.add(cube);
        boolean skipNextPaths = false;

        // boggle words have to be at least 3 characters
        if (visited.size() > 2) {
            // look the word up in the dictionary
            String possibleWord = visited.stream()
                    .map(Cube::getValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));

            int index = dictionary.find(possibleWord);
            if (index > -1) {
                words.putIfAbsent(possibleWord, visited);

                //
                // If we found a valid word, we can peek ahead in the dictionary to see if the next word starts with this word.
                // If it does not, we don't have to bother walking the adjacent cubes anymore
                //
                String peekAtNext = dictionary.getWordAt(index + 1);
                if (!peekAtNext.toUpperCase().startsWith(possibleWord.toUpperCase())) {
                    return;
                }
            }
        }

        // get the cubes that are next to this one, but filter out the ones we have already visited
        List<Location> adjacentLocations = cube.getAdjacentLocations().stream().filter(location -> {
            // only can use the locations that we haven't already visited
            Optional<Cube> maybeAlreadySawThis = visited.stream()
                    .filter(cubeVisited -> cubeVisited.getLocation().equals(location))
                    .findFirst();
            return !maybeAlreadySawThis.isPresent();
        }).collect(Collectors.toList());

        for (Location loc : adjacentLocations) {
            // traverse from here too
            Cube next = boggle.getCube(loc);
            if (next != null) {
                traverse(next, new ArrayList<>(visited));
            }
        }
    }


}
