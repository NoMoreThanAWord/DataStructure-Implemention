public class Array<E> {
    private E[] data;//存储的数组
    private int size;//记录数组中元素的个数，同时也是新增元素的下标

    public Array(int capacity){
        data = (E[]) new Object[capacity];//Java无法直接创建泛型数组，只能这样强转
        size = 0;
    }

    public Array(){
        this(10);//默认容量为10
    }

    //获取数组容量
    public int getCapacity(){
        return data.length;
    }

    //获取数组大小
    public int getSize(){
        return size;
    }

    //检测数组是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    //向数组某位置添加元素
    public void add(int index, E e){
        if(index < 0 || index > size)//检测添加位置是否合法
            throw new IllegalArgumentException("add failed, index illegal");

        if(size == data.length)//数组将满，进行扩容
            resize(2 * data.length);

        //将添加位置以及之后的元素集体向后移动一个单位
        for(int i = size-1 ; i >= index ; i--){
            data[i+1] = data[i];
        }
        //添加操作
        data[index] = e;
        //更新数组大小
        size++;
    }

    public void addLast(E e){
        add(size, e);
    }

    public void addFirst(E e){
        add(0, e);
    }

    public E get(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("get failed, illegal index");
        return data[index];
    }

    public void set(int index, E e){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("get failed, illegal index");
        data[index] = e;
    }

    public boolean contains(E e){
        for(int i = 0 ; i < size ; i++){
            if(data[i].equals(e))
                return true;
        }
        return false;
    }

    public int find(E e){
        for(int i = 0 ; i < size ; i++){
            if(data[i].equals(e))
                return i;
        }
        return -1;
    }

    //移除某位置的元素，并返回
    public E remove(int index){
    	//参数合法性判断
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("remove failed, illegal index");
        E ret = data[index];//待删除的返回元素
        //把待删除后面的元素往前移动一个单位，对待删除元素进行覆盖
        for(int i = index+1 ; i < size ; i++){
            data[i - 1] = data[i];
        }
        //更新数组大小
        size--;
        data[size] = null;//及时置空无用指向，便于垃圾回收
        //当数组使用不足容量的 1/4 时而且缩容后容量不为零时执行缩容
        if(size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);
        return ret;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size-1);
    }

    public void removeElement(E e){
        int index = find(e);
        if(index != -1)
            remove(index);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append("[");
        for(int i = 0 ; i < size ; i++){
            res.append(data[i]);
            if(i != size-1)
                res.append(", ");
        }
        res.append("]");
        return res.toString();
    }

    //储存元素的数组重新分配空间
    private void resize(int newCapacity){
        E[] newdata = (E[]) new Object[newCapacity];//重新开辟数组
        for(int i = 0 ; i < size ; i++)//原数组内容复制
            newdata[i] = data[i];
        data = newdata;//修改储存元素的数组的指向，指向新开辟的数组，原数组被垃圾回收
    }
}
