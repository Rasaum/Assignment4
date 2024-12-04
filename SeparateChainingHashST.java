import java.util.LinkedList;
import java.util.Queue;

public class SeparateChainingHashST<Key, Value> {
    private int N;
    private int M;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private int hash(Key key, int hashType) {
        int hash = 0;
        String keyString = key.toString();

        if (hashType == 1) { // Old hash function
            int skip = Math.max(1, keyString.length() / 8);
            for (int i = 0; i < keyString.length(); i += skip) {
                hash = (hash * 37) + keyString.charAt(i);
            }
        } else { // Current hash function
            for (int i = 0; i < keyString.length(); i++) {
                hash = (hash * 31) + keyString.charAt(i);
            }
        }
        return (hash & 0x7fffffff) % M;
    }

    public void put(Key key, Value val, int hashType) {
        int index = hash(key, hashType);
        st[index].put(key, val);
        N++;
    }

    public Value get(Key key, int hashType) {
        int index = hash(key, hashType);
        return st[index].get(key);
    }

    public int getCost(Key key, int hashType) {
        int index = hash(key, hashType);
        return st[index].getWithCost(key);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                queue.add(key);
            }
        }
        return queue;
    }
}
