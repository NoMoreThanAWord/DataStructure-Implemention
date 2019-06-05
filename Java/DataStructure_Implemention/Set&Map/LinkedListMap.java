import java.util.ArrayList;

public class LinkedListMap<K, V> implements Map<K, V> {
    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(){
            this(null, null, null);
        }

        public Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value){
            this(key, value, null);
        }

        @Override
        public  String toString() {
            return key.toString() + ":" + value.toString();
        }
    }

    private Node dummyHead;//虚拟头结点
    private int size;//链表中元素的个数

    public LinkedListMap(){
        dummyHead = new Node();
        size = 0;
    }

    public Node getNode(K key){
        Node temp = dummyHead.next;
        while(temp != null){
            if(temp.key.equals(key))
                return temp;
            temp = temp.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        Node temp = getNode(key);
        if(temp == null){
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        } else{
            temp.value = value;
        }
    }

    @Override
    public V remove(K key) {
        Node temp = dummyHead;
        while(temp.next != null){
            if(temp.next.key.equals(key)){
                Node ret = temp.next;
                temp.next = temp.next.next;
                ret.next = null;
                size--;
                return ret.value;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node temp = getNode(key);
        return temp == null ? null : temp.value;
    }

    @Override
    public void set(K key, V value){
        Node temp = getNode(key);
        if(temp == null)
            throw new IllegalArgumentException(key + "doesn't exist!");
        temp.value = value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            LinkedListMap<String, Integer> map = new LinkedListMap<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
