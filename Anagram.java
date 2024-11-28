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
	
			// Count the frequency of each character in both strings
			for (char c = 'a'; c <= 'z'; c++) {
				int count1 = 0;
				int count2 = 0;
				
				// Count occurrences of 'c' in processedStr1
				for (int i = 0; i < processedStr1.length(); i++) {
					if (processedStr1.charAt(i) == c) {
						count1++;
					}
				}
				
				// Count occurrences of 'c' in processedStr2
				for (int i = 0; i < processedStr2.length(); i++) {
					if (processedStr2.charAt(i) == c) {
						count2++;
					}
				}
	
				// If the counts don't match, they are not anagrams
				if (count1 != count2) {
					return false;
				}
			}
	
			// If all counts match, they are anagrams
			return true;
	}
	   
	// Returns a preprocessed version of the given string: all the letter characters are converted
	// to lower-case, and all the other characters are deleted, except for spaces, which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		String result = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) || Character.isSpaceChar(c)) { // Allow spaces as well
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
