import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name2: Chirag Chirag
 * Student2: 040886270
 * Assignment2
 * 03-09-2018
 * Class is used to store Items(PurchasedItem and Manufactured Items) , Write items from Inventory ArrayList to a file and Read items from a file into ArrayList
 * Members:
 * items: Dynamic ArrayList used to hold Item objects of Purchased and Manufactured.
 * Methods:
 * Inventory(): Default constructor to initialize Item array.
 * addItem(): Takes input from user and invokes corresponding addItem method depending if Purchased or Manufactured Item.
 * toString():Return a string representation of all the items in the Inventory array.
 * alreadyExist(): method checks for duplicates (updated with binary search for efficiency).
 * updateQuantity(): denotes whether buying or selling and updates quantity according to conditions.
 * binarySearch() : search through arrayList to find correct index (time complexity of O(log n)).
 * insertItem() : calls binary search when inserting item into arrayList.
 * openOutputFile(): Ask user to input file name to prepare file to write items in from ArrayList, also handles all errors and exceptions.
 * createFile(): appends the items in the ArrayList and writes to file, also handles all errors and exceptions.
 * openFile(): Ask user to input file name that he wants to read in, also handles all errors and exceptions.
 * readFile(): Reads the contents in the file and stores the items into the arrayList inventory, also handles all errors and exceptions.
 */

public class Inventory
{
	private ArrayList<Item> items;

	public Inventory() {
		items = new ArrayList<Item>();
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
				if (items.isEmpty())
				{
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

				if (!item.checkCode(null, 0, items.size(), items))
				{
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

			}

		}

		if (alreadyExists(item) == -1)
		{
			insertItem(item);
			return true;
		} else
		{
			System.out.println("Item did not get added.. already exist in inventory.\n");
			return false;
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.size(); i++)
		{
			sb.append(items.get(i) + "\n");
		}

		return sb.toString();
	}

	public int alreadyExists(Item item)
	{
		int index = binarySearch(item);
		if (!items.isEmpty())
		{
			if (index < items.size())
			{
				if (items.get(index).isEqual(item))
				{

					return index;
				}
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

			if (items.get(currentItem).updateItem(quantity))
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

			if (items.get(currentItem).updateItem(-quantity))
			{
				System.out.println();
				return true;
			} else
			{
				return false;
			}
		}
	}

	public int binarySearch(Item item)
	{
		int lowIndex = 0;
		int highIndex = items.size() - 1;
		int midIndex = highIndex / 2;

		while (lowIndex <= highIndex)
		{
			if (item.compareInputCode(items.get(midIndex)) < 0)
			{
				highIndex = midIndex - 1;
			} else if (item.compareInputCode(items.get(midIndex)) > 0)
			{
				lowIndex = midIndex + 1;
			} else
			{
				return midIndex; // only hits when checking for duplicates
			}
			midIndex = (lowIndex + highIndex) / 2;
		}
		return lowIndex;
	}

	public void insertItem(Item item)
	{
		if (items.isEmpty()) // check to see if arrayList is empty
			items.add(item);
		else
			items.add(binarySearch(item), item);
	}

	public void openOutputFile(Scanner input)
	{
		if (items.isEmpty())
		{
			System.out.println("There are no items to write to file \n");
		} else
		{
			String fileName = new String();
			FileWriter outFile = null;
			char option = '\u0000';

			System.out.print("\nEnter name of file to write to: ");
			fileName = input.next();
			File file = new File(fileName);
			try
			{
				if (file.exists())
				{
					do
					{
						System.out.println(
								"File name already exist. Would you like to overwrite the file?(y for yes, anything else for no");
						option = input.next().charAt(0);
						if (option == 'y' || option == 'Y')
						{
							break;

						} else
						{
							System.out.println("Please enter a new file name to write to :");
							fileName = input.next();
							file = new File(fileName);
						}

					} while (file.exists());
				}
				outFile = new FileWriter(fileName);
				createFile(outFile);
				System.out.println("Successfully written to file\n");

			} catch (IOException e)
			{
				System.out.println("Could not open file...." + fileName + "exiting");
			}
		}
	}

	public void createFile(FileWriter outFile)
	{
		try
		{
			for (int i = 0; i < items.size(); i++)
			{
				outFile.append(items.get(i).toWriteFile() + "\n");
			}
			outFile.close();

		} catch (IOException e)
		{
			System.out.println("Error writing to file");
		}
	}

	public void openFile(Scanner input)
	{
		Scanner inFile = null;
		String fileName = new String();
		System.out.print("\nEnter name of file to read from: ");
		fileName = input.next();
		try
		{
			File file = new File(fileName);
			if (file.exists())
			{
				inFile = new Scanner(file);
				if (inFile.hasNext())
				{
					readFile(inFile);
					inFile.close();
				} else
					System.out.println("File is empty there is no items to load in\n");
			} else
			{
				throw new IOException("Could not find file..." + fileName + " exiting\n");
			}
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void readFile(Scanner read)
	{
		Item item = null;
		char itemType = '\u0000';
		int itemCode = 0;
		String itemName = null;
		int quantity = 0;
		float price = 0;

		while (read.hasNext())
		{
			if (read.hasNextLine())
				itemType = read.next().charAt(0);
			if (read.hasNextInt())
			{
				itemCode = read.nextInt();
			} else
			{
				System.out.println("Error reading in itemCode... garbage value unable to read file \n");
				return;
			}
			if (read.hasNextLine())
				itemName = read.next();
			if (read.hasNextInt())
			{
				quantity = read.nextInt();
			} else
			{
				System.out.println("Error reading in quantity... garbage value unable to read file \n");
				return;
			}
			if (read.hasNextFloat())
			{
				price = read.nextFloat();
			} else
			{
				System.out.println("Error reading in price... garbage value unable to read file \n");
				return;
			}

			if (itemType == 'p' || itemType == 'P')
			{
				String supplierName = null;

				if (read.hasNext())
					supplierName = read.next();
				item = new PurchasedItem(itemCode, itemName, quantity, price, supplierName);

			} else
			{
				int[] itemsUsed = new int[10];

				for (int i = 0; i < itemsUsed.length; i++)
				{
					if (read.hasNextInt())
					{
						itemsUsed[i] = read.nextInt();
					} else
					{
						System.out.println("Error reading in itemCodesUsed... garbage value unable to read file \n");
						return;
					}
					if (itemsUsed[i] == -1)
					{
						break;
					}
				}

				if (itemsUsed[0] != -1)
				{
					item = new ManufacturedItem(itemCode, itemName, quantity, price, itemsUsed);
				} else
				{
					System.out.println(
							"\nNot enough usedItemCode to create " + itemName + " ManufacturedItem... skipping over\n");
					continue;
				}
			}

			if (alreadyExists(item) == -1)
			{
				insertItem(item);
			} else
			{
				System.out.println("Item did not get added.. already exist in inventory.\n");
			}
		}
		System.out.println("Successfully read from file\n");
	}

}
