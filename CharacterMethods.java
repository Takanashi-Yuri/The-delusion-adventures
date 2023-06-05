// Import the Scanner class
import java.util.Scanner;

// Import libraries for databases
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Import libraries for hashmaps
import java.util.HashMap;
import java.util.Map;

public class CharacterMethods {
    public static String[] createNewCharacter(Scanner scanner) {
        // Set user name
        String userName;
        do {
            System.out.print("Please enter your name (Or nickname).\n~> ");
            userName = scanner.nextLine().trim();  // Read user input
            System.out.println();
            if (userName.isEmpty()) {
                System.out.print("Error: Your name (nickname) cannot be blank!\n");
            }
        } while (userName.isEmpty());
    
        // Set gender
        System.out.print("Please enter your gender (Or leave blank).\n~> ");

        String userGender = scanner.nextLine().trim();  // Read user input

        System.out.println();

        // Set orientation
        System.out.print("Please enter your orientation (Or leave blank).\n~> ");

        String userOrientation = scanner.nextLine().trim();  // Read user input

        System.out.println();

        // Set pronouns
        String userPronouns;
        do {
            System.out.print("Please enter your pronouns in the format 'she/her' (Used for correct storytelling).\n~> ");
            userPronouns = scanner.nextLine().trim();  // Read user input

            System.out.println();

            try {
                String[] pronounsParts = userPronouns.split("/");
                String userSubjectPronouns = pronounsParts[0].trim();
                String userObjectPronouns = pronounsParts[1].trim();

                // Save the information to a file - still in testing
                try {
                    // Load the SQLite JDBC driver
                    Class.forName("org.sqlite.JDBC");
        
                    // Connect to the SQLite database
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");
        
                    // Create a statement
                    Statement statement = connection.createStatement();
        
                    // Create a table
                    String createTableQuery = "CREATE TABLE IF NOT EXISTS characters (id INTEGER PRIMARY KEY, userName TEXT, userGender TEXT, userOrientation TEXT, userSubjectPronouns TEXT, userObjectPronouns TEXT)";
                    statement.execute(createTableQuery);
        
                    // Insert data into the table
                    String insertDataQuery = "INSERT INTO characters (userName, userGender, userOrientation, userSubjectPronouns, userObjectPronouns) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertDataQuery);
                    
                    // Set the parameter values
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, userGender);
                    preparedStatement.setString(3, userOrientation);
                    preparedStatement.setString(4, userSubjectPronouns);
                    preparedStatement.setString(5, userObjectPronouns);

                    // Execute the prepared statement
                    preparedStatement.executeUpdate();

                    // Close the statement and connection
                    preparedStatement.close();
                    statement.close();
                    connection.close();
        
                    System.out.println("Database operations completed successfully.");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                
                // Return user info
                return new String[]{userName, userSubjectPronouns, userObjectPronouns};
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("Error: Invalid format of the pronouns! Please enter pronouns in the format 'she/her'.\n");
            }
        } while (true);
    }

    // Method to retrieve existing characters
    public static String[] getExistingCharacters() {
        // Save the information to a file - still in testing
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
    
            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data/main.db");
    
            // Create a statement
            Statement statement = connection.createStatement();
    
            // Retrieve the character names and IDs from the characters table
            String getCharacterInfoQuery = "SELECT id, userName FROM characters";
            ResultSet resultSet = statement.executeQuery(getCharacterInfoQuery);
    
            // Create a map to store character names by ID
            Map<Integer, String> characterMap = new HashMap<>();
    
            // Retrieve the character names and IDs and store them in the map
            while (resultSet.next()) {
                int characterId = resultSet.getInt("id");
                String characterName = resultSet.getString("userName");
                characterMap.put(characterId, characterName);
            }
    
            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
    
            // Create an array to store the character names sorted by ID
            String[] characters = new String[characterMap.size()];
            for (Map.Entry<Integer, String> entry : characterMap.entrySet()) {
                int characterId = entry.getKey();
                String characterName = entry.getValue();
                characters[characterId - 1] = characterName;
            }
    
            return characters;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage()); // Just for debugging purposes
            return new String[0]; // Return an empty array if an error occurs
        }
    }
}