public interface Merger<E> {
    //定义了区间操作，可以是最值，中位数，期望，平均值等等
    public E merge(E a, E b);
}
