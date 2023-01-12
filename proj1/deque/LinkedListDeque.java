package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>{
    public IntNode head;
    public IntNode tail;
    public IntNode backup;
    public int size;
    //public int len;
    public LinkedListDeque() {
        head = null;
        tail = null;
        backup = null;
        size = 0;
    }
    public class IntNode {
        public T data;
        public IntNode prev;
        public IntNode next;
        public IntNode() {
            this.data = null;
            this.prev = null;
            this.next = null;
        }
    }
    public void addFirst(T x) {
        IntNode n = new IntNode();
        n.data = x;
        n.next = head;
        if(head != null) {
            head.prev = n;
        }
        head = n;
        if((head.next == null) || (size == 0)) {
            tail = head;
        }
        size += 1;
    }
    public void addLast(T x) {
        if(size == 0) {
            addFirst(x);
            return;
        }
        IntNode n = new IntNode();
        n.data = x;
        tail.next = n;
        n.prev = tail;
        tail = n;
        size += 1;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        IntNode h = head;
        for(int i = 0; i < size - 1; i++) {
            System.out.printf(h.data + " ");
            if(h.next != null) h = h.next;
        }
        System.out.print(h.data);
        System.out.println();
    }
    public T removeFirst() {
        if(isEmpty()) {
            System.out.println("Empty state!");
            return null;
        }
        else {
            T item = head.data;
            IntNode n = head.next;
            if(n != null) {
                n.prev = null;
            }
            head = null;
            head = n;
            size -= 1;
            if(size == 0) tail = null;
            return item;
        }
    }
    public T removeLast() {
        if(isEmpty()) {
            System.out.println("Empty state!");
            return null;
        }
        else {
            T item = tail.data;
            IntNode n = tail.prev;
            if(n != null) {
                n.next = null;
            }
            tail = null;
            tail = n;
            size -= 1;
            if(size == 0) head = null;
            return item;
        }
    }
    public T get(int index){
        int num = 0;
        for(IntNode n = head; n != null; n = n.next) {
            if(num == index) return n.data;
            else num += 1;
        }
        return null;
//        if(isEmpty()) {
//            //System.out.println("Empty state!");
//            return null;
//        }
//        for(int i = 0; i < index; i++) {
//            n = n.next;
//        }
//        return n.data;
    }
    public T getRecursive(int index) {
        T data = null;
        if(index == 0) {
            data = head.data;
            return data;
        }
        else {
            index--;
            data = getRecursive(index);
        }
        head = backup;
        return data;
    }
    public Iterator<T> iterator(){
        LinkedListDeque<T> link = this;
        Iterator<T> it = new Iterator<T>() {
            private int pointer = -1;
            public boolean hasNext(){
                if( pointer < (link.size() - 1) ) return true;
                return false;
            }
            public T next(){
                pointer++;
                T element;
                element = link.get(pointer);
                return element;
            }
        };
        return it;
    }
    public boolean equals(Object o) {
        if(o instanceof LinkedListDeque) return true;
        return false;
    }
}