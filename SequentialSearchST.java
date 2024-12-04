import java.util.LinkedList;
import java.util.Queue;

public class SequentialSearchST<Key, Value> {
    private Node first; // First node in the linked list

    // Node class
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    // Put key-value pair into the list
    public void put(Key key, Value val) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);
    }

    // Get value for a given key
    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) return x.val;
        }
        return null;
    }

    // Collect all keys in the linked list
  /**  public Iterable<Key> keys() {
        Queue<Key> queue = new LinkedList<>();
        for (Node x = first; x != null; x = x.next) {
            queue.add(x.key);
        }
        return queue;
    }**/

    public int getWithCost (Key key){
        int comparison = 0;
        for (Node x = first; x != null; x = x.next){
            comparison++;
            if (key.equals(x.key)) return comparison;
        }
        return comparison;
    }

    public Iterable<Key> keys(){
        Queue<Key> queue = new LinkedList<>();
        for (Node x = first; x != null; x =x.next){
            queue.add(x.key);
        }
        return queue;
    }
}
