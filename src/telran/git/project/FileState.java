package telran.git.project;

import java.nio.file.Path;

//public record FileState(Status status, Path path, String[] data) {

public record FileState(Path path, Status status) {
}
