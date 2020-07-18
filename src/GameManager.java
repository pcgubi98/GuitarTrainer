import java.util.*;

public class GameManager {


    public static void main(String[] args) {



        //initialize gameList
        List<Game> gameList = new ArrayList<>();
        gameList.add(new NoteName());

        Game activeGame = null;

        System.out.println("This program allows you to play a variety of games to improve your musical skills");
        System.out.println("Press q to quit");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            int selection = selectGame(scanner, gameList);
            if(selection == -1) {
                return;
            } else {
                activeGame = gameList.get(selection);
                activeGame.init();
                String input = "";
                while(!input.equals("q")) {
                    activeGame.handleInput(input);
                    input = scanner.nextLine();
                }
                activeGame = null;
            }
        }


    }

    private static int selectGame(Scanner scanner, List<Game> gameList) {
        for (int i = 0; i < gameList.size(); i++) {
            Game g = gameList.get(i);
            System.out.println(i + ". " + g.getName());
        }



        String input = "";
        int sel = -1;
        while(!input.equals("q") && (sel >= gameList.size() || sel < 0)){
            System.out.println("Select a game by number (0-" + (gameList.size() - 1)  + "):");
            input = scanner.nextLine();
            try {
                sel = Integer.parseInt(input);
            } catch (NumberFormatException e){
                continue;
            }
        }

        if (input.equals("q")) {
            return -1;
        }

        return sel;
    }
}
