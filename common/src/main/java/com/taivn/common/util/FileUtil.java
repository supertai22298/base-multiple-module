/**
 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.util;

import com.taivn.common.constant.Constants;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Instantiates a new File Util.
 */
@NoArgsConstructor
public final class FileUtil {
    public static byte[] downloadFileFromServer(String path, String name) throws IOException {
        String dirFolder = Paths.get(Constants.RESOURCE_SERVER, path + File.separator + name).toString();
        File fileDir = new File(dirFolder);

        byte[] data = FileUtils.readFileToByteArray(fileDir);
        return data;
    }

    public static String uploadFileToServer(byte[] uploadFile, String path, String name) throws FileNotFoundException {
        if (uploadFile == null) {
            throw new FileNotFoundException("Failed to store file");
        }

        try {
            String dirFolder = Paths.get(Constants.RESOURCE_SERVER, path).toString();
            File fileDir = new File(dirFolder);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            Path pathFile = Paths.get(dirFolder, name);
            Files.write(pathFile, uploadFile);
            return dirFolder + File.separator + name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException("Failed to store file");
        }
    }

    public static boolean deleteFileOnServer(String path, String name) {
        String dirFolder = Paths.get(Constants.RESOURCE_SERVER, path + File.separator + name).toString();
        File fileDir = new File(dirFolder);

        fileDir.delete();
        return true;
    }

    /**
     * Write Files
     */

    public static Path fileOutAllPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_All).toURI();
        return Paths.get(uri);
    }

    public static Path fileOutBeanPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_BEAN).toURI();
        return Paths.get(uri);
    }

    public static Path fileOutOnePath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_ONE).toURI();
        return Paths.get(uri);
    }

    /**
     * Read Files
     */

    public static Path twoColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.TWO_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path fourColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.FOUR_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path namedColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.NAMED_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    /**
     * Simple File Reader
     */

    public static String readFile(Path path) {
        String response = "";
        try {
            FileReader fr = new FileReader(path.toString());
            BufferedReader br = new BufferedReader(fr);
            String strLine;
            StringBuffer sb = new StringBuffer();
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
            }
            response = sb.toString();
            fr.close();
            br.close();
        } catch (Exception ex) {
            FileUtil.err(ex);
        }
        return response;
    }

    public static void printLine(String message) {
        System.out.println(message);
    }

    public static void err(Exception ex) {
        System.out.println(Constants.GENERIC_EXCEPTION + " " + ex);
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String getNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
