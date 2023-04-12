package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {
	InputOutput io = new StandardInputOutput();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Disabled
	void testReadObject() {
		System.out.println(io.readObject("Enter a number", "Wrong type of Object", Integer::valueOf));
	}

	@Test
	@Disabled
	void testReadStringPredicate() {
		System.out.println(io.readStringPredicate("Enter a String",
				"Entered value doesn't match the Predicate conditions", i -> i.compareTo("Abcde") > 0));
	}

	@Test
	@Disabled
	void testReadStringOptions() {
		Set<String> set = new HashSet<String>();
		set.add("One");
		set.add("Two");
		set.add("Three");
		System.out.println(io.readStringOptions("Enter a String", "Entered value must be one of the Options", set));
	}

	@Test
	@Disabled
	void testReadInt() {
		System.out.println(io.readInt("Enter a number",
				String.format("Entered value must be a number in range: %s - %s", 0, Integer.MAX_VALUE)));
	}

	@Test
	@Disabled
	void testReadIntMinMax() {
		System.out.println(io.readInt("Enter a number",
				String.format("Entered value must be a number in range: %s - %s", 10, 10000), 10, 10000));
	}

	@Test
	@Disabled
	void testReadLong() {
		System.out.println(io.readLong("Enter a number",
				String.format("Entered value must be a number in range: %s - %s", 10, 10000), 10, 10000));
	}

	@Test
	@Disabled
	void testReadDateISO() {
		System.out.println(io.readDateISO("Enter a LocalDate", "Entered value doesn't match the LocalDate"));
	}

	@Test
	@Disabled
	void testReadDate() {
		System.out.println(io.readDate("Enter a LocalDate", "Entered value doesn't match the LocalDate", "MM-dd-yyyy",
				LocalDate.of(1990, 11, 07), LocalDate.of(2023, 03, 28)));
	}

	@Test
	@Disabled
	void testReadNumber() {
		System.out.println(io.readNumber("Enter a number",
				String.format("Entered value must be a number in range: %s - %s", 10.1, 10000.1), 10.1, 10000.1));
	}
}
