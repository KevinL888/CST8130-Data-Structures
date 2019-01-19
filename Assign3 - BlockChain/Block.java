import java.util.Scanner;

/* Reference: Linda Crane - Class was edited from her solution.
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name: Tanishq Bansal
 * Student Num: 040883753
 * Assignment3
 * 03-19-2018
 * Class is used to store data for student (blocks), date, studentNumber, grades.  
 * Members:
 * date: holds the date in a certain format
 * studentNumber: unique for every student( every student at algonquin must have a student number)
 * grade: grade of the student in the course. 
 * Methods:
 * Block(): default constructor to initialize data ( date, studentNumber, grade).
 * calculateHash(): hash is calculated by adding all of the data and dividing by 88.
 * addBadBlock(): add a bad Block(Student) to BlockChain(Course).(previousHash is random)
 * getCurrentHash: getter to fetch the current hash for student block.
 * getPreviousHash: getter to fetch the previous hash for student block.
 * toString(): return a string representation of date, studentNumber and grade in a certain format.
 *updatePreviousHash: takes student block and updates the previous hash with the current hash of the previous block or random hash if bad block.
 * isEqual(): checks to see if the previousHash of the student block is equal to the current hash of it's previous block.
 * addInfoToBlock(): adds the new date, studentNumber and grade for the block. than calls caculateHash and set the previousHash for block.
 * errorHandling(): deals with handling exceptions for invalid user inputs.
 */

public class Block {
	private int date; // part of data - in month day year format (eg) 2152018
	private int studentNumber; // part of data
	private int grade; // part of data
	private float previousHash;
	private float currentHash;

	public Block() {
		// create the Genesis block
		date = 2152018;
		studentNumber = 0;
		grade = 100;
		previousHash = 0f;
		currentHash = calculateHash();
	}

	public float calculateHash() {
		return (date + studentNumber + grade) / 88;
	}

	public String toString() {
		return "" + studentNumber + " " + grade + " " + date + " current: " + currentHash + " previous: "
				+ previousHash;
	}

	public float getCurrentHash() {
		return currentHash;
	}

	public float getPreviousHash() {
		return previousHash;
	}

	public void updatePreviousHash(float previousHash) {
		this.previousHash = previousHash;
	}

	public boolean isEqual(Block temp) {
		return (previousHash == temp.currentHash);
	}

	public boolean addInfoToBlock(Scanner keyboard, float previousHash) {
		System.out.print("Enter date: ");
		date = errorHandling(keyboard, date);

		System.out.print("Enter student number: ");
		studentNumber = errorHandling(keyboard, date);

		System.out.println("Enter grade: ");
		grade = errorHandling(keyboard, grade);

		currentHash = calculateHash();
		this.previousHash = previousHash;
		return true;
	}

	public int errorHandling(Scanner input, int option) {
		input.nextLine();
		while (true) {
			if (!input.hasNextInt()) {
				System.out.println("Invalid entry: please enter a positive integer : ");
				input.nextLine();
			} else {
				option = input.nextInt();

				if (option >= 0) {
					break;
				} else {
					System.out.println("Invalid entry: please enter a positive integer : ");
					input.nextLine();
				}
			}
		}
		return option;
	}

}
