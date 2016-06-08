package ch.jmildner.thread.threads;

import java.util.ArrayList;
import java.util.Iterator;

import ch.java_akademie.tools.MyTools;

public class TabelleSynchr
{
	public static void main(String[] args)
	{
		ArrayList<Integer> tab1 = new ArrayList<Integer>();
		TabelleThread t1 = new TabelleThread(tab1, 1);
		Thread th1 = new Thread(t1);
		th1.start();
		TabelleThread t2 = new TabelleThread(tab1, 2);
		Thread th2 = new Thread(t2);
		th2.start();
		TabelleThread t3 = new TabelleThread(tab1, 3);
		Thread th3 = new Thread(t3);
		th3.start();
		MyTools.pause();
		Iterator<Integer> it = tab1.iterator();
		int summe = 0;
		while (it.hasNext())
		{
			Integer i = it.next();
			int x = i.intValue();
			summe = summe + x;
		}
		if (summe != 0)
		{
			System.out.println("summe nicht null");
		}
		else
		{
			System.out.println("summe ist null");
		}
	}
}



class TabelleThread implements Runnable
{
	int tNr = 0;
	ArrayList<Integer> t;


	TabelleThread(ArrayList<Integer> t, int n)
	{
		this.t = t;
		this.tNr = n;
	}


	@Override
	public void run()
	{
		for (int i = 0; i < 3000; i++)
		{
			t.add(new Integer(i));
			t.add(new Integer(-i));
			MyTools.sleep(0);
			t.add(new Integer(5));
			t.add(new Integer(-5));
		}
		System.out.println("Thread beendet: " + tNr);
	}
}
