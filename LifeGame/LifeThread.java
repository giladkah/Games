
public class LifeThread implements Runnable {

	LifeMatrix lifeMatrix; 

	LifeThreadController controller; 

	int x,y;

	boolean cellStatus, nextStatus; 

	public LifeThread(LifeThreadController controller, LifeMatrix lifeMatrix, int x, int y) {

		this.x=x;
		this.y=y;
		this.lifeMatrix = lifeMatrix;
		this.controller=controller;



	}

	@Override
	public void run() {	
		// TODO Auto-generated method stub

		//while (true) {
			
			System.out.println("Thread (" + x +  "," +y + ") started" );
			
			getStatusOfNeighbors(); 
			
			System.out.println("Thread (" + x +  "," +y + ") status before: " 
			+ cellStatus +  " next status : "  +nextStatus  );

			while (!lifeMatrix.didSucceedToChangeValue(x,y,nextStatus)) {
				
				System.out.println("Thread (" + x +  "," +y + ") trying to change status: " + nextStatus );

				Thread.yield(); 
			}
			
			System.out.println("Thread (" + x +  "," +y + ") SUCCEEDED to change status to:" + nextStatus );


			try {
				controller.finished();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		//}

	}
	
	
	public void getStatusOfNeighbors() {

		// get status from neighbors 
		LifeCellStatus lifeCellStatus =  lifeMatrix.getLiveNeighbors(x,y);	
		cellStatus = lifeCellStatus.status;
		
		System.out.println("Thread (" + x +  "," +y + ") alive neighbors: " +  lifeCellStatus.numOfNeighborsAlive + " status: " + lifeCellStatus.status);
		// if Alive: 
		if (cellStatus) {

			// if too many or too little - die. Else, stay alive 
			if ((lifeCellStatus.numOfNeighborsAlive>=4) || (lifeCellStatus.numOfNeighborsAlive<2)) 
				nextStatus=false; 
			else 
				nextStatus = true; 

		}
		else {

			// if 3 exactly - live, else stay dead. 
			if (lifeCellStatus.numOfNeighborsAlive==3)
				nextStatus=true; 
			else 
				nextStatus=false; 

		}
		
		System.out.println("Thread (" + x +  "," +y + ") next status is : " +  nextStatus);


	}

}
