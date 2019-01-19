import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Assignment1
 * 02-09-2018
 * Class is used to store Items(PurchasedItem and Manufactured Items) 
 * Members:
 * items: Dynamic array used to hold Item objects of Purchased and Manufactured.
 * numItems: Keeps track of how many Item objects are in the Item Array.
 * Methods:
 * Inventory(): Default constructor to initialize Item array.
 * addItem(): Takes input from user and invokes corresponding addItem method depending if Purchased or Manufactured Item.
 * toString():Return a string representation of all the items in the Inventory array.
 */

public class Inventory
{
	private Item[] items;
	private int numItems = 0;

	public Inventory() {
		items = new Item[1000];
	}

	public boolean addItem(Scanner input)
	{

		System.out.print("Do you wish to add a purchased item (enter P/p) or manufactured (enter anything else)? ");

		char option = input.next().charAt(0);

		Item item;

		if (option == 'p' || option == 'P')
		{
			input.nextLine();
			item = new PurchasedItem();
			item.addItem(input);

		}

		else
		{
			input.nextLine();
			item = new ManufacturedItem();
			if (item.addItem(input))
			{

				if (numItems == 0)
				{
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

				if (!item.checkCode(null, 0, numItems, items))
				{
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

			}

		}

		if (alreadyExists(item) == -1)
		{
			items[numItems] = item;
			numItems++;
			return true;
		} else
		{
			return false;
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numItems; i++)
		{
			sb.append(items[i] + "\n");
		}

		return sb.toString();
	}

	public int alreadyExists(Item item)
	{
		for (int i = 0; i < numItems; i++)
		{
			if (items[i].isEqual(item))
			{

				return i;
			}
		}
		return -1;
	}

	public boolean updateQuantity(Scanner input, boolean bool)
	{
		Item item = new Item();
		int quantity = 0;
		int currentItem = 0;

		item.inputCode(input);

		currentItem = alreadyExists(item);

		if (currentItem == -1)
		{
			System.out.println("Could not found in inventory...");
			return false;
		}

		input.nextLine();
		while (true)
		{
			System.out.print("Enter valid quantity : ");
			if (!input.hasNextInt())
			{
				System.out.println("Please enter a valid quantity");
				input.nextLine();
			} else
			{
				quantity = input.nextInt();
				if (quantity > 0)
				{
					break;
				} else
				{
					System.out.println("Please enter a valid quantity");
					input.nextLine();
				}
			}

		}

		if (bool)
		{

			if (items[currentItem].updateItem(quantity))
			{
				System.out.println();
				return true;
			} else
			{
				return false;
			}
		}

		else
		{
			input.nextLine();

			if (items[currentItem].updateItem(-quantity))
			{
				System.out.println();
				return true;
			} else
			{
				return false;
			}
		}
	}

}
