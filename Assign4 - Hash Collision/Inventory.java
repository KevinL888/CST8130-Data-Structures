import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name: Seongyeop Jeong
 * Student Num: 040885882
 * Assignment4
 * 04-20-2018
 * Class is used to store Items(PurchasedItem and Manufactured Items) , Write items from Inventory ArrayList to a file and Read items from a file into ArrayList
 * Members:
 * items: Dynamic ArrayList used to hold Item objects of Purchased and Manufactured.
 * Methods:
 * Inventory(): Default constructor to initialize Item array.
 * addItem(): Takes input from user and invokes corresponding addItem method depending if Purchased or Manufactured Item.
 * toString():Return a string representation of all the items in the Inventory array.
 * alreadyExist(): method checks for duplicates (updated with binary search for efficiency).
 * updateQuantity(): denotes whether buying or selling and updates quantity according to conditions.
 * openOutputFile(): Ask user to input file name to prepare file to write items in from ArrayList, also handles all errors and exceptions.
 * createFile(): appends the items in the ArrayList and writes to file, also handles all errors and exceptions.
 * openFile(): Ask user to input file name that he wants to read in, also handles all errors and exceptions.
 * readFile(): Reads the contents in the file and stores the items into the arrayList inventory, also handles all errors and exceptions.
 * insertHash(): Inserts item using Hashing
 * searchInventory(): Using Hashing to search for an Item in the Inventory and display that Item
 */

public class Inventory {
	private ArrayList<Item> items;
	private ArrayList<LinkedList<Item>> inventory;

	public Inventory() {
		inventory = new ArrayList<LinkedList<Item>>(100);
		for (int i = 0; i < 100; i++) {
			inventory.add(i, new LinkedList<Item>());
		}
		items = new ArrayList<Item>();
	}

	public boolean addItem(Scanner input) {

		System.out.print("Do you wish to add a purchased item (enter P/p) or manufactured (enter anything else)? ");

		char option = input.next().charAt(0);

		Item item;

		if (option == 'p' || option == 'P') {
			input.nextLine();
			item = new PurchasedItem();
			item.addItem(input);

		}

		else {
			input.nextLine();
			item = new ManufacturedItem();
			if (item.addItem(input)) {
				if (inventory.isEmpty()) {
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

				if (!item.checkCode(null, 0, items.size(), inventory)) {
					System.out.println("Error... manufactured item consist of an item not in inventory\n");
					return false;
				}

			} else {
				return false;
			}
		}

		if (alreadyExists(item) == -1) {
			insertHash(item);
			return true;
		} else {
			System.out.println("Item did not get added.. already exist in inventory.\n");
			return false;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).size() != 0) {
				for (int j = 0; j < inventory.get(i).size(); j++) {
					sb.append(inventory.get(i).get(j) + "\n");
				}
			}
		}
		return sb.toString();
	}

	public int alreadyExists(Item item) {
		if (item.searchItemCodeArrayList(item, inventory) == null)
			return -1;
		else
			return 1;
	}

	public boolean updateQuantity(Scanner input, boolean bool) {
		Item item = new Item();
		int quantity = 0;

		item.inputCode(input);
		item = item.searchItemCodeArrayList(item, inventory);

		if (item == null) {
			System.out.println("Could not found in inventory...");
			return false;
		}

		input.nextLine();
		while (true) {
			System.out.print("Enter valid quantity : ");
			if (!input.hasNextInt()) {
				System.out.println("Please enter a valid quantity");
				input.nextLine();
			} else {
				quantity = input.nextInt();
				if (quantity > 0) {
					break;
				} else {
					System.out.println("Please enter a valid quantity");
					input.nextLine();
				}
			}

		}

		if (bool) {

			if (item.updateItem(quantity)) {
				System.out.println();
				return true;
			} else {
				return false;
			}
		}

		else {
			input.nextLine();

			if (item.updateItem(-quantity)) {
				System.out.println();
				return true;
			} else {
				return false;
			}
		}
	}

	public void openOutputFile(Scanner input) {
		boolean isEmpty = true;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).size() > 0)
				isEmpty = false;
		}

		if (isEmpty) {
			System.out.println("There are no items to write to file \n");
		} else {
			String fileName = new String();
			FileWriter outFile = null;
			char option = '\u0000';

			System.out.print("\nEnter name of file to write to: ");
			fileName = input.next();
			File file = new File(fileName);
			try {
				if (file.exists()) {
					do {
						System.out.println(
								"File name already exist. Would you like to overwrite the file?(y for yes, anything else for no");
						option = input.next().charAt(0);
						if (option == 'y' || option == 'Y') {
							break;

						} else {
							System.out.println("Please enter a new file name to write to :");
							fileName = input.next();
							file = new File(fileName);
						}

					} while (file.exists());
				}
				outFile = new FileWriter(fileName);
				createFile(outFile);
				System.out.println("Successfully written to file\n");

			} catch (IOException e) {
				System.out.println("Could not open file...." + fileName + "exiting");
			}
		}
	}

	public void createFile(FileWriter outFile) {
		try {
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i).size() != 0) {
					for (int j = 0; j < inventory.get(i).size(); j++) {
						outFile.append(inventory.get(i).get(j).toWriteFile() + "\n");
					}
				}
			}
			outFile.close();

		} catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}

	public void openFile(Scanner input) {
		Scanner inFile = null;
		String fileName = new String();
		System.out.print("\nEnter name of file to read from: ");
		fileName = input.next();
		try {
			File file = new File(fileName);
			if (file.exists()) {
				inFile = new Scanner(file);
				if (inFile.hasNext()) {
					readFile(inFile);
					inFile.close();
				} else
					System.out.println("File is empty there is no items to load in\n");
			} else {
				throw new IOException("Could not find file..." + fileName + " exiting\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void readFile(Scanner read) {

		Item item = null;
		char itemType = '\u0000';

		while (read.hasNext()) {
			itemType = read.next().charAt(0);
			if (itemType == 'p' || itemType == 'P') {
				item = new PurchasedItem();

			} else {
				item = new ManufacturedItem();
			}

			if (item.addFromFile(read)) {
				if (alreadyExists(item) == -1) {
					insertHash(item);
				} else {
					System.out.println("Item did not get added.. already exist in inventory.\n");
				}
			} else {
				System.out.println("Item was not created.. error processing the item \n");
			}
		}
		System.out.println("Successfully read from file\n");
	}

	public void insertHash(Item item) {
		int index = item.calcHash();

		if (index <= inventory.size() - 1)
			inventory.get(index).add(item);
		else {
			System.out.println("Item cannot be added to Inventory\n");
			return;
		}

	}

	public void searchInventory(Scanner input) {
		Item item = new Item();
		int itemCode;
		System.out.println("Please enter the Itemcode of the item you would like to search for : ");
		itemCode = item.inputCode(input);

		item = item.searchItemCodeArrayList(item, inventory);

		if (item != null) {
			System.out.println(item + "\n");
		} else
			System.out.println("\nitemCode " + itemCode + " was not found in the Inventory\n");
	}

}
