package ch.jmildner.threadpool;

import java.net.ServerSocket;
import java.net.Socket;

public class DayTimeServer
{
	static final int MAX_THREADS = 20;


	public static void main(String[] args) throws Exception
	{
		ThreadPool threadPool = new ThreadPool(MAX_THREADS);

		System.out.printf("Pool mit %d Threads erzeugt %n",
				MAX_THREADS);

		ServerSocket serverSocket = new ServerSocket(1300);

		while (true)
		{
			Socket socket = serverSocket.accept();

			if (socket.getPort() == 1301)
			{
				break;
			}

			DayTimeService dayTimeService = new DayTimeService(socket);

			threadPool.execute(dayTimeService);

			// new Thread(dayTimeService).start();
		}

		serverSocket.close();
	}
}
