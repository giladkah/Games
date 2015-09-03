import java.util.Scanner;


public class LifeGame {

	static int size; 
	static Thread[][] lifeThreadMatrix ;

	static LifeMatrix lifeMatrix;
	static LifeThreadController controller; 

	public static void main(String[] args) {
		
		
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter a size for the Matrix  between 2 and 25: ");
	    while (!sc.hasNextInt()) sc.next();
	    int num = sc.nextInt();
	    
	    if (num < 2 || num > 25) {
		    System.out.println("Not a valid number ");
		    System.exit(0);
	  
	    }
	    
	    size = num;
		startGame(); 
		
	}

	

	public static void startGame() {
		
		lifeMatrix = new LifeMatrix(size);

		lifeMatrix.initUI();

		controller = new LifeThreadController(size*size, lifeMatrix);

		lifeThreadMatrix = new Thread[size][size];

		// run threads always 

		int generation=0; 
		while (true) {
			
			System.out.println("Generation::: " + generation);
			
			for (int i=0; i<size; i++) {
				for (int j=0; j<size; j++) {

					lifeThreadMatrix[i][j] = new Thread( new LifeThread(controller, lifeMatrix, i, j));
					lifeThreadMatrix[i][j].start();

				}

			}
			
			controller.waitForThreads();
			generation++;
		}
		
		
	}


}
