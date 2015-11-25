package ie.cit.adf.muss.utility;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Utility class to recursively find a list of files within a given directory
 * for a given pattern.
 *  
 * @author Larkin.Cunningham
 *
 */
public class FileFinder {

	public static class Finder extends SimpleFileVisitor<Path> {
		private final PathMatcher matcher;
		private List<Path> fileList = new ArrayList<Path>();
		
		Finder(String pattern) {
	        matcher = FileSystems.getDefault()
	                .getPathMatcher("glob:" + pattern);
	    }
	
		public List<Path> getFileList() {
			return fileList;
		}
		
	    // Compares the glob pattern against
	    // the file or directory name.
	    void find(Path file) {
	        Path name = file.getFileName();
	        if (name != null && matcher.matches(name)) {
	        	fileList.add(file);
	        }
	    }
	
	    // Invoke the pattern matching
	    // method on each file.
	    public FileVisitResult visitFile(Path file,
	                                     BasicFileAttributes attrs) {
	        find(file);
	        return CONTINUE;
	    }
	
	    // Invoke the pattern matching
	    // method on each directory.
	    public FileVisitResult preVisitDirectory(Path dir,
	                                             BasicFileAttributes attrs) {
	        find(dir);
	        return CONTINUE;
	    }
	
	    public FileVisitResult visitFileFailed(Path file,
	                                           IOException exc) {
	        System.err.println(exc);
	        return CONTINUE;
	    }

	}
	
	public static List<Path> getFileList(String directory, String pattern)
			throws IOException {

		Path startingDir = Paths.get(directory);
		
		Finder finder = new Finder(pattern);
		Files.walkFileTree(startingDir, finder);
		
		return finder.getFileList();
	}
	
}