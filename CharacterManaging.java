// Import the Scanner class
import java.util.Scanner;

// Import libraries for databases
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Import libraries for hashmaps
import java.util.HashMap;
import java.util.Map;

public class CharacterManaging {

    // Create new character
    public static String newCharacter(Scanner scanner) {
        // Set variables
        String characterName;
        String characterPronouns;

        // Ask user for character name
        while (true) {
            // Prompt user to enter character name
            System.out.print("Please enter your character name.\n~> ");

            // Check if user entere3d something
            if (scanner.hasNextLine()) {
                characterName = scanner.nextLine().trim();  // Read user input
                break;
            }

            System.out.println();
        }
    
        // Ask user for character gender
        System.out.print("Please enter your gender (Or leave blank).\n~> ");

        String characterGender = scanner.nextLine().trim();  // Read user input

        System.out.println();

        // Ask user for character sexual orientation
        System.out.print("Please enter your orientation (Or leave blank).\n~> ");

        String characterOrientation = scanner.nextLine().trim();  // Read user input

        System.out.println();

        // Ask user for character pronouns
        do {
            // Prompt user to enter character pronouns
            System.out.print("Please enter your pronouns in the format 'she/her' (Used for correct storytelling).\n~> ");
            characterPronouns = scanner.nextLine().trim();  // Read user input

            try {
                // Split pronouns to subject and object part
                String[] pronounsParts = characterPronouns.split("/");
                String characterSubjectPronouns = pronounsParts[0].trim();
                String characterObjectPronouns = pronounsParts[1].trim();

                // Save the pronouns to a database for later use
                try {
                    // Load the SQLite JDBC driver
                    Class.forName("org.sqlite.JDBC");
        
                    // Connect to the SQLite database
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");
        
                    // Create a statement
                    Statement statement = connection.createStatement();
        
                    // Create a table
                    String createTableQuery = "CREATE TABLE IF NOT EXISTS characters (id INTEGER PRIMARY KEY, characterName TEXT, characterGender TEXT, characterOrientation TEXT, characterSubjectPronouns TEXT, characterObjectPronouns TEXT)";
                    statement.execute(createTableQuery);
        
                    // Insert data into the table
                    String insertDataQuery = "INSERT INTO characters (characterName, characterGender, characterOrientation, characterSubjectPronouns, characterObjectPronouns) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertDataQuery);
                    
                    // Set the parameter values
                    preparedStatement.setString(1, characterName);
                    preparedStatement.setString(2, characterGender);
                    preparedStatement.setString(3, characterOrientation);
                    preparedStatement.setString(4, characterSubjectPronouns);
                    preparedStatement.setString(5, characterObjectPronouns);

                    // Execute the prepared statement
                    preparedStatement.executeUpdate();

                    // Close the statement and connection
                    preparedStatement.close();
                    statement.close();
                    connection.close();
        
                    System.out.println("Character created successfully.");
                } catch (Exception e) {
                    System.err.println("ERROR: " + e.getMessage());
                }
                
                // Return character info
                return characterName;
            } catch (ArrayIndexOutOfBoundsException e) { // Check if pronouns are in the right format
                System.out.print("ERROR: Invalid format of the pronouns! Please enter pronouns in the format 'she/her'.\n");
            }
        } while (true);
    }

    // Load existing character
    public static String loadCharacter(Scanner scanner) {
        // Set variables
        boolean done = false;
        String characterSelection = "";
        String[] characters = {};
        boolean found = false;
        String characterName = "";

        // Retrieve existing characters
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
    
            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");
    
            // Create a statement
            Statement statement = connection.createStatement();
    
            // Retrieve the character names and IDs from the characters table
            String getCharacterInfoQuery = "SELECT id, characterName FROM characters";
            ResultSet resultSet = statement.executeQuery(getCharacterInfoQuery);
    
            // Create a map to store character names by ID
            Map<Integer, String> characterMap = new HashMap<>();
    
            // Retrieve the character names and IDs and store them in the map
            while (resultSet.next()) {
                int characterId = resultSet.getInt("id");
                characterName = resultSet.getString("characterName");
                characterMap.put(characterId, characterName);
            }
    
            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
    
            // Create an array to store the character names sorted by ID
            characters = new String[characterMap.size()];
            for (Map.Entry<Integer, String> entry : characterMap.entrySet()) {
                int characterId = entry.getKey();
                characterName = entry.getValue();
                characters[characterId - 1] = characterName;
            }
        } catch (SQLException e) {
            System.out.println("No characters avilable.");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        
        // Prompt user to select character
        do {
            if (characters == null) {
                System.out.println("No characters avilable.");
            } else {
                System.out.println("Available characters:");

                // Print characters under each other
                for (int i = 0; i < characters.length; i++) {
                    System.out.println("- " + characters[i]);
                }
                
                System.out.print("~> ");

                // Check if user entered something
                if (scanner.hasNextLine()) {
                    characterSelection = scanner.nextLine().trim(); // Read user input

                    // Check if user input is existing character
                    for (String character : characters) {
                        if (character.equals(characterSelection)) {
                            found = true;
                            break;
                        }
                    }

                    // Print error if user input is not existing character
                    if (found) {
                        done = true;
                    } else {
                        System.out.println("Character not found! Please select one of the available characters. (It is case sensitive)");
                        System.out.println();
                        done = false;
                    }
                }
            }
        }
        while (done != true);

        return characterSelection;
    }
}