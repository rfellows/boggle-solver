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

        // boggle words have to be at least 3 characters
        if (visited.size() > 2) {
            // look the word up in the dictionary
            String possibleWord = visited.stream()
                    .map(Cube::getValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));

            //
            // First, see if any words in the dictionary begin with the word we are looking for.
            // If nothing even starts with it, we can just return and not process any more cubes in this path
            //
            int index = dictionary.findPrefix(possibleWord);

            if (index > -1) {
                String prefixFound = dictionary.getWordAt(index);

                if (prefixFound.equals(possibleWord)) {
                    words.putIfAbsent(possibleWord, visited);
                } else {
                    // the prefix found is not our word exactly, so we still need to try to go find it
                    index = dictionary.find(possibleWord);
                    if (index > -1) {
                        words.putIfAbsent(possibleWord, visited);
                    }
                }
            } else {
                return;
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
