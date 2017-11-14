package it.fanciullini.crypting.utils;

import java.io.File;
import java.nio.file.Paths;

public class PathConverter
{

    public static String convertPath(String originalPath, String original, String substitute)
    {
        File tmp = new File(originalPath);
        String path = tmp.getParent();
        String filename = tmp.getName();
        //String filename = tmp.getName().replace(original, substitute);
        if (original.equalsIgnoreCase(Costanti.dot)) {
            int index = filename.lastIndexOf(original);
            if (index >= 0) {
                filename = new StringBuilder(filename).replace(index, index + 1, substitute).toString();
            }
        }else {
            filename = filename.replace(original, substitute);
        }
        return Paths.get(path, filename).toString();
    }
}
