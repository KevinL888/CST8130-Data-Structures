import java.util.Scanner;

/*
 * Name:Kevin Lai
 * Student Num: 040812704
 * Name: Tanishq Bansal
 * Student Num: 040883753
 * Assignment3
 * 03-19-2018
 * Class is menu used to handle a College's BlockChain
 * Members:
 * Methods:
 * main(): used as a college's block chain menu to prompt user for input.
 */

public class Assign3Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		College college = new College();
		String menuChoice = "a";

		while (menuChoice.charAt(0) != '6') {
			System.out.println("\nEnter 1 to display the college courses: ");
			System.out.println("2 to add a new course: ");
			System.out.println("3 to add a block: ");
			System.out.println("4 to verify chains: ");
			System.out.println("5 to fix chain: ");
			System.out.println("6 to quit: ");
			menuChoice = input.next();

			switch (menuChoice.charAt(0)) {
			case '1':
				college.displayCourses();
				break;
			case '2':
				college.addCourse(input);
				break;
			case '3':
				college.addBlock(input);
				break;
			case '4':
				college.verifyChain();
				break;
			case '5':
				college.removeBadBlocks(input);
				break;
			case '6':
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Invalid choice... select an option 1-6");
			}
		}

	}

}
