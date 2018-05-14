public class LinkedListQueue<E> implements Queue<E> {

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

    //队列一端执行删除令一端执行插入。头结点删除为 o(1),插入也为 o(1)，尾结点删除为 o(n),插入为 o(1)，为了使所有操作都为 o(1)，故在尾部执行入队，头部执行出队
    private Node head;//头结点，出队
    private Node tail;//尾结点，入队
    private int size;

    public LinkedListQueue(){
        //队列为空
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E e) {
        //队列为空时单另处理
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            throw new IllegalArgumentException("can not dequeue from an empty queue");
        Node ret = head;
        head = head.next;
        ret.next = null;
        //出队后队列为空，对尾结点也进行处理
        if(head == null)
            tail = null;
        size--;
        return ret.data;
    }

    @Override
    public E getFront() {
        if(isEmpty())
            throw new IllegalArgumentException("empty queue");
        return head.data;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("queue: front ");
        Node cur = head;
        while(cur != null){
            ret.append(cur.data + "->");
            cur = cur.next;
        }
        ret.append("null tail");
        return ret.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedListQueue<>();
        for(int i = 1 ; i <= 5 ; i++) {
            queue.enqueue(i);
            System.out.println(queue);
        }

        queue.dequeue();
        System.out.println(queue);
    }
}
