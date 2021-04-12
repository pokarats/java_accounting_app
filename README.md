# Simple Account App

This is a simple Java program as part of 2017 CS261.

## Instructions

1. clone the repo
2. To test the program, run `javac TestAccount.java` in the src/ directory

## Design Analysis

The program is as an accounting application where the user can import transactions from a data file, 
add new transactions, search for specific transactions based on different search criteria, and removing 
a specified transaction. 

The program writes back to the data file when it terminates. 
The program consists of the following classes:

### Transaction Class
This is the abstract base class for both the Expense and Income Classes as Expense and Income both have a is-a relationship with Transaction. Each instance of Expense/Income contains a transaction number, a description, which is handled by a separate Descriptor Class, the transaction amount, and a user-defined data type to specify the transaction type (debit or credit). 

### Descriptor Class
This class keeps track of only the category and description of each transaction.

### Expense and Income Classes 
These classes model “credit” and “debit” transactions. For instance, the setAmount methods are overridden such that the field “amount” in the Expense Class is always negative regardless of how the user enters the data. The method is overridden to behave in the opposite way in the Income Class. Since both Expense and Income Classes are derived from the same base class, they can share similar attributes while allowing the behaviors of the same attributes to be customized differently. The Expense Class also has an additional attribute to determine whether or not an Expense is deductible. 

### Account Class
This is the class that manages the transactions and interacts with other classes that perform other core functions in the program, 
namely the ExternalFile Class and Found Class that handle external file read/write and searching methods respectively. 
The main data structures used by the private member fields in this class is List and ArrayList. 

This data structure is chosen allow for efficient item retrieval by indexed location as well as the ability to grow as needed. 
While the Account Class is not abstract, it is also a parent/base class for the InterestAccount Class, which is a more specialized 
version of the Account Class with an additional feature to add interest charges/credit to the total amount.

By setting up the program in a way that each class handles each aspect of the application separately, the program maintains 
flexibility to allow for further customization or addition of new features in the future. 

For instance, more Account Types can be added to the application and the existing methods can be overridden. 
Similarly, more types of Transaction can be derived from the existing abstract class. 
Since a Java program is organized into Classes, the nature of this programming language facilitated OOP design. 
Additional features can be added to the program simply by adding additional classes without affecting the existing 
functions of the current classes. In addition, the “super” keyword in Java makes overriding methods easier to program as one does not have 
to concentrate on the details of the parent/base class as much as the implementation of the features that need to be different in the 
derived classes. It also makes it clear which methods belong to the parental class and which ones are the overridden or the additional implementations.

If I had more time, I could improve this program to make it more modular by putting the TransactionType and AccountType into a Collection instead of the currently hard-coded version as a user-defined Enum Type. Also, instead of relying on the category field in the Descriptor Class to distinguish different types of expenses or income, I could derive further specialized classes from the basic Expense and Income classes to keep track of any special attributes and/or behaviors. Also, in the InterestAccount Class, I had made the interest rate attribute a defined constant for simplicity. A more real-world approach way to this would have been to allow user input and to make this field modifiable via public methods. 
