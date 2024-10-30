package Exceptions;

public class BlankInputException extends Exception {

	public BlankInputException() {
		super("Input must contain at least one letter.");
	}
	
}
