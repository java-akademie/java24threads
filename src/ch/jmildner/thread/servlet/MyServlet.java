package ch.jmildner.thread.servlet;

import ch.jmb.tools.MyTools;
import ch.jmildner.thread.servlet.TestMyServlet.za;

public class MyServlet implements Servlet
{
	public void init()
	{
		System.out.println("init-gestartet");
		MyTools.sleep(1000);
		System.out.println("init-beendet");
	}


	public void destroy()
	{
		System.out.println("destroy");
	}


	public void service(int a, int z, za b)
	{
		System.out.printf("%4d. anfrage %4d gestartet %n", z, a);
		MyTools.sleep(a);
		System.out.printf("                              %4d. anfrage %4d beendet %n", z, a);
		b.add();
	}
}
