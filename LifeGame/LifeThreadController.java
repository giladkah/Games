import java.util.concurrent.TimeUnit;


public class LifeThreadController {


	private int num; // number of threads to wait for

	private boolean resetGame = false; 


	private LifeMatrix lifeMatrix; 
	private int count = 0; // number of threads finished

	private boolean startCountAgain = false; 

	public LifeThreadController(int num, LifeMatrix lifeMatrix){
		this.lifeMatrix = lifeMatrix;

		this.num = num;

	}

	public synchronized void finished () throws InterruptedException{
		count++;
		System.out.println("Count is : " +  count);
		if (count >= num) {

			lifeMatrix.updateUI();
			
			TimeUnit.SECONDS.sleep(1);

			notify();
		}
	}

	public synchronized void waitForThreads (){

		System.out.println("waitForThreads : Count is : " +  count + " " + startCountAgain);
		while (count < num)
			try{	
				wait();
				System.out.println("Wake Up and start again.");

			}catch( InterruptedException e){
				System.out.println("interrupted while waiting" );
			}

		
		System.out.println("Initializting count");
		
		if (resetGame) 
			lifeMatrix.resetGame();
			
		count=0; 
		System.out.println("count : " + count);


	}

}

