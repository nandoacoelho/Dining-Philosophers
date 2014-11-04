//Chris Lenk for COS 326
// A solution to the Dining Philosophers problem using monitors.
// Note that the program will run infinitely and must be terminated manually.
package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The  DiningPhilosophers  class  implement  the interface  DiningServer
 * using its methods to get and return the chopsticks as the Philosophers
 * are in the states EATING, HUNGRY or THINKING. This program was created
 * using Java Language.  This application was  made as an Assignment  for
 * CSCI  215 Operating Systems at Clark University.
 *
 * Created by Fernando de Almeida Coelho and Ana Luiza Motta Gomes
 * on 11/04/14.
 */
public class DiningPhilosophers implements DiningServer
{
	private enum State {THINKING, HUNGRY, EATING};
	private State[] state = new State[5];
	private Condition[] self = new Condition[5];
	private Lock lock = new ReentrantLock();

	/**
	 * Constructor DiningPhilosophers() creates new conditions, set every
	 * philosopher to think and start the Philosopher threads.
	 */
	public DiningPhilosophers()
	{
		for (int i = 0; i < 5; i++) { self[i] = lock.newCondition(); }

		for (int i = 0; i < 5; i++)
		{
			state[i] = State.THINKING;
			System.out.println("Philosopher " + i + " is thinking");
		}

		for (int i = 0; i < 5; i++)
		{
			Philosopher phi = new Philosopher(i, this);
			Thread philosopher = new Thread(phi);
			philosopher.start();
		}
	}

	/**
	 * Method  getChopsticks() receives the number of the Philosopher,
	 * gets the lock and set the philosopher to HUNGRY, test if it can
	 * eat, and if yes set to EATING. After everything is done, unlock
	 * the lock.
	 */
	@Override
	public void getChopsticks(int number)
	{
		lock.lock();
		
		state[number] = State.HUNGRY;
		System.out.println("Philosopher " + number + " is hungry. ಠ_ಠ");
		test(number);
		if (state[number] != State.EATING)
		{
			try { self[number].await(); }
			catch (InterruptedException e) {
				System.out.println("Error: Interrupted Exception with philosopher " + number + " is waiting for the chopsticks. =(");
			}
		}
		
		lock.unlock();
	}

	/**
	 * Method returnChopsticks() receives the number of the Philosopher,
	 * gets the lock and set the philosopher to THINKING, test for left
	 * and right to return the chopsticks.
	 */
	@Override
	public void returnChopsticks(int number)
	{
		lock.lock();
		
		state[number] = State.THINKING;
		System.out.println("Philosopher " + number + " is done eating! =D");
		System.out.println("Philosopher " + number + " is thinking. ┐(￣ー￣)┌");
		test((number + 4) % 5);
		test((number + 1) % 5);
		
		lock.unlock();
	}

	/**
	 * Method  test() receives  the number of the Philosopher, and
	 * test left and right to get the chopsticks, if its ok prints
	 * out that is eating and let the other philosophers know that
	 * it's eating.
	 */
	private void test(int number)
	{
		if ( (state[(number + 4) % 5] != State.EATING) &&
				(state[number] == State.HUNGRY) &&
				(state[(number + 1) % 5] != State.EATING) )
		{
			state[number] = State.EATING;
			System.out.println("Philosopher " + number + " is eating. ¯\\_(ツ)_/¯");
			self[number].signal(); // Signal other philosophers that something has changed
		}
	}


	public static void main(String[] args) {
		DiningPhilosophers monitor = new DiningPhilosophers();
	}//Main

}