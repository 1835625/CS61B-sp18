public class OffByN implements CharacterComparator {

    private int offset;

    public OffByN(int N) {
        offset = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (x - y) == offset || (y - x) == offset;
    }

//    public static void main(String[] args) {
//        OffByN offBy5 = new OffByN(5);
//        System.out.println(offBy5.equalChars('a', 'f'));  // true
//        System.out.println(offBy5.equalChars('f', 'a'));;  // true
//        System.out.println(offBy5.equalChars('f', 'h'));;  // false
//    }
}
