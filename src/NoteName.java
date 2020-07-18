public class NoteName implements Game{

    private String name = "Locate the note";
    private String desc = "Press key A";
    //TODO: settings
    private String sciDesc = "Press to togle";
    private String question = "";
    private String answer = "";
    private String answerSci = "";
    private boolean sciSetting = false;
    private Guitar guitar;


    public NoteName() {

        guitar = new Guitar();
    }

    @Override
    public void init() {
        //populate question
        generateQuestionAndAnswer();
    }

    @Override
    public void handleInput(String in) {
        if(in.equals("s")) {
            sciSetting = !sciSetting;
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
        this.question = "enter the key D4";
        this.answer = "D";
        this.answerSci = "D4";

    }

    private void printQuestion() {
        System.out.println(question);
        if(sciSetting) {
            System.out.println();
        }
    }

    private boolean checkAnswer(String in) {
        if(sciSetting) {
            return false;
        } else {
            if(in.equals(answer) || in.equals(answerSci)) {
                System.out.println("\033[32mCorrect\033[0m");
                return true;
            } else {
                System.out.println("\033[31mIncorrect\033[0m");
                return false;
            }
        }
    }
}
