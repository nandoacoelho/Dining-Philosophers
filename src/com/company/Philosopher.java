package com.company;

/**
 * The Philosopher class implements Runnable to be started as a Thread,
 * has a int for differentiation of the philosophers and a timer.
 *
 * Created by Fernando de Almeida Coelho and Ana Luiza Motta Gomes
 * on 11/04/14.
 */
public class Philosopher implements Runnable
{
	private int philosopherNumber;
	private DiningPhilosophers dp;
	private long timer;

	/**
	 * Constructor Philosopher() receives parameters when is created
	 * and receive the current time in milliseconds to the timer.
	 */
	public Philosopher(int number, DiningPhilosophers diningPhilosophers)
	{
		philosopherNumber = number;
		dp = diningPhilosophers;
		timer = System.currentTimeMillis();
	}

	/**
	 * Method eat() kills some time for the philosopher to eat.
	 */
	private void eat()
	{
		long seconds = 0;
		while (seconds <= 5)
		{
			long ms = System.currentTimeMillis() - timer;
			seconds = ms / 1000;
		}
	}

	/**
	 * Starts the thread.
	 */
	@Override
	public void run()
	{
		while (true)
		{
			dp.getChopsticks(philosopherNumber);
			eat();
			dp.returnChopsticks(philosopherNumber);
		}
	}

}