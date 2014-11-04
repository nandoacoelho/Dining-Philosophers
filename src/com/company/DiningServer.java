package com.company;

/**
 * The  DiningServer interface has generic method to be implemented
 * by the DiningPhilosophers class.
 *
 * Created by Fernando de Almeida Coelho and Ana Luiza Motta Gomes
 * on 11/04/14.
 */
public interface DiningServer
{

	public void getChopsticks(int philosopherNumber); // Generic  method to the philosopher get the Chopsticks

	public void returnChopsticks(int philosopherNumber); // Generic method to the philosopher return the Chopsticks
}