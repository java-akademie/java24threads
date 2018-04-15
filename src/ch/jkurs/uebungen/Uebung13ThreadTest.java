package ch.jkurs.uebungen;

public class Uebung13ThreadTest
{
	public static void main(String[] args) throws InterruptedException
	{
		Uebung13Thread2 t2 = new Uebung13Thread2();

		Uebung13Thread1 t1 = new Uebung13Thread1();

		Uebung13Runnable2 r4 = new Uebung13Runnable2();
		Thread t4 = new Thread(r4);

		Uebung13Runnable1 r3 = new Uebung13Runnable1();
		Thread t3 = new Thread(r3);

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();

		System.out.println("Stoppmeldung");
	}

}
