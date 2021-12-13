public class TrieNode<T> {
    private String letter;  // the letter carried by the node
    private TrieNode[] childNodes; // an array of pointers to child nodes
    protected boolean isEndOfWord; // true if a word ends on the current node

    public TrieNode() {
        this(null);
    }
    public TrieNode(String letter) {
        this.letter = letter;
        childNodes = new TrieNode[26]; // an empty array for child nodes of size 26 (A-Z)
        isEndOfWord = false;
    }

    public String getLetter() {
        return this.letter;
    }
    public TrieNode[] getChildren() {
        return this.childNodes;
    }

    // Checks if a TrieNode containing a specific letter is a child of this node
    public TrieNode<String> getChild(String s) {
        if (this.childNodes.length == 0) // if this node has no child nodes
            return null;
        int hashIndex = getHash(s); // the hash index for the childNodes array
        TrieNode child = this.getChildren()[hashIndex]; // get the child node carrying the letter
        return child;  // return the child node; if it is null, the method returns null
    }

    // Adds a new child node to the current node
    public TrieNode<String> addChild(String s) {
        TrieNode<String> newChild = new TrieNode<>(s); // create a new TrieNode with the specified letter
        int hashIndex = getHash(s); // the hash index for the childNodes array
        childNodes[hashIndex] = newChild; // set the childNode at the specified index to the new TrieNode
        return newChild;
    }

    // Returns the hash index for a letter in the childNodes array
    public int getHash(String s) {
        char letter = s.toUpperCase().charAt(0);
        return letter - 'A';
    }


}
