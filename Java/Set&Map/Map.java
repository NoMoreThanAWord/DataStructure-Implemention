public interface Map<K, V> {
    public void add(K key, V value);//添加的key已存在，则执行更新操作

    public V remove(K key);

    public boolean contains(K key);

    public V get(K key);

    public void set(K key, V value);//更新的key不存在，报错

    public int getSize();

    public boolean isEmpty();
}
