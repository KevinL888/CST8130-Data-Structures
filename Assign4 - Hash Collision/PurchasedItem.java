import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name: Seongyeop Jeong
 * Student Num: 040885882
 * Assignment4
 * 04-20-2018
 * Class is used to create PurchasedItems,(subclass) inherits from Item.  
 * Members:
 * supplierName: The suppliers name that is being purchased from.
 * Methods:
 * PurchasedItem(): Default constructor to call super() and initialize variables.
 * addItem(): Used to take input of supplier name.
 * toString(): Return a string representation of the members from super class along with supplierName.
 * toWriteFile() : returns a string representation with a certain format for writing to a file.
 */

public class PurchasedItem extends Item {
	private String supplierName;

	public PurchasedItem() {
		super();
		supplierName = "";
	}

	@Override
	public boolean addFromFile(Scanner read) {
		if (super.addFromFile(read)) {
			if (read.hasNext()) {
				supplierName = read.next();
				return true;
			} else {
				System.out.println("There is no SupplierName to read in error processing \n");
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean addItem(Scanner input) {
		if (super.addItem(input)) {

			input.nextLine();

			while (supplierName.replaceAll(" ", "").equals("")) {
				System.out.println("Enter the name of the supplier: ");
				if (input.hasNextLine()) {
					supplierName = input.nextLine();
					System.out.println();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String name = "";
		name = " Supplier: " + supplierName;
		return super.toString() + name;
	}

	@Override
	public String toWriteFile() {
		return "p " + super.toWriteFile() + supplierName;
	}

}
