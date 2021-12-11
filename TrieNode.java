import java.util.ArrayList;

public class TrieNode<T> {
    private String letter;  // the letter carried by the node
    private ArrayList<TrieNode<String>> nextNodesPointer; // an ArrayList of pointers to child nodes
    protected boolean isEndOfWord; // true if a word ends on the current node

    public TrieNode() {
        this(null);
    }
    public TrieNode(String letter) {
        this.letter = letter;
        nextNodesPointer = new ArrayList<TrieNode<String>>();
        isEndOfWord = false;
    }

    public String getLetter() {
        return this.letter;
    }
    public ArrayList<TrieNode<String>> getChildren() {
        return this.nextNodesPointer;
    }

    // Checks if a TrieNode containing a specific letter is a child of this node
    public TrieNode<String> isChild(String s) {
        if (this.nextNodesPointer.isEmpty()) // if this node has no child nodes
            return null;
        for (TrieNode child : nextNodesPointer) // loop over each child node
            if (child.letter.equals(s))
                return child;
        return null;  // if no child node has the specified letter
    }

    public TrieNode<String> addChild(String s) {
        TrieNode<String> newChild = new TrieNode<>(s);
        nextNodesPointer.add(newChild);
        return newChild;
    }


}
