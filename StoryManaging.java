// Import the Scanner class
import java.util.Scanner;

// Import libraries for databases
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StoryManaging {
    // Create new story for specified character
    public static String newStory(Scanner scanner, String characterName) {
        // Set variables
        boolean done = false;
        String storyName = "";

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
            String getCharacterIdQuery = "SELECT id FROM characters WHERE characterName = ?";
            PreparedStatement getCharacterIdStatement = connection.prepareStatement(getCharacterIdQuery);
            getCharacterIdStatement.setString(1, characterName);
            ResultSet characterIdResult = getCharacterIdStatement.executeQuery();

            int characterId = characterIdResult.getInt("id");

            do {
                // Prompt the user to enter the story text
                System.out.print("Enter the story name (save name).\n~> ");

                if (scanner.hasNextLine()) {
                    storyName = scanner.nextLine().trim();

                    // Insert the story into the stories table
                    String insertStoryQuery = "INSERT INTO stories (characterId, storyName) VALUES (?, ?)";
                    PreparedStatement insertStoryStatement = connection.prepareStatement(insertStoryQuery);
                    insertStoryStatement.setInt(1, characterId);
                    insertStoryStatement.setString(2, storyName);
                    insertStoryStatement.executeUpdate();

                    System.out.println("Story saved successfully!");
                    done = true;
                }
            }
            while (done != true);

            // Close the result set, statements, and connection
            characterIdResult.close();
            getCharacterIdStatement.close();
            statement.close();
            connection.close();

            return storyName;
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return storyName;
        }
    }

    // Load existing story - currently not working
    public static String loadStory(Scanner scanner, String characterName) {
        // Set variables
        String chosenStory = "";

        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");

            // Create a statement
            Statement statement = connection.createStatement();

            // Retrieve the character's ID from the characters table
            String getCharacterIdQuery = "SELECT id FROM characters WHERE characterName = ?";
            PreparedStatement getCharacterIdStatement = connection.prepareStatement(getCharacterIdQuery);
            getCharacterIdStatement.setString(1, characterName);
            ResultSet characterIdResult = getCharacterIdStatement.executeQuery();

            int characterId = characterIdResult.getInt("id");

            // Retrieve the stories for the character from the stories table
            String getStoriesQuery = "SELECT storyName FROM stories WHERE characterId = ?";
            PreparedStatement getStoriesStatement = connection.prepareStatement(getStoriesQuery);
            getStoriesStatement.setInt(1, characterId);
            ResultSet storiesResult = getStoriesStatement.executeQuery();

            // Print available stories
            System.out.println("Available stories:");

            while (storiesResult.next()) {
                String storyName = storiesResult.getString("storyName");
                System.out.println("- " + storyName);
            }

            // Prompt the user to choose a story
            System.out.print("~> ");

            if (scanner.hasNextLine()) {
                chosenStory = scanner.nextLine().trim();
                // Perform further operations with the chosen story if needed
            }

            // Close the result sets, statements, and connection
            storiesResult.close();
            characterIdResult.close();
            getStoriesStatement.close();
            getCharacterIdStatement.close();
            statement.close();
            connection.close();

            return chosenStory;
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return chosenStory;
        }
    }
}