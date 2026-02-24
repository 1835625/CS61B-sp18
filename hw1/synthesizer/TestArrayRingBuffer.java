package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(5);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
//        arb.enqueue(6);
        for (Integer x : arb) {
            System.out.println(x);
        }
        arb.dequeue();
        System.out.println(arb.peek());
        arb.dequeue();
        System.out.println(arb.peek());
        arb.enqueue(6);
        arb.enqueue(7);
        for (Integer x : arb) {
            System.out.println(x);
        }

//        Iterator<Integer> seer = arb.iterator();
//        while (seer.hasNext()) {
//            System.out.println(seer.next());
//        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
