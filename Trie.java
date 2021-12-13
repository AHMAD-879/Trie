import java.util.*;

public class Trie<T> {
    private TrieNode<String> root; // the root node of the Trie

    public Trie() {
        root = new TrieNode<>();
    }

    // Returns the hash index for a letter in the childNodes array
    public int getHash(String s) {
        char letter = s.toUpperCase().charAt(0);
        return letter - 'A';
    }

    // TODO: Required Methods

    // Checks if a specified String is a word contained in the trie
    public boolean contains(String s) {
        s = s.toUpperCase();
        TrieNode<String> currentNode = root; // initialized value for the loop
        for (String letter : s.split("")) { // loop over all the letters
            currentNode = currentNode.getChild(letter); // set the currentNode to the child carrying the currentLetter
            if (currentNode == null) // if the child node carrying the letter is null;
                return false;
        }
        return currentNode.isEndOfWord; // if the currentNode of the last letter is an end of a word, return true
    }

    // Checks if a String is a prefix of any word in the trie
    public boolean isPrefix(String p) {
        p = p.toUpperCase();
        TrieNode<String> currentNode = root; // initialized value for the loop
        for (String letter : p.split("")) {  // for each letter in the specified String
            currentNode = currentNode.getChild(letter); // checks if the letter is a child node of the current node
            if (currentNode == null)   // if the letter is not a child node of the current node
                return false;
        }
        return true; // if all the letters are contained as a sequence in the trie, return true
    }

    // Inserts a word into the trie
    public void insert(String s) {
        s = s.toUpperCase().replaceAll("[^A-Z]", ""); // change to uppercase, then remove all non-alphabetic characters
        TrieNode<String> childNode, currentNode = root; // initialized values for the loop
        for (String letter : s.split("")) {  // for each letter in the specified String
            childNode = currentNode.getChild(letter); // checks if the letter is a child node of the current node
            if (childNode == null) // if the letter is not a child node of the current node
                childNode = currentNode.addChild(letter); // assign the childNode to a new child node containing the letter
            currentNode = childNode; // assign the currentNode to the childNode for the next iteration
        }
        currentNode.isEndOfWord = true; // assign the last letter of the word to be the end of the word
    }

    // Deletes a String from the trie
    public void delete(String s) {
        s = s.toUpperCase();
        if (!this.contains(s)) { // if the word is not contained in the trie
            System.out.printf("ERROR: The word %s is not contained in the trie.\n", s); // print an error message to the user
            return;
        }
        String currentWord = ""; // an empty string to add letters onto
        TrieNode<String> childNode, currentNode = root; // initialized values for the loop
        for (String letter : s.split("")) { // loop over all the letters
            currentWord += letter;
            childNode = currentNode.getChild(letter); // set the childNode to the child carrying the currentLetter
            int childWordsCount = allWordsPrefix(currentWord).length; // counts all the words made from the current word as a prefix
            if (childWordsCount == 1) { // if the childNode only has 1 child word
                currentNode.getChildren()[getHash(childNode.getLetter())] = null; // set the node at the index of the child node to null
                System.out.printf("Word %s deleted.\n", s); // print successful deletion to the user
                return;
            }
            currentNode = childNode;
        }
        // if the word is a prefix for other words (deleting the node is not possible);
        currentNode.isEndOfWord = false; // set the value of isEndOfWord of the node carrying the last letter to false
        System.out.printf("Word %s deleted.\n", s); // print successful deletion to the user
    }

    // Checks if a trie is empty
    public boolean isEmpty() {
        return (root.getChildren().length == 0);
    }

    // Clears the trie
    public void clear() {
        root = new TrieNode<>();
    }

    // Returns all words starting with a specified prefix
    public String[] allWordsPrefix(String p) {
        // Checks if the prefix is contained in the trie, and gets the node carrying the last letter of the prefix
        p = p.toUpperCase();
        TrieNode<String> currentNode = root; // initialized value for the loop
        for (String letter : p.split("")) {  // for each letter in the prefix
            currentNode = currentNode.getChild(letter); // checks if the letter is a child node of the current node
            if (currentNode == null)   // if the letter is not a child node of the current node;
                return null; // then the prefix is not contained in the trie
        }

        ArrayList<String> allWords = new ArrayList<>(); // an empty list to contain the words

        if (currentNode.isEndOfWord) // if the prefix is a word in the trie;
            allWords.add(p);  // add it to the words list

        if (currentNode.getChildren().length != 0) { // if the node with the last letter of the prefix has children;
            ArrayList<String> childrenWords = RecursiveAllWordsPrefix(p, currentNode); // get all the words from the child nodes starting with the specified prefix
            allWords.addAll(childrenWords); // add the new words to the current list of words
        }

        return allWords.toArray(new String[0]); // return the current list of words as an array
    }

    // A helper method for allWordsPrefix; Returns a list with all words starting after a specific node in the trie
    public ArrayList<String> RecursiveAllWordsPrefix(String prefix, TrieNode<String> n) {
        ArrayList<String> listOfWords = new ArrayList<>(); // an empty list to contain the words
        for (TrieNode<String> childNode : n.getChildren()) { // for each of the child nodes of the specified node n

            if (childNode == null) // if the child node is null;
                continue;     // continue to the next iteration

            if (childNode.isEndOfWord) // if the child node is an end of a word;
                listOfWords.add(prefix + childNode.getLetter()); // add the word to the list of words

            if (childNode.getChildren().length != 0) // if the node has other children;
                listOfWords.addAll(RecursiveAllWordsPrefix(prefix + childNode.getLetter(), childNode)); // recursively add the words from the child nodes
        }

        return listOfWords;  // return the list of words starting after the specified node
    }

    // Returns all words in the trie
    public String[] allWords() {
        ArrayList<String> allWords = new ArrayList<>(); // an empty ArrayList to hold all the words contained in the trie
        for (TrieNode<String> childNode : root.getChildren()) // for each child node of the root node
            if (childNode != null) // if the child node is not null;
                allWords.addAll(List.of(allWordsPrefix(childNode.getLetter()))); // add all the words that start with the letter of the child node
        return allWords.toArray(new String[0]);
    }

    // Returns the number of nodes in the trie
    public int size() {
        return size(this.root);
    }

    // A helper method for size(); recursively counts the number of nodes in the trie
    public int size(TrieNode<String> n) {
        int total = 0;
        for (TrieNode<String> childNode : n.getChildren()) { // for each of the child nodes of the specified node n
            if (childNode == null) // if the child node is null;
                continue;       // continue to the next iteration

            total++; // increment the total by 1
            if (childNode.getChildren().length > 0) // if the node has other children;
                total += size(childNode); // recursively count the number of child nodes
        }
        return total;
    }
}
