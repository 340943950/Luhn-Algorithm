/*
* Date: April 2, 2021
* Name: Adarsh Padalia and Vaughn Chan
* Teacher: Mr. Ho
* Description: Creating a customer validation and information storage system
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CustomerSystem {
    public static void main(String[] args) throws IOException {
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below
		String currentCustomerData = "This is a placeholder text";

        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
		        // Any necessary variables may be added to this if section, but nowhere else in the code
                currentCustomerData = enterCustomerInfo(reader);
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                // generateCustomerDataFile();
				generateFile(currentCustomerData, "./", "customer-data.csv");
            }
            else{
                System.out.println("Please type in a valid option (A number from 1-9)");
            }
        } while (!userInput.equals(exitCondition));         // Exits once the user types 
        
        reader.close();
        System.out.println("Program Terminated");
    }
    public static void printMenu() {
        System.out.println("Customer and Sales System\n"
        .concat("1. Enter Customer Information\n")
        .concat("2. Generate Customer data file\n")
        .concat("3. Report on total Sales (Not done in this part)\n")
        .concat("4. Check for fraud in sales data (Not done in this part)\n")
        .concat("9. Quit\n")
        .concat("Enter menu option (1-9)")
        );
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static String enterCustomerInfo(Scanner reader) throws IOException {
        System.out.println("\nWhat is the customer's first name?");
        String firstName = reader.nextLine();
        System.out.println("\nWhat is the customer's last name?");
        String lastName = reader.nextLine();
        System.out.println("\nWhich city does the customer live in?");
        String city = reader.nextLine();
        
        long creditCard = 0;
        boolean validCreditCard;
        do {
            System.out.println("\nWhat is the customer's credit card number (please enter a valid number)?");
            try {
                creditCard = reader.nextLong();
            }
            catch (Exception e) {
                validCreditCard = false;
                reader.next();
                continue;
            }
            validCreditCard = validateCreditCard(creditCard);
        } while (!(validCreditCard));

        String postalCode = "";
        boolean validPostalCode;
        reader.nextLine();
        do {
            System.out.println("\nWhat is the customer's postal code (please enter a valid postal code)?");
            try {
                postalCode = reader.nextLine();
            }
            catch (Exception e) {
                validPostalCode = false;
                reader.next();
                continue;
            }
            validPostalCode = validatePostalCode(postalCode, "./postal_codes.csv");
        } while (!(validPostalCode));

        String output = firstName + "," + lastName + "," + city + "," + creditCard + "," + postalCode;
        return output;
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static boolean validatePostalCode(String postalCode, String fileName) throws IOException {
        File textFile = new File (fileName);
        boolean valid = false;

        // Create the buffered reader to read the file
        BufferedReader fileReader = new BufferedReader(new FileReader(textFile));
        String currLine, currPostalCode;
        int endIndex;

        // Read the line within the file
        while ((currLine = fileReader.readLine()) != null) {
            endIndex = 0;
            for (int i = 0; i < currLine.length(); i++) {
                if ((currLine.substring(i,i+1)).equals("|")) {
                    endIndex = i;
                    break;
                }
            }
            currPostalCode = currLine.substring(0, endIndex);
            if (currPostalCode.equals(postalCode)) {
                valid = true;
                break;
            }
        }

        fileReader.close();
        return valid;
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static boolean validateCreditCard(long creditNum) {
        if (creditNum/Math.pow(10, 9) < 1) {
            return false;
        }
        else {
            long tempCreditNum = creditNum;
            creditNum = 0;
            while(tempCreditNum != 0) {
                long digit = tempCreditNum % 10;
                creditNum = creditNum * 10 + digit;
                tempCreditNum /= 10;
            }

            int counter = 1;
            int sum = 0;
            while(creditNum != 0) {
                long digit = creditNum % 10;

                if (counter % 2 == 1) {
                    sum += digit;
                }
                else {
                    sum += sumDigits(digit*2);
                }

                creditNum /= 10;
                counter += 1;
            }

            boolean valid = sum % 10 == 0;
            return valid;
        }
    }

	/**
	 * Method that writes a string to a file
	 * 
	 * @param String info    	    The string to output to the file
	 * @param String fileLocation   Where the file should be made/edited
	 * @param String fileName 		The name of the file that should be created.
	 */	
	public static void generateFile(String info, String fileLocation, String fileName){
		try {
			// Open the file at the specific location
			File file = new File(fileLocation + fileName);

			// Write to file
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(info);
			printWriter.close();

		// In case anything goes wrong
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/*
	* This method takes in a value and outputs the sum of its digits
	* 
	* @param long num
	* @return int sum
	*/
    public static int sumDigits (long num) {
        int sum = 0;
        while(num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
