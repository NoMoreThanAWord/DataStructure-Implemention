//基于动态数组实现的栈
public class ArrayStack<E> implements Stack<E> {
    private Array<E> array;

    public ArrayStack(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayStack(){
        array = new Array<>();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    //push操作在动态数组的末尾进行添加，不用对数组进行整理，是 o(1) 的
    @Override
    public void push(E e) {
        array.addLast(e);
    }

    //pop操作在动态数组的末尾进行添加，不用对数组进行整理，也是 o(1) 的
    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("stack: ");
        res.append("[");
        for(int i = 0 ; i < array.getSize() ; i++){
            res.append(array.get(i));
            if(i != array.getSize() - 1)
                res.append(", ");
        }
        res.append("] top");
        return res.toString();
    }
}
