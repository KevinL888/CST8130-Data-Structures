
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name2: Chirag Chirag
 * Student2: 040886270
 * Assignment2
 * 03-09-2018
 * Class is used for inheritance, super class of ManufacturedItem and PurchasedItem.  
 * All members are private so to manipulate a members you must do so in the following class.
 * Members:
 * itemCode: ItemCode for product being created.
 * itemName: Name of the product being created.
 * itemQuantityInStock: Current stock available.
 * itemPrice: Price of product being created.
 * Methods:
 * Item(): Default constructor to initialize variables.
 * addItem(): Used to take input of itemCode, itemName, itemQuantityInStock, itemPrice corresponding to sub class that invokes this method.
 * toString(): Return a string representation to display the data members corresponding to sub class.
 * updateItem(): Updates quantity in stock depending on buying or selling and returns a boolean if successful.
 * isEquals(): Compares an Item objects input code with another and returns boolean depending on if they match.
 * inputCode(): Takes in a input code and returns boolean if successfully read into scanner.
 * checkCode(): Compares Manufactured item codes with existing item codes in Inventory Array and returns boolean.
 *  compareInputCode(): Comparator for itemCodes, used to aid our binary search when we are inserting a item to check if it is greater or less
 *  toWriteFile() : returns a string representation with a certain format for writing to a file.
 */

public class Item
{
	private int itemCode;
	private String itemName;
	private int itemQuantityInStock;
	private float itemPrice;

	public Item() {
		itemCode = 0;
		itemName = "";
		itemQuantityInStock = 0;
		itemPrice = 0.0f;
	}

	public Item(int itemCode, String itemName, int itemQuantityInStock, float itemPrice) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemQuantityInStock = itemQuantityInStock;
		this.itemPrice = itemPrice;
	}

	public boolean addItem(Scanner input)
	{

		while (true)
		{
			System.out.print("Enter the code for the item: ");

			if (!input.hasNextInt())
			{
				System.out.print("Invalid code... please enter integer greater than 0\n");

				input.nextLine();
			} else
			{
				itemCode = input.nextInt();

				if (itemCode > 0)
				{
					break;
				}

				else
				{
					System.out.print("Invalid code... please enter integer greater than 0\n");
					input.nextLine();
				}

			}

		}

		input.nextLine();

		while (itemName.replaceAll(" ", "").equals(""))
		{
			System.out.print("Enter the name for the item: ");
			if (input.hasNextLine())
			{
				itemName = input.nextLine();
			}
		}

		while (true)
		{
			System.out.print("Enter the quantity for the item: ");

			if (!input.hasNextInt())
			{
				System.out.print("Invalid quantity... please enter integer greater than 0\n");

				input.nextLine();
			} else
			{
				itemQuantityInStock = input.nextInt();

				if (itemQuantityInStock > 0)
				{
					break;
				}

				else
				{
					System.out.print("Invalid quantity... please enter integer greater than 0\n");
					input.nextLine();
				}

			}

		}

		input.nextLine();

		while (true)
		{
			System.out.print("Enter the price for the item: ");

			if (!input.hasNextFloat())
			{
				System.out.print("Invalid price... please enter float greater than 0\n");

				input.nextLine();
			} else
			{
				itemPrice = input.nextFloat();

				if (itemPrice > 0)
				{
					break;
				}

				else
				{
					System.out.print("Invalid price... please enter float greater than 0\n");
					input.nextLine();
				}

			}

		}
		return true;
	}

	public String toString()
	{
		String name = "";
		String price = String.format(" price: $%.2f", itemPrice);
		name = "Item: " + itemCode + " " + itemName + " " + itemQuantityInStock + price;
		return name;
	}

	public boolean updateItem(int amount)
	{
		if (amount < 0)
		{
			if (Math.abs(amount) <= itemQuantityInStock)
			{
				itemQuantityInStock += amount;
				return true;
			} else
			{
				return false;
			}
		} else
		{
			itemQuantityInStock += amount;
			return true;
		}

	}

	public boolean isEqual(Item item)
	{
		if (item.itemCode == this.itemCode)
		{
			return true;
		}
		return false;
	}

	public boolean inputCode(Scanner input)
	{
		System.out.print("Enter valid item code: ");
		if (input.hasNextInt())
		{
			itemCode = input.nextInt();
			return true;
		} else
		{
			input.nextLine();
		}

		return false;
	}

	public boolean checkCode(int[] array, int lengthArray, int itemsLength, ArrayList<Item> item)
	{
		int count = 0;
		for (int i = 0; i < itemsLength; i++)
		{

			for (int j = 0; j < lengthArray; j++)
			{
				if (item.get(i).itemCode == array[j])
				{
					count++;
				}
			}

		}
		if (count == lengthArray)
		{
			return true;
		}

		return false;
	}

	public int compareInputCode(Item item)
	{
		if (this.itemCode < item.itemCode)
			return -1;
		else if (this.itemCode > item.itemCode)
			return 1;
		else
			return 0;
	}

	public String toWriteFile()
	{
		return itemCode + " " + itemName + " " + itemQuantityInStock + " " + itemPrice + " ";
	}

}
