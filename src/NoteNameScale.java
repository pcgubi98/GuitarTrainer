import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class NoteNameScale implements Game{

    private String name = "Guitar - Name the note and use it in a scale";
    private String desc = "Name the note and press enter";
    private String sciDesc = "Use S to toggle scientific pitch notation";
    private String question = "";
    private int answerNum;
    private String answer = "";
    private String answerSci = "";
    private String answerF = "";
    private String answerSciF = "";
    private Integer[] pitches = {};
    private String scale = "";
    private boolean sciSetting = false;
    private boolean subSetting = true;
    private Music music;

    //TODO: SHarp flats enharmonic equivalents


    public NoteNameScale() {

        music = new Music();
    }

    @Override
    public void init() {

        System.out.println(desc);
        System.out.println(sciDesc);
        //populate question
        generateQuestionAndAnswerNote();


    }

    @Override
    public void handleInput(String in) {

        if(subSetting) {
            in = in.replace('.', '#');
            in = in.replace(',', 'b');
        }

        in = in.toUpperCase();
        if(in.equals("S")) {
            sciSetting = !sciSetting;
            printSciSetting();
            return;
        }

        if(in.equals("P") && this.answer.equals("N")) {
            printPitches();
            printQuestion();
            return;
        }


        if(in.equals("")) {
            System.out.println(question);
            return;
        }
        checkAnswer(in);
        generateQuestionAndAnswer();
        printQuestion();
    }

    @Override
    public String getName() {
        return name;
    }

    private void generateQuestionAndAnswer() {
        if(!answer.toUpperCase().equals("N")) {
            generateQuestionAndAnswerScale();
        } else { // answer is N
            generateQuestionAndAnswerNote();
        }
    }

    private void generateQuestionAndAnswerNote() {
        int fret = ThreadLocalRandom.current().nextInt(0, music.getFrets());
        int string = ThreadLocalRandom.current().nextInt(0, music.getStrings());
        this.question = "What is the note on the " + (string + 1) + " string at the " + fret + " fret?";
        int numPitch = music.getNote(string, fret);

        this.answerNum = numPitch;
        this.answer = music.getSimplePitch(numPitch);
        this.answerF = music.getSimplePitchF(numPitch).toUpperCase();
        this.answerSci = music.getSciPitch(numPitch);
        this.answerSciF = music.getSciPitchF(numPitch).toUpperCase();

    }

    private void generateQuestionAndAnswerScale() {
        Map<String, Integer[]> scales = music.getScales();
        List<String> chordList = new ArrayList(scales.keySet());
        int rand = ThreadLocalRandom.current().nextInt(0, chordList.size());
        this.scale = chordList.get(rand);
        this.pitches = scales.get(scale);
        rand = ThreadLocalRandom.current().nextInt(0, pitches.length);
        int offset = pitches[rand];
        int scaleRoot = this.answerNum - offset;
        String scaleWithRoot = music.getSimplePitch(scaleRoot) + " " + scale;

        this.question = "Play a " + scaleWithRoot + " using that note.";
        this.question += " Press any key to continue.";
        this.question += " Press P to list the intervals in the scale.";

        String n = "N";
        this.answer = n;
        this.answerF = n;
        this.answerSci = n;
        this.answerSciF = n;



    }

    private void printQuestion() {
        System.out.println(question);
        if(sciSetting) {
            System.out.println();
        }
    }

    private boolean checkAnswer(String in) {
        if(answer.equals("N")) {
            return true;
        }

        if(sciSetting) {
            if(in.equals(answerSci) || in.equals(answerSciF)) {
                System.out.println("\033[32mCorrect\033[0m");
                return true;
            } else {
                System.out.println("\033[31mIncorrect\033[0m");
                System.out.println("The correct answer is \033[32m" + answerSci + "\033[0m");
                return false;
            }
        } else {
            if(in.equals(answer) || in.equals(answerF) || in.equals(answerSci) || in.equals(answerSciF)) {
                System.out.println("\033[32mCorrect\033[0m");
                return true;
            } else {
                System.out.println("\033[31mIncorrect\033[0m");
                System.out.println("The correct answer is \033[32m" + answer + "\033[0m");
                return false;
            }
        }
    }

    private void printSciSetting() {
        System.out.println("Scientific pitch notation is set to " + sciSetting);
    }

    private void printPitches() {
        System.out.println("The intervals in a " + this.scale + " are: ");
        for(int i = 0; i < pitches.length; i++) {
            System.out.println(music.intervals[pitches[i]]);
        }
    }
}
