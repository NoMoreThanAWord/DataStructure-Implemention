public class Main {

    public static void main(String[] args) {
        Array<Integer> array = new Array<Integer>();
        for(int i = 0 ; i < 10 ; i++)
            array.addLast(i+1);
        System.out.println(array);

        array.addLast(11);
        System.out.println(array);

        array.removeFirst();
        System.out.println(array);

        array.removeFirst();
        System.out.println(array);
        
    }
}
