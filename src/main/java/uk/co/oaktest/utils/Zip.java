package uk.co.oaktest.utils;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;
import org.apache.log4j.Logger;

public class Zip {

    final static Logger logger = Logger.getLogger(Zip.class);

    String sourcePath;
    String destinationPath;

    public Zip(String sourcePath, String destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    public Boolean unzip() {
        try {
            ZipFile zipFile = new ZipFile(sourcePath);
            zipFile.extractAll(destinationPath);
        } catch (ZipException e) {
            logger.error("Unzip error: " + e.getMessage());
            return false;
        }
        return true;
    }
}
