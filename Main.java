import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Trie<String> dictionary = new Trie<>(); // a trie for the dictionary
        Scanner sc = new Scanner(new File("dictionary.txt"));  // input file
        while (sc.hasNextLine())
            dictionary.insert(sc.nextLine()); // insert all words from the file to the trie
        sc.close(); // close the scanner

        Trie<String> trie = new Trie<>(); // a trie for the user
        String inp; // for containing user input
        Scanner input = new Scanner(System.in);
        String choice;
        String menu = """

                TRIE PROJECT: Choose one of the following..
                1) Create an empty trie
                2) Create a trie with initial letters
                3) Insert a word
                4) Delete a word
                5) List all words that begin with a prefix
                6) Size of the trie
                7) Search for a word
                8) Print all words
                9) End
                Enter your choice:\s""";
        // loop while the user does not choose to exit
        boolean ended = false;
        while (!ended) {  // while the user does not choose to exit
            System.out.print(menu);
            choice = input.next();
            switch (choice) {
                case "1" -> {   // Create an empty Trie
                    trie = new Trie<>();
                    System.out.println("New empty trie created.");
                }
                case "2" -> {   // Create a trie with initial letters
                    trie = new Trie<>();
                    System.out.print("Enter your list of letters> ");
                    input.nextLine(); // to get rid of the remaining portion of the previous input line
                    inp = input.nextLine().toUpperCase().replaceAll(" ", ""); // convert to uppercase, and remove whitespaces
                    String[] permutations = diffLengthPermutations(inp); // all possible permutations with different lengths
                    for (String word : permutations) // for each word from the permutations;
                        if (dictionary.contains(word)) // if the word is in the dictionary;
                            trie.insert(word);  // add the word to the trie

                    System.out.printf("Trie with initials %s created.\n", inp);
                }
                case "3" -> {  // Insert a word
                    System.out.print("Enter a word to insert> ");
                    inp = input.next().toUpperCase().replaceAll("[^A-Z]", ""); // change to uppercase, then remove all non-alphabetic characters;
                    trie.insert(inp);
                    System.out.printf("Word %s added.\n", inp);
                }
                case "4" -> {  // Delete a word
                    System.out.print("Enter a word to delete> ");
                    inp = input.next().toUpperCase();
                    trie.delete(inp);
                }
                case "5" -> {  // List all words that begin with a prefix
                    System.out.print("Enter a prefix> ");
                    inp = input.next().toUpperCase();
                    System.out.printf("All words that start with %s:\n", inp);
                    System.out.println(Arrays.toString(trie.allWordsPrefix(inp)));
                }
                case "6" ->  // Size of the trie
                        System.out.println("Size of the trie: " + trie.size());
                case "7" -> {  // Search for a word
                    System.out.print("Enter a word to search for> ");
                    inp = input.next().toUpperCase();
                    if (trie.contains(inp))
                        System.out.printf("Word Found: %s\n", inp);
                    else
                        System.out.printf("Word %s not found.\n", inp);
                }
                case "8" -> {  // Print all words in the trie
                    String[] allWords = trie.allWords();
                    System.out.println("All words in the trie:\n" + Arrays.toString(allWords));
                }
                case "9" -> {  // End
                    System.out.println("Exiting program..");
                    ended = true;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
        input.close();  // close the scanner
    }

    // Gets all permutations of a word, with all possible lengths
    public static String[] diffLengthPermutations(String s) {
        Set<String> words = new HashSet<>(); // a set to avoid duplicate values
        String[] permutations = permutations(s); // getting the permutations with the full length of the string (using permutations method)
        for (String perm : permutations) { // for each permutation of full length;
            for (int i = perm.length(); i > 0; i--)
                words.add(perm.substring(0, i)); // add all words with all lengths starting from the first letter to the set
        }
        return words.toArray(new String[0]); // return the set as an array
    }

    // Gets all permutations of a word
    public static String[] permutations(String s) {
        ArrayList<String> words = new ArrayList<>();
        if (s.length() <= 1)  // if the word is empty or has 1 character
            words.add(s);
        else if (s.length() == 2) { // if the word has 2 characters
            words.add("" + s.charAt(0) + s.charAt(1)); // first letter + second letter
            words.add("" + s.charAt(1) + s.charAt(0)); // second letter + first letter
        }
        else { // if the word has more than 2 characters
            for (String letter : s.split("")) { // for each letter in the word
                String[] permsForOtherLetters = permutations(s.replaceFirst(letter, "")); // get the permutations with the other letters
                for (String perm : permsForOtherLetters) // for each of the permutations;
                    words.add(letter + perm); // add the current letter to the permutation, then add it to the list
            }
        }

        return words.toArray(new String[0]); // return the list as an array
    }
}
