import java.util.Scanner;

public class Main {
    // Main setion
    public static void main(String[] args) {
        // Show loading screen
        displayLoadingScreen();

        // Initiate scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Wait for user to press Enter
        waitForEnter(scanner);

        // Show main menu of the game
        displayMainMenu(scanner);

        // Close scanner and free resources
        scanner.close();
    }

    // Method for displaying loading screen
    public static void displayLoadingScreen() {
        System.out.println("Welcome to the Adventure");
        SmallMethods.sleep(1000);

        System.out.println("- Loading astral matter...");
        SmallMethods.sleep(1000);
        
        System.out.println("- Planting ash flowers...");
        SmallMethods.sleep(1000);

        System.out.println("Loading completed\n");
    }

    // Method for user to press Enter to continue
    public static void waitForEnter(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    // Method for handling Main menu
    public static void displayMainMenu(Scanner scanner) {
        while (true) {
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
                        System.out.println("Select existing character or create new one.");

                        // Retrieve existing characters
                        String[] characters = CharacterMethods.getExistingCharacters();

                        // Display character menu
                        System.out.println("Available characters:\n");
                        for (int i = 0; i < characters.length; i++) {
                            System.out.println((i + 1) + ". " + characters[i]);
                        }
                        System.out.println("New character");
                        System.out.println();
                        
                        System.out.print("~> ");

                        String characterSelection = scanner.nextLine().trim();

                        if (characterSelection.equals("New character")) {
                            // Welcome message
                            String introduction = "Hi, my name is Nanami, and I am your companion on this journey.\n" + "First, we'll create your character that will represent you.\n";
                            SmallMethods.slowPrint(introduction);
                            System.out.println();

                            // Create new character
                            String[] userInfo = CharacterMethods.createNewCharacter(scanner);

                            // Create new story
                            StoryMethods.newStory(scanner, userInfo[0]);
                        } else {
                            StoryMethods.newStory(scanner, characterSelection);
                        }

                        break;
                    case "load":
                        // Implement the code for loading an existing story
                        System.out.println("Loading an existing story...");
                        break;
                    case "exit":
                        System.out.println("Leaving the program. See you next time!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Unknown option, please choose one of the available options.");
                        break;
                }

                System.out.println();
            }
        }
    }
}