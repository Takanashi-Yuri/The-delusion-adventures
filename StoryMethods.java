import java.util.Scanner;  // Import the Scanner class

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StoryMethods {
    // Create new story and character
    public static void newStory(Scanner scanner, String characterName) {
        // Create new story and save it to the database
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");

            // Create a statement
            Statement statement = connection.createStatement();

            // Create the stories table if it doesn't exist
            String createStoriesTableQuery = "CREATE TABLE IF NOT EXISTS stories (id INTEGER PRIMARY KEY, characterId INTEGER, storyText TEXT, FOREIGN KEY (characterId) REFERENCES characters(id))";
            statement.execute(createStoriesTableQuery);

            // Retrieve the character's ID from the characters table
            String getCharacterIdQuery = "SELECT id FROM characters WHERE userName = ?";
            PreparedStatement getCharacterIdStatement = connection.prepareStatement(getCharacterIdQuery);
            getCharacterIdStatement.setString(1, characterName);
            ResultSet characterIdResult = getCharacterIdStatement.executeQuery();

            int characterId = -1; // Default value if character not found
            if (characterIdResult.next()) {
                characterId = characterIdResult.getInt("id");

                // Prompt the user to enter the story text
                System.out.print("Enter the story name (save name): ");
                String storyText = scanner.nextLine().trim();

                // Insert the story into the stories table
                String insertStoryQuery = "INSERT INTO stories (characterId, storyText) VALUES (?, ?)";
                PreparedStatement insertStoryStatement = connection.prepareStatement(insertStoryQuery);
                insertStoryStatement.setInt(1, characterId);
                insertStoryStatement.setString(2, storyText);
                insertStoryStatement.executeUpdate();

                System.out.println("Story saved successfully!");
            } else {
                System.out.println("Character not found!");
            }

            // Close the result set, statements, and connection
            characterIdResult.close();
            getCharacterIdStatement.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Load existing story
    public static void loadStory(Scanner scanner) {
        String introduction = "Please insert your character name for list of existing stories\n~> ";
        SmallMethods.slowPrint(introduction);
        String characterName = scanner.nextLine();  // Read user input
    }
}