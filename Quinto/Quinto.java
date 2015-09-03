import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Quinto extends JFrame implements ActionListener {

	public static Color COLOR1, COLOR2;
	public static int size; 
	private static QuintoButton b[][] ;

	JButton resetButton;
	Color color1, color2 ; 
	int sizeFromUser = 0 ;
	JFrame frame; 
	JTextField intFiled;
	JComboBox<String> comboColors1;
	JComboBox<String> comboColors2;
	JLabel mesageLabel; 
	
	public static void main(String[] args) {
		// initialization 
		size = 5; 
		COLOR1 = Color.BLUE;
		COLOR2 = Color.GREEN;
		new Quinto();
		
	}

	public Quinto() {

		// frame 
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("QUINTO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel 
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
		
		intFiled = new JTextField(2);
		intFiled.setText(Integer.toString(size));
		contentPane.add(intFiled);

		// Colors 
		String colors[]={"RED","ORANGE","YELLOW","GREEN", "BLUE", "PINK", "BLACK"};

		comboColors1=new JComboBox<String>(colors);
		comboColors2=new JComboBox<String>(colors);

		comboColors1.setMaximumRowCount(4);
		comboColors2.setMaximumRowCount(4);
		
		comboColors1.setSelectedItem("GREEN");
		comboColors2.setSelectedItem("BLUE");

		contentPane.add(comboColors1);
		contentPane.add(comboColors2);

		// Start Button 
		JButton start =new JButton("Start Game");

		start.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						startGame();
					}
				});

		contentPane.add(start);

		// reset game 
		contentPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));    
		JPanel labelPanel = new JPanel();
		JLabel positionLabel = new JLabel("Reset Game", JLabel.CENTER);
		JPanel buttonLeftPanel = new JPanel();
		resetButton = new JButton("Clear");
		resetButton.addActionListener(this);
		labelPanel.add(positionLabel);
		buttonLeftPanel.add(resetButton);
		leftPanel.add(labelPanel);
		leftPanel.add(buttonLeftPanel);
		contentPane.add(leftPanel);

		// Message 
		mesageLabel = new JLabel("Play Game", JLabel.CENTER);
		contentPane.add(mesageLabel);

		
		
		// Grid View 
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(size, size));
		b = new QuintoButton[size][size];

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				b[row][col] = new QuintoButton(row, col);
				b[row][col].addActionListener(this);

				buttonPanel.add(b[row][col]);

			}
		}
		
		contentPane.add(buttonPanel);

		// add all 
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();

		if (source == resetButton) 
			resetGame();

		if (source instanceof QuintoButton) {

			QuintoButton qButton = (QuintoButton) source;
			int row = qButton.getRow();
			int col = qButton.getCol();

			pressOnButton(row,  col); 
			pressOnButton(row,col-1);
			pressOnButton(row,col+1);
			pressOnButton(row-1,col);
			pressOnButton(row+1,col);

			if (isWinner()) {
				mesageLabel.setText("WINNER!!!");
				//frame.setEnabled(false);
			}

		}
	}

	private void pressOnButton(int row, int col) {
		if ((row<0) || (row>=size) || (col<0) || (col>=size))
			return;
		else 
			b[row][col].pressQuintoButton();
	}

	private boolean isWinner() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				if (!b[row][col].getColor().equals(COLOR2)) 
					return false; 
			}
		}
		return true;
	}


	private void resetGame() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				b[row][col].setToResetColor();
			}
		}

	}


	private Color getColor(String str) {
		if (str.equals("RED")) 
			return Color.RED;
		else if (str.equals("ORANGE"))
			return Color.ORANGE;
		else if (str.equals("YELLOW"))
			return Color.YELLOW;
		else if (str.equals("GREEN"))
			return Color.GREEN;
		else if (str.equals("BLUE"))
			return Color.BLUE;
		else if (str.equals("PINK"))
			return Color.PINK;
		else 
			return Color.BLACK;

	}

	private void startGame() { 
		try  
		{ 
			sizeFromUser = Integer.parseInt(intFiled.getText()); 

		} catch(NumberFormatException nfe)  
		{  
			JOptionPane.showMessageDialog(null,"Please Insert A Number");
			return; 
			//throw new IllegalArgumentException("Null number");		
		}  

		
		if (sizeFromUser>20 || sizeFromUser<2) {
			JOptionPane.showMessageDialog(null,"Please Insert A Number Between 2 and 50");
			return; 
		}


		if (comboColors1.getSelectedItem().toString().equals(comboColors2.getSelectedItem().toString())){
			JOptionPane.showMessageDialog(null,"Please Choose 2 different Colors");
			return; 
		}
		
		
		frame.setVisible(false);
		frame.dispose();
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		
		size = sizeFromUser;
		COLOR1 = getColor(comboColors1.getSelectedItem().toString());
		COLOR2 = getColor(comboColors2.getSelectedItem().toString());
		new Quinto(); 

	}

}


class QuintoButton extends JButton {

	private int row, col; 
	private Color color; 
	private int value; 

	public QuintoButton(int row, int col) { 
		super("(" + row + "," + col + ")"); 
		this.row = row;
		this.col = col;
		this.color = Quinto.COLOR1;
		this.value=0;
		this.setBackground(color);
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public Color getColor() {
		return this.color;
	}

	public void pressQuintoButton() {
		value+=1;
		value%=2;

		if (value==0)
			this.color=Quinto.COLOR1; 
		else 
			this.color=Quinto.COLOR2; 

		this.setBackground(color);
	}

	public void setToResetColor() {
		this.value=0;
		this.setBackground(Quinto.COLOR1);

	}

}

