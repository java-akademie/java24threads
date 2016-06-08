package ch.jmildner.thread.aThreadTest;


public class InterruptTest
{
	static Thread starteThread()
	{
		Thread th = new Thread()
		{
			@Override
			public void run()
			{
				int i = 0;

				System.out.println("Thread gestartet!");

				while (true)
				{
					System.out.println("Thread laeuft ... " + ++i);
					System.out.println("Thread laeuft ... ");
					System.out.println("Thread laeuft ... ");
					warte(751);
					System.out.println("Thread laeuft ... ");
					System.out.println("Thread laeuft ... ");
					System.out.println("Thread laeuft ... ");
					System.out.println("Thread laeuft ... ");
					System.out.println("Thread laeuft ... ");


					/*
					 * this.isInterrupted() laesst das interrupt-Flag
					 * wie es ist; Thread.interrupted() setzt das
					 * interruptFlag wieder zurueck!
					 */

					if (this.isInterrupted())
					{
						System.out.println("isInterrupted ... "
								+ this.isInterrupted());

						System.out.println("isInterrupted ... "
								+ this.isInterrupted());

						System.out.println("interrupted ... "
								+ Thread.interrupted());
						System.out.println("interrupted ... "
								+ Thread.interrupted());

						System.out.println("isInterrupted ... "
								+ this.isInterrupted());
						System.out.println("isInterrupted ... "
								+ this.isInterrupted());

						break;
					}
				}

				System.out.println("Thread fertig!");
			}
		};

		th.start();

		return th;
	}


	static void warte(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			System.out.println("sleep interrupted!");

			/*
			 * bei der Abarbeitung der InterruptedException wird das
			 * interrupted-Flag wieder zurueckgesetzt!! soll nicht nur
			 * der Schlafmodus sondern der Thread interrupted werden, so
			 * muss ein erneuter interrupt aufgesetzt werden!!!
			 */

			// Thread.currentThread().interrupt();
		}

	}


	void interruptTest()
	{
		Thread th = starteThread();

		while (true)
		{
			warte(750);

			/*
			 * ein interrupt() bewirkt in einem laufenden Thread das
			 * Setzten des interruptFlags und in einem schlafenden
			 * Thread das Ausloesen einer InterruptedException ......
			 * Das interruptFlag kann mit Thread.interrupted() oder
			 * this.isInterrupted() geprueft werden.
			 */
			th.interrupt();
		}
	}
}
