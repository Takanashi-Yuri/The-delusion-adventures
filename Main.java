// Import the Scanner class
import java.util.Scanner;

public class Main {
    // Set global class variables
    public static Scanner scanner = new Scanner(System.in);
    public static boolean done = false;
    public static boolean doBreak = false;

    // Main setion
    public static void main(String[] args) {
        // Set variables
        Object[] result = {};

        // Show loading screen
        displayLoadingScreen();

        // Wait for user to press Enter
        waitForEnter();

        while (true) {
            doBreak = false;

            // Show main menu
            displayMainMenu();

            while (true) {
                doBreak = false;

                // Show character menu
                result = (Object[]) displayCharacterMenu();

                // Retrieve loaded character name
                String characterName = (String) result[0];

                // check if not returning to main menu
                doBreak = (Boolean) result[1];

                System.out.println(characterName);

                // Break if returning to main menu
                if (doBreak) {
                    break;
                }

                while (true) {
                    doBreak = false;

                    // Show story menu
                    result = (Object[]) displayStoryMenu(characterName);

                    // Retrieve loaded character name
                    String storyName = (String) result[0];

                    // check if not returning to main menu
                    doBreak = (Boolean) result[1];

                    System.out.println(storyName + " - " + characterName);

                    // Break if returning to main menu
                    if (doBreak) {
                        break;
                    }

                    // Start the main part
                    // loadStory();
                }
            }
        }
    }

    // Method for displaying loading screen
    public static void displayLoadingScreen() {
        System.out.println("Welcome to the Text adventures");
        Utility.sleep(1000);

        System.out.println("- Loading astral matter...");
        Utility.createDirectory();
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

    // Method for displaying main menu
    public static void displayMainMenu() {
        do {
            String[] mainMenuOptions = {
                "Start - Start game.",
                "Settings - Open settings.",
                "Help - Get help about this game.",
                "Exit - Exit the program."
            };

            System.out.println(String.join("\n", mainMenuOptions));
            System.out.print("~> ");

            if (scanner.hasNextLine()) {
                String selectedOption = scanner.nextLine().trim();

                switch (selectedOption.toLowerCase()) {
                    case "start":
                        // Just pass to next part of the main method
                        done = true;
                        break;
                    case "settings":
                        // Open method for settings from Main.java
                        // ...
                        System.out.println();

                        done = false;
                        break;
                    case "help":
                        // Open method for help from Main.java
                        // ...
                        System.out.println();
                        
                        done = false;
                        break;
                    case "exit":
                        System.out.println("Leaving the astral dimension. See you next time!");
                        scanner.close(); // Close scanner and free resources

                        // End (kill) program
                        System.exit(1);
                    default:
                        System.out.println("Unknown option, please choose one of the available options.");
                        System.out.println();

                        done = false;
                        break;
                }
            }
        }
        while (done != true);

        System.out.println();
    }

    // Method for displaying character menu
    public static Object displayCharacterMenu() {
        // Set variables
        String characterName = "";

        do {
            String[] characterMenuOptions = {
                "New - Create new character.",
                "Load - Load an existing character.",
                "Exit - Exit to main menu."
            };

            System.out.println(String.join("\n", characterMenuOptions));
            System.out.print("~> ");

            if (scanner.hasNextLine()) {
                String option = scanner.nextLine().trim();

                switch (option.toLowerCase()) {
                    case "new":
                        // Welcome message
                        System.out.println();
                        Utility.slowPrint("Hi, my name is Nanami, and I will be your companion on this journey.\n" + "First, we'll create your character that will represent you.\n");

                        // Create new character
                        characterName = CharacterManaging.newCharacter(scanner);

                        done = true;
                        break;
                    case "load":
                        // Load existing character
                        System.out.println();
                        characterName = CharacterManaging.loadCharacter(scanner);

                        done = true;
                        break;
                    case "exit":
                        System.out.println();
                        System.out.println("Returning to main menu...");

                        doBreak = true;
                        done = true;
                        break;
                    default:
                        System.out.println();
                        System.out.println("Unknown option, please choose one of the available options.");
                        System.out.println();

                        done = false;
                        break;
                }
            }
        }
        while (done != true);

        System.out.println();

        return new Object[] {characterName, doBreak};
    }

    // Method for displaying story menu
    public static Object displayStoryMenu(String characterName) {
        // Set variables
        String storyName = "";

        do {
            String[] storyMenuOptions = {
                "New - Create new story.",
                "Load - Load an existing story.",
                "Exit - Exit to character menu."
            };

            System.out.println(String.join("\n", storyMenuOptions));
            System.out.print("~> ");

            if (scanner.hasNextLine()) {
                String option = scanner.nextLine().trim();

                switch (option.toLowerCase()) {
                    case "new":
                        // Welcome message
                        System.out.println();
                        Utility.slowPrint("Okay, let's create new interesting story.\n");

                        // Create new story
                        storyName = StoryManaging.newStory(scanner, characterName);

                        done = true;
                        break;
                    case "load":
                        System.out.println();
                        storyName = StoryManaging.loadStory(scanner, characterName);

                        done = true;
                        break;
                    case "exit":
                        System.out.println();
                        System.out.println("Returning to character menu...");

                        doBreak = true;
                        done = true;
                        break;
                    default:
                        System.out.println();
                        System.out.println("Unknown option, please choose one of the available options.");
                        System.out.println();

                        done = false;
                        break;
                }
            }
        }
        while (done != true);

        System.out.println();

        return new Object[] {storyName, doBreak};
    }
}