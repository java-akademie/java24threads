package ch.jmildner.thread.servlet;

import ch.jmildner.tools.MyTools;

public class TestMyServlet
{
	static int anfrage = 0;
	static Servlet servlet = null;
	static boolean load_on_startup = false;
	static int zaehler = 0;
	static String servletName = "";


	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		try
		{
			if (load_on_startup)
			{
				initServlet();
			}

			while (true)
			{
				getAnfrage();

				if (anfrage == 0)
				{
					break;
				}

				service();
			}

			MyTools.pause();

			servlet.destroy();
		}
		finally
		{
			System.out.printf("zaehler %d, beende %d %n ", zaehler,beendet.getZ());
		}
	}

	static class za{
		int z=0;
		void add(){
			z++;
		}
		int getZ(){return z;}
		
	};
	static za beendet=new za();
	
	private static void service() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException
	{

		if (servlet == null)
		{
			initServlet();
		}

		MyTools.sleep(1);
		
		Thread th = new Thread()
		{
			public void run()
			{
				servlet.service(anfrage, ++zaehler,beendet);
			}
		};

		th.setDaemon(true);

		th.start();
	}


	private static void getAnfrage()
	{
		servletName = "ch.jmildner.thread.servlet.MyServlet";
		anfrage = (int) (Math.random() * 5500);
	}


	private static void initServlet() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException
	{
		servlet = (Servlet) Class.forName(servletName).newInstance();
		servlet.init();
	}
}
