import java.util.ArrayList;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node{
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap(){
        root = null;
        size = 0;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    // 向二分搜索树中添加新的元素(key, value)
    @Override
    public void add(K key, V value){
        root = add(key, value, root);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(K key, V value, Node node){
        if(node == null){
            size++;
            return new Node(key, value);
        }

        else if(key.compareTo(node.key) == 0){
            node.value = value;
        }
        else if(key.compareTo(node.key) < 0)
            node.left = add(key, value, node.left);
        else
            node.right = add(key, value, node.right);
        return node;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){
        if(node == null)
            return null;
        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else
            return getNode(node.right, key);
    }

    @Override
    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){
        if(node.left == null){
            size--;
            Node temp = node.right;//待移除结点的右孩子顶替他成为根
            node.right = null;
            return temp;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    @Override
    public V remove(K key) {
        Node ret = getNode(root, key);
        if(ret == null)
            return null;
        root = remove(key, root);
        return ret.value;
    }

    private Node remove(K key, Node node){
        if(node == null)//查找不到
            return null;

        //查找成功
        if(node.key.compareTo(node.key) == 0){
            Node temp;
            //左孩子为空，右孩子成为根
            if(node.left == null){
                temp = node.right;
                node.right = null;
                size--;
            }
            //右孩子为空，左孩子成为根
            else if(node.right == null){
                temp = node.left;
                node.left = null;
                size--;
            }
            //两孩子都不为空，右子树中最大的(后继)或者左子树中最小的(前驱)成为根，这里选择后继作为实现
            else {
                temp = minimum(node.right);//找出后继结点

                //在右子树中移除后继结点，并把后继结点连接上原来根节点的左右子树
                temp.right = removeMin(node.right);//在右子树中移除后继结点的操作中已经维护了size，不用做多余的维护了
                temp.left = node.left;

                //断开原来根节点
                node.left = node.right = null;
            }
            return temp;
        }
        else if(node.key.compareTo(node.key) < 0){
            node.left = remove(key, node.left);
        }
        else
            node.right = remove(key, node.right);
        return node;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            BSTMap<String, Integer> map = new BSTMap<>();
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