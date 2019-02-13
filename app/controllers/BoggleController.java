package controllers;

import boggle.*;
import com.google.gson.Gson;
import model.BoardRequest;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoggleController extends Controller {

    static BoggleDictionary dictionary;

    static {
        dictionary = new BinarySearchBoggleDictionary();
        InputStream is = BoggleController.class.getClassLoader().getResourceAsStream("dictionary.txt");
        try {
            dictionary.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Result solve(Http.Request request) {
        String json = request.body().asJson().toString();
        Gson gson = new Gson();
        BoardRequest boardRequest = gson.fromJson(json, BoardRequest.class);

        BoggleSolver solver = new BoggleSolver(dictionary);

        Boggle boggle = convert(boardRequest);

        solver.setBoggle(boggle);

        Map<String, List<Cube>> results = solver.solve();

        // just return the words for now.
        return ok(gson.toJson(results.keySet()));
    }

    private Boggle convert(BoardRequest boardRequest) {
        Boggle boggle = new Boggle();
        for (int row = 0; row < boardRequest.getBoard().size(); row++) {
            ArrayList<Character> columnValues = boardRequest.getBoard().get(row);
            boggle.setRow(row, columnValues);
        }

        return boggle;
    }

}
