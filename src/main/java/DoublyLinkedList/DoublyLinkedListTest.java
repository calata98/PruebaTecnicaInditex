package DoublyLinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DoublyLinkedListTest {
	
	private DoublyLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<Integer>();
    }

    @Test
    public void testAddFirst() {
        list.addFirst(10);
        list.addFirst(20);
        list.addFirst(30);

        assertEquals(3, list.getSize());
    }

    @Test
    public void testAddLast() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals(3, list.getSize());
    }

    @Test
    public void testRemoveFirst() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        int removed = list.removeFirst();

        assertEquals(10, removed);
        assertEquals(2, list.getSize());
    }

    @Test
    public void testRemoveLast() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        int removed = list.removeLast();

        assertEquals(30, removed);
        assertEquals(2, list.getSize());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFirstEmptyList() {
        list.removeFirst();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveLastEmptyList() {
        list.removeLast();
    }

    @Test
    public void testGetSize() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addLast(40);

        assertEquals(4, list.getSize());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.addLast(10);

        assertFalse(list.isEmpty());
    }
    
    @Test
    public void testPrintList() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        String expected = "DoublyLinkedList: [10, 20, 30]";
        String actual = list.printList();

        assertEquals(expected, actual);
    }

    @Test
    public void testPrintListWithEmptyList() {
        String expected = "DoublyLinkedList: []";
        String actual = list.printList();

        assertEquals(expected, actual);
    }

    @Test
    public void testPrintListWithSingleElement() {
        list.addFirst(10);

        String expected = "DoublyLinkedList: [10]";
        String actual = list.printList();

        assertEquals(expected, actual);
    }

    @Test
    public void testPrintListWithManyElements() {
    	
        for (int i = 1; i <= 1000; i++) {
            list.addLast(i);
        }

        StringBuilder expected = new StringBuilder("DoublyLinkedList: [");
        
        for (int i = 1; i <= 1000; i++) {
            expected.append(i);
            if (i != 1000) {
                expected.append(", ");
            }
        }
        expected.append("]");

        String actual = list.printList();

        assertEquals(expected.toString(), actual);
    }

    @Test
    public void testAddAt() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        list.addAt(1, 15);

        assertEquals(4, list.getSize());
        assertEquals(Integer.valueOf(10), list.getNodeAt(0).getData());
        assertEquals(Integer.valueOf(15), list.getNodeAt(1).getData());
        assertEquals(Integer.valueOf(20), list.getNodeAt(2).getData());
        assertEquals(Integer.valueOf(30), list.getNodeAt(3).getData());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtInvalidIndex() {
        list.addAt(1, 10);
    }

    @Test
    public void testRemoveAt() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        int removed = list.removeAt(1);

        assertEquals(20, removed);
        assertEquals(2, list.getSize());
        assertEquals(Integer.valueOf(10), list.getNodeAt(0).getData());
        assertEquals(Integer.valueOf(30), list.getNodeAt(1).getData());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtInvalidIndex() {
        list.removeAt(0);
    }

    @Test
    public void testGetNodeAt() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        DoublyLinkedList.Node<Integer> node  = list.getNodeAt(1);

        assertEquals(Integer.valueOf(20), node.getData());
        assertEquals(Integer.valueOf(10), node.getPrev().getData());
        assertEquals(Integer.valueOf(30), node.getNext().getData());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNodeAtInvalidIndex() {
        list.getNodeAt(0);
    }

}
