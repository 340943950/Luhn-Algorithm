# Methods Assignment 1: Luhn Algorithm Project

## Task

You have been asked to help a rental company in Canada to setup their **customer information** and **sales analysis system**.

Your solution should be as robust (defect free) as possible. 

Your source code should be: modular, readable, and use coding standards for the language.

Internal documentation should be provided for methods/functions, and classes.

An example user interface is below:

```
	Cusotmer and Sales System
	1. Enter Customer Information
	2. Generate Customer data file
	3. Report on total Sales (Not done in this part)
	4. Check for fraud in sales data (Not done in this part)
	5. Quit
	Enter menu option (1-9)
```

In this project, you will be given the main of the program. Read closely into the program and to see which components can be edited. In this project, there will be **minimal** changes done to the main.

This project will be done in **partners**. This is because you will be taking a "Divide and Conquer" strategy where each member will elect some of the prewritten methods in the main.  You can also propose to create additional methods in the program to further break down the problem.

Despite some of these methods having dependencies with each other, it’s important for you to communicate with your partner and determine what the proposed inputs and outputs of each method is so that you can each work concurrently.

**The methods will be marked individually. Projects without a work breakdown submissions will NOT be marked**.

## Customer System

The software system should provide an easy to use interface for employees to enter customer information including the following information for each customer:

- First Name (String)
- Last name (String)
- City (String)
- Postal Code (3 or more characters - validation is required)
- Credit Card number (9 or more characters - validation is required)

Some important requirements of the system include:
1. User should be able to enter customer information
	* Postal Codes must be validated
		* Must be at least 3 characters in length
		* The first 3 characters must match the postal codes loaded from the file "**postal_codes.csv**". A '|' delimiter is used for each field.
	* Credit card numbers must be validated
		* Must be at least 9 digits in length
		* The digits must pass the Luhn algorithm
	* The system should automatically assign a unique customer number to each customer starting with an id value of 1
2. User should be able to generate a (Comma Separated Values) CSV output file for all of the customer information (including their assigned id value). User should be able to provide the output file name and location.

