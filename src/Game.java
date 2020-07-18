public interface Game {
    //TODO: possible refactor of this interface to include methods like verifyAnswer, open settings
    //basically would move more logic to the game manager
    public void handleInput(String in);

    public void init();

    public String getName();

}