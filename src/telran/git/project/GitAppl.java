package telran.git.project;

import telran.view.Item;
import telran.view.StandardInputOutput;

public class GitAppl {

	public static void main(String[] args) {
		GitRepositoryImpl git = new GitRepositoryImpl();
		GitControllerItems gitController = new GitControllerItems(git);
		Item menu = gitController.menu();
		menu.perform(new StandardInputOutput());
	}
}
