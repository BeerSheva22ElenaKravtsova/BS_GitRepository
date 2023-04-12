package telran.git.project;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

public record Commit(
		CommitMessage commitMessage, Date timeOfCommit, Map<Path, FileParameters> fileParameters, String prevCommitName)
		implements Serializable {
}
