import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager{


    public static void main(String[] args) {


        //initialize gameList
        List<Game> gameList = new ArrayList<>();
        gameList.add(new Game1());

        System.out.println("This program allows you to play a variety of games to improve your knowledge of the fretboard");

        for (int i = 0; i < gameList.size(); i++) {
            Game g = gameList.get(i);
            System.out.println(i + ". " + g.getName());
        }

        String[] sciPitchNotations = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

        Map<Integer, String> notes = new HashMap<>();

        for(int i = 0; i < 100; i++) {
            notes.put(i, sciPitchNotations[i % 12] + (i / 12));
        }

        System.out.println(notes);
    }
}