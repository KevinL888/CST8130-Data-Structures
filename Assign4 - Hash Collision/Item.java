
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
 *  searchItemCodeArrayList() : Looks for an itemsCode that matches to an itemCode of an Item in the Inventory and returns the reference to that Item
 *  ErrorHandling(): Handles all errors exceptions
 */

public class Item {
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

	public boolean addFromFile(Scanner read) {

		if (read.hasNextInt()) {
			itemCode = read.nextInt();
		} else {
			System.out.println("Error reading in itemCode... garbage value unable to read file \n");
			return false;
		}
		if (read.hasNextLine())
			itemName = read.next();
		if (read.hasNextInt()) {
			itemQuantityInStock = read.nextInt();
		} else {
			System.out.println("Error reading in quantity... garbage value unable to read file \n");
			return false;
		}
		if (read.hasNextFloat()) {
			itemPrice = read.nextFloat();
		} else {
			System.out.println("Error reading in price... garbage value unable to read file \n");
			return false;
		}
		return true;
	}

	public boolean addItem(Scanner input) {

		System.out.print("Enter the code for the item: ");

		itemCode = errorHandling(input, itemCode);

		input.nextLine();

		while (itemName.replaceAll(" ", "").equals("")) {
			System.out.print("Enter the name for the item: ");
			if (input.hasNextLine()) {
				itemName = input.nextLine();
			}
		}

		System.out.print("Enter the quantity for the item: ");

		itemQuantityInStock = errorHandling(input, itemQuantityInStock);

		input.nextLine();

		while (true) {
			System.out.print("Enter the price for the item: ");

			if (!input.hasNextFloat()) {
				System.out.print("Invalid price... please enter float greater than 0\n");

				input.nextLine();
			} else {
				itemPrice = input.nextFloat();

				if (itemPrice > 0) {
					break;
				}

				else {
					System.out.print("Invalid price... please enter float greater than 0\n");
					input.nextLine();
				}

			}

		}
		return true;
	}

	public String toString() {
		String name = "";
		String price = String.format(" price: $%.2f", itemPrice);
		name = "Item: " + itemCode + " " + itemName + " " + itemQuantityInStock + price;
		return name;
	}

	public boolean updateItem(int amount) {
		if (amount < 0) {
			if (Math.abs(amount) <= itemQuantityInStock) {
				itemQuantityInStock += amount;
				return true;
			} else {
				return false;
			}
		} else {
			itemQuantityInStock += amount;
			return true;
		}

	}

	public boolean isEqual(Item item) {
		if (item.itemCode == this.itemCode) {
			return true;
		}
		return false;
	}

	public int inputCode(Scanner input) {
		System.out.print("Enter valid item code: ");
		itemCode = errorHandling(input, itemCode);
		return itemCode;
	}

	public boolean checkCode(int[] array, int lengthArray, int itemsLength, ArrayList<LinkedList<Item>> item) {
		int count = 0;
		Item temp = new Item();
		for (int i = 0; i < lengthArray; i++) {
			temp.itemCode = array[i];
			temp.itemQuantityInStock = -1; // set this so no message will pop up
											// when search ItemCode
			if (searchItemCodeArrayList(temp, item) != null) {
				count++;
			}
		}

		if (count == lengthArray) {
			return true;
		}
		return false;
	}

	public Item searchItemCodeArrayList(Item item, ArrayList<LinkedList<Item>> inventory) {
		int index = 0;
		int listIndex = 0;
		index = item.calcHash();

		if (inventory.get(index).size() == 0) {
			return null;
		} else {
			while (inventory.get(index).size() != 0
					&& !inventory.get(index).get(listIndex).checkInputCode(item.itemCode)) {
				if (index <= inventory.size() - 1) {
					if (listIndex < inventory.get(index).size() - 1) {
						listIndex++;
					} else {
						return null;
					}
				} else {
					break;
				}
			}
			if (inventory.get(index).size() != 0) {
				if (item.itemQuantityInStock != -1)
					System.out.println("\nitemCode " + item.itemCode + " is located at index " + index
							+ " in the Inventory and at index " + listIndex + " of the Linked List");
				return inventory.get(index).get(listIndex);
			} else
				return null;
		}
	}

	public int compareInputCode(Item item) {
		if (this.itemCode < item.itemCode)
			return -1;
		else if (this.itemCode > item.itemCode)
			return 1;
		else
			return 0;
	}

	public boolean checkInputCode(int itemCode) {
		if (itemCode == this.itemCode)
			return true;
		else
			return false;
	}

	public String toWriteFile() {
		return itemCode + " " + itemName + " " + itemQuantityInStock + " " + itemPrice + " ";
	}

	public int calcHash() {
		return itemCode % 100;
	}

	public int errorHandling(Scanner input, int option) {
		while (true) {
			if (!input.hasNextInt()) {
				System.out.print("\nInvalid entry: please enter a positive integer : ");
				input.nextLine();
			} else {
				option = input.nextInt();

				if (option >= 0) {
					break;
				} else {
					System.out.print("\nInvalid entry: please enter a positive integer : ");
					input.nextLine();
				}
			}
		}
		return option;
	}
}
