package telran.view;

public class ArithmeticOperations {
	public static Item sum = Item.of("Summary", io -> compute("summary", io));
	public static Item sub = Item.of("Subtraction", io -> compute("subtraction", io));
	public static Item mult = Item.of("Multiplication", io -> compute("multiplication", io));
	public static Item div = Item.of("Division", io -> compute("division", io));

	static String errorPromtDouble = String.format("Entered value must be a number in range from %s to %s", -Double.MAX_VALUE,
			Double.MAX_VALUE);

	private static void compute(String operation, InputOutput io) {
		double num1 = io.readNumber(String.format("Write first number for %s", operation), errorPromtDouble, -Double.MAX_VALUE,
				Double.MAX_VALUE);
		double num2 = io.readNumber(String.format("Write second number for %s", operation), errorPromtDouble, -Double.MAX_VALUE,
				Double.MAX_VALUE);
		if (operation.equals("division") && num2 == 0) {
			io.writeLine("Error: Division by 0");
			num2 = io.readNumber("Write second number", errorPromtDouble, -Double.MAX_VALUE, Double.MAX_VALUE);
		}
		switch (operation) {
		case "summary" -> io.writeLine("Result: " + (num1 + num2));
		case "subtraction" -> io.writeLine("Result: " + (num1 - num2));
		case "multiplication" -> io.writeLine("Result: " + (num1 * num2));
		case "division" -> io.writeLine("Result: " + (num1 / num2));
		default -> throw new IllegalArgumentException("Unexpected value: " + operation);
		}
	}


}
