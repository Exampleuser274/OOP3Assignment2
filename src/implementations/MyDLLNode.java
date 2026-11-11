package implementations;

/**
 * A single node in a doubly linked list (DLL), used as the building block
 * for {@link MyDLL} in CPRG 304 at SAIT.
 * <p>
 * Each node stores a {@link String} element and maintains two directional
 * references: a predecessor ({@code pred}) pointing to the node immediately
 * before it in the chain, and a successor ({@code succ}) pointing to the
 * node immediately after it. A {@code null} predecessor indicates the node
 * is the head of the list; a {@code null} successor indicates it is the tail.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code DLLNode<E>}), allowing any object type to be stored in the node.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its usages.
 * </p>
 */
public class MyDLLNode
{
	 // The value stored inside this node
    private String element;
    
    // Predecessor and Successor are defined above as pred and succ

    // The node before this one
    private MyDLLNode pred;

    // The node after this one
    private MyDLLNode succ;

    // Constructor to create a new node
    public MyDLLNode(String element, MyDLLNode pred, MyDLLNode succ) {
        this.element = element;
        this.pred = pred;
        this.succ = succ;
    }

    // Returns the stored element
    public String getElement() {
        return this.element;
    }

    // Returns the previous node
    public MyDLLNode getPred() {
        return this.pred;
    }

    // Returns the next node
    public MyDLLNode getSucc() {
        return this.succ;
    }

    // Replaces the element and returns the old value
    public String setElement(String element) {
        String oldElement = this.element;
        this.element = element;
        return oldElement;
    }

    // Updates the previous node
    public void setPred(MyDLLNode pred) {
        this.pred = pred;
    }

    // Updates the next node
    public void setSucc(MyDLLNode succ) {
        this.succ = succ;
    }
}