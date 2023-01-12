package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> {
    private static int DEFAULT = 100;
    public T[] items;
    public int size;
    private int front;
    private int rear;
    public Comparator<T> com;
    public MaxArrayDeque(Comparator<T> c) {
        this.items = (T[]) new Object[DEFAULT];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
        this.com = c;
    }
    public T max() {
        if(size <= 0) {
            System.out.println("Empty state!");
            return null;
        }
        else {
            T max = items[front + 1];
            int i = front + 1;
            int j = front + 1;
            while(i <= front + size) {
                if(j >= items.length) j = 0;
                if(com.compare(items[j], max) > 0) max = items[j];
                j += 1;
                i += 1;
            }
            return max;
        }
    }
    public T max(Comparator<T> c) {
        if(size <= 0) {
            System.out.println("Empty state!");
            return null;
        }
        else {
            T max = items[front + 1];
            int i = front + 1;
            int j = front + 1;
            while(i <= front + size) {
                if(j >= items.length) j = 0;
                if(c.compare(items[j], max) > 0) max = items[j];
                j += 1;
                i += 1;
            }
            return max;
        }
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
        MaxArrayDeque<T> array = this;
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