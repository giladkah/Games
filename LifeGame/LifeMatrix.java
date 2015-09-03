
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LifeMatrix {
	public static final int LIFE_PARAMTER = 50; 

	private int size; 
	private Lock lock = new ReentrantLock(); 

	private static LifeCell[][] lifeMatrixOfCells; 

	public LifeMatrix(int size) {
		this.size=size; 

		lifeMatrixOfCells = new LifeCell[size][size];

		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {

				boolean randomStatus = setRandomStatus(); 

				lifeMatrixOfCells[i][j] = new LifeCell(i,j,randomStatus);
				//lifeMatrix[i][j] = new LifeCell(i,j,true);

			}
		}

	}


	public LifeCell getLifeCell(int i, int j) {

		return lifeMatrixOfCells[i][j];

	}


	// get neighbors 
	public LifeCellStatus getLiveNeighbors(int x, int y) {

		// lock the object
		lock.lock(); 

		try {

			LifeCell lifeCell = lifeMatrixOfCells[x][y];

			int numberOfLifes=0;

			if (getUp(lifeCell))
				numberOfLifes++;
			if (getDown(lifeCell))
				numberOfLifes++;
			if (getLeft(lifeCell))
				numberOfLifes++;
			if (getRight(lifeCell))
				numberOfLifes++;
			if (getUpRight(lifeCell))
				numberOfLifes++;
			if (getUpLeft(lifeCell))
				numberOfLifes++;
			if (getDownRight(lifeCell))
				numberOfLifes++;
			if (getDownLeft(lifeCell))
				numberOfLifes++;


			LifeCellStatus lifeCellStatus = new LifeCellStatus(lifeCell.getStatus(), numberOfLifes);
			return lifeCellStatus ;


		} finally {	
			lock.unlock();
		} 


	}

	public boolean didSucceedToChangeValue(int x, int y, boolean nextValue) { 

		// lock the object
		lock.lock(); 


		try{
			LifeCell lifeCell = lifeMatrixOfCells[x][y];

			lifeCell.setNextValueReady();
			
			if (
					getIsNextValueReadyUp(lifeCell) && 
					getIsNextValueReadyDown(lifeCell) &&
					getIsNextValueReadyLeft(lifeCell) &&
					getIsNextValueReadyRight(lifeCell) &&
					getIsNextValueReadyUpLeft(lifeCell) &&
					getIsNextValueReadyUpRight(lifeCell) &&
					getIsNextValueReadyDownLeft(lifeCell) &&
					getIsNextValueReadyDownRight(lifeCell) 

					) {

				lifeCell.setStatus(nextValue);
				return true; 

			}
			
			return false; 

		}
		finally {

			lock.unlock();
		}


	}

	///////////////////////////
	private boolean getUp(LifeCell lifeCell) {	

		if (lifeCell.getY()==0) 
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()][lifeCell.getY()-1].getStatus();

	}

	private boolean getDown(LifeCell lifeCell) {

		if (lifeCell.getY()==size-1) 
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()][lifeCell.getY()+1].getStatus();

	}

	private boolean getLeft(LifeCell lifeCell) {

		if (lifeCell.getX()==0) 
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()].getStatus();

	}

	private boolean getRight(LifeCell lifeCell) {

		if (lifeCell.getX()==size-1) 
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()].getStatus();

	}

	private boolean getUpLeft(LifeCell lifeCell) {	

		if ((lifeCell.getY()==0) ||  (lifeCell.getX()==0))
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()-1].getStatus();

	}

	private boolean getUpRight(LifeCell lifeCell) {	

		if ((lifeCell.getY()==0) ||  (lifeCell.getX()== size-1))
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()-1].getStatus();

	}

	private boolean getDownLeft(LifeCell lifeCell) {	

		if ((lifeCell.getY()==size-1) ||  (lifeCell.getX()== 0))
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()+1].getStatus();

	}

	private boolean getDownRight(LifeCell lifeCell) {

		if ((lifeCell.getY()==size-1) ||  (lifeCell.getX()== size-1))
			return false; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()+1].getStatus();

	}

	////////////////////////

	private boolean getIsNextValueReadyUp(LifeCell lifeCell) {	

		if (lifeCell.getY()==0) 
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()][lifeCell.getY()-1].isNextValueReady();

	}

	private boolean getIsNextValueReadyDown(LifeCell lifeCell) {

		if (lifeCell.getY()==size-1) 
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()][lifeCell.getY()+1].isNextValueReady();

	}


	private boolean getIsNextValueReadyLeft(LifeCell lifeCell) {

		if (lifeCell.getX()==0) 
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()].isNextValueReady();

	}

	private boolean getIsNextValueReadyRight(LifeCell lifeCell) {

		if (lifeCell.getX()==size-1) 
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()].isNextValueReady();

	}

	private boolean getIsNextValueReadyUpLeft(LifeCell lifeCell) {	

		if ((lifeCell.getY()==0) ||  (lifeCell.getX()==0))
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()-1].isNextValueReady();

	}

	private boolean getIsNextValueReadyUpRight(LifeCell lifeCell) {	

		if ((lifeCell.getY()==0) ||  (lifeCell.getX()== size-1))
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()-1].isNextValueReady();

	}


	private boolean getIsNextValueReadyDownLeft(LifeCell lifeCell) {	

		if ((lifeCell.getY()==size-1) ||  (lifeCell.getX()== 0))
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()-1][lifeCell.getY()+1].isNextValueReady();

	}

	private boolean getIsNextValueReadyDownRight(LifeCell lifeCell) {

		if ((lifeCell.getY()==size-1) ||  (lifeCell.getX()== size-1))
			return true; 

		return lifeMatrixOfCells[lifeCell.getX()+1][lifeCell.getY()+1].isNextValueReady();

	}


	///////////////////////////

	private boolean setRandomStatus() {

		int rand = randInt(0,100); 

		if (rand < LIFE_PARAMTER) 
			return false; 

		return true; 
	}


	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}














	JFrame frame; 
	JButton[][] cellLabel;

	public void initUI() {

		frame = new JFrame();
		frame.setTitle("Life Game");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

		// reset Button 
		JButton reset =new JButton("Reset");

		reset.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//reset();
						resetGame();
					}
				});

		contentPane.add(reset);


		// Grid View 
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(new GridLayout(size, size));
		cellLabel = new JButton[size][size];

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				cellLabel[row][col] = new JButton();

				cellLabel[row][col].setText("(" + row + "," + col + ")");

				cellPanel.add(cellLabel[row][col]);
			}
		}

		contentPane.add(cellPanel);

		updateUI(); 
		
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

	}

	public void updateUI() {

		lock.lock(); 

		System.out.println("UPDATE!");
		try {
			for (int row = 0; row  < size; row++) {
				for (int col = 0; col < size; col++) {

					lifeMatrixOfCells[row][col].update();
					
					if (lifeMatrixOfCells[row][col].getStatus())
						cellLabel[row][col].setBackground(Color.GREEN);
					else
						cellLabel[row][col].setBackground(Color.BLUE);

				}
			}

		}
		finally {

			lock.unlock();
		}

	}

	public void resetGame() {
		
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {

				boolean randomStatus = setRandomStatus(); 

				lifeMatrixOfCells[i][j] = new LifeCell(i,j,randomStatus);
				//lifeMatrix[i][j] = new LifeCell(i,j,true);

			}
		}		
	}

}
