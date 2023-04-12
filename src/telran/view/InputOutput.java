package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String promt);

	void writeString(Object obj);

	default void writeLine(Object obj) {
		writeString(obj.toString() + "\n");
	};

	private <T> T readData(String promt, String errorPromt, Function<String, T> mapper, Predicate<T> predicate,
			String errorMessage) {
		boolean running = true;
		T res = null;
		while (running) {
			try {
				String str = readString(promt);
				res = mapper.apply(str);
				if (predicate != null && !predicate.test(res)) {
					throw new Exception(errorMessage);
				}
				running = false;
			} catch (Exception e) {
				writeLine(errorPromt + " - " + e.getMessage());
			}
		}
		return res;
	}

	private <T> String resString(T min, T max) {
		return String.format("Entered value must be a number in range: %s - %s", min, max);
	}

	public default <R> R readObject(String promt, String errorPromt, Function<String, R> mapper) {
		return readData(promt, errorPromt, mapper, null, null);
	}

	public default String readString(String promt, String errorPromt) {
		return readData(promt, errorPromt, s -> s, null, null);
	}

	public default String readStringPredicate(String promt, String errorPromt, Predicate<String> predicate) {
		return readData(promt, errorPromt, s -> s, predicate, "Entered value doesn't match the Predicate conditions");
	}

	public default String readStringOptions(String promt, String errorPromt, Set<String> options) {
		return readData(promt, errorPromt, s -> s, options::contains, "Entered value must be one of the Options");
	}

	public default int readInt(String promt, String errorPromt) {
		return readInt(promt, errorPromt, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public default int readInt(String promt, String errorPromt, int min, int max) {
		return readData(promt, errorPromt, Integer::parseInt, i -> i >= min && i <= max, resString(min, max));
	}

	public default long readLong(String promt, String errorPromt, long min, long max) {
		return readData(promt, errorPromt, Long::parseLong, l -> l >= min && l <= max, resString(min, max));
	}

	public default LocalDate readDateISO(String promt, String errorPromt) {
		return readData(promt, errorPromt, LocalDate::parse, s -> true, "Entered value doesn't match the LocalDate");
	}

	public default LocalDate readDate(String promt, String errorPromt, String format, LocalDate min, LocalDate max) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return readData(promt, errorPromt, s -> LocalDate.parse(s, df), ld -> ld.isAfter(min) && ld.isBefore(max),
				resString(min, max));
	}

	public default double readNumber(String promt, String errorPromt, double min, double max) {
		return readData(promt, errorPromt, Double::parseDouble, d -> d >= min && d <= max, resString(min, max));
	}
}
