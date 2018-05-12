package ch.jmildner.thread.threads;

import ch.jmildner.tools.MyTools;

class Pipe
{
	static int z = 0;
	static int w = 0;
	static int r = 0;


	synchronized public void read()
	{
		if (z != 0)
		{
			System.out
					.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			z = 0;
			r++;
		}
		z++;
		System.out.println("read  " + r + " w:" + w);
		// MyTools.wait(1);
		z--;
	}


	synchronized public void write()
	{
		if (z != 0)
		{
			System.out
					.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
			z = 0;
			w++;
		}
		z++;
		System.out.println("write " + w + " r:" + r);
		// MyTools.wait(1);
		// MyTools.halt("---------------");
		z--;
	}
}



class Reader extends Thread
{
	private Pipe pipe;


	Reader(Pipe p)
	{
		pipe = p;
	}


	@Override
	public void run()
	{
		while (true)
		{
			pipe.read();
		}
	}
}



public class TestRaceCondition1
{
	public static void main(String[] args)
	{
		Pipe pipe = new Pipe();
		Writer writerThread = new Writer(pipe);
		Reader readerThread = new Reader(pipe);
		writerThread.setPriority(5);
		readerThread.setPriority(5);
		writerThread.start();
		readerThread.start();
		MyTools.pause();
		MyTools.pause();
	}
}



class Writer extends Thread
{
	private Pipe pipe;


	Writer(Pipe p)
	{
		pipe = p;
	}


	@Override
	public void run()
	{
		for (;;)
		{
			pipe.write();
		}
	}
}
