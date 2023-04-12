package telran.view;

import java.time.LocalDate;

public class DatesOperations {
	public static Item sumDate = Item.of("Summary", io -> compute("summary", io));
	public static Item subDate = Item.of("Subtraction", io -> compute("subtraction", io));

	static String errorPromtLong = String.format("Entered value must be a number in range: %s - %s", Long.MIN_VALUE,
			Long.MAX_VALUE);

	private static void compute(String operation, InputOutput io) {
		LocalDate date = io.readDateISO("Write date in format yyyy-MM-dd",
				"Entered value doesn't match the format of Date");
		long days = io.readLong(String.format("Write number of days for %s", operation), errorPromtLong, Long.MIN_VALUE,
				Long.MAX_VALUE);
		switch (operation) {
		case "summary" -> io.writeLine("Result: " + date.plusDays(days));
		case "subtraction" -> io.writeLine("Result: " + date.minusDays(days));
		default -> throw new IllegalArgumentException("Unexpected value: " + operation);
		}
	}
}
