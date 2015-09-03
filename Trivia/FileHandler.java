package mine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	private Scanner input; 	
	private String question, answer1, answer2, answer3, answer4; 
	private int correctAnswer, rowNum=1; 
	private ArrayList<TriviaQuestion> triviaQuestions = new ArrayList<TriviaQuestion>(); 

	public FileHandler(String fileName) throws FileNotFoundException{
		input = new Scanner(new File(fileName)); 
		readTriviaFile(); 
	}
	// reads the trivia file. first line is question. Next 4 lines are answers, and the last one is the correct answer. 
	// checks if the 6th line is an int. 
	// inserts to an array only if all the fields exist. 	
	private void readTriviaFile() {
		while (input.hasNext()){
			String st = input.nextLine();
			//System.out.print("GILAD:::" + st + " \n") ;

			switch (rowNum%6) {		
			case 1: question = st; break; 
			case 2: answer1 = st; break; 
			case 3: answer2	= st; break; 
			case 4: answer3 = st; break; 
			case 5: answer4 = st; break; 
			case 0:	 {
				try {
					correctAnswer = Integer.parseInt(st);
				}
				catch(NumberFormatException nfe)  {  
					System.out.print("File can't be used. Please insert correct answer NUMBER.");
				}
				// add a new Trivia Question 
				TriviaQuestion tq = new TriviaQuestion(question,answer1,answer2,answer3,answer4,correctAnswer);

				triviaQuestions.add(tq);
			} 
			break; 	
			}
			rowNum++;
		}

		if (rowNum%6!=1) 
			System.out.print("Didn't get the last Questin. Not in the right format." );

		input.close(); 
	}
	
	public ArrayList<TriviaQuestion> getTriviaQuestions() {
		return triviaQuestions;
	}
}
