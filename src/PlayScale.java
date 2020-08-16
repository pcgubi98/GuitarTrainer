import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PlayScale implements Game{

    private String name = "Keyboard - Play a scale";
    private String desc = "Name the note and press enter";
    private String sciDesc = "Use S to toggle scientific pitch notation";
    private String question = "";
    private String answer = "";
    private String[] pitches = {};
    private String scale = "";
    private boolean sciSetting = false;
    private Guitar guitar;

    //TODO: SHarp flats enharmonic equivalents


    public PlayScale() {

        guitar = new Guitar();
    }

    @Override
    public void init() {

        System.out.println(desc);
        System.out.println(sciDesc);
        //populate question
        generateScale();


    }

    @Override
    public void handleInput(String in) {


        in = in.toUpperCase();


        if(in.equals("P")) {
            printPitches();
            printQuestion();
            return;
        }

        if(in.equals("")) {
            System.out.println(question);
            return;
        }

        //checkAnswer(in);
        generateScale();
        printQuestion();
    }

    @Override
    public String getName() {
        return name;
    }

    private void generateScale() {

        String[] notes = guitar.sciPitchNotations;
        int noteNum = ThreadLocalRandom.current().nextInt(0, notes.length);
        String note = notes[noteNum];
        Map<String, Integer[]> scales = guitar.getScales();
        List<String> scaleList = new ArrayList(scales.keySet());
        int rand = ThreadLocalRandom.current().nextInt(0, scaleList.size());
        String scale = scaleList.get(rand);
        Integer[] scaleNums = scales.get(scale);
        this.pitches = new String[scaleNums.length];


        /*
        for(int i = 0; i < scaleNums.length; i++) {
            this.pitches[i] = guitar.getSimplePitch(noteNum + scaleNums[i]);
            System.out.println(scaleNums[i]);
        }
        */


        this.scale = note + " " + scale;

        this.question = "Play a " + this.scale + ". " ;
        this.question += " Press any key to continue.";
        this.question += " Press P to list the intervals in the scale.";

    }

    private void printQuestion() {
        System.out.println(question);
        if(sciSetting) {
            System.out.println();
        }
    }


    private void printPitches() {
        System.out.println("The intervals in a " + this.scale + " are: ");
        for(int i = 0; i < pitches.length; i++) {
            System.out.print(pitches[i] + " ");
        }
        System.out.println();
    }
}
