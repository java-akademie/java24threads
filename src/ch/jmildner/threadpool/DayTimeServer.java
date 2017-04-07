package ch.jmildner.threadpool;

import java.net.ServerSocket;
import java.net.Socket;

public class DayTimeServer
{
	static final int MAX_THREADS = 20;
	static final String SERVICE_NAME = "DayTimeService";


	public static void main(String[] args) throws Exception
	{
		ThreadPool threadPool = new ThreadPool(MAX_THREADS,
				SERVICE_NAME);

		System.out.printf("Pool mit %d %s Threads erzeugt %n", MAX_THREADS,
				SERVICE_NAME);

		ServerSocket serverSocket = new ServerSocket(1300);

		while (true)
		{
			Socket socket = serverSocket.accept();

			if (socket.getPort() == 1301)
			{
				threadPool.dump();
				System.out.println("vor break");
				break;
			}

			DayTimeService dayTimeService = new DayTimeService(socket);
			
			//threadPool.dump();
			
			threadPool.execute(dayTimeService);

			// new Thread(dayTimeService).start();
		}
		
		System.out.println("vor close");
		serverSocket.close();
		System.out.println("nach close");
	}
}
