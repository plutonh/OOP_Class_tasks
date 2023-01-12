package deque;

import static org.junit.Assert.assertEquals;

public class Test {
    //    @Test
//    public void linked_randomized() {
//        LinkedListDeque<Integer> L = new LinkedListDeque<>();
//
//        int N = 500;
//        for (int i = 0; i < N; i += 1) {
//            int operationNumber = StdRandom.uniform(0, 2);
//            if (operationNumber == 0) {
//                // addLast
//                int randVal = StdRandom.uniform(0, 100);
//                L.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
//            } else if (operationNumber == 1) {
//                // size
//                int size = L.size();
//                System.out.println("size: " + size);
//            }
//        }
//    }
//
//    @Test
//    public void array_randomized() {
//        ArrayDeque<Integer> L = new ArrayDeque<>();
//
//        int N = 500;
//        for (int i = 0; i < N; i += 1) {
//            int operationNumber = StdRandom.uniform(0, 2);
//            if (operationNumber == 0) {
//                // addLast
//                int randVal = StdRandom.uniform(0, 100);
//                L.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
//            } else if (operationNumber == 1) {
//                // size
//                int size = L.size();
//                System.out.println("size: " + size);
//            }
//        }
//    }
//    @Test
//    public void Test_A() {
//
//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
//
//        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
//        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
//
//        for (int i = 0; i < 10000000; i++) {
//            lld1.addLast(i);
//            ad1.addLast(i);
//        }
//
//        for (double i = 0; i < 5000000; i++) {
//            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
//            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
//        }
//
//        for (double i = 9999999; i > 5000000; i--) {
//            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
//            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
//        }
//    }

    @org.junit.Test
    public void Test_B() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            lld1.addFirst(i);
            ad1.addFirst(i);
            System.out.printf("Linked: ");
            lld1.printDeque();
            //System.out.print(lld1.size());
            //System.out.println();
            System.out.printf("Array:  ");
            ad1.printDeque();
            //System.out.print(ad1.size());
            //System.out.println();
        }

        for (double i = 0; i < 5; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }

        for (double i = 9; i >= 5; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }
        System.out.print(lld1.size());
        System.out.println();
        System.out.print(ad1.size());
        System.out.println();
        System.out.print(lld1.isEmpty());
        System.out.println();
        System.out.print(ad1.isEmpty());
        System.out.println();
    }
}
