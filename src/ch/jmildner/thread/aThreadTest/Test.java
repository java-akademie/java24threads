package ch.jmildner.thread.aThreadTest;

public class Test
{
	public static void main(String[] args)
	{
		 SleepTest st = new SleepTest();
		 st.sleepTest();
		InterruptTest it = new InterruptTest();
		it.interruptTest();
	}
}
