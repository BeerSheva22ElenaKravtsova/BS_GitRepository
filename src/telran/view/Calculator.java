package telran.view;

import static telran.view.ArithmeticOperations.*;
import static telran.view.DatesOperations.*;

public class Calculator {
	static Item digitsMenu = new Menu("Arithmetic Operations", sum, sub, mult, div, Item.exit());
	static Item dateMenu = new Menu("Dates Operations", sumDate, subDate, Item.exit());
	static Item menu = new Menu("Calculator", digitsMenu, dateMenu, Item.exit());

	public static void main(String[] args) {
		menu.perform(new StandardInputOutput());
	}
}