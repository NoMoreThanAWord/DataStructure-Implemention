public class Main {

    public static void main(String[] args) {

        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for(int num: nums)
            bst.add(num);

        /////////////////
        //      5      //
        //    /   \    //
        //   3    6    //
        //  / \    \   //
        // 2  4     8  //
        /////////////////

        for(int i = 8 ; i >= 2 ; i--){
            bst.remove(i);
            bst.inOrder();
            System.out.println();
        }
        System.out.println("size = " + bst.getSize());

    }
}