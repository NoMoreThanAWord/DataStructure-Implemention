//基于动态数组实现的队列
public class ArrayQueue<E> implements Queue<E> {

    private Array<E> array;

    public ArrayQueue(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayQueue(){
        array = new Array<>();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    //enqueue操作在动态数组的末尾进行添加，不用对数组进行整理，是 o(1) 的
    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    //dequeue操作在动态数组的头部进行添加，每次都要对数组进行整理，是 o(n) 的
    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("queue: ");
        res.append("front [");
        for(int i = 0 ; i < array.getSize() ; i++){
            res.append(array.get(i));
            if(i != array.getSize() - 1)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }
}
