// Import the Scanner class
import java.util.Scanner;

public class Main {
    // Set global class variables
    public static boolean DEBUG = false;
    public static Scanner scanner = new Scanner(System.in);
    public static String storyName = "";

    // Main setion
    public static void main(String[] args) {
        // Show loading screen
        displayLoadingScreen();

        // Wait for user to press Enter
        waitForEnter();

        while (true) {
            // Show main menu of the game
            storyName = displayMainMenu();

            // Start the main part
            // loadStory();
        }
    }

    // Method for displaying loading screen
    public static void displayLoadingScreen() {
        System.out.println("Welcome to the Text adventures");
        Utility.sleep(1000);

        System.out.println("- Loading astral matter...");
        Utility.createDirectory(DEBUG);
        Utility.sleep(1000);
        
        System.out.println("- Planting ash flowers...");
        Utility.sleep(1000);

        System.out.println("Loading completed");
        System.out.println();
    }

    // Method for user to press Enter to continue
    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    // Method for handling Main menu
    public static String displayMainMenu() {
        boolean done = false;

        do {
            String[] mainMenuOptions = {
                "New - Start a brand new story.",
                "Load - Load an existing story.",
                "Exit - Exit the program."
            };

            System.out.println(String.join("\n", mainMenuOptions));
            System.out.print("~> ");

            if (scanner.hasNextLine()) {
                String option = scanner.nextLine().trim();
                System.out.println();

                switch (option.toLowerCase()) {
                    case "new":
                        Object[] infoMainMenu = (Object[]) handleMainMenuNew();
                        done = (boolean) infoMainMenu[0];
                        storyName = (String) infoMainMenu[1];

                        break;
                    case "load":
                        // Implement the code for loading an existing story
                        System.out.println("Loading an existing story...");
                        done = true;
                        break;
                    case "exit":
                        System.out.println("Leaving the astral dimension. See you next time!");
                        scanner.close(); // Close scanner and free resources
                        System.exit(1);
                    default:
                        System.out.println("Unknown option, please choose one of the available options.");
                        break;
                }

                System.out.println();
            }
        }
        while (!done);

        return storyName;
    }

    // Method for handling Main menu new story section
    public static Object handleMainMenuNew() {
        boolean done = false;
        
        System.out.println("Select existing character or create new one.");

        do {
            // Retrieve existing characters
            String[] characters = CharacterManaging.getExistingCharacters(DEBUG);

            // Display character menu
            System.out.println("Available characters:");
            for (int i = 0; i < characters.length; i++) {
                System.out.println("- " + characters[i]);
            }
            System.out.println("- New character");

            boolean empty = false;

            do {
                empty = false;

                System.out.print("~> ");

                String characterSelection = scanner.nextLine().trim();

                if (characterSelection.equals("New character")) {
                    // Welcome message
                    System.out.println();
                    Utility.slowPrint("Hi, my name is Nanami, and I am your companion on this journey.\n" + "First, we'll create your character that will represent you.\n");
                    System.out.println();

                    // Create new character
                    String[] userInfo = CharacterManaging.createNewCharacter(scanner);

                    System.out.println();

                    // Create new story
                    Object[] storyInfo = (Object[]) StoryManaging.newStory(scanner, userInfo[0], DEBUG);
                    // done = true;
                    done = (boolean) storyInfo[0];
                    storyName = (String) storyInfo[1];
                    break;
                } else if (characterSelection.isBlank()) {
                    System.out.println("Error: Character name cannot be empty!");
                    empty = true;
                } else {
                    // Create new story for existing character
                    Object[] storyInfo = (Object[]) StoryManaging.newStory(scanner, characterSelection, DEBUG);
                    done = (boolean) storyInfo[0];
                    storyName = (String) storyInfo[1];
                    break;
                }
            }
            while (empty);

            System.out.println();
        }
        while (!done);

        return new Object[] {done, storyName};
    }

    public static Object handleMainMenuLoad() {
        
    }
}