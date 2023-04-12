package telran.git.project;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public interface GitRepository extends Serializable {
	String GIT_FILE = ".mygit";
	String CURRENT_DIR = ".";

	String commit(String commitMessage);

	List<FileState> info();

	String createBranch(String branchName);

	String renameBranch(String branchName, String newName);

	String deleteBranch(String branchName);

	List<CommitMessage> log();

	List<String> branches(); // list of branch names

	List<Path> commitContent(String commitName);

	String switchTo(String name); // name is either a commit name or a branch name

	String getHead(); // return null if head refers commit with no branch

	void save(); // saving to .mygit serialization to file (Object Stream)

	String addIgnoredFileNameExp(String regex);

}
