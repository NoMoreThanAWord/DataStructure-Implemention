//动态数组实现循环队列，队列为空 front == tail，队列为满(不是 front == tail 而是 tail + 1 == front 即浪费一个容量)
public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int size;
    private int front, tail;

    public LoopQueue(int capacity){
        data = (E[]) new Object[capacity + 1];//多开一个冗余容量
        size = 0;
        front = 0;
        tail = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity + 1];
        for(int i = 0 ; i < size ; i++)
            newData[i] = data[(front + i) % data.length];//新开数组从头开始排

        front = 0;
        tail = size;
        data = newData;
    }

    @Override
    public void enqueue(E e) {
        if((tail + 1) % data.length == front)
            resize(getCapacity() * 2);
        data[tail] = e;
        tail = (tail + 1) % data.length;//入队维护 tail
        size++;//维护 size
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            throw new IllegalArgumentException("can not dequeue a empty queue");
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;//出队维护 front
        size--;//维护 size
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);
        return ret;
    }

    @Override
    public E getFront() {
        if(isEmpty())
            throw new IllegalArgumentException("empty queue");
        return data[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("queue: size = %d capacity = %d ", getSize(), getCapacity()));
        res.append("front [");
        for(int i = 0 ; i < getSize() ; i++){
            res.append(data[(front + i) % data.length]);
            if(i != getSize() - 1)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }

}
