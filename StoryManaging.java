// Import the Scanner class
import java.util.Scanner;

// Import libraries for databases
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoryManaging {

    // Create new story for specified character
    public static Object newStory(Scanner scanner, String characterName, boolean DEBUG) {
        boolean done = false;

        // Create new story and save it to the database
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");

            // Create a statement
            Statement statement = connection.createStatement();

            // Create the stories table if it doesn't exist
            String createStoriesTableQuery = "CREATE TABLE IF NOT EXISTS stories (id INTEGER PRIMARY KEY, characterId INTEGER, storyName TEXT, FOREIGN KEY (characterId) REFERENCES characters(id))";
            statement.execute(createStoriesTableQuery);

            // Retrieve the character's ID from the characters table
            String getCharacterIdQuery = "SELECT id FROM characters WHERE userName = ?";
            PreparedStatement getCharacterIdStatement = connection.prepareStatement(getCharacterIdQuery);
            getCharacterIdStatement.setString(1, characterName);
            ResultSet characterIdResult = getCharacterIdStatement.executeQuery();

            int characterId = -1; // Default value if character not found
            String storyName = "";

            if (characterIdResult.next()) {
                characterId = characterIdResult.getInt("id");

                // Prompt the user to enter the story text
                System.out.println();
                System.out.println("Enter the story name (save name).");
                while (true) {
                    System.out.print("~> ");
                    storyName = scanner.nextLine().trim();

                    if (!storyName.isBlank()) {
                        // Insert the story into the stories table
                        String insertStoryQuery = "INSERT INTO stories (characterId, storyName) VALUES (?, ?)";
                        PreparedStatement insertStoryStatement = connection.prepareStatement(insertStoryQuery);
                        insertStoryStatement.setInt(1, characterId);
                        insertStoryStatement.setString(2, storyName);
                        insertStoryStatement.executeUpdate();

                        System.out.println("Story saved successfully!");
                        done = true;
                        break;
                    } else {
                        System.out.println("Error: Story name cannot be empty!");
                    }
                }
            } else {
                System.out.println("Character not found!");
                done = false;
            }

            // Close the result set, statements, and connection
            characterIdResult.close();
            getCharacterIdStatement.close();
            statement.close();
            connection.close();

            return new Object[] {done, storyName};
        } catch (SQLException e) {
            System.out.println("Character not found!");
            done = false;
            return done;
        } catch (Exception e) {
            if (DEBUG) {
                System.err.println("Error: " + e.getMessage());
            }
            return done;
        }
    }

    // Load existing story - currently not working
    public static void loadStory(Scanner scanner) {
        String introduction = "Please insert your character name for list of existing stories\n~> ";
        Utility.slowPrint(introduction);
        String characterName = scanner.nextLine();  // Read user input
    }
}