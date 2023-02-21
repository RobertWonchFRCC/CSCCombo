import java.util.Random;
import java.util.Scanner;

public class RankedVoting
{
	static final int CHOICES = 5;
	static final int VOTERS = 20;
	static Random randGen;
	static Scanner input;
	public static void main( String[ ] args )
	{
		randGen = new Random ( );
		input = new Scanner(System.in);
		int[ ][ ] votesArray = new int[VOTERS][CHOICES]; // all of the voters choices
		int[ ] totalChoices = new int[CHOICES]; // total for each column
		double[ ] percentChoices = new double[CHOICES]; // percentage for each candidate
		int[ ] removedChoices = new int[CHOICES]; // list of each column if it was to be removed
		int winner = -1;
		int lowChoiceLocation; // The lowest column
		fillArray ( votesArray );
		
		calcTotalFirstChoice ( votesArray, totalChoices );
		winner = calcPercentages ( totalChoices, percentChoices, removedChoices );
		printIntArray ( totalChoices, "totals" );
		printPercentageArray ( percentChoices );

		while ( winner == -1)
		{
			printArray(votesArray);
			lowChoiceLocation = findLowestChoice ( votesArray, percentChoices, removedChoices );
			redistibuteVotes ( votesArray, totalChoices, lowChoiceLocation);
			winner = calcPercentages ( totalChoices, percentChoices, removedChoices );
			
			System.out.println ( "\nwinner " + winner );
			printIntArray ( totalChoices, "totals" );
			printPercentageArray ( percentChoices );

			input.nextInt ( );  //pauses the output
		}
		System.out.println ( "The winner is " + winner );
	}

	/*
	 * if a voters choice is no longer valid give them their next lowest choice
	 */
	public static void redistibuteVotes( int[ ][ ] votesArray, int[ ] totalChoices, int lowChoiceLocation)
	{
		int choiceNum = 0;

		for ( int voterNum = 0; voterNum < VOTERS; voterNum++ )
		{
			if ( votesArray[voterNum][lowChoiceLocation] == 1 ) // their choice is no longer valid
			{
				printIntArray(votesArray[voterNum], "voter before " + voterNum);
				
				for ( choiceNum = 0; choiceNum < CHOICES; choiceNum++ )  //all choices are moved down one
				{
					votesArray[voterNum][choiceNum] --;
				}
				votesArray[voterNum][lowChoiceLocation] = CHOICES+1;  //put this candidate out of range
				printIntArray(votesArray[voterNum], "voter after " + voterNum);

				choiceNum = 0;
				
				while (choiceNum < CHOICES && votesArray[voterNum][choiceNum] > 1)  
				{
					choiceNum++; 
				}
				if (choiceNum >= CHOICES)
				{
					System.out.println("Error");
					printIntArray(votesArray[voterNum], "voter" + voterNum);
				}
				else
				{
					System.out.print (" *" + choiceNum);
					totalChoices[choiceNum]++;  //add their vote to their next choice
				}
			}
			else	{	;	}
		}
						// clears out the votes for lowest column
		int smallValue;
		for ( int voterNum = 0; voterNum < VOTERS; voterNum++ )
		{
			System.out.print(lowChoiceLocation);
			smallValue = votesArray[voterNum][lowChoiceLocation];
			votesArray[voterNum][lowChoiceLocation] = CHOICES + 1;
			for (choiceNum = 0; choiceNum < CHOICES; choiceNum++)
			{
				if (votesArray[voterNum][choiceNum] > smallValue)
				{
					votesArray[voterNum][choiceNum]--;
				}
			}
		}
	}

	/*
	 * Finds the total of the votes of 1.
	 */
	public static void calcTotalFirstChoice( int[ ][ ] votesArray, int[ ] totalChoices )
	{
		for ( int voterNum = 0; voterNum < VOTERS; voterNum++ )
		{
			for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
			{
				if ( votesArray[voterNum][choiceNum] == 1 )
				{
					totalChoices[choiceNum]++;
				}
			}
		}
	}

	/*
	 * Finds the lowest column from teh remaining. Sets the lowest column percentage 
	 * to 0.4999 sets the marker for the lowest column to -1
	 */
	public static int findLowestChoice( int[ ][ ] votesArray, double[ ] percentChoices, 
													int[ ] removedChoices )
	{
		int lowChoice = 0;

		for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
		{
			// find the lowest percentage
			if ( percentChoices[lowChoice] > percentChoices[choiceNum] && removedChoices[choiceNum] != -1 )
			{
				lowChoice = choiceNum;
			}
		}
		System.out.println ( "low point" + lowChoice );
		removedChoices[lowChoice] = -1;
		percentChoices[lowChoice] = 0.4999;

		return lowChoice;
	}

	/*
	 * Calculate the percentage of the each columns votes
	 */
	public static int calcPercentages( int[ ] totalChoices, double[ ] percentChoices, 
													int[ ] removedChoices )
	{
		int winner = -1;
		for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
		{
			if ( removedChoices[choiceNum] != -1 )
			{
				percentChoices[choiceNum] = totalChoices[choiceNum] / (double) VOTERS;
				if ( percentChoices[choiceNum] >= 0.50 )
				{
					winner = choiceNum;
				}
			}
		}
		return winner;
	}

	/*
	 * generates random votes
	 */
	public static void fillArray( int[ ][ ] votesArray )
	{
		int randLoc;
		for ( int voterNum = 0; voterNum < VOTERS; voterNum++ )
		{
			for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
			{
				do
				{
					randLoc = randGen.nextInt ( 0, CHOICES );
				} while ( votesArray[voterNum][randLoc] > 0 );
				votesArray[voterNum][randLoc] = choiceNum + 1;
			}
		}
	}

	/**************************************************************
	 * prints all of the arrays in the methods below
	 */
	public static void printArray( int[ ][ ] votesArray )

	{
		for ( int voterNum = 0; voterNum < VOTERS; voterNum++ )
		{
			for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
			{
				System.out.printf ( "%2d", votesArray[voterNum][choiceNum] );
			}
			System.out.println ( );
		}
		System.out.println ( );
	}

	public static void printPercentageArray( double[ ] percentChoices )
	{
		System.out.println ( "Percentage" );
		for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
		{
			System.out.printf ( "%2d.%6.2f  ", choiceNum, percentChoices[choiceNum] );
		}
		System.out.println ( );
	}

	public static void printIntArray( int[ ] totalChoices, String name )
	{
		System.out.println ( name );
		for ( int choiceNum = 0; choiceNum < CHOICES; choiceNum++ )
		{
			System.out.printf ( "%2d.%4d    ", choiceNum, totalChoices[choiceNum] );
		}
		System.out.println ( );
	}
}
