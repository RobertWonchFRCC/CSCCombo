import java.util.Random;
import java.util.Scanner;

/**
 * @author Bob Wonch
 * Tenzi
 * It's a game using dice.
 * 
 * Part 1
 * Due 9/20/2022
 * 
 *Work with input and random
 */
public class Tenzi_1_BW
{
	static Scanner input;
	static Random random;
	
	public static void main( String[ ] args )
	{
		int userGuess; //what the user inputs
		int diceNum; //number the computer generates
		
		
		input = new Scanner(System.in);
		random = new Random();
		
		System.out.println ( "Enter a number between 1 and 6" );
		userGuess = input.nextInt ( );
		
		System.out.println ( "The computer will pick a number" );
		diceNum = random.nextInt(6)+1;
		
		System.out.println ( "Here is the number you entered: " + userGuess );
		System.out.println ( "The computers number was " + diceNum );
		
		
		
		input.close ( );
	}

}
/*
 * Problems:
 * Went too fast and students got lost. SOLVED went slower
*/
