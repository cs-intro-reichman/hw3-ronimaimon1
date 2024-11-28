import java.util.Random;

/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent","listen"));  // true
		System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie","Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true

		// Tests the preProcess function.
		System.out.println(preProcess("What? No way!!!"));
		
		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
		
		// Performs a stress test of randomAnagram 
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}  

	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		   // Preprocess both strings
		   String processedStr1 = preProcess(str1);
		   String processedStr2 = preProcess(str2);
   
		   // If lengths differ after preprocessing, they can't be anagrams
		   if (processedStr1.length() != processedStr2.length()) {
			   return false;
		   }
   
		   // Check if each character in processedStr1 can be found in processedStr2
		   while (processedStr1.length() > 0) {
			   char c = processedStr1.charAt(0);
			   int index = -1;
			   // Find the character in processedStr2 (manually, no substring)
			   for (int i = 0; i < processedStr2.length(); i++) {
				   if (processedStr2.charAt(i) == c) {
					   index = i;
					   break;
				   }
			   }
   
			   if (index == -1) {
				   // If 'c' is not found in processedStr2, they are not anagrams
				   return false;
			   } else {
				   // Manually remove the character from processedStr2
				   String temp = "";
				   for (int i = 0; i < processedStr2.length(); i++) {
					   if (i != index) {
						   temp += processedStr2.charAt(i);
					   }
				   }
				   processedStr2 = temp;
			   }
   
			   // Remove the character from processedStr1
			   processedStr1 = processedStr1.substring(1);
		   }
   
		   // If we've removed all characters and no mismatch occurred, they are anagrams
		   return true;
	}
	   
	// Returns a preprocessed version of the given string: all the letter characters are converted
	// to lower-case, and all the other characters are deleted, except for spaces, which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		String result = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) { // Only consider alphabetic characters
                result += Character.toLowerCase(c); // Convert to lowercase and append
            }
        }
        return result;
	} 
	   
	// Returns a random anagram of the given string. The random anagram consists of the same
	// characters as the given string, re-arranged in a random order. 
	public static String randomAnagram(String str) {
		 // Preprocess the string to remove spaces and punctuation, and convert to lowercase
        String processedStr = preProcess(str);
        String result = "";
        String remainingChars = processedStr;

        Random rand = new Random();

        // Randomly select characters and build the anagram
        while (remainingChars.length() > 0) {
            int randomIndex = rand.nextInt(remainingChars.length());
            char randomChar = remainingChars.charAt(randomIndex);
            result += randomChar; // Append the selected character to the result

            // Manually remove the selected character from remainingChars
            String temp = "";
            for (int i = 0; i < remainingChars.length(); i++) {
                if (i != randomIndex) {
                    temp += remainingChars.charAt(i);
                }
            }
            remainingChars = temp;
        }

        return result;
	}
}
