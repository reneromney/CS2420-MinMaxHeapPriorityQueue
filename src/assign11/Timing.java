package assign11;

import java.util.Random;

/**
 * A class used to test the performance and growth-rate behavior of
 * ChainingHashTable and QuadProbeHashTable
 * 
 * @author Jordan Hensley, Romney Doria, jHensley, doria CS 2420-fall 2015
 *         Assignment 10  Last Updated: 11/25/2015
 *
 */
public class Timing {

	public static void main(String[] args) {
		// Do 10000 lookups and use the average running time.
		int timesToLoop = 200;

		// For each problem size n . . .
		for (int n = 100000; n <= 2000000; n += 100000) {

			// Setup of n size for testing
			long startTime, midpointTime, stopTime;

			// Used to generate random strings
			DoublyEndedPriorityQueue<Integer> testPQ = new DoublyEndedPriorityQueue<Integer>();
			Random rng = new Random();
			for (int i = 0; i < n; i++) {
				testPQ.add(rng.nextInt(n));
			}

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.
			startTime = System.nanoTime();

			// Run the methods for testing
			for (int i = 0; i < timesToLoop; i++) {
				testPQ.add(rng.nextInt(n));
			}

			midpointTime = System.nanoTime();

			// Time it takes to run loop
			for (int i = 0; i < timesToLoop; i++) {
				rng.nextInt(n);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and doing the lookups.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(averageTime);
		}
	}
}
