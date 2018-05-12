package ch.jkurs.uebungen;

import ch.jmildner.tools.MyTools;

public class Uebung14
{
	public static void main(String[] args)
	{
		Thread t1 = null;
		t1 = methode1();
		t1.start();
		MyTools.sleep(10000);
		t1.interrupt();
	}


	private static Thread methode1()
	{
		return new Thread()
		{
			@Override
			public void run()
			{
				int i = 0;

				while (true)
				{
					System.out.println(++i);
					if (isInterrupted())
					{
						break;
					}
				}

				System.out.println("ende");
			}
		};
	}
}
