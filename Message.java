
public class Message {
	
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){
		String validatedMessage = message.toLowerCase().replaceAll("[^a-z]","");
		message = validatedMessage;
		lengthOfMessage = validatedMessage.length();
	}
	
	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		//remove redundency in key
		int betterKey = key%26;
		
		//iterate through the message and shift
		int i;
		String newMessage ="";
		for (i=0 ; i<message.length(); i++)
		{
			char shiftedChar = message.charAt(i);
			shiftedChar += betterKey;
			if (shiftedChar > 'z')
			{
				shiftedChar -=26;
			}
			else if (shiftedChar<'a')
			{
				shiftedChar+=26;
			}
			newMessage += shiftedChar;		
		}
		message = newMessage;
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){

		int charCounter[] = new int[26];
		for (int i = 0; i < message.length(); i++)
		{
			char c = message.charAt(i);
			charCounter[(int)(c-'a')]+=1;
			//have to make it so 'a' = 0 and 'z'=25 
		}
		int currentMax=0;
		int j=0;
		for (j=0;j<charCounter.length;j++)
		{
			if (charCounter[j]>charCounter[currentMax]) //compare each element in array with the "saved" max
			{
				currentMax = j; //if the max at that j is bigger than the current leading one, that position is the new max
			}
		} 
		int reverseKey = currentMax - 4; //the key shift from 'e'
		//'a' = 97 'e' = 101
		int key = -reverseKey; //to undo the change
		caesarCipher(key);
	}
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		
		String vigenereString="";
		
		for (int i=0, j=0; i<message.length(); i++)
		{
			char shiftedChar = message.charAt(i);
			int add = key[j];
			int betterAdd = add%26; //in case number in key is not perfect input
			shiftedChar += betterAdd;
			j = (j+1) % key.length; //creates the loop of the key word and increments through key array
			if (shiftedChar > 'z')
			{
				shiftedChar -=26;
			}
			else if (shiftedChar<'a')
			{
				shiftedChar+=26;

			}vigenereString += shiftedChar;
		}message = vigenereString;
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		
		String vigenereString="";
		
		for (int i=0, j=0; i<message.length(); i++)
		{
			char shiftedChar = message.charAt(i);
			int shift = key[j];
			int betterShift = shift%26; //in case number in key is not perfect input
			shiftedChar -= betterShift;
			j = (j+1) % key.length; //creates the loop of the key word and increments through key array
			if (shiftedChar > 'z')
			{
				shiftedChar -=26;
			}
			else if (shiftedChar<'a')
			{
				shiftedChar+=26;

			}vigenereString += shiftedChar;
		}message = vigenereString;
	}
	
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		//create a 2d array to hold the original message
		//read in the original message to the array, specifying the length of key 
		//replace all non filled values with *
		//read out the message in the different order
		int rowsTest = message.length()%key;
		int rows = message.length()/key;
		if (rowsTest != 0)
		{
			rows++;
		}
		char transpoArray[][] = new char[rows][key];
		
		while (message.length()<(rows*key)) //fill the message with all the * for the empty cells
		{
				String newMessage = message + '*';
				message = newMessage;
		}
		int y=0;
		for (int i=0; i<rows; i++)  //populate our array
		{
			for (int j = 0; j<key; j++)
			{
			char c = message.charAt(y);
			transpoArray[i][j] = c;
			y++;
			}			
		}
		String finalizedMessage="";
		for (int i=0; i<key; i++)  //read out array in transposed order
		{
			for (int j = 0; j<rows; j++)
			{
			char c =transpoArray[j][i]; //iterates down column then changes row index, based on nested for loop
			finalizedMessage += c;
			}			
		}message = finalizedMessage;
		lengthOfMessage = message.length(); //readjusting variables for equals method to work
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		//fill 2d array with ciphered message
		//read out deciphered message
		//remove all *
				
		int rows = message.length()/key; 
		//we can assume perfect input in being a multiple since the array should be filled with * in all extra spaces
		char transpoArray[][] = new char[rows][key];
		
		int y=0;
		for (int i=0; i<key; i++)  //populate our array, filling in by the clumn not row
		{
			for (int j = 0; j<rows; j++)
			{
			char c = message.charAt(y);
			transpoArray[j][i] = c;
			y++;
			}		
		}
		String finalizedDecipher = "";
		for (int i=0; i<rows; i++)  //read out array in transposed order
		{
			for (int j = 0; j<key; j++)
			{
			char c =transpoArray[i][j]; //iterates down column then changes row index, based on nested for loop
			finalizedDecipher+= c;
			}			
		}message = finalizedDecipher.replaceAll("\\*", "");
		lengthOfMessage = message.length();
	}
	
}
