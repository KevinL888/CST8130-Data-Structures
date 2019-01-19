import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Assignment1
 * 02-09-2018
 * Class is used to create PurchasedItems,(subclass) inherits from Item.  
 * Members:
 * supplierName: The suppliers name that is being purchased from.
 * Methods:
 * PurchasedItem(): Default constructor to call super() and initialize variables.
 * addItem(): Used to take input of supplier name.
 * toString(): Return a string representation of the members from super class along with supplierName.
 */

public class PurchasedItem extends Item
{
	private String supplierName;

	public PurchasedItem() {
		super();
		supplierName = "";
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

}
