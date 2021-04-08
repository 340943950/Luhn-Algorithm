/*
 * Date: April 2, 2021
 * Name: Adarsh Padalia and Vaughn Chan
 * Teacher: Mr. Ho
 * Description: Creating a customer validation and information storage system
 */

import java.util.Scanner;
// More packages may be imported in the space below
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class CustomerSystem {
    public static void main(String[] args) {
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
                enterCustomerInfo();
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
						   .concat("Enter menu option (1-9)\n")
						   );
    }
    /*
	 * This method may be edited to achieve the task however you like.
	 * The method may not nesessarily be a void return type
	 * This method may also be broken down further depending on your algorithm
	 */
    public static void enterCustomerInfo() {
    }
    /*
	 * This method may be edited to achieve the task however you like.
	 * The method may not nesessarily be a void return type
	 * This method may also be broken down further depending on your algorithm
	 */
    public static void validatePostalCode() {
    }
    /*
	 * This method may be edited to achieve the task however you like.
	 * The method may not nesessarily be a void return type
	 * This method may also be broken down further depending on your algorithm
	 */
    public static boolean validateCreditCard(long creditNum) {
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

	/**
	 * Assigns a unique number ID per line given a file
	 * 
	 * @param String filename	The file to assign unique IDs on
	 */
	public static void assignUniqueId(String fileName) {
		try {
			// Open the file at the specific location
			File file = new File(fileName);
			Scanner fileScanner = new Scanner(file);

			// Sort through each line in the file and add them to a temporary string
			String fileTempBuffer = "";
			int line = 1;
			while(fileScanner.hasNextLine()) {
				fileTempBuffer += (fileScanner.nextLine() + ",#" + line + "\n");
				line++;
			}
			fileScanner.close();

			// Write the temporary string into the file
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(fileTempBuffer);
			printWriter.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
			System.out.println("\u001b[31m" + "Please enter a file that exists." + "\u001b[0m\n");
		}
	}
	
	/**
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
