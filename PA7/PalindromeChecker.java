/* Name:  Robert Chance
 * PID:   A13088857
 * Login: cs11faay
 * Date:  Nov 7, 2015
 * Sources of help: Java - An Eventful Approach, Oracle String() Javadocs
 *
 * Program Description:  This program takes in a string from the user and
 *   through a series of recursive method calls, the program will analyze and
 *   will determine whether or not the string is a palindrome.  The program
 *   will filter any white space characters or any non digit or non letter
 *   character.
 */


/** Class PalindromeChecker will prompt the user for a string and determine
 *  if it is a palindrome.  The program will then print the determination to
 *  the terminal window.
 */
public class PalindromeChecker {

  public static final int ASCII_ZERO = 48;
  public static final int ASCII_NINE = 57;
  public static final int ASCII_A = 65;
  public static final int ASCII_Z = 90;
  public static final int ASCII_a = 97;
  public static final int ASCII_z = 122;
  public static final int TWO = 2;


  /** Method filter() takes in a string and removes all non-alphanumeric 
   *  characters and standarizes the case.  It will then return the modified
   *  string.
   *
   *  @param phrase This is the initial string that the user inputs.
   *
   *  @return This method returns the modified string
   */ 
  public static String filter( String phrase ) {

    String modifiedPhrase = "";

    // turn the String into an array
    char[] phraseArray = phrase.toCharArray();

    for( int i = 0 ; i < phraseArray.length ; i++ ) {
     
      // This conditional will construct a new string with only alphanumeric
      // characters
      if( (int)phraseArray[i] >= ASCII_ZERO && (int)phraseArray[i] <=
          ASCII_NINE || (int)phraseArray[i] >= ASCII_A && 
          (int)phraseArray[i] <= ASCII_Z || (int)phraseArray[i] >= 
          ASCII_a && (int)phraseArray[i] <= ASCII_z ) {
  
        modifiedPhrase = modifiedPhrase + phraseArray[i];

      }// end of if
    }// end of for loop 
   
    // convert all characters to upper case 
    modifiedPhrase = modifiedPhrase.toUpperCase();     

    return modifiedPhrase;
 
  }// end of method filter()  



  /** Method isPalindrome( String, int, int ) compares the first and last 
   *  characters of the string and returns true if they are the same.  The
   *  method will then update the high and low parameters and pass these new
   *  values along with the original string recursively back into the method.
   *
   *  @param phrase This is the filtered phrase
   *  @param low The array element of the first character that will be compared
   *  @param high The array element of the last character that will be compared 
   *
   *  @return This method returns true if the phrase is a palindrome
   */
  public static boolean isPalindrome( String phrase, int low, int high ) {
 
    boolean allMatched = false;

    // in the case of an empty string, which is a palindrome
    if( high == -1 ) {
    
      return true;

    }// end of if

    // Base case, when there are no more pairs of chars to compares
    if( low + 1 >= high - 1 ) {
    
      allMatched = ( phrase.charAt(low ) == phrase.charAt(high) );    

      return allMatched;   

    }// end of if

    // Recursive case, if there are unchecked pairs of chars to compare
    else{

      // if the low and high elements are matches      
      if( phrase.charAt(low) == phrase.charAt(high) ) {

        // return the value from the recursively called method  
        allMatched = isPalindrome( phrase, low + 1, high - 1 );

      }// end of if   
    }// end of else

    return allMatched;

  }// end of method isPalindrome( String, int, int )



  /** Method isPalindrome( String ) takes in a string, compares the first and 
   *  last characters, and returns true if they are match.  It will then
   *  modify the string to remove the end characters and pass the modified
   *  string recursively back into the method.
   *
   *  @param phrase This filtered string is modified on every recursion
   *
   *  @return This method returns true if the phrase is a palindrome
   */
   public static boolean isPalindrome( String phrase ) {

     boolean allMatched = false;
     String modifiedString = "";

     // Base case, either 0 or 1 string element, either way, it is a palindrome
     if( phrase.length() < TWO ) {
     
       return true;
     }// end of if
  
     // if there are only two elements left to compare, a second base case
     else if( phrase.length() == TWO ) {
     
       return ( phrase.charAt(0) == phrase.charAt(1) );
     }// end of else if

     // Recursive case, checks that ends match, truncates those ends and passes
     // a new modified String recursively into the method again
     else if( phrase.charAt( 0 ) == phrase.charAt( phrase.length() - 1 ) ) {

         modifiedString = phrase.substring( 1, phrase.length() - 1 );
         

         // recursively call the method with a new modified string parameter
         allMatched = isPalindrome( modifiedString );

       }// end of if

       return allMatched;// if first and last aren't a match
     
   }// end of method isPalindrome( String )   

}// end of class PalindromeChecker

