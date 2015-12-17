package assign11;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * A class used to test the quality and functionality of the
 * DoublyEndedPriorityQueue class.
 * 
 * @author Jordan Hensley, Romney Doria, jHensley, doria CS 2420-fall 2015
 *         Assignment 10 Last Updated: 11/25/2015
 *
 */
public class DoublyEndedPriorityQueueTester {

	@Test
	public void testConstructor() {

		// Tests instantiation of the priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		assertTrue(test.size() == 0);
	}

	@Test
	public void testAddFirst() {
		// Tests adding the first item to the priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(1);
		assertTrue(test.size() == 1);
		assertTrue(test.findMin() == 1);
		assertTrue(test.findMax() == 1);
	}

	@Test
	public void testAddTwo() {
		// Tests add two elements to the priority queue in reverse sorted order
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(2);
		test.add(1);
		assertTrue(test.size() == 2);
		assertTrue(test.findMax() == 2);
		assertTrue(test.findMin() == 1);
	}

	@Test
	public void testAddTwoReverse() {
		// Tests adding the same two elements from the previous trial in sorted
		// order
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(1);
		test.add(2);
		assertTrue(test.size() == 2);
		assertTrue(test.findMax() == 2);
		assertTrue(test.findMin() == 1);
	}

	@Test
	public void testAddMultiple() {
		// Tests the adding of multiple elements to the priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(5);
		test.add(3);
		test.add(11);
		test.add(15);
		test.add(4);
		assertTrue(test.size() == 5);
		assertTrue(test.findMin() == 3);
		assertTrue(test.findMax() == 15);
	}

	@Test
	public void testAddingDuplicates() {
		// Ensures the priority queue can handle the adding of duplicates
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(5);
		test.add(1);
		test.add(5);
		test.add(5);
		test.add(7);
		test.add(-1);
		assertTrue(test.size() == 6);
		assertTrue(test.findMin() == -1);
		assertTrue(test.findMax() == 7);
	}

	@Test
	public void testDeleteMin() {
		// Tests the delete min method by deleting everything in the priority
		// queue.
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(1);
		test.add(7);
		test.add(11);
		test.add(0);
		test.add(14);
		assertTrue(test.deleteMin() == 0);
		assertTrue(test.size() == 4);
		assertTrue(test.findMin() == 1);
		assertTrue(test.findMax() == 14);

		assertTrue(test.deleteMin() == 1);
		assertTrue(test.size() == 3);

		assertTrue(test.deleteMin() == 7);
		assertTrue(test.size() == 2);

		assertTrue(test.deleteMin() == 11);
		assertTrue(test.size() == 1);

		assertTrue(test.deleteMin() == 14);
		assertTrue(test.size() == 0);
	}

	@Test
	public void testDeleteMax() {
		// Tests the quality of the deleteMax method by calling it for every
		// element in the priority queue.
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(1);
		test.add(7);
		test.add(11);
		test.add(0);
		test.add(14);
		test.add(4);
		test.add(18);

		assertTrue(test.deleteMax() == 18);
		assertTrue(test.size() == 6);

		assertTrue(test.deleteMax() == 14);
		assertTrue(test.size() == 5);

		assertTrue(test.deleteMax() == 11);
		assertTrue(test.size() == 4);

		assertTrue(test.deleteMax() == 7);
		assertTrue(test.size() == 3);

		assertTrue(test.deleteMax() == 4);
		assertTrue(test.findMin() == 0);
		assertTrue(test.size() == 2);

		assertTrue(test.deleteMax() == 1);
		assertTrue(test.size() == 1);

		assertTrue(test.deleteMax() == 0);
		assertTrue(test.size() == 0);
	}

	@Test(expected = NoSuchElementException.class)
	public void testFindMinFail() {
		// Ensures a NoSuchElementException is thrown when calling findMin on an
		// empty priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(10);
		test.deleteMin();
		test.findMin();
	}

	@Test(expected = NoSuchElementException.class)
	public void testDeleteMinFail() {
		// Ensures a NoSuchElementException is thrown when calling deleteMin on
		// an empty priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.add(10);
		test.deleteMax();
		test.deleteMin();
	}

	@Test(expected = NoSuchElementException.class)
	public void testFindMaxFail() {
		// Ensures a NoSuchElementException is thrown when calling findMax on an
		// empty priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.findMax();
	}

	@Test(expected = NoSuchElementException.class)
	public void testDeleteMaxFail() {
		// Ensures a NoSuchElementException is thrown when calling deleteMax on
		// an empty priority queue
		DoublyEndedPriorityQueue<Integer> test = new DoublyEndedPriorityQueue<Integer>();
		test.deleteMax();
	}
}
