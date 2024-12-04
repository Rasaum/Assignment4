public class LinearProbingHashST <Key, Value> {
        private int M = 20000; // Fixed size
        private Key[] keys;
        private Value[] vals;

        public LinearProbingHashST() {
            keys = (Key[]) new Object[M];
            vals = (Value[]) new Object[M];
        }

        private int hash(Key key, int hashType) {
            int hash = 0;
            if (hashType == 1) { // Old hash function
                int skip = Math.max(1, key.toString().length() / 8);
                for (int i = 0; i < key.toString().length(); i += skip)
                    hash = (hash * 37) + key.toString().charAt(i);
            } else { // Current hash function
                for (int i = 0; i < key.toString().length(); i++)
                    hash = (hash * 31) + key.toString().charAt(i);
            }
            return (hash & 0x7fffffff) % M;
        }

        public void put(Key key, Value val, int hashType) {
            int i;
            for (i = hash(key, hashType); keys[i] != null; i = (i + 1) % M) {
                if (keys[i].equals(key)) {
                    vals[i] = val;
                    return;
                }
            }
            keys[i] = key;
            vals[i] = val;
        }

        public Value get(Key key, int hashType) {
            int i;
            for (i = hash(key, hashType); keys[i] != null; i = (i + 1) % M) {
                if (keys[i].equals(key)) return vals[i];
            }
            return null;
        }

        public int getCost(Key key, int hashType) {
            int i, comparisons = 0;
            for (i = hash(key, hashType); keys[i] != null; i = (i + 1) % M) {
                comparisons++;
                if (keys[i].equals(key)) break;
            }
            return comparisons;
        }
    }



