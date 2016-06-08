package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class Pipe2
{
	private String[] array = new String[8];
	private int index = 0;


	synchronized public String read(String tName)
	{
		String value;
		while (index == 0)
		{
			System.out.println("ReaderThread '" + tName
					+ "' muss warten");
			try
			{
				this.wait();
				System.out.println("ReaderThread '" + tName
						+ "' aufgewacht");
			}
			catch (InterruptedException e)
			{
				System.out.println("ReaderThread '" + tName
						+ "' interrupt");
				MyTools.pause();
			}
		}
		value = array[0];
		index--;
		System.out.print(tName + "   empfangen: " + value + ": ");
		show();
		for (int i = 0; i < index; i++)
		{
			array[i] = array[i + 1];
		}
		array[index] = "";
		if (index == array.length - 1)
		{
			System.out.println("ReaderThread '" + tName
					+ "' notify all");
			this.notifyAll();
			// es kann wieder geschrieben werden
		}
		return value;
	}


	void show()
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + "/");
		}
		System.out.println("");
	}


	synchronized public void write(String tName, String i)
	{
		while (index == array.length)
		{
			System.out.println("WriterThread '" + tName
					+ "' muss warten");
			try
			{
				this.wait();
				System.out.println("WriterThread '" + tName
						+ "' aufgewacht");
			}
			catch (InterruptedException e)
			{
				System.out.println("WriterThread '" + tName
						+ "' interrupt");
				MyTools.pause();
			}
		}
		array[index] = i;
		index++;
		System.out.print(tName + " geschrieben: " + i + ": ");
		show();
		if (index == 1)
		{
			System.out.println("WriterThread '" + tName
					+ "' notify all");
			this.notifyAll(); // es kann wieder gelesen werden
		}
	}
}



class Reader2 extends Thread
{
	private String tName;
	private Pipe2 pipe;


	Reader2(Pipe2 p, String n)
	{
		pipe = p;
		tName = n;
	}


	@Override
	public void run()
	{
		String empfang;
		empfang = pipe.read(tName);
		while (empfang != "")
		{
			empfang = pipe.read(tName);
		}
	}
}



public class TestRaceCondition2
{
	public static void main(String[] args)
	{
		Pipe2 pipe = new Pipe2();
		Writer2 writerThread1 = new Writer2(pipe, "a");
		// Writer2 writerThread2 = new Writer2(pipe, "b");
		Reader2 readerThread1 = new Reader2(pipe, "a");
		// Reader2 readerThread2 = new Reader2(pipe, "b");
		// readerThread1.setPriority(10);
		writerThread1.start();
		readerThread1.start();
		try
		{
			writerThread1.join();
			readerThread1.join();
		}
		catch (Exception e)
		{
			System.out.println("join interrupt");
			MyTools.pause();
		}
		MyTools.pause();
		MyTools.pause();
	}
}



class Writer2 extends Thread
{
	private String tName;
	private Pipe2 pipe;


	Writer2(Pipe2 p, String n)
	{
		pipe = p;
		tName = n;
	}


	@Override
	public void run()
	{
		for (int i = 1; i < 1000; i++)
		{
			pipe.write(tName, tName + i);
		}
		pipe.write(tName, "");
	}
}
