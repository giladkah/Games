package mine;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Trivia {	
	
	private JFrame frame ;
    private JLabel timerLabel, questionLabel, answerLabel1, answerLabel2, answerLabel3, answerLabel4, scoreLabel;
	private int delay = 1000, score = 0; 

	ArrayList<TriviaQuestion> triviaQuestions = new ArrayList<TriviaQuestion>(); 
	ArrayList<TriviaQuestion> randomTriviaQuestions;
    private TriviaQuestion userQuestion;
	FileHandler fileHandler =  new FileHandler("trivia.txt"); 
	
	private static Timer timer;
    static int timeRemaining = 10;
    
	public Trivia() throws FileNotFoundException {	
		triviaQuestions = fileHandler.getTriviaQuestions();
		initUI();
		initTimer(); 
		displayNextQuestion(); 
	}

	
	public static void main(String[] args) throws FileNotFoundException{
		new Trivia();	
	}
	
	private void initTimer() { 
		
	    timer = new Timer(delay, new ActionListener() {					
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	 		
	            if (--timeRemaining > 0) {
	            	timerLabel.setText(String.valueOf(timeRemaining));
	              } else {
	            	  timerLabel.setText("Time's up! Next Question");
	            	  displayNextQuestion();
	            	  //timer.stop();
	              }
	        }
	      });
	    
	    timer.start();		
		
	}
	
	private void displayNextQuestion() {
		// so timer will start from 10 
		timeRemaining = 11; 
		
		userQuestion = getRandomQuestion(); 
		scoreLabel.setText(Integer.toString(score));
		questionLabel.setText(userQuestion.getQuestion());
		answerLabel1.setText(userQuestion.getAnswer1());
		answerLabel2.setText(userQuestion.getAnswer2());
		answerLabel3.setText(userQuestion.getAnswer3());
		answerLabel4.setText(userQuestion.getAnswer4());
		
	}
	
	
	private TriviaQuestion getRandomQuestion() {

		if ((randomTriviaQuestions == null ) || (randomTriviaQuestions.isEmpty())) 
			randomTriviaQuestions = new ArrayList<TriviaQuestion>(triviaQuestions);

		if (randomTriviaQuestions.size()==1)
			return randomTriviaQuestions.remove(0);
		
		Random random = new Random();
		int randomNumber = random.nextInt(randomTriviaQuestions.size()-1);
		
		return randomTriviaQuestions.remove(randomNumber);


	}
	
	
    private void initUI() {
        frame = new JFrame();
        frame.setTitle("Trivia");
        frame.setSize(700, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout()); 
        
        JPanel panelContainer = new JPanel(	);
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));  
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel timewAndScorePanel  =new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel quitPanel = 	new JPanel(new BorderLayout());
        
        JPanel panel0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        
        JLabel titleLabel  = new JLabel("Trivia");

        timerLabel  = new JLabel("Timer");
        JLabel timerLabelTitle  = new JLabel("Time: ");
        scoreLabel  = new JLabel("Score");
        JLabel scoreLabelTitle  = new JLabel("Score: ");

        questionLabel  = new JLabel("Question");
        JButton button1 = new JButton("a");
        answerLabel1 = new JLabel("Answer1");
        JButton button2 = new JButton("b");
        answerLabel2 = new JLabel("Answer2");
        JButton button3 = new JButton("c");
        answerLabel3 = new JLabel("Answer3");
        JButton button4 = new JButton("d");
        answerLabel4 = new JLabel("Answer4");
        JButton quitButton = new JButton("quit");	

        titlePanel.add(titleLabel);
        
        timewAndScorePanel.add(timerLabelTitle);
        timewAndScorePanel.add(timerLabel);
        timewAndScorePanel.add(scoreLabelTitle);
        timewAndScorePanel.add(scoreLabel);

        panel0.add(questionLabel);
        panel1.add(button1);
        panel1.add(answerLabel1);
        panel2.add(button2);
        panel2.add(answerLabel2);
        panel3.add(button3);
        panel3.add(answerLabel3);
        panel4.add(button4);
        panel4.add(answerLabel4);
        
        
        panelContainer.add(panel0);
        panelContainer.add(panel1);
        panelContainer.add(panel2);
        panelContainer.add(panel3);
        panelContainer.add(panel4);
        
        quitPanel.add(quitButton, BorderLayout.EAST);
        
        //		panelContainer.add(panel1);
        	
        mainPanel.add(timewAndScorePanel, BorderLayout.EAST);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(panelContainer, BorderLayout.WEST); 
        mainPanel.add(quitPanel, BorderLayout.SOUTH	); 

        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 		
            	JOptionPane.showMessageDialog(frame, "Your Score is: " + score);
          	frame.setVisible(false);
          	frame.dispose();
            }
          });
        
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkAnswer(1);
            }
          });
        
        	
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkAnswer(2);
            }
          });
        
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkAnswer(3);
            }
          });
        
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkAnswer(4);
            }
          });
        
    }
    
    private void checkAnswer(int userAnswer) {
    	//System.out.println("Gilad: " + userAnswer + " " + correctAnswer);

    	if (userAnswer==userQuestion.getCorrectAnswer())
    		score+=10; 
    	else
    		score-=5; 
    	
    	displayNextQuestion();
    	
    }

}	

