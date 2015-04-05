/*************************************************************************

 * Compilation:  javac Words.java
 * Execution:    java Words
 * About: The end goal will be a scrabble-esque game where users will be 
 * presented with a number of characters that they will be able to create words
 * from. 
 *************************************************************************/


import java.util.Random;
import java.io.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class Words {
    static final int MAX_LETTERS = 6;
    static final String FILE = "../../public/words";
    static Random randGen = null;

    /**
     *  read words from a file then return the list of words in an ArrayList
     * @param  reader - a bufferReader that reads a file
     * @return  an ArrayList that contains all the words from the file
     */
    public static ArrayList<String> createDictionary(BufferedReader reader)
    throws IOException{
    	//dictionary has 73064 words
    	ArrayList<String> dictionary = new ArrayList<String>();
    	String line = reader.readLine();
    	for(int index = 0; line != null; index++){
    		dictionary.add(line);
			line = reader.readLine();    		
    	}
    	return dictionary;
    }

    /**
     *  get a new list of random letters for option "2"
     * @param  numOfLetters - number of random letters
     * @return an ArrayList of characters that contains random letters
     */
    public static ArrayList<Character> getRandomLetters(int numOfLetters){
    	ArrayList<Character> randomLetters = new ArrayList<Character>();
    	for(int i = 0; i < numOfLetters; i++){
    		randomLetters.add(getLetter());
    	}
    	return randomLetters;
    }

    /**
     *  check if a word is in the dictionary, returns a boolean
     * @param input - a string, a word, 
     * @param dictionary - an ArrayList of words of strings in dictionary
     * @return a Boolean that tells if a word is in the ditionary
     */
    public static Boolean isWordInDictionary(String input, 
    ArrayList<String> dictionary){
    	Boolean isInDictionary = false;
    	if(dictionary.contains(input)){
    		isInDictionary = true;
    	}
    	return isInDictionary;
    }

    /**
     * check if we have the letters for the input word, returns a boolean
     *
     * @param input - an input string 
     * @param letters - an ArrayList of letters
     * @return Boolean - true if we have the letters for the input word
     */
    public static Boolean haveLettersForTheWord(String input, 
    ArrayList<Character> letters){
    	Boolean haveEnoughLetters = false;
    	int matchedLetters = 0;
    	ArrayList<Character> inputLetters = new ArrayList<Character>();
    	//declare a temp arrayList of letters so the original arrayList
    	//can not be changed when changes are made to the temp arrayList
    	ArrayList<Character> lettersTemp = new ArrayList<Character>();
		for(int i = 0; i < letters.size(); i++){
			lettersTemp.add(letters.get(i));
		}    	
    	//split input into chars
    	for(int i = 0; i < input.length(); i++){
    		inputLetters.add(input.charAt(i));
    	}

    	int inputLettersSize = inputLetters.size();
    	int lettersSize = lettersTemp.size();
    	//compare two the input and the letters as arrayLists
    	//set the similar letters to null so the number of matched letters
    	//can be tracked
		for(int i = 0; i < inputLettersSize;i++){
			for(int j = 0; j < lettersSize; j++){
				if(inputLetters.get(i) == lettersTemp.get(j)){
					inputLetters.set(i, null);
					lettersTemp.set(j,null);
					continue;
				}
			}
		}
		//find how many matched letters
		for(int i = 0; i < inputLetters.size(); i++){
			if(inputLetters.get(i) == null){
				matchedLetters++;
			}
		}
		// if all of the letters are matched, then we have enough letters
		if(inputLetters.size() == matchedLetters){
			haveEnoughLetters = true;
			//matchedLetters = 0;
		}
    	return haveEnoughLetters;
    }

    /**
     * create a new list of letters where the letters that are used in the
     * input word is replaced by new random letters
     *
     * @param input - a string of input word
     * @param letters, an ArrayList of letters
     * @return Boolean - returns a new list of letters
     */
    public static ArrayList<Character> NewWithreplacedLetters(String input, 
    ArrayList<Character> letters){
		ArrayList<Character> inputLetters = new ArrayList<Character>();
		//declare a temp arrayList of letters so the original arrayList
    	//can not be changed when changes are made to the temp arrayList
    	ArrayList<Character> lettersTemp = new ArrayList<Character>();
		for(int i = 0; i < letters.size(); i++){
			lettersTemp.add(letters.get(i));
		}    	
    	//split input into an arrayList of characters
    	for(int i = 0; i < input.length(); i++){
    		inputLetters.add(input.charAt(i));
    	}

    	int inputLettersSize = inputLetters.size();
    	int lettersSize = lettersTemp.size();
  		//compare two the input and the letters as arrayLists
    	//set the similar letters to null so the number of matched letters
    	//can be tracked		
		for(int i = 0; i < inputLettersSize;i++){
			for(int j = 0; j < lettersSize; j++){
				if(inputLetters.get(i) == lettersTemp.get(j)){
					inputLetters.set(i, null);
					lettersTemp.set(j,null);
					continue;
				}
			}
		}
		//replace the letters used in input word with new randomLetters
		//then return the new list of letters
		for(int i = 0; i < lettersTemp.size(); i++){
			if(lettersTemp.get(i) == null){
				lettersTemp.set(i, getLetter());
			}
		}
		return lettersTemp;
    }

    /**
     * search through the arrayList of words in dictionary and returns the 
     * longest word that's no longer than length of 6
     *
     * @param letters - an arrayList list of letters
     * @param dictionary - an arrayList of words in dictionary
     * @return String - the longest words that has a length from 1 to 6
     */
    public static String getLongestWord(ArrayList<Character> letters,
    ArrayList<String> dictionary){
    	String longestWord = dictionary.get(1);
    	//create a temp array so the original array will not be referenced
    	ArrayList<Character> lettersTemp = new ArrayList<Character>();
		for(int i = 0; i < letters.size(); i++){
			lettersTemp.add(letters.get(i));
		}
		//go through each words in dictionary and reassign the longestWord
    	for(int i = 2; i < dictionary.size(); i++){
    		String input = dictionary.get(i);
			Boolean haveLetters = haveLettersForTheWord(input, lettersTemp);
			if(haveLetters && input.length() >= longestWord.length() 
						   && input.length() <= 6){
				longestWord = input;
			}
    	}
    	return longestWord;
    }


    /**
     * gets a single random weighted character
     *  provided by instructor
     *
     * @return char - the random weighted char
     * @see <a href=
	 * "http://en.wikipedia.org/wiki/Scrabble_letter_distributions#English">
     * Scrabble letter distribution</a>
     */
    public static char getLetter() {
		int rand = randGen.nextInt(197);

		if(rand >= 0 && rand <= 15) return 'A';
		else if (rand >= 16 && rand <= 19) return 'B';
		else if (rand >= 20 && rand <= 25) return 'C';
		else if (rand >= 26 && rand <= 33) return 'D';
		else if (rand >= 34 && rand <= 57) return 'E';
		else if (rand >= 58 && rand <= 61) return 'F';
		else if (rand >= 62 && rand <= 66) return 'G';
		else if (rand >= 67 && rand <= 71) return 'H';
		else if (rand >= 72 && rand <= 84) return 'I';
		else if (rand >= 85 && rand <= 86) return 'J';
		else if (rand >= 87 && rand <= 88) return 'K';
		else if (rand >= 89 && rand <= 96) return 'L';
		else if (rand >= 97 && rand <= 102) return 'M';
		else if (rand >= 103 && rand <= 115) return 'N';
		else if (rand >= 116 && rand <= 130) return 'O';
		else if (rand >= 131 && rand <= 134) return 'P';
		else if (rand >= 135 && rand <= 136) return 'Q';
		else if (rand >= 137 && rand <= 149) return 'R';
		else if (rand >= 150 && rand <= 159) return 'S';
		else if (rand >= 160 && rand <= 174) return 'T';
		else if (rand >= 175 && rand <= 181) return 'U';
		else if (rand >= 182 && rand <= 184) return 'V';
		else if (rand >= 185 && rand <= 188) return 'W';
		else if (rand >= 189 && rand <= 190) return 'X';
		else if (rand >= 191 && rand <= 194) return 'Y';
		else if (rand >= 195 && rand <= 196) return 'Z';
		else {
		    System.out.println("Something went wrong!");
		    return '?';
		}	 	    
    }

   	/**
     * the main method calls the helper method, reads the dictionary from a
     * file into an arrayList, prints out the player menu, then respond to
     * the player's inputs. Input 1 for computer help, input 2 for all new 
     * letters, input 3 to quit the game. At the same time, we keep a score.
     * @param args - args is an array of console line argument whose data 
     * type is a String
     */
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);

		randGen = new Random();
		randGen.setSeed(0);

		int score = 0;

		try {
			File file = new File(FILE);
			String input = "";
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			ArrayList<String> dictionary = createDictionary(reader);
			ArrayList<Character> letters = getRandomLetters(MAX_LETTERS);
			//System.out.println(dictionary);
			System.out.println(getRandomLetters(MAX_LETTERS));

			//print out the menu
			System.out.println("-------------------------------------");
			System.out.println("--         WORDS WITH CSE11        --");
			System.out.println("-------------------------------------");
			System.out.println("-- Enter \"1\" for computer help     --");
			System.out.println("-- Enter \"2\" for all new letters   --");
			System.out.println("-- Enter \"3\" to quit game          --");
			
			while(!input.equals("3")){
				System.out.println();
				//print out the letters
				for(int i = 0; i < letters.size(); i++){
					System.out.print(letters.get(i) + " ");
				}
				System.out.println();
				System.out.print("Your turn (" + score + ") : ");
				input = scanner.nextLine();
				Boolean wordInDic = isWordInDictionary(input, dictionary);
				Boolean haveLetters = haveLettersForTheWord(input, letters);

				//user options
				if(input.equals("1")){
					//computer help
					System.out.println("Thinking...");
					String answer = getLongestWord(letters, dictionary);
					System.out.println("We suggest using: " + answer);
				}else if(input.equals("2")){
					//all new letters
					letters = getRandomLetters(MAX_LETTERS);
				}else if(input.equals("3")){
					//quit the game
					System.out.println("Goodbye");
					System.exit(0);
				}else if(wordInDic && haveLetters){
					System.out.println("Valid letters(s) and word");
					//if user gussed the right number, renew random letters
					letters = NewWithreplacedLetters(input, letters);
					//add score
					score += input.length();
				}else if(!wordInDic && haveLetters){
					System.out.println("That's not a valid word!");
				}else if(wordInDic && !haveLetters){
					System.out.println("You don't have those letters");
				}else if(!wordInDic && !haveLetters){
					System.out.println("Your don't have letters and"
										+ " word is not in dictionary");
				}

			}

		} catch (IOException e){
			e.printStackTrace();
		}
    }

}
