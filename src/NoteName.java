import java.util.concurrent.ThreadLocalRandom;

public class NoteName implements Game{

    private String name = "Guitar - Name the note";
    private String desc = "Name the note and press enter";
    private String sciDesc = "Use S to toggle scientific pitch notation";
    private String question = "";
    private String answer = "";
    private String answerSci = "";
    private String answerF = "";
    private String answerSciF = "";
    private boolean sciSetting = false;
    //replace '.' with '#' and ',' with 'b'
    private boolean subSetting = true;
    private Guitar guitar;

    //TODO: SHarp flats enharmonic equivalents


    public NoteName() {

        guitar = new Guitar();
    }

    @Override
    public void init() {

        System.out.println(desc);
        System.out.println(sciDesc);
        //populate question
        generateQuestionAndAnswer();


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
        int fret = ThreadLocalRandom.current().nextInt(0, guitar.getFrets());
        int string = ThreadLocalRandom.current().nextInt(0, guitar.getStrings());
        this.question = "What is the note on the " + (string + 1) + " string at the " + fret + " fret?";
        int numPitch = guitar.getNote(string, fret);

        this.answer = guitar.getSimplePitch(numPitch);
        this.answerF = guitar.getSimplePitchF(numPitch).toUpperCase();
        this.answerSci = guitar.getSciPitch(numPitch);
        this.answerSciF = guitar.getSciPitchF(numPitch).toUpperCase();

    }

    private void printQuestion() {
        System.out.println(question);
        if(sciSetting) {
            System.out.println();
        }
    }

    private boolean checkAnswer(String in) {
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
}
