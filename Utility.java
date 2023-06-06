// Import libraries for file managing
import java.io.File;

// Import libraries for array managing
import java.util.ArrayList;
import java.util.List;

public class Utility {

    // Slowly print text to terminal
    public static void slowPrint(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(50); // Adjust the delay duration (in milliseconds) as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Sleep for specified time
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Loading stopped, exiting program...");
            System.exit(1);
        }
    }

    // Create directories if they doesn't exist
    public static void createDirectory(boolean DEBUG) {
        List<String> directoryPaths = new ArrayList<>();
        directoryPaths.add("data");
        // directoryPaths.add("path/to/directory2");
        // directoryPaths.add("path/to/directory3");

        for (String directoryPath : directoryPaths) {
            File directory = new File(directoryPath);

            if (!DEBUG) {
                if (!directory.exists()) {
                    boolean created = directory.mkdirs();

                    if (created) {
                        System.out.println("  - Portal '" + directoryPath + "' created successfully!");
                    } else {
                        System.out.println("  - Failed to create the portal '" + directoryPath + "'.");
                    }
                } else {
                    System.out.println("  - Portal '" + directoryPath + "' already exists.");
                }
            }
        }
    }
}