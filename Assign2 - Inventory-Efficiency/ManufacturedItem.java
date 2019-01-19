import java.util.ArrayList;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name2: Chirag Chirag
 * Student2: 040886270
 * Assignment2
 * 03-09-2018
 * Class Is used to create ManufacturedItems,(subclass) inherits from Item.  
 * Members:
 * itemsUsed:Item codes that are used in the creation of an Manufactured item are stored in this Array.
 * numItemsUsed: Used to keep track of amount of used item codes(up to a limit of 10 per manufactured item)
 * Methods:
 * ManufacturedItem(): Default constructor to call super() and initialize variables.
 * addItem(): Take input of used items codes in creation of Manufactured items .
 * toString(): Return a string representation of the members from super class along with the used item codes.
 * checkCode(): Invokes super.checkCode(); and passes array of used item codes along with array size and item object.
 * toWriteFile() : returns a string representation with a certain format for writing to a file. 
 */

public class ManufacturedItem extends Item
{
	private int[] itemsUsed = new int[10];
	private int numItemsUsed;

	public ManufacturedItem() {
		super();
	}

	public ManufacturedItem(int itemCode, String itemName, int quantity, float price, int[] itemscodeUsed) {
		super(itemCode, itemName, quantity, price);
		for (int i : itemscodeUsed)
		{
			if (i == -1)
				break;
			else
			{
				this.itemsUsed[numItemsUsed] = i;
				numItemsUsed++;
			}
		}
	}

	@Override
	public boolean addItem(Scanner input)
	{
		int codeNum = 0;

		if (super.addItem(input))
		{
			System.out.println("Enter up to 10 codes used in this item (-1 to quit):");

			while (codeNum != -1 && numItemsUsed < 10)
			{
				input.nextLine();
				while (true)
				{
					if (!input.hasNextInt())
					{
						System.out.println("Input code must be a integer value");
						input.nextLine();
					} else
					{
						codeNum = input.nextInt();
						if (codeNum > 0 || codeNum == -1)
						{
							if (codeNum != -1)
							{
								itemsUsed[numItemsUsed] = codeNum;
							}
							break;
						} else
						{
							System.out.println("Please enter a positive integer");
							input.nextLine();
						}
					}
				}

				numItemsUsed++;
			}
			System.out.println();
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		String code = "";
		String name = "";

		for (int i = 0; i < itemsUsed.length; i++)
		{
			if (itemsUsed[i] != 0)
				code += Integer.toString(itemsUsed[i]) + ", ";
		}

		name = String.format(" Codes used: %s", code);

		return super.toString() + name;
	}

	@Override
	public boolean checkCode(int[] array, int lengthArray, int itemsLength, ArrayList<Item> item)
	{
		if (numItemsUsed > 0)
			if (super.checkCode(itemsUsed, numItemsUsed - 1, itemsLength, item))
				return true;
		return false;
	}

	@Override
	public String toWriteFile()
	{
		String code = "";

		for (int i = 0; i < itemsUsed.length; i++)
		{
			if (itemsUsed[i] != 0)
				code += Integer.toString(itemsUsed[i]) + " ";
		}
		return "m " + super.toWriteFile() + code + "-1";
	}

}
