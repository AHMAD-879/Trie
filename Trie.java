import java.util.*;

public class Trie<T> {
    private TrieNode<String> root; // the root node of the Trie

    public Trie() {
        root = new TrieNode<String>();
    }

    // TODO: Required Methods

    // Checks if a String is a word contained in the trie
    public boolean contains(String s) {
        s = s.toUpperCase();
        TrieNode<String> child, currentNode = root; // initialized values for the loop
        for (String letter : s.split("")) {  // for each letter in the specified String
            child = currentNode.isChild(letter); // checks if the letter is a child node of the current node
            if (child == null)   // if the letter is not a child node of the current node
                return false;
            else  // if the letter is a child node of the current node
                currentNode = child;
        }
        return currentNode.isEndOfWord; // if the last node is assigned as an end of a word, return true
    }

    // Checks if a String is a prefix of any word in the trie
    public boolean isPrefix(String p) {
        p = p.toUpperCase();
        TrieNode<String> child, currentNode = root; // initialized values for the loop
        for (String letter : p.split("")) {  // for each letter in the specified String
            child = currentNode.isChild(letter); // checks if the letter is a child node of the current node
            if (child == null)   // if the letter is not a child node of the current node
                return false;
            else  // if the letter is a child node of the current node
                currentNode = child;
        }
        return true; // if all the letters are contained as a sequence in the trie, return true
    }

    // Inserts a word into the trie
    public void insert(String s) {
        s = s.toUpperCase().replaceAll("[^A-Z]", ""); // change to uppercase, then remove all non-alphabetic characters
        TrieNode<String> child, currentNode = root; // initialized values for the loop

        for (String letter : s.split("")) {  // for each letter in the specified String
            child = currentNode.isChild(letter); // checks if the letter is a child node of the current node
            if (child != null)   // if the letter is already a child node of the current node
                currentNode = child;  // assign the currentNode to the child node
            else   // if the letter is not a child node of the current node
                currentNode = currentNode.addChild(letter); // assign the currentNode to a new child node containing the letter
        }
        currentNode.isEndOfWord = true; // assign the last letter of the word to be the end of the word
    }

    // Deletes a String from the trie
    public void delete(String s) {
        s = s.toUpperCase();
        TrieNode<String> child, currentNode = root; // initialized values for the loop
        for (String letter : s.split("")) { // for each letter in the specified String
            child = currentNode.isChild(letter); // checks if the letter is a child node of the current node
            if (child == null) { // if the letter is not a child node of the current node
                System.out.printf("ERROR: The word %s is not contained in the trie.\n", s); // print a message to the user
                return;
            } else  // if the letter is a child node of the current node
                currentNode = child;
        }
        if (currentNode.isEndOfWord) // if the word is contained in the trie
            currentNode.isEndOfWord = false; // set the isEndOfWord value of the last letter to false, thus deleting the word from the trie
        else // if the String is not a word in the trie, but is a prefix instead
            System.out.printf("ERROR: The word %s is not contained in the trie.\n", s); // print a message to the user
    }

    // Checks if a trie is empty
    public boolean isEmpty() {
        return (root.getChildren().isEmpty());
    }

    // Clears the trie
    public void clear() {
        root = new TrieNode<String>();
    }

    // Returns all words starting with a specified prefix
    public String[] allWordsPrefix(String p) {
        p = p.toUpperCase();
        TrieNode<String> child, currentNode = root; // initializing variables for the loop
        for (String letter : p.split("")) {  // for each letter in the specified String
            child = currentNode.isChild(letter); // checks if the letter is a child node of the current node
            if (child == null)   // if the letter is not a child node of the current node
                return null;
            else  // if the letter is a child node of the current node
                currentNode = child;
        }

        ArrayList<String> allWords = new ArrayList<>(); // an empty list to contain the words

        if (currentNode.isEndOfWord) // if the prefix is a word in the trie;
            allWords.add(p);  // add it to the words list

        if (!currentNode.getLetter().isEmpty()) { // if the node with the last letter of the prefix has children;
            ArrayList<String> childrenWords = RecursiveAllWordsPrefix(p, currentNode); // get all the words from the child nodes starting with the specified prefix
            allWords.addAll(childrenWords); // add the new words to the current list of words
        }

        return allWords.toArray(new String[0]); // return the current list of words as an array
    }

    // Returns all words in the trie
    public String[] allWords() {
        ArrayList<String>  allWords = new ArrayList<>();
        for (TrieNode<String> childNode : root.getChildren()) // for each child node of the root node
            allWords.addAll(List.of(allWordsPrefix(childNode.getLetter()))); // add all the words that start with the letter of the child node
        return allWords.toArray(new String[0]);
    }

    // A helper method for allWordsPrefix; Returns a list with all words starting after a specific node in the trie
    public ArrayList<String> RecursiveAllWordsPrefix(String prefix, TrieNode<String> n) {
        ArrayList<String> listOfWords = new ArrayList<>(); // an empty list to contain the words
        for (TrieNode<String> childNode : n.getChildren()) { // for each of the child nodes of the specified node n

            if (childNode.isEndOfWord) // if the child node is an end of a word;
                listOfWords.add(prefix + childNode.getLetter()); // add the word to the list of words

            if (!childNode.getChildren().isEmpty()) // if the node has other children;
                listOfWords.addAll(RecursiveAllWordsPrefix(prefix + childNode.getLetter(), childNode)); // recursively add the words from the child nodes
        }

        return listOfWords;  // return the list of words starting after the specified node
    }

    // Returns the number of nodes in the trie
    public int size() {
        return size(this.root);
    }

    // A helper method for size(); recursivly counts the number of nodes in the trie
    public int size(TrieNode<String> n) {
        int total = 0;
        for (TrieNode<String> childNode : n.getChildren()) { // for each of the child nodes of the specified node n
            total++; // increment the total by 1
            if (!childNode.getChildren().isEmpty()) // if the node has other children;
                total += size(childNode); // recursively count the number of child nodes
        }
        return total;
    }
}
