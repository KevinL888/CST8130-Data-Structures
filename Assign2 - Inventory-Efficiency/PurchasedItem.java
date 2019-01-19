import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name2: Chirag Chirag
 * Student2: 040886270
 * Assignment2
 * 03-09-2018
 * Class is used to create PurchasedItems,(subclass) inherits from Item.  
 * Members:
 * supplierName: The suppliers name that is being purchased from.
 * Methods:
 * PurchasedItem(): Default constructor to call super() and initialize variables.
 * addItem(): Used to take input of supplier name.
 * toString(): Return a string representation of the members from super class along with supplierName.
 * toWriteFile() : returns a string representation with a certain format for writing to a file.
 */

public class PurchasedItem extends Item
{
	private String supplierName;

	public PurchasedItem() {
		super();
		supplierName = "";
	}

	public PurchasedItem(int itemCode, String itemName, int quantity, float price, String supplierName) {
		super(itemCode, itemName, quantity, price);
		this.supplierName = supplierName;
	}

	@Override
	public boolean addItem(Scanner input)
	{
		if (super.addItem(input))
		{

			input.nextLine();

			while (supplierName.replaceAll(" ", "").equals(""))
			{
				System.out.println("Enter the name of the supplier: ");
				if (input.hasNextLine())
				{
					supplierName = input.nextLine();
					System.out.println();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		String name = "";
		name = " Supplier: " + supplierName;
		return super.toString() + name;
	}

	@Override
	public String toWriteFile()
	{
		return "p " + super.toWriteFile() + supplierName;
	}
}
