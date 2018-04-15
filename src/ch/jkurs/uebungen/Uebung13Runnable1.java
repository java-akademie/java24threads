package ch.jkurs.uebungen;

public class Uebung13Runnable1 implements Runnable
{

	@Override
	public void run()
	{
		int a = 1;
		int b = 100;
		int summe = 0;

		for (int i = a; i <= b * 1000; i++)
		{
			summe += i;
		}

		System.out.println("\nr1 summe: " + summe);
	}

}
