/*
 * Date: April 2, 2021
 * Name: Adarsh Padalia and Vaughn Chan
 * Teacher: Mr. Ho
 * Description: Creating a customer validation and information storage system
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
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
		String currentCustomerData = "";

        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
		        // Any necessary variables may be added to this if section, but nowhere else in the code
                currentCustomerData += (enterCustomerInfo(reader) + "\n");
                System.out.println("\n");
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                // generateCustomerDataFile();
                generateFile(currentCustomerData, "./", "customer-data.csv");
				assignUniqueId("./customer-data.csv");
                System.out.println("\n");
                currentCustomerData = "";
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
    /**
     * This method takes in a Scanner and reads in user input to generate a csv string
     * 
     * @param Scanner reader            The reader that reads in user input
     * @return String output            The csv string to be outputted
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
            System.out.println("\nWhat is the customer's postal code (please enter a valid postal code - all caps)?");
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
    /**
     * Takes in a fileName with multiple postal codes and a postal code and then checks
     * whether that postal code is in the file
     * 
     * @param String postalCode         The postalCode to be validated
     * @param String fileName           The name of the file with all the valid postal codes
     * @return boolean valid            Whether or not the inputted postal code is valid
     */
    public static boolean validatePostalCode(String postalCode, String fileName) throws IOException {
        try {
            postalCode = postalCode.substring(0,3);
        }
        catch (Exception e) {
            return false;
        }

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
    /**
     * This method takes in a credit card number and checks if it is valid
     * using the Luhn algorithm
     * 
     * @param long creditNum            The credit card number to be validated
     * @return boolean valid            Whether or not the credit card number inputted is valid
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
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true));

			// Add a column header if doesn't exist or is empty
			if (!file.exists() || file.length() == 0) {
				printWriter.println("First Name,Last Name,City,Credit Card,Postal,Id,");
			}

			// Write to file
			printWriter.print(info);
			printWriter.close();
		}

		// In case anything goes wrong
		 catch (FileNotFoundException e) {
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
			int line = 0;
			while(fileScanner.hasNextLine()) {
				String id = ",#" + line + ",";
				String currentLine = fileScanner.nextLine();
				
				// Make sure that the id was not already assigned (or if it's a header row)
				if (!currentLine.contains(id) && line != 0) {
					fileTempBuffer += (currentLine + id + "\n");
				} else {
					fileTempBuffer += (currentLine + "\n");
				}
				
				line++;
			}
			fileScanner.close();

			// Remove the new line at the end of the buffer (Should not make a new line there)
			fileTempBuffer = fileTempBuffer.substring(0, fileTempBuffer.length() - 1);

			// Write the temporary string into the file
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, false));
			printWriter.println(fileTempBuffer);
			printWriter.close();

			// An error caused by file not being available
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
			System.out.println("\u001b[31m" + "That file name does not exist." + "\u001b[0m\n");
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
