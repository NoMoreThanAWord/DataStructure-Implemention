import java.util.HashMap;
//结点不保存数据，边保存数据
public class Trie {
    private class Node{
        private HashMap<Character, Node> next;//多叉，根据边寻找下一个结点
        private boolean isWord;//指示当前结点是否为一个单词的结尾

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new HashMap<>();
        }

        public Node(){
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public void add(String word){
        Node p = root;
        for(int i = 0 ; i < word.length() ; i++){
            char temp = word.charAt(i);
            if(!p.next.containsKey(temp))
                p.next.put(temp, new Node());
            p = p.next.get(temp);
        }
        //防止重复添加
        if(!p.isWord){
            p.isWord = true;
            size++;
        }
    }

    //检查是否包含某单词
    public boolean contains(String word){
        Node p = root;
        for(int i = 0 ; i < word.length() ; i++){
            char temp = word.charAt(i);
            if(!p.next.containsKey(temp))
                return false;
            p = p.next.get(temp);
        }
        return p.isWord;//为添加该单词，仅仅为前缀，返回假
    }

    //检查是否包含某前缀
    public boolean isPrefix(String prefix){
        Node p = root;
        for(int i = 0 ; i < prefix.length() ; i++){
            char temp = prefix.charAt(i);
            if(!p.next.containsKey(temp))
                return false;
            p = p.next.get(temp);
        }
        return true;//直接返回真
    }

}
