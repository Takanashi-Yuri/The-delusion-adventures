import java.util.Scanner;  // Import the Scanner class

public class StoryMethods {
    // Create new story and character
    public static void newStory(Scanner scanner, String character) {
        String introduction = "Hi, my name is Nanami, and I am your companion on this journey.\n" + "First, we'll create your character that will represent you.\n";
        SmallMethods.slowPrint(introduction);
        System.out.println();



        String[] userInfo = CharacterMethods.createNewCharacter(scanner);
        String userName = userInfo[0];
        String userSubjectPronouns = userInfo[1];
        String userObjectPronouns = userInfo[2];
    }

    // Load existing story
    public static void loadStory(Scanner scanner) {
        String introduction = "Please insert your character name for list of existing stories\n~> ";
        SmallMethods.slowPrint(introduction);
        String characterName = scanner.nextLine();  // Read user input
    }
}