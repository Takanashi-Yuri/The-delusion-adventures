import java.util.Scanner;

public class Combat {
    public static boolean mainCombat(int userHeal, int weaponsLVL, int enemyHeal, String enemyName, String userName, Scanner scanner) {
        // Initialyze variables
        String[] weaponOptions = {};

        int[] weaponsDamage = {};

        // Unlock weapons based on lvl of the character
        if (weaponsLVL >= 1){
            weaponOptions[0] = "gun";
            weaponsDamage[0] = 1;

            if (weaponsLVL >= 2){
                weaponOptions[1] = "axe";
                weaponsDamage[1] = 2;

                if (weaponsLVL >= 3){
                    // And so on...
                }
            }
        }

        // let player to choose weapon
        System.out.println("Choose your weapon:");

        System.out.println(String.join("\n", "- " + weaponOptions));
        System.out.print("~> ");

        // Actual combat mechanics
        boolean win = true; // Just for demonstation - remove after code completion
        // ...
        while (true) {


            // Check if player or enemy died, if yes and combat
            if (enemyHeal == 0 || userHeal == 0) {
                break;
            }
        }
        // Return true if player wins
        if (win == true){
            return true;
        } else {
            return false;
        }
    }
}
