public class SmallMethods {

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

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Loading stopped, exiting program...");
            System.exit(1);
        }
    }
}