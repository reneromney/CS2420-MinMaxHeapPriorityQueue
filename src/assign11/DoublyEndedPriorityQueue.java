package assign11;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a doubly-ended priority queue of generically-typed items. The
 * queue is implemented as a min-max heap.
 * 
 * @author Erin Parker, Jordan Hensley, and Romney Doria; jHensley, doria;
 *         CS_2420-Fall 2015; Assignment 11; 11/25/2015
 */
public class DoublyEndedPriorityQueue<AnyType> {
	
	private final static int DEFAULT_CAPACITY = 100;

	private int currentSize;

	private AnyType[] array;

	private Comparator<? super AnyType> cmp;

	/**
	 * Constructs an empty doubly-ended priority queue. Orders elements
	 * according to their natural ordering (i.e., AnyType is expected to be
	 * Comparable).
	 */
	public DoublyEndedPriorityQueue() {
		currentSize = 0;
		cmp = null;
		array = createArray(DEFAULT_CAPACITY + 1);
	}

	/**
	 * Construct an empty doubly-ended priority queue with a specified
	 * comparator. Orders elements according to the input Comparator (i.e.,
	 * AnyType need not be Comparable).
	 */
	public DoublyEndedPriorityQueue(Comparator<? super AnyType> c) {
		currentSize = 0;
		cmp = c;
		array = createArray(DEFAULT_CAPACITY + 1);
	}

	/**
	 * Helper method for creating a new array of Object and casting to
	 * AnyType[].
	 * 
	 * Warning suppressed because type-casting guaranteed to be safe.
	 */
	@SuppressWarnings("unchecked")
	private AnyType[] createArray(int capacity) {
		return (AnyType[]) new Object[capacity];
	}

	/**
	 * @return the number of items in this doubly-ended priority queue
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Makes this doubly-ended priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
	}

	/**
	 * @return the minimum item in this doubly-ended priority queue
	 * @throws NoSuchElementException
	 *             if this doubly-ended priority queue is empty
	 * 
	 *             (Runs in constant time.)
	 */
	public AnyType findMin() throws NoSuchElementException {
		if (currentSize == 0)
			throw new NoSuchElementException();
		else
			return array[1];
	}

	/**
	 * @return the maximum item in this doubly-ended priority queue.
	 * @throws NoSuchElementException
	 *             if this doubly-ended priority queue is empty
	 * 
	 *             (Runs in constant time.)
	 */
	public AnyType findMax() throws NoSuchElementException {
		if (currentSize == 0)
			throw new NoSuchElementException();
		if (currentSize == 1)
			return array[1];
		if (currentSize == 2)
			return array[2];
		//Find the max between indexes 2 and 3
		else {
			if (compare(array[2], array[3]) < 0)
				return array[3];
			else
				return array[2];
		}
	}

	/**
	 * Removes the minimum item in this doubly-ended priority queue.
	 * 
	 * @return the minimum item in this doubly-ended priority queue
	 * @throws NoSuchElementException
	 *             if this doubly-ended priority queue is empty
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AnyType deleteMin() throws NoSuchElementException {
		if (currentSize == 0)
			throw new NoSuchElementException();

		// hold the minimum item so that it may be returned by the method
		AnyType minimum = array[1];
		
		// replace the item at minIndex with the last item in the tree
		array[1] = array[currentSize];
		array[currentSize] = null;

		//Reestablish order between the new item and its children
		if (currentSize == 3) {
			if (compare(array[1], array[2]) > 0)
				swap(1, 2);
		} else if (currentSize > 3 && (compare(array[2], array[3]) < 0)){
				if (compare(array[1], array[2]) > 0)
					swap(1, 2);
		} else if (currentSize > 3 && compare(array[1], array[3]) > 0)
			swap(1, 3);

		currentSize--;
		
		//percolate down the tree until order is obtained.
		percolateDownMin(1);

		return minimum;
	}

	/**
	 * Removes the maximum item in this doubly-ended priority queue.
	 * 
	 * @return the maximum item in this doubly-ended priority queue
	 * @throws NoSuchElementException
	 *             if this doubly-ended priority queue is empty
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AnyType deleteMax() throws NoSuchElementException {
		if (currentSize == 0)
			throw new NoSuchElementException();

		AnyType maximum;
		// hold the maximum item so that it may be returned by the method
		//Case if the heap is of size 1
		if (currentSize == 1) {
			maximum = array[1];
			array[1] = null;
			currentSize--;
			return maximum;
		}
		//Case if the heap is of size 2
		if (currentSize == 2) {
			maximum = array[2];
			array[2] = null;
			currentSize--;
			return maximum;
		}

		//Find the current max
		int maxIndex;
		if (compare(array[2], array[3]) < 0)
			maxIndex = 3;
		else
			maxIndex = 2;

		maximum = array[maxIndex];

		// replace the item at maxIndex with the last item in the tree
		array[maxIndex] = array[currentSize];
		array[currentSize] = null;

		// determine if the node at maxIndex and its children are violating heap
		// order
		// if so, do the appropriate swap
		if (currentSize == maxIndex*2 + 1){
			if (compare(array[maxIndex], array[maxIndex*2]) < 0)
				swap(maxIndex, maxIndex*2);
		}
		else if (currentSize > maxIndex*2 + 1 && compare(array[maxIndex * 2], array[maxIndex * 2 + 1]) > 0) {
			if (compare(array[maxIndex], array[maxIndex * 2]) < 0)
				swap(maxIndex, 2 * maxIndex);
		} else if (currentSize > maxIndex*2 + 1 && compare(array[maxIndex], array[maxIndex * 2 + 1]) < 0)
			swap(maxIndex, maxIndex * 2 + 1);

		currentSize--;
		// percolate down the max levels starting at maxIndex
		percolateDownMax(maxIndex);
	

		// return the previous minimum item that was held
		return maximum;
	}

	/**
	 * Adds an item to this doubly-ended priority queue.
	 * 
	 * (Runs in logarithmic time.)
	 * 
	 * @param x
	 *            - the item to be inserted
	 */
	public void add(AnyType x) {

		// if the array is full, double its capacity
		if (currentSize == array.length - 1)
			resize();

		// update the current size
		currentSize++;
		
		//Case if it is the first item added
		if (currentSize == 1) {
			array[1] = x;
			return;
		}

		array[currentSize] = x;

		//Determine if the parent node is a min or a max
		int parentLevel = (int) ((Math.log(currentSize / 2) / Math.log(2)) + 1);
		
		//If the parent is on a min level
		if (parentLevel % 2 != 0) {
			//reestablish order and percolate up the tree
			if (compare(array[currentSize], array[currentSize / 2]) < 0) {
				swap(currentSize, currentSize / 2);
				percolateUpMin(currentSize / 2);
			} else
				percolateUpMax(currentSize);
		} else {
			//reestablish order and percolate up the tree
			if (compare(array[currentSize], array[currentSize / 2]) > 0) {
				swap(currentSize, currentSize / 2);
				percolateUpMax(currentSize / 2);
			} else
				percolateUpMin(currentSize);
		}
	}

	/**
	 * Generates a DOT file for visualizing the binary (min-max) heap.
	 * 
	 * @param filename
	 *            - name of file to write
	 */
	public void generateDotFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println("digraph Tree {\n\tnode [shape=record]\n");

			for (int i = 1; i <= currentSize; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + array[i] + "|<f2> \"]");
				if ((2 * i) <= currentSize)
					out.println("\tnode" + i + ":f0 -> node" + (2 * i) + ":f1");
				if ((2 * i + 1) <= currentSize)
					out.println("\tnode" + i + ":f2 -> node" + (2 * i + 1) + ":f1");
			}

			out.println("}");
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by
	 * the user (and stored in cmp), or Comparable.
	 * 
	 * @param lhs
	 *            - left-hand side of comparison
	 * @param rhs
	 *            - right-hand side of comparison
	 * @return <0 if lhs is smaller than rhs, >0 if lhs is is greater than rhs,
	 *         0 otherwise
	 * 
	 *         Warning suppressed because type-casting guaranteed to be safe.
	 */
	@SuppressWarnings("unchecked")
	private int compare(AnyType lhs, AnyType rhs) {
		if (cmp == null)
			return ((Comparable<? super AnyType>) lhs).compareTo(rhs);

		return cmp.compare(lhs, rhs);
	}

	/**
	 * Helper method to percolate up the minimum levels of the min-max heap.
	 * 
	 * @param hole
	 *            - the index at which the percolation begins
	 */
	private void percolateUpMin(int hole) {
		AnyType tmp = array[hole];

		// The nearest ancestor of 'hole' also in a min level is the
		// grandparent.
		// Stop traversing up the min levels of the tree when the order property
		// is reinstated (i.e., grandparent is <= than hole or root is reached).
		for (int grandParent = hole / 4; grandParent > 0 && compare(tmp, array[grandParent]) < 0; grandParent /= 4) {
			array[hole] = array[grandParent];
			hole = grandParent;
		}

		array[hole] = tmp;
	}

	/**
	 * Helper method to percolate up the maximum levels of the min-max heap.
	 * 
	 * @param hole
	 *            - the index at which the percolation begins
	 */
	private void percolateUpMax(int hole) {
		AnyType tmp = array[hole];

		// The nearest ancestor of 'hole' also in a max level is the
		// grandparent.
		// Stop traversing up the max levels of the tree when the order property
		// is reinstated (i.e., grandparent is >= than hole or root is reached).
		for (int grandParent = hole / 4; grandParent > 0 && compare(tmp, array[grandParent]) > 0; grandParent /= 4) {
			array[hole] = array[grandParent];
			hole = grandParent;
		}

		array[hole] = tmp;
	}

	/**
	 * Helper method to percolate down the minimum levels of the min-max heap.
	 * 
	 * @param hole
	 *            - the index at which the percolation begins
	 */
	private void percolateDownMin(int hole) {
		int grandChild;

		// The nearest descendants of 'hole' also in a min level are its
		// grandchildren.
		// First consider levels with a complete set of grandchildren.
		while (hole * 4 + 3 <= currentSize) {
			grandChild = hole * 4;

			// Find the smallest grandchild.
			int min = grandChild;
			for (int i = 1; grandChild + i <= currentSize && i < 4; i++)
				if (compare(array[grandChild + i], array[min]) < 0)
					min = grandChild + i;
			grandChild = min;

			// Stop traversing down the min levels if hole is <= grandchild.
			// Order property reinstated (so terminate).
			if (compare(array[grandChild], array[hole]) > 0)
				return;

			// Else, swap hole and grandchild.
			swap(hole, grandChild);

			// Reinstate order property among new grandChild and its parent, if
			// necessary.
			if (compare(array[grandChild], array[grandChild / 2]) > 0)
				swap(grandChild, grandChild / 2);

			// Continue down the min levels of the tree.
			hole = grandChild;
		}

		// Finally, consider the last min level (with a possibly incomplete set
		// of children and grandchildren).
		if (hole * 2 <= currentSize) {

			// Find min among children and grandchildren.
			int min = hole * 2;
			if (hole * 2 < currentSize && compare(array[hole * 2], array[hole * 2 + 1]) > 0)
				min = hole * 2 + 1;
			grandChild = hole * 4;
			for (int i = 0; i < 4 && grandChild + i <= currentSize; i++)
				if (compare(array[grandChild + i], array[min]) < 0)
					min = grandChild + i;

			// If hole <= than min, order property reinstated (so terminate).
			if (compare(array[min], array[hole]) > 0)
				return;

			// Else, swap hole and min.
			swap(min, hole);

			// If min was a grandchild, need to also check order property with
			// parent.
			if (min >= grandChild && compare(array[min], array[min / 2]) > 0)
				swap(min, min / 2);
		}
	}

	/**
	 * Helper method to percolate down the maximum levels of the min-max heap.
	 * 
	 * @param hole
	 *            - the index at which the percolation begins
	 */
	private void percolateDownMax(int hole) {
		int grandChild;

		// The nearest descendants of 'hole' also in a max level are its
		// grandchildren.
		// First consider levels with a complete set of grandchildren.
		while (hole * 4 + 3 <= currentSize) {
			grandChild = hole * 4;

			// Find the largest grandchild.
			int max = grandChild;
			for (int i = 1; grandChild + i <= currentSize && i < 4; i++)
				if (compare(array[grandChild + i], array[max]) > 0)
					max = grandChild + i;
			grandChild = max;

			// Stop traversing down the max levels if hole is >= grandchild.
			// Order property reinstated (so terminate).
			if (compare(array[grandChild], array[hole]) < 0) {
				return;
			}

			// Else, swap hole and grandchild.
			swap(hole, grandChild);

			// Reinstate order property among new grandChild and its parent, if
			// necessary.
			if (compare(array[grandChild], array[grandChild / 2]) < 0) {
				swap(grandChild, grandChild / 2);
			}

			// Continue down the max levels of the tree.
			hole = grandChild;
		}

		// Finally, consider the last max level (with a possibly incomplete set
		// of children and grandchildren).
		if (hole * 2 <= currentSize) {

			// Find max among children and grandchildren.
			int max = hole * 2;
			if (hole * 2 < currentSize && compare(array[hole * 2], array[hole * 2 + 1]) < 0)
				max = hole * 2 + 1;
			grandChild = hole * 4;
			for (int i = 0; i < 4 && grandChild + i <= currentSize; i++)
				if (compare(array[grandChild + i], array[max]) > 0)
					max = grandChild + i;

			// If hole >= max, order property reinstated (so terminate).
			if (compare(array[max], array[hole]) < 0) {
				return;
			}

			// Else, swap hole and max.
			swap(max, hole);

			// If max was a grandchild, need to also check order property with
			// parent.
			if (max >= grandChild && compare(array[max], array[max / 2]) < 0)
				swap(max, max / 2);
		}
	}

	/**
	 * Helper method to swap the elements in array[i] and array[j].
	 * 
	 * @param i
	 *            - index of first item to swap
	 * @param j
	 *            - index of second item to swap
	 */
	private void swap(int i, int j) {
		AnyType temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	private void resize() {
		AnyType[] temp = createArray(array.length * 2);
		for (int i = 0; i < array.length; i++)
			temp[i] = array[i];
		array = temp;
	}

	/**
	 * DO NOT MODIFY OR REMOVE this method, it is needed for grading.
	 * 
	 * @return copy of the heap array
	 */
	public Object[] toArray() {
		Object[] ret = new Object[currentSize];
		for (int i = 0; i < currentSize; i++)
			ret[i] = array[i + 1];
		return ret;
	}
}