import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TriadGame implements Game{

    private String name = "Guitar - Simple chords with inversions";
    private String desc = "Name the note and press enter";
    private String sciDesc = "Use S to toggle scientific pitch notation";
    private String invDesc = "Use I to toggle inversions";
    private String simplestDesc = "Use M to toggle simplest chord set";
    private String question = "";
    private int answerNum;
    private int numPitch;
    private String answer = "";
    private String answerSci = "";
    private String answerF = "";
    private String answerSciF = "";
    private Integer[] pitches = {};
    private String chord = "";
    private boolean sciSetting = false;
    private boolean subSetting = true;
    private boolean invSetting = true;
    private boolean simplest = false;
    private Music music;

    //TODO: SHarp flats enharmonic equivalents


    public TriadGame() {

        music = new Music();
    }

    @Override
    public void init() {


        printSciSetting();
        printInvSetting();
        printSimplest();


        System.out.println(desc);
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
            printQuestion();
            return;
        }

        if(in.equals("I")) {
            invSetting = !invSetting;
            printInvSetting();
            printQuestion();
            return;
        }

        if(in.equals("M")) {
            simplest = !simplest;
            printSimplest();
            printQuestion();
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
            generateQuestionAndAnswerChord();
        } else { // answer is N
            generateQuestionAndAnswerNote();
        }
    }

    private void generateQuestionAndAnswerNote() {
        int fret = ThreadLocalRandom.current().nextInt(0, music.getFrets());
        int string = ThreadLocalRandom.current().nextInt(0, music.getStrings());
        this.question = "What is the note on the " + (string + 1) + " string at the " + fret + " fret?";
        this.numPitch = music.getNote(string, fret);

        this.answerNum = numPitch;
        this.answer = music.getSimplePitch(numPitch);
        this.answerF = music.getSimplePitchF(numPitch).toUpperCase();
        this.answerSci = music.getSciPitch(numPitch);
        this.answerSciF = music.getSciPitchF(numPitch).toUpperCase();

    }

    private void generateQuestionAndAnswerChord() {
        Map<String, Integer[]> chords = music.getChords();
        this.chord = simplest ? music.getRandomSimplestChord() : music.getRandomSimpleChord();
        this.pitches = chords.get(chord);
        int rand = ThreadLocalRandom.current().nextInt(0, pitches.length);
        int offset = pitches[rand];
        int chordRoot = this.answerNum - offset;
        String chordWithRoot = music.getSimplePitch(chordRoot) + " " + chord;


        if(invSetting) {
            String[] notes = music.getNotesOfChord(chordRoot, chord);
            List<String> n = Arrays.asList(notes);
            Collections.shuffle(n);
            this.question = "Play a " + chordWithRoot + " using that note ";

            this.question += "in order";
            for (String note: notes) {
                this.question += " " + note;
            }
            this.question += ".";
        } else {
            this.question = "Play a " + chordWithRoot + " using that note.";
        }
        this.question += " Press any key to continue.";
        this.question += " Press P to list the intervals in the chord.";

        String n = "N";
        this.answer = n;
        this.answerF = n;
        this.answerSci = n;
        this.answerSciF = n;



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

    private void printQuestion() {
        System.out.println(question);
        if(sciSetting) {
            System.out.println();
        }
    }

    private void printSciSetting() {
        System.out.println("Scientific pitch notation is set to " + sciSetting);
        System.out.println(sciDesc);
    }

    private void printInvSetting() {
        System.out.println("Inversion setting is set to " + invSetting);
        System.out.println(invDesc);
    }

    private void printSimplest() {
        System.out.println("Simplest is set to " + simplest );
        System.out.println(simplestDesc);
    }


    private void printPitches() {
        System.out.println("The intervals in a " + this.chord + " are: ");
        for(int i = 0; i < pitches.length; i++) {
            System.out.println(music.intervals[pitches[i]]);
        }
    }
}
