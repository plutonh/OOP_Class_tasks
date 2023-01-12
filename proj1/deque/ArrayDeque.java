//package deque;
//
//import java.util.Iterator;
//
//public class ArrayDeque<T> implements Deque<T>{
//    public T[] items;
//    public int size;
//    public ArrayDeque(int cap) {
//        this.items = (T[]) new Object[cap];
//        size = 0;
//    }
//    public ArrayDeque() {
//        this.items = (T[]) new Object[100];
//        size = 0;
//    }
//    public void addFirst(T x) {
//        if(size >= items.length) {
//            T[] n = (T[]) new Object[size + 1];
//            System.arraycopy(items, 0, n, 1, size);
//            items = n;
//            n = null;
//        }
//        else {
//            T[] n = (T[]) new Object[size + 1];
//            System.arraycopy(items, 0, n, 1, size);
//            items = n;
//            n = null;
//        }
//        items[0] = x;
//        size += 1;
//    }
//    public void addLast(T x) {
//        if(size >= items.length) {
//            T[] n = (T[]) new Object[size + 1];
//            System.arraycopy(items, 0, n, 0, size);
//            items = n;
//            n = null;
//        }
//        items[size] = x;
//        size += 1;
//    }
//
//    public boolean isEmpty() {
//        if (size == 0) return true;
//        else return false;
//    }
//    public int size() {
//        return size;
//    }
//    public void printDeque() {
//        for(int i = 0; i < size; i++) {
//            System.out.print(items[i]);
//            System.out.println();
//        }
//    }
//    public T removeFirst() {
//        if (size == 0) {
//            System.out.println("Empty state!");
//            return null;
//        } else {
//            T r = (T) new Object[1];
//            T[] n = (T[]) new Object[size - 1];
//            r = items[0];
//            System.arraycopy(items, 1, n, 0, size - 1);
//            items = n;
//            n = null;
//            size -= 1;
//            return r;
//        }
//    }
//    public T removeLast() {
//        if (size == 0) {
//            System.out.println("Empty state!");
//            return null;
//        } else {
//            T r = items[size - 1];
//            items[size - 1] = null;
//            size -= 1;
//            return r;
//        }
//    }
//    public T get(int index) {
//        if(isEmpty()) {
//            System.out.println("Empty state!");
//            return null;
//        }
//        else return items[index];
//    }
//    public Iterator<T> iterator(){
//        ArrayDeque<T> array = this;
//        Iterator<T> it = new Iterator<T>() {
//            private int pointer = -1;
//            public boolean hasNext(){
//                if(array.size() < 0) return false;
//                if( pointer < (array.size() - 1) ) return true;
//                return false;
//                // return pos < size; -> class version
//            }
//            public T next(){
//                pointer++;
//                T element;
//                element = array.get(pointer);
//                return element;
//                // return items[pos++]; -> class version
//            }
//        };
//        return it;
//    }
//    public boolean equals(Object o) {
//        for(int i = 0; i < size; i++) {
//            if(items[i] == o) return true;
//        }
//        return false;
//    }
//}

package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>{
    private static int DEFAULT = 100;
    private T[] items;
    private int front;
    private int rear;
    public int size;
    public ArrayDeque() {
        this.items = (T[]) new Object[DEFAULT];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }
    public ArrayDeque(int cap) {
        this.items = (T[]) new Object[cap];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }
    public void resize(int newCap) {
        int len = items.length;
        T[] n = (T[]) new Object[(int)(newCap*1.01)];
        for(int i = 1, j = front + 1; i <= size; i++, j++) {
            n[i] = items[j % len];
        }
        this.items = null;
        this.items = n;
        front = 0;
        rear = size;
    }
    public void addFirst(T x) {
        if((front - 1 + items.length) % items.length == rear) {
            resize(items.length * 2);
        }
        items[front] = x;
        front = (front - 1 + items.length) % items.length;
        size++;
    }
    public void addLast(T x) {
        if((rear + 1) % items.length == front) {
            resize(items.length * 2);
        }
        rear = (rear + 1) % items.length;
        items[rear] = x;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        int i = front + 1;
        int j = front + 1;
        while(i <= front + size - 1) {
            if(j >= items.length) j = 0;
            System.out.printf(items[j] + " ");
            j += 1;
            i += 1;
        }
        if(j >= items.length) j = 0;
        System.out.print(items[j]);
        System.out.println();
    }
    public T removeFirst() {
        if(size == 0) return null;
        front = (front + 1) % items.length;
        T item = (T) items[front];
        items[front] = null;
        size--;
        if(items.length > DEFAULT && size < (items.length / 4)) {
            resize(Math.max(DEFAULT, items.length / 2));
        }
        return item;
    }
    public T removeLast() {
        if(size == 0) return null;
        T item = (T) items[rear];
        items[rear] = null;
        rear = (rear - 1 + items.length) % items.length;
        size--;
        if(items.length > DEFAULT && size < (items.length / 4)) {
            resize(Math.max(DEFAULT, items.length / 2));
        }
        return item;
    }
    public T get(int index) {
        return items[(front + 1 + index) % items.length];
    }
//    public void initialize(T x) {
//        for(int i = 0; i < items.length; i++) {
//            items[i] = x;
//        }
//        front = rear = size = 0;
//    }
    public Iterator<T> iterator(){
        ArrayDeque<T> array = new ArrayDeque<>();
        Iterator<T> it = new Iterator<T>() {
            private int pointer = -1;
            public boolean hasNext(){
                if(array.size() < 0) return false;
                if( pointer < (array.size() - 1) ) return true;
                return false;
                // return pos < size; -> class version
            }
            public T next(){
                pointer++;
                T element;
                element = array.get(pointer);
                return element;
                // return items[pos++]; -> class version
            }
        };
        return it;
    }
    public boolean equals(Object o) {
        if(o instanceof ArrayDeque) return true;
        return false;
    }
}
