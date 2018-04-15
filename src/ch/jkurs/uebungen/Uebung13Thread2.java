package ch.jkurs.uebungen;

public class Uebung13Thread2 extends Thread
{
	@Override
	public void run()
	{
		int a = 1;
		int b = 1000;

		for (int i = a; i <= b; i++)
		{
			if (i % 100 == 0)
				System.out.println();
			System.out.print("t2:" + i + " ");
		}
	}

}
