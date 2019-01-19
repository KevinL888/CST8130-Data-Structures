import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/*
 * Name: Kevin Lai
 * Student Num: 040812704
 * Name: Tanishq Bansal
 * Student Num: 040883753
 * Assignment3
 * 03-19-2018
 * Class is used to manipulate a LinkedList(BlockChain) inserting,removing,printing,verifying.  
 * Members:
 * blockChain: LinkList(Course) to hold Blocks(date, student id and grades)
 * courseName: name of course E.g.(CST8110, CST8130) at Algonquin College.
 * it: Iterator used to traverse through the Linked List. 
 * Methods:
 * BlockChain(): default constructor to initialize courseName and blockChain(LinkedList).
 * addBlock(): add a Block(Student) to BlockChain(Course).
 * addBadBlock(): add a bad Block(Student) to BlockChain(Course).(previousHash is random)
 * verifyChain(): goes through the BlockChain(Courses) and verifies that each Block(Student) has the previousHash of the one before it
 * toString(): return a string representation of courseName.
 * removeBadBlocks(): remove all bad blocks which have previousHash that don't correspond to the previous Block's currentHash.
 * printBlockChain() : traverse through the chain and print out all Blocks data(toString in Block class).
 */

public class BlockChain {
	private LinkedList<Block> blockChain;
	private String courseName = "NotEntered";
	private Iterator<Block> it;

	public BlockChain(String courseName) {
		blockChain = new LinkedList<Block>();
		this.courseName = new String(courseName);
		blockChain.add(new Block());
	}

	public void printBlockChain() {
		it = blockChain.iterator();
		Block current = null;
		System.out.print("For course: " + courseName + "\n[");

		for (int i = 0; it.hasNext(); i++) {
			current = it.next();

			if (i == 0) {
				System.out.print("\n[Block " + (i + 1) + "] [Head] - " + current + ",");
			} else if (blockChain.getLast() == current) {
				System.out.print("\n[Block " + (i + 1) + "] [Tail] - " + current + ",");
			} else {
				System.out.print("\n[Block " + (i + 1) + "]\t - " + current + ",");
			}
		}
		System.out.println("]\n");
	}

	public boolean verifyChain() {
		it = blockChain.iterator();
		Block previous = it.next();
		Block current = null;

		while (it.hasNext()) {
			current = it.next();
			if (!current.isEqual(previous))
				return false;
			previous = current;
		}
		return true;
	}

	public void removeBadBlocks() {
		it = blockChain.iterator();
		Block previous = it.next();
		Block current = null;

		while (it.hasNext()) {
			current = it.next();
			if (!current.isEqual(previous)) {

				it.remove();
				if (it.hasNext()) {
					current = it.next();
					current.updatePreviousHash(previous.getCurrentHash());
					previous = current;
				}
			} else {
				previous = current;
			}
		}
	}

	public void addBlock(Scanner input) {
		Block newOne = new Block();
		char option = '\u0000';
		System.out.println("Add good block or bad? (g for good, anything else for bad):");
		option = input.next().charAt(0);
		if (option == 'g' || option == 'G') {
			if (newOne.addInfoToBlock(input, blockChain.getLast().getCurrentHash())) {
				System.out.println("Successfully created a good student block in chain");
			} else {
				System.out.println("Error creating block");
			}
			blockChain.add(newOne);
		} else {
			addBadBlock(input);
		}
	}

	public void addBadBlock(Scanner keyboard) {
		Random random = new Random();
		Block newOne = new Block();
		if (newOne.addInfoToBlock(keyboard, random.nextFloat())) {
			System.out.println("Successfully created a bad student block in the chain");
		} else {
			System.out.println("Error creating block");
		}
		blockChain.add(newOne);
	}

	public String toString() {
		return courseName;
	}
}
