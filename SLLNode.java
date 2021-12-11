//************************  SLLNode.java  *******************************
//           node in a generic singly linked list class 

public class SLLNode<T> {
    public T el;
    public SLLNode<T> next;
    public SLLNode() {
        this(null,null);
    }
    public SLLNode(T el) {
        this(el,null);
    }
    public SLLNode(T el, SLLNode<T> next) {
        this.el = el; this.next = next;
    }
}

