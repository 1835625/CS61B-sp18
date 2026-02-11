public class ArrayDeque<T> {

    int size;
    int nextFirst;
    int nextLast;
    T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private boolean isFull() {
        return size == items.length;
    }

    private int getStartPos() {
        int startP;
        if(nextFirst == items.length - 1) {
            startP = 0;
        } else startP = nextFirst + 1;
        return startP;
    }

    private int getEndPos() {
        int endP;
        if(nextLast == 0) {
            endP = items.length - 1;
        } else endP = nextLast - 1;
        return endP;
    }

    private void resize(int newSize) {
//        int startP = getStartPos();
//        int endP = getEndPos();
        T[] a = (T[]) new Object[newSize];
//        if(startP > endP) {
//            for (int i = 0; i < size; i++) {
//                if ((i + startP) < items.length) {
//                    a[i] = items[i + startP];
//                } else {
//                    a[i] = items[i + startP - items.length];
//                }
//            }
//        } else {
//            System.arraycopy(items, startP, a, 0, endP - startP + 1);
//        }
        int start = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            a[i] = items[(start + i) % items.length];
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        if(isFull()) {
            resize(size * 2);
            items[nextFirst] = item;
            nextFirst -= 1;
        } else {
            items[nextFirst] = item;
            if(nextFirst == 0) {
                nextFirst = items.length - 1;
            } else {
                nextFirst -= 1;
            }
        }
        size += 1;
    }

    public void addLast(T item) {
        if(isFull()) {
            resize(size * 2);
            items[nextLast] = item;
            nextLast += 1;
        } else {
            items[nextLast] = item;
            if(nextLast == items.length - 1) {
                nextLast = 0;
            } else {
                nextLast += 1;
            }
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) return;
//        int startP = getStartPos();
//        int endP = getEndPos();
//        if(startP > endP) {
//            for (int i = 0; i < size; i++) {
//                if((i + startP) < items.length) {
//                    System.out.print(items[i + startP]);
//                } else {
//                    System.out.print(items[i + startP - items.length]);
//                }
//                System.out.print(" ");
//            }
//        } else {
//            for (int i = startP; i <= endP; i++) {
//                System.out.print(items[i]);
//                System.out.print(" ");
//            }
//        }
        int start = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            System.out.print(items[(i + start) % items.length] + " ");
        }
    }

    private boolean isTooLarge() {
        return (double) size / items.length <= 0.25;
    }

    public T removeFirst() {
        if(size == 0) {
            return null;
        } else {
            T firstItem = items[getStartPos()];
            /** Whether to resize the array down or not.*/
//            if(isTooLarge()) {
//                resize(items.length / 2);
//                items[getStartPos()] = null;
//                if(nextFirst == items.length - 1) {
//                    nextFirst = 0;
//                } else {
//                    nextFirst += 1;
//                }
//            } else {
//                items[getStartPos()] = null;
//                if(nextFirst == items.length - 1) {
//                    nextFirst = 0;
//                } else {
//                    nextFirst += 1;
//                }
//            }
//            if(isTooLarge()) {
//                resize(items.length / 2);
//            }
            items[getStartPos()] = null;
            if(nextFirst == items.length - 1) {
                nextFirst = 0;
            } else {
                nextFirst += 1;
            }
            size -= 1;
            if(items.length >= 16 && size < items.length / 4) {
                resize(items.length / 2);
            }
            return firstItem;
        }
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        } else {
            T lastItem = items[getEndPos()];
            items[getEndPos()] = null;
            if(nextLast == 0) {
                nextLast = items.length - 1;
            } else {
                nextLast -= 1;
            }
            size -= 1;
            if(items.length >= 16 && size < items.length / 4) {
                resize(items.length / 2);
            }
            return lastItem;
        }
    }

    public T get(int index) {
        if(index < 0 || index >= size) return null;
//        int startP = getStartPos();
//        int endP = getEndPos();
//        if(startP > endP) {
//            if((index + startP) > items.length - 1) {
//                return items[index + startP - items.length];
//            } else {
//                return items[index + startP];
//            }
//        } else {
//            return items[index + startP];
//        }
        int start = (nextFirst + 1) % items.length;
        return items[(start + index) % items.length];
    }

//    public static void main(String[] args) {
//        ArrayDeque<Integer> A = new ArrayDeque<>();
//        System.out.println(A.size);
//        A.addFirst(1);
//        A.addLast(2);
//        A.addFirst(3);
//        A.addFirst(4);
//        A.addLast(5);
//        A.addFirst(6);
//        A.addFirst(7);
//        A.addLast(8);
//        A.addFirst(9);
//        A.addLast(10);
//        A.addLast(11);
//        A.addLast(12);
//        A.addLast(13);
//        A.addFirst(14);
//        A.addFirst(15);
//        A.addLast(16);
//        A.addLast(17);
//        A.printDeque();
//        System.out.println(A.get(1));
//        for (int i = 0; i < 10; i++) {
//            System.out.print(A.get(i));
//        }
//        for (int i = 0; i < 15; i++) {
//            A.removeLast();
//        }
//        System.out.println(A.size);
//        A.printDeque();
//        A.addLast(1);
//        A.addLast(2);
//        A.addFirst(3);
//        System.out.println(A.size);
//        A.printDeque();
//    }

}