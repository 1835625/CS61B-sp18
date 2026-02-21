import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        // Test words of length 0 and 1.
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("z", cc));
        // Test words that are palindromes.
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a b b a")); // works for Spaces too.
        assertTrue(palindrome.isPalindrome("ab!d@f@d!ba")); // And punctuations.
        assertTrue(palindrome.isPalindrome("abcdefedcba"));
        assertTrue(palindrome.isPalindrome("abcdefghgfedcba"));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("ach%c&idb", cc));
        // Test words that are not palindromes.
        assertFalse(palindrome.isPalindrome("house"));
        assertFalse(palindrome.isPalindrome("abcdefedcbz"));
        // Test words that have capital letters.
        assertFalse(palindrome.isPalindrome("atvvTa"));
    }
}
