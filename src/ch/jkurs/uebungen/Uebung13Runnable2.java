package ch.jkurs.uebungen;

public class Uebung13Runnable2 implements Runnable
{

	@Override
	public void run()
	{
		int a = 1;
		int b = 1000;

		for (int i = a; i <= b; i++)
		{
			if (i % 100 == 0)
			{
				System.out.println();
			}
			System.out.print("r2:" + i + " ");
		}
	}

}
