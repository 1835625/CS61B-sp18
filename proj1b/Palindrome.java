public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> retDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            retDeque.addLast(word.charAt(i));
        }
        return retDeque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Deque<Character> target = wordToDeque(word);
        if (target.removeFirst() == target.removeLast()) {
            int num = target.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num; i++) {
                sb.append(target.removeFirst());
            }
            String shorterWord = sb.toString();
            return isPalindrome(shorterWord);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        Deque<Character> target = wordToDeque(word);
        if (cc.equalChars(target.removeFirst(), target.removeLast())) {
            int num = target.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num; i++) {
                sb.append(target.removeFirst());
            }
            String shorterWord = sb.toString();
            return isPalindrome(shorterWord, cc);
        } else {
            return false;
        }
    }
}
