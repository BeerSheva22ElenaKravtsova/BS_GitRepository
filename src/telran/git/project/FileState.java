package telran.git.project;

import java.nio.file.Path;

public record FileState(Path path, Status status) {
}
