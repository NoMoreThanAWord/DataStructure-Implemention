public class LinkedList<E> {
	//把结点设立为私有内部类
    private class Node{
        public E data;
        public Node next;

        public Node(){
            this(null, null);
        }

        public Node(E data, Node next){
            this.data = data;
            this.next = next;
        }

        public Node(E data){
            this(data, null);
        }

        @Override
        public  String toString() {
            return data.toString();
        }
    }

    private Node dummyHead;//虚拟头结点
    private int size;//链表中元素的个数

    public LinkedList(){
        this.dummyHead = new Node(null, null);
        this.size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("add failed, illegal index");

        Node prev = dummyHead;
        for(int i = 0 ; i < index ; i++)
            prev = prev.next;

//        Node node = new Node(e);
//        node.next = prev.next;
//        prev.next = node;

        prev.next = new Node(e, prev.next);
        size++;
    }

    //头部添加元素 o(1)
    public void addFirst(E e){
//        Node node = new Node(e);
//        node.next = head;
//        head = node;
        add(0, e);
    }

    //尾部添加元素 o(n)
    public void addLast(E e){
        add(size, e);
    }

    public E get(int index){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("get failed, illegal index");
        Node cur = dummyHead.next;
        for(int i = 0 ; i < index ; i++)
            cur = cur.next;
        return cur.data;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size-1);
    }

    public void set(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("set failed, illegal index");
        Node cur = dummyHead.next;
        for(int i = 0 ; i < index ; i++)

        cur.data = e;
    }

    public boolean contains(E e){
        Node cur = dummyHead.next;
        while(cur != null){
            if(cur.data == e)
                return true;
            cur = cur.next;
        }
        return false;
    }

    public E remove(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("remove failed, illegal index");
        Node pre = dummyHead;
        for(int i = 0 ; i < index ; i++)
            pre = pre.next;
        Node ret = pre.next;
        pre.next = ret.next;
        ret.next = null;
        size--;
        return ret.data;
    }

    //删除头部 o(1)
    public E removeFirst(){
        return remove(0);
    }

    //删除尾部 o(n)
    public E removeLast(){
        return remove(size-1);
    }

    // 从链表中删除元素e
    public void removeElement(E e){

        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.data.equals(e))
                break;
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size --;
        }
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        Node cur = dummyHead.next;
        while(cur != null){
            res.append(cur.data + "->");
            cur = cur.next;
        }
        res.append("null");
        return res.toString();
    }

}
