public class SegmentTree<E> {
    private E[] data;//存储数组(大小为 n,无冗余)
    private E[] tree;//存储区间信息的线段树(大小为 4*n 有冗余)
    private Merger<E> merger;//定义区间统计行为的接口(对区间执行什么操作，加和，求最值，求平均值)

    public SegmentTree(E[] arr, Merger<E> merger){
        data = (E[])new Object[arr.length];
        tree = (E[])new Object[4 * arr.length];
        this.merger = merger;
        for(int i = 0 ; i < arr.length ; i++)
            data[i] = arr[i];
        build(0, 0, data.length-1);//从根结点开始构建线段树
    }

    //更新原始数组中的值并维护线段树
    public void set(int i, E e){
        if(i < 0 || i >= data.length)
            throw new IllegalArgumentException("insert postion out of range!");
        data[i] = e;
        set(0, 0, data.length-1, i, e);
    }

    //在根结点索引为 Index 的线段树中更新 i 位置的值并维护树结构
    private void set(int index, int l, int r, int i, E e){
        if(l == r){
            //找到更新位置
            tree[index] = e;
            return;
        }

        int mid = l + (r-l)/2;
        int lChild = leftChild(index);
        int rChild = rightChild(index);

        if(i > mid)
            set(rChild, mid+1, r, i, e);
        else if(i <= mid)
            set(lChild, l, mid, i, e);
        tree[index] = merger.merge(tree[lChild], tree[rChild]);//更新区间值
    }

    //查询区间结果
    public E query(int lQuery, int rQuery){
        if(lQuery < 0 || lQuery >= data.length || rQuery < 0 || rQuery > data.length || lQuery > rQuery)
            throw new IllegalArgumentException("query range illegal!");
        return query(0, 0, data.length-1, lQuery, rQuery);
    }

    //在根结点索引为 index 指示范围为 l~r 的树执行范围查询结果
    private E query(int index, int l, int r, int lQuery, int rQuery){
        if(l == lQuery && r == rQuery)
            return tree[index];
        int mid = l + (r-l)/2;
        int lChild = leftChild(index);
        int rChild = rightChild(index);
        if(lQuery > mid)
            return query(rChild, mid+1, r, lQuery, rQuery);
        else if(rQuery <= mid)
            return query(lChild, l, mid, lQuery, rQuery);
        else
            return merger.merge(query(lChild, l, mid, lQuery, mid), query(rChild, mid+1, r, mid+1, rQuery));
    }

    //构建根结点为 index, 根结点指示范围为 l~r 的线段树
    private void build(int index, int l, int r){
        //递归到底，指示范围为一个点，tree 结点的值与 data 数组的值对应
        if(l == r){
            tree[index] = data[l];
            return;//及时返回
        }
        int left = leftChild(index);
        int right = rightChild(index);
        int mid = l + (r-l) / 2;
        build(left, l, mid);//递归构建左边线段树
        build(right, mid+1, r);//递归构建右边线段树
        tree[index] = merger.merge(tree[left], tree[right]);//类似后续遍历，利用左右线段子树的区间值得到根的区间值
    }

    //获得原始数组的大小
    public int getSize(){
        return data.length;
    }

    //获取原始数组元素
    public E get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("index out of range!");
        return data[index];
    }

    //获取左孩子的索引
    private int leftChild(int index){
        return 2*index + 1;
    }

    //获取右孩子的索引
    private int rightChild(int index){
        return 2*index + 2;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int i = 0 ; i < tree.length ; i++){
            if(tree[i] == null)
                stringBuilder.append("null");
            else
                stringBuilder.append(tree[i]);
            if(i != tree.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
