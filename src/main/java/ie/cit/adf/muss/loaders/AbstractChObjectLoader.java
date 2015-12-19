package ie.cit.adf.muss.loaders;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.utility.FileFinder;

/**
 * ChObject
 */
@EnableAspectJAutoProxy
public abstract class AbstractChObjectLoader {

    private String extension, objectsDirectory;

    /**
     * Abstract loader constructor
     *
     * @param extension
     * @param objectsDirectory
     */
    public AbstractChObjectLoader(String extension, String objectsDirectory) {
        this.extension = extension;
        this.objectsDirectory = objectsDirectory;
    }


    /**
     * Load the paths of the ch object files
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException 
     */
    protected List<String> loadFiles() throws IOException, URISyntaxException {
        try {
            URL u = AbstractChObjectLoader.class.getProtectionDomain().getCodeSource().getLocation();
            JarURLConnection juc = (JarURLConnection) u.openConnection();
            JarFile jf = juc.getJarFile();
            Enumeration<JarEntry> entries = jf.entries();
            List<String> out = new ArrayList<>();
            for (JarEntry je = entries.nextElement(); entries.hasMoreElements(); je = entries.nextElement()) {
                if (je.getName().startsWith("objects/")  &&  je.getName().endsWith("." + this.extension)) {
                    out.add("/" + je.getName());
                }
            }
            return out;
        }
        catch (ClassCastException notJar) {
            try {
                String objectsDirectory = '/' + this.objectsDirectory;
                objectsDirectory = URLDecoder.decode(objectsDirectory, "utf-8");
                return FileFinder
                        .getFileList(objectsDirectory, "*." + this.extension)
                        .stream()
                        .map(path -> path.toFile().getPath())
                        .collect(Collectors.toList());
            } catch (Exception e) {
                e.printStackTrace();
                String objectsDirectory = AbstractChObjectLoader.class.getResource('/' + this.objectsDirectory).toURI().toString().replace("file:/", "");
                objectsDirectory = URLDecoder.decode(objectsDirectory, "utf-8");
                return FileFinder
                        .getFileList(objectsDirectory, "*." + this.extension)
                        .stream()
                        .map(path -> path.toFile().getPath())
                        .collect(Collectors.toList());
            }
        }
    }


    /**
     * Map a file to an object
     *
     * @param path
     * @return
     */
    protected abstract ChObject mapFile(String path);


    /**
     * Load all the objects from the storage
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException 
     */
    public List<ChObject> loadChObjects() throws IOException, URISyntaxException {
        return loadFiles()
                .stream()
                .map(this::mapFile)
                .filter(o -> o != null)
                .collect(Collectors.toList());
    }

}
