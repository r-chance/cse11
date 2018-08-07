import java.util.*;

public class Tester 
{
  public static void main( String[] args )
  {
    Scanner input = new Scanner (System.in);
    String phrase;

    do{
      System.out.print(PA7Strings.PROMPT);

      if(input.hasNextLine()){
        phrase = input.nextLine();

        //only alphanumeric characters alllowed
        //Enter key is an empty string is a palindrome
        //Ctrl D quits the program
        String filteredPhrase = PalindromeChecker.filter(phrase);

        System.out.println();


        System.out.print("Testing 3-Arg isPalindrome: ");

        printResult(PalindromeChecker.isPalindrome
                               (filteredPhrase, 0, filteredPhrase.length() - 1), 
                                phrase);

        System.out.print("Testing 1-Arg isPalindrome: ");


        printResult(PalindromeChecker.isPalindrome(filteredPhrase), 
                               phrase);

        System.out.println();

      }
      else{
        phrase = null;
      }


    } while(phrase != null);

  } 


  private static void printResult(boolean bool, String phrase){
    if(bool){ 
      System.out.printf(PA7Strings.PALINDROME, phrase);
    }

    else{
      System.out.printf(PA7Strings.NOT_PALINDROME, phrase);
    }
  }

}
