import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name: Tanishq Bansal
 * Student Num: 040883753
 * Assignment3
 * 03-19-2018
 * Class is used to store BlockChains(Courses) in an ArrayList.  
 * Members:
 * college: dynamic ArrayList used to hold BlockChains(Courses)
 * collegeName: name of the college, in our case (Algonquin College)
 * it: Iterator used to traverse through ArrayList
 * Methods:
 * College(): default constructor to initialize collegeName and college ArrayList.
 * addCourse(): add (BlockChain)Courses to the ArrayList.
 * addBlock(): add a Block(Student) to BlockChain(Courses).
 * verifyChain(): go through every BlockChain(Course) in the ArrayList and invoke verifyChain in BlockChain class. 
 * removeBadBlocks(): ask the user which Course he wants to remove bad blocks than calls removeBadBlocks in BlockChain class.
 * displayCourses() : displays all BlockChain(courses) along with the date, student and grades(Block) in a proper format
 * errorHandling() : deals with handling exceptions for invalid user inputs.
 */

public class College {
	private ArrayList<BlockChain> college;
	private String collegeName;
	private Iterator<BlockChain> it;

	public College() {
		collegeName = "Algonquin";
		college = new ArrayList<BlockChain>();
	}

	public void addCourse(Scanner input) {
		String course = null;

		System.out.println("Enter the name of course to add: ");
		course = input.next();
		if (college.size() > 0) {
			for (BlockChain i : college) {
				if (i.toString().equals(course)) {
					while (i.toString().equals(course)) {
						System.out.println("\nCannot add course with same name");
						System.out.println("Enter the name of course to add: ");
						course = input.next();
					}
				}
			}
			college.add(new BlockChain(course));
			System.out.println("Successfully create course " + course);
		} else {
			college.add(new BlockChain(course));
			System.out.println("Successfully create course " + course);
		}
	}

	public void addBlock(Scanner input) {
		int option = 0;
		it = college.iterator();
		if (college.size() > 0) {
			System.out.println("Enter which course to add : ");

			for (int i = 0; it.hasNext(); i++) {
				System.out.println(i + " " + it.next() + ":");
			}

			option = errorHandling(input, option);
			college.get(option).addBlock(input);
		} else {
			System.out.println("There are no courses to add a new block");
		}
	}

	public void verifyChain() {
		if (college.size() > 0) {
			for (BlockChain i : college) {
				if (!i.verifyChain()) {
					System.out.println("Chain for " + i + " is not verified");
				} else {
					System.out.println("Chain for " + i + " is verified");
				}
			}
		} else {
			System.out.println("There are no chains to verify!");
		}
	}

	public void removeBadBlocks(Scanner input) {
		int option = 0;
		it = college.iterator();

		if (college.size() > 0) {
			System.out.println("Enter which course to fix : ");
			for (int i = 0; it.hasNext(); i++) {
				System.out.println(i + " " + it.next() + ":");
			}

			option = errorHandling(input, option);
			college.get(option).removeBadBlocks();
			System.out.println("Chain is fixed");
		} else {
			System.out.println("There are no chains to fix");
		}
	}

	public void displayCourses() {
		System.out.println("For college: " + collegeName + "\n");
		for (BlockChain i : college) {
			i.printBlockChain();
		}
	}

	public int errorHandling(Scanner input, int option) {
		input.nextLine();
		while (true) {
			if (!input.hasNextInt()) {
				System.out.println("Invalid entry: please reenter:");
				input.nextLine();
			} else {
				option = input.nextInt();

				if (option > -1 && option < college.size()) {
					break;
				} else {
					System.out.println("Invalid entry: please reenter:");
					input.nextLine();
				}
			}
		}
		return option;
	}

}
