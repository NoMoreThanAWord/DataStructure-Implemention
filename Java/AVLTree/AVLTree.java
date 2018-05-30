import java.util.ArrayList;

//键值形二叉树，方便封装成 map 与 set
public class AVLTree<K extends Comparable<K>, V> {
    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;//记录结点的高度，叶子结点的高度为 1

        //新增的结点都是叶子结点，所有 height 值默认为 1
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;//结点个数

    public AVLTree() {
        root = null;
        size = 0;
    }

    //获取结点个数
    public int getSize() {
        return size;
    }

    //判断空树
    public boolean isEmpty() {
        return size == 0;
    }

    //判断是否为二叉搜索树(中序遍历)
    public boolean isBST(){
        ArrayList<K> arrayList = new ArrayList<>();
        inOrder(root, arrayList);
        for(int i = 1 ; i < arrayList.size() ; i++)
            if(arrayList.get(i-1).compareTo(arrayList.get(i)) > 0)
                return false;
        return true;
    }

    private void inOrder(Node node, ArrayList<K> arrayList){
        if(node != null){
            inOrder(node.left, arrayList);
            arrayList.add(node.key);
            inOrder(node.right, arrayList);
        }
    }

    //判读树是否平衡
    public boolean isBalanced(){
        return isBalanced(root);
    }

    //递归判读树是否平衡
    private boolean isBalanced(Node node){
        if(node == null)//空即为平衡
            return true;
        if(Math.abs(getBalanceFactor(node)) > 1)//根节点平衡因子绝对值不超过 1
            return false;
        return isBalanced(node.left) && isBalanced(node.right);//左右子树也平衡
    }

    //获取结点的高度，空高度为零
    private int getHeight(Node node){
        if(node != null)
            return node.height;
        return 0;
    }

    //获取平衡因子(左右子树高度差)，为正说明左树高，为负说明右树高
    private int getBalanceFactor(Node node){
        if(node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2

    //只对 x,y 进行操作，操作后 T1~T4 的大小关系不变
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;
        y.left = T3;
        x.right = y;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));//重新维护结点高度，从子到父
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4

    //只对 x,y 进行操作，操作后 T1~T4 的大小关系不变
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;
        y.right = T2;
        x.left = y;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value) {
        root = add(key, value, root);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(K key, V value, Node node) {
        if (node == null) {
            size++;
            return new Node(key, value);
        } else if (key.compareTo(node.key) == 0) {
            node.value = value;
        } else if (key.compareTo(node.key) < 0)
            node.left = add(key, value, node.left);
        else
            node.right = add(key, value, node.right);
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));//插入完成后重新计算结点的高度
        int balanceFactor = getBalanceFactor(node);//重新计算结点的平衡因子

        //ll，左子树的左支插入导致，右旋解决
        if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);
        //rr，右子树的右支插入导致，左旋解决
        if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0)//rr
            return leftRotate(node);

        //lr，左子树的右分支插入导致，先对左子树左旋，转换为 ll
        if(balanceFactor > 1 && getBalanceFactor(node.left) < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //rl，右子树的左支插入导致，先对右子树右旋，转换为 rr
        if(balanceFactor < -1 && getBalanceFactor(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key) {
        if (node == null)
            return null;
        if (key.equals(node.key))
            return node;
        else if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else
            return getNode(node.right, key);
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key) {
        Node ret = getNode(root, key);
        if (ret == null)
            return null;
        root = remove(key, root);
        return ret.value;
    }

    private Node remove(K key, Node node) {
        if (node == null)//查找不到
            return null;

        Node temp;
        //查找成功
        if (key.compareTo(node.key) == 0) {

            //左孩子为空，右孩子成为根
            if (node.left == null) {
                temp = node.right;
                node.right = null;
                size--;
            }
            //右孩子为空，左孩子成为根
            else if (node.right == null) {
                temp = node.left;
                node.left = null;
                size--;
            }
            //两孩子都不为空，右子树中最大的(后继)或者左子树中最小的(前驱)成为根，这里选择后继作为实现
            else {
                temp = minimum(node.right);//找出后继结点

                //在右子树中移除后继结点，并把后继结点连接上原来根节点的左右子树
                temp.right = remove(temp.key, node.right);//在右子树中移除后继结点的操作中已经维护了size，不用做多余的维护了
                temp.left = node.left;

                //断开原来根节点
                node.left = node.right = null;
            }

        }
        else if (key.compareTo(node.key) < 0) {
            node.left = remove(key, node.left);
            temp = node;
        }
        else {
            node.right = remove(key, node.right);
            temp = node;
        }

        //删除后根为空，直接返回
        if(temp == null)
            return temp;

        temp.height = 1 + Math.max(getHeight(temp.left), getHeight(temp.right));//重新计算高度
        int balanceFactor = getBalanceFactor(temp);//重新计算平衡因子


        //尝试重新平衡
        if(balanceFactor > 1 && getBalanceFactor(temp.left) >= 0)
            return rightRotate(temp);
        if(balanceFactor < -1 && getBalanceFactor(temp.right) <= 0)
            return leftRotate(temp);
        if(balanceFactor > 1 && getBalanceFactor(temp.left) < 0){
            node.left = leftRotate(temp.left);
            return rightRotate(temp);
        }
        if(balanceFactor < -1 && getBalanceFactor(temp.right) > 0){
            node.right = rightRotate(temp.right);
            return leftRotate(temp);
        }
        //未进行任何平衡，直接返回
        return temp;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalanced());

            for(String word: words){
                map.remove(word);
                if(!map.isBST() || !map.isBalanced())
                    throw new RuntimeException("gaga");
            }

        }

        System.out.println();
    }
}
