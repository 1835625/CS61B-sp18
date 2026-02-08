public class LinkedListDeque<T> {

    private class Node {
        Node prev;
        T item;
        Node next;

        public Node(T thing, Node prevNode, Node nextNode) {
            prev = prevNode;
            item = thing;
            next = nextNode;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    private LinkedListDeque(T t) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel.next = new Node(t, sentinel, sentinel);
        size = 1;
    }

    public void addFirst(T t) {
        size += 1;
        sentinel.next = new Node(t, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(T t) {
        size += 1;
        sentinel.prev = new Node(t, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
//        Node p = sentinel.next;
//        while(p.next != sentinel) {
//            System.out.print(p.item);
//            System.out.print(" ");
//            p = p.next;
//        }
//        System.out.print(p.item);
        for(Node p = sentinel.next; p != sentinel; p = p.next) {
            System.out.print(p.item);
            if (p.next != sentinel) {
                System.out.print(" ");
            }
        }
    }

    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T temp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return temp;
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        }
        T temp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return temp;
    }

    public T get(int index) {
        if(index < 0 || size <= index) {
            return null;
        }
        int currentIndex = 0;
        Node p = sentinel.next;
        while(currentIndex < index) {
            currentIndex += 1;
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if(index < 0 || size <= index) {
            return null;
        }
        Node p = sentinel.next;
        return getRecursive(index, p);
    }

    /* get the index th item of a Deque starting at Node start.*/
    private T getRecursive(int index, Node start) {
        if(index == 0) {
            return start.item;
        }
        return getRecursive(index - 1, start.next);
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>(2);
        L.addFirst(1);
        L.addLast(3);
        L.addFirst(0);
        L.addLast(4);
        L.printDeque();
        System.out.println(L.getRecursive(-1));
        int f = L.removeFirst();
        System.out.println(f);
        L.printDeque();
        int l = L.removeLast();
        System.out.println(l);
        L.printDeque();
//        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>();
//        L.addFirst(1);
//        System.out.println(L.removeFirst());
//        System.out.println(L.isEmpty());
//        L.printDeque();
//        L.addLast(2);
//        System.out.println(L.removeLast());
//        System.out.println(L.isEmpty());
    }

}