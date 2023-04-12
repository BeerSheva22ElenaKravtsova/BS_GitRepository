package telran.git.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitTest {
	GitRepositoryImpl git = new GitRepositoryImpl();

	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	
	
//	5.	Run sanity test with the menu items
//	5.1.	Create three files and perform different commits with updated files and using such commands as “status” and “log” make sure that the application works properly
//	5.2.	Create branches and additional commits to different branches. Using the command “branches” and “log” make sure the application works properly
//	5.3.	Perform switchTo and by introspecting the file contents make sure that the application works properly
//	5.4.	Exit from application
//	5.5.	Start again and make sure that the previous state has been restored
	
//	5. Запустите проверку работоспособности с помощью пунктов меню.
//	5.1. Создайте три файла и выполните различные коммиты с обновленными файлами и с помощью таких команд, как «status» и «log», убедитесь, что приложение работает правильно.
//	5.2. Создавайте ветки и дополнительные коммиты в разные ветки. Используя команду «ветви» и «журнал», убедитесь, что приложение работает правильно.
//	5.3. Выполните switchTo и, проверив содержимое файла, убедитесь, что приложение работает правильно.
//	5.4. Выйти из приложения
//	5.5. Запустите снова и убедитесь, что предыдущее состояние было восстановлено


	@Test
//	@Order()
	void testInit() {
		System.out.println("");
		System.out.println("*******INIT*********");
		GitRepositoryImpl initGit = GitRepositoryImpl.init();
		assertEquals(git.getHead(), initGit.getHead());
	}

	@Test
	@Order(2)
	void testCommit() {
		System.out.println("");
		System.out.println("*******COMMIT*********");
		git.info().forEach(System.out::println);
		System.out.println(git.commit("FirstCommit"));
		git.save();
		System.out.println(git.commit("SecondCommit"));
		git.save();
		System.out.println(git.commit("ThirdCommit"));
	}

	@Test
	@Order(3)
	void testInfo() {
		System.out.println("");
		System.out.println("*******INFO*********");
		System.out.println("");
		git.info().forEach(System.out::println);
	}

	@Test
	@Order(4)
	void testCreateBranch() {
		System.out.println("");
		System.out.println("*******CREATE BRANCH*********");
		System.out.println(git.createBranch("master"));
		assertEquals("master", git.getHead());
		System.out.println(git.createBranch("new branch"));
		assertEquals("new branch", git.getHead());
		System.out.println(git.createBranch("new branch 2"));
	}

	@Test
	@Order(5)
	void testRenameBranch() {
		System.out.println("");
		System.out.println("*******RENAME BRANCH*********");
		System.out.println(git.renameBranch("master", "maestro"));
		assertEquals("maestro", git.getHead());
	}

	@Test
	@Order(6)
	void testDeleteBranch() {
		System.out.println("");
		System.out.println("*******DELETE BRANCH*********");
		System.out.println(git.deleteBranch("maestro"));
	}

	@Test
	void testLog() {
		System.out.println("");
		System.out.println("*******LOG*********");
		git.log().forEach(cm -> System.out.println(cm.message));
	}

	@Test
	void testBranches() {
		System.out.println("");
		System.out.println("*******BRANCHES*********");
		git.branches().forEach(System.out::println);
	}

	@Test
	void testCommitContent() {
		System.out.println("");
		System.out.println("*******CONTENT*********");
//		git.commits.keySet().forEach(System.out::println);
		git.commitContent(git.commits.get(git.branches.get(git.getHead())).commitMessage().name).forEach(System.out::println);
	}

	@Test
	@Order(8)
	void testSwitchTo() {
		System.out.println("");
		System.out.println("*******SWITCH TO*********");
		System.out.println(git.getHead());
		System.out.println(git.switchTo("new branch"));
		System.out.println(git.getHead());
		git.branches().forEach(System.out::println);
	}

	@Test
	
	void testSave() {
		System.out.println("");
		System.out.println("*******SAVE*********");
		git.save();
	}

	@Test
	@Order(1)
	void testAddIgnoredFileNameExp() {
		System.out.println("*******IGNORED FILE NAMES*********");
		System.out.println(git.addIgnoredFileNameExp("\\.[a-zA-Z]"));
	}
}
