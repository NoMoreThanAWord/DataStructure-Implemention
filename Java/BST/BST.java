import java.util.*;

public class BST<E extends Comparable<E>> {
    private class Node{
        public E data;//结点存放的数据
        public Node left;//左孩子
        public Node right;//右孩子

        //初始化结点
        public Node(E e){
            data = e;
            left = null;
            right = null;
        }
    }

    private Node root;//二分搜索树的根结点
    private int size;//搜索树的结点个数

    //初始化搜索树
    public BST(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //对外接口，添加元素
    public void add(E e){
        root = add(root, e);
    }

    //在以某结点为根的子树上插入元素，并返回插入后树的根结点。该方法在执行插入的过程中不断的把结点串联起来
    private Node add(Node root, E e){
        if(root == null){
            size++;
            return new Node(e);
        }
        if(e.compareTo(root.data) < 0)
            root.left = add(root.left, e);
        else if(e.compareTo(root.data) > 0)
            root.right = add(root.right, e);
        return root;
    }

    public boolean contains(E e){
        return contains(root, e);
    }

    private boolean contains(Node root, E e){
        if(root == null)
            return false;
        if(e.compareTo(root.data) == 0)
            return true;
        else if(e.compareTo(root.data) < 0)
            return contains(root.left, e);
        else
            return contains(root.right, e);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    // 生成以node为根节点，深度为depth的描述二叉树的字符串
    private void generateBSTString(Node node, int depth, StringBuilder res){

        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.data + "\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++)
            res.append("--");
        return res.toString();
    }

    //非递归先序遍历
    public void preOrderNR(){
        if(root == null)
            return;

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        while(!stack.empty()){
            Node temp = stack.pop();
            System.out.print(temp.data + " ");
            if(temp.right != null)
                stack.push(temp.right);

            if(temp.left != null)
                stack.push(temp.left);
        }
    }

    //先序遍历
    private void preOrder(Node node){
        if(node == null)
            return;
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void preOrder(){
        preOrder(root);
    }

    //后序遍历
    private void postOrder(Node node){
        if(node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    public void postOrder(){
        postOrder(root);
    }

    //中序遍历
    private void inOrder(Node node){
        if(node == null)
            return;
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    public void inOrder(){
        inOrder(root);
    }

    //先序遍历
    public void levelOrder(){
        if(root == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node temp = queue.remove();
            System.out.print(temp.data + " ");
            if(temp.left != null)
                queue.add(temp.left);
            if(temp.right != null)
                queue.add(temp.right);
        }
    }

    public E minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty!");
        return minimum(root).data;
    }

    //找以某结点为根的子树中最小的结点
    public Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);

    }

    //找以某结点为根的子树中最大的结点
    public Node maximum(Node node){
        if(node.right == null)
            return node;
        return minimum(node.right);

    }

    public E maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty!");
        return maximum(root).data;
    }

    public E removeMin(){
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    public E removeMax(){
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    //返回移除以某结点为根的子树中最小的结点后的根结点
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

    //返回移除以某结点为根的子树中最大的结点后的根结点
    private Node removeMax(Node node){
        if(node.right == null){
            size--;
            Node temp = node.left;//待移除结点的左孩子顶替他成为根
            node.left = null;
            return temp;
        }

        node.right = removeMax(node.right);
        return node;
    }

    public void remove(E e){
        root = remove(root, e);
    }

    //返回移除以某结点为根的子树中任意值的结点后的根结点
    private Node remove(Node node, E e){
        if(node == null)//查找不到
            return null;

        //查找成功
        if(e.compareTo(node.data) == 0){
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
        else if(e.compareTo(node.data) < 0){
            node.left = remove(node.left, e);
        }
        else
            node.right = remove(node.right, e);
        return node;
    }
}
