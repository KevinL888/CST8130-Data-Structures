import java.util.Scanner;

/*
 * Name:Kevin Lai
 * Student Num: 040812704
 * Name2: Chirag Chirag
 * Student2: 040886270
 * Assignment2
 * 03-09-2018
 * Class is used to simulate a polymorphic inventory system.
 * Members:
 * Methods:
 * main(): used as a inventory menu to prompt user for input.
 */

public class Assign2Main
{

	public static void main(String[] args)
	{
		int choice = 0;
		Inventory inventory = new Inventory();
		Scanner input = new Scanner(System.in);

		do
		{
			// print the menu
			System.out.println("Enter 1 to add an item to inventory");
			System.out.println("2 to display current inventory");
			System.out.println("3 buy some of an item");
			System.out.println("4 sell some of an item");
			System.out.println("5 to write items in ArrayList to file");
			System.out.println("6 to read from File and add items to inventory");
			System.out.println("7 to quit");

			while (true)
			{

				if (!input.hasNextInt())
				{
					System.out.println("Invalid entry... please enter choice from 1-7");
					input.nextLine();
				} else
				{

					choice = input.nextInt();
					if (choice > 0 && choice < 8)
					{
						break;
					} else
					{
						System.out.println("Invalid Entry... please enter choice from 1-7");
						input.nextLine();
					}
				}
			}
			input.nextLine();

			// switch/case statements for user choices

			switch (choice)
			{
			case 1:
				inventory.addItem(input);

				break;

			case 2:
				System.out.println("Inventory:");
				System.out.println(inventory);
				break;

			case 3:

				if (!inventory.updateQuantity(input, true))
				{
					System.out.println("Error...could not buy item\n");
				}

				break;

			case 4:

				if (!inventory.updateQuantity(input, false))
				{
					System.out.println("Error...could not sell item\n");
				}

				break;

			case 5:
				inventory.openOutputFile(input);
				break;

			case 6:
				inventory.openFile(input);
				break;
			case 7:
				break;
			default:
				break;
			}

		} while (choice != 7);
		input.close();
	}

}
