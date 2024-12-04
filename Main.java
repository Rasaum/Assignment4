import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SeparateChainingHashST<String, String> oldHashTable = new SeparateChainingHashST<>(1000);
        SeparateChainingHashST<String, String> currentHashTable = new SeparateChainingHashST<>(1000);

        String filepath = "wordlist.10000.txt";
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                oldHashTable.put(line, String.valueOf(lineNumber), 1); // Old hash function
                currentHashTable.put(line, String.valueOf(lineNumber), 2); // Current hash function
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Please enter a password, enter 'exit' to quit:");
        while (true) {
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (password.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program.");
                break;
            }

            boolean isStrong = isStrongPassword(password, oldHashTable, currentHashTable);
            if (isStrong) {
                System.out.println("Password is strong.");
            }
        }
    }

    public static boolean isStrongPassword(String password,
                                           SeparateChainingHashST<String, String> oldHashTable,
                                           SeparateChainingHashST<String, String> currentHashTable) {

        if (password.length() < 8) {
            System.out.println("Password is too short.");
            return false;
        }

        if (oldHashTable.get(password, 1) != null || currentHashTable.get(password, 2) != null) {
            System.out.println("Password is in the dictionary.");
            return false;
        }

        if (password.length() > 1) {
            String withoutLast = password.substring(0, password.length() - 1);
            char lastChar = password.charAt(password.length() - 1);

            if (Character.isDigit(lastChar) &&
                    (oldHashTable.get(withoutLast, 1) != null || currentHashTable.get(withoutLast, 2) != null)) {
                System.out.println("Password is in the dictionary followed by a digit.");
                return false;
            }
        }

        int costOldChaining = oldHashTable.getCost(password, 1);
        int costCurrentChaining = currentHashTable.getCost(password, 2);

        LinearProbingHashST<String, String> linearProbingTable = new LinearProbingHashST<>();
        for (String word : currentHashTable.keys()) {
            linearProbingTable.put(word, currentHashTable.get(word, 2), 1); // Old hash function
            linearProbingTable.put(word, currentHashTable.get(word, 2), 2); // Current hash function
        }

        int costLinearOld = linearProbingTable.getCost(password, 1);
        int costLinearCurrent = linearProbingTable.getCost(password, 2);

        System.out.printf("Separate Chaining (Old Hash): %d comparisons\n", costOldChaining);
        System.out.printf("Separate Chaining (Current Hash): %d comparisons\n", costCurrentChaining);
        System.out.printf("Linear Probing (Old Hash): %d comparisons\n", costLinearOld);
        System.out.printf("Linear Probing (Current Hash): %d comparisons\n", costLinearCurrent);

        return true;
    }
}