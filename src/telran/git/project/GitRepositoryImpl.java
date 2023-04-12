package telran.git.project;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class GitRepositoryImpl implements GitRepository {

	private static final long serialVersionUID = 1L;
	private Set<String> ignoredFiles = new HashSet<>();
	// УБРАТЬ ПАБЛИК
	public HashMap<String, Commit> commits = new HashMap<>();
	public HashMap<String, String> branches = new HashMap<>();
	private String head = null;

	public static GitRepositoryImpl init() {
		File file = new File(GIT_FILE);
		GitRepositoryImpl git = new GitRepositoryImpl();
		if (file.exists()) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
				git = (GitRepositoryImpl) input.readObject();
			} catch (Exception e) {
				System.out.println("Problems with reading saved Object, returned new Git without saved information");
			}
		}
		return git;
	}

	@Override
	public String commit(String commitMessage) {
		if (commits.containsKey(head)) {
			return "It is impossibile to do commit not to the Branch";
		}
		List<FileState> fs = info();

		if (!fs.stream().anyMatch(f -> f.status() != Status.COMMITED)) {
			return "Nothing to commit";
		}

		CommitMessage commitMessageObj;
		do {
			commitMessageObj = new CommitMessage(commitMessage);
		} while (commits.containsKey(commitMessageObj.name));
		Commit newCommit = new Commit(commitMessageObj, new Date(), getFileParameters(fs), branches.get(head));
		commits.put(newCommit.commitMessage().name, newCommit);
		if (head == null) {
			head = newCommit.commitMessage().name;
			createBranch("master");
		} else {
			branches.replace(head, newCommit.commitMessage().name);
		}
		return "Files have been commited";
	}

	private Map<Path, FileParameters> getFileParameters(List<FileState> fs) {
		Map<Path, FileParameters> res = new HashMap<>();
		fs.stream().forEach(f -> {
			Path path = f.path();
			res.put(path, new FileParameters(getData(path), new Date(path.toFile().lastModified())));
		});
		return res;
	}

	private String[] getData(Path path) {
		try {
			return Files.readAllLines(path).toArray(String[]::new);
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<FileState> info() {
		Path directory = Path.of(CURRENT_DIR);
		try {
			return Files.walk(directory, 1).filter(Files::isRegularFile)
					.filter(path -> !regexMatches(path.getFileName().toString()))
					.map(path -> new FileState(path, getStatus(path))).toList();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	// доделать тест с конкр проверкой
	private boolean regexMatches(String fileName) {
		return ignoredFiles.stream().anyMatch(fileName::matches);
	}

	private Status getStatus(Path path) {
		System.out.println();
		Commit commit = findCommit(head);
		if (commit == null || !commit.fileParameters().containsKey(path)) {
			return Status.UNTRACKED;
		} else {
			Date commitDate = commit.timeOfCommit();
			Date fileDate = new Date(path.toFile().lastModified());
			return commitDate.before(fileDate) ? Status.MODIFIED : Status.COMMITED;
		}
	}

	private Commit findCommit(String name) {
		return commits.get(branches.getOrDefault(name, name));
	}

	@Override
	public String createBranch(String branchName) {
		if (head == null) {
			return "There is no Commits yet, You can create new Branch only on Commit";
		} else if (branches.containsKey(branchName)) {
			return String.format("Branch with name %s already exists, change new Branch name", branchName);
		}
		branches.put(branchName, findCommit(head).commitMessage().name);
		head = branchName;
		return String.format("New Branch with name %s has been created", branchName);
	}

	@Override
	public String renameBranch(String branchName, String newName) {
		if (branches.containsKey(newName)) {
			return String.format("Branch Name %s already exists, change new Name", newName);
		}
		if (!branches.containsKey(branchName)) {
			return String.format("Branch Name %s  doesn't exist", branchName);
		}
		String commitName = branches.remove(branchName);
		branches.put(newName, commitName);
		if (head == branchName) {
			head = newName;
		}
		return String.format("Branch %s has been renamed to %s", branchName, newName);
	}

	@Override
	public String deleteBranch(String branchName) {
		if (!branches.containsKey(branchName)) {
			return String.format("Branch Name %s  doesn't exist", branchName);
		}
		if (head == branchName) {
			return String.format(
					"%s is Current branch (referred by the HEAD) and can't be deleted. If you want to delete this branch move HEAD",
					branchName);
		}
		branches.remove(branchName);
		return String.format("Branch %s has been removed", branchName);
	}

	// если хед налл - проверить что рез пустой список
	@Override
	public List<CommitMessage> log() {
		List<CommitMessage> res = new ArrayList<>();
		Commit commit = findCommit(head);
		while (commit != null) {
			res.add(commit.commitMessage());
			commit = commits.get(commit.prevCommitName());
		}
		return res;
	}

	@Override
	public List<String> branches() {
		return branches.keySet().stream().map(name -> name == head ? "*" + name : name).toList();
	}

	@Override
	public List<Path> commitContent(String commitName) {
		return commits.get(commitName).fileParameters().keySet().stream().toList();
	}

	@Override
	public String switchTo(String name) {
		if (name.equals(head)) {
			return String.format("HEAD is already equals %s", name);
		}
		if (info().stream().anyMatch(fs -> !fs.status().equals(Status.COMMITED))) {
			return "SwitchTo may be done only after commit";
		}
		if (findCommit(name) == null) {
			return String.format("Branch or Commit with name %s doesn't exist", name);
		}
		deleteFiles();
		addFiles(name);
		head = name;
		return String.format("Git switched to the %s ", name);
	}

	private void deleteFiles() {
		try {
			Files.walk(Path.of(CURRENT_DIR)).filter(Files::isRegularFile).forEach(file -> {
				try {
					Files.delete(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addFiles(String name) {
		Commit commit = findCommit(name);
		commit.fileParameters().entrySet().stream().forEach(entry -> {
			Path path = entry.getKey();
			Date dateOfModified = entry.getValue().timeOfFileModified();
			String[] data = entry.getValue().data();
			try {
				Files.createFile(path);
				Files.write(path, Arrays.asList(data));
				Files.setLastModifiedTime(path, FileTime.from(dateOfModified.toInstant()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getHead() {
		return branches.containsKey(head) ? head : null;
	}

	@Override
	public void save() {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(GIT_FILE))) {
			output.writeObject(this);
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public String addIgnoredFileNameExp(String regex) {
		String checker = "checker";
		try {
			checker.matches(regex);
			ignoredFiles.add(regex);
		} catch (PatternSyntaxException e) {
			System.out.println("Incorrect regex, write another one");
		}
		return String.format("Ignored Expression %s was added", regex);
	}
}
