public class Game1 implements Game{

    private String name = "Locate the note";
    private String desc = "Press key A";
    private Guitar guitar;

    public Game1() {
        guitar = new Guitar();
    }

    @Override
    public void handleInput(String in) {

    }

    @Override
    public String getName() {
        return null;
    }
}
