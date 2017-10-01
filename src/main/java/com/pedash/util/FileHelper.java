package com.pedash.util;

import com.pedash.entities.RemoteDoc;

import java.io.*;

/**
 * Created by Yuliya Pedash on 07.06.2017.
 */
public class FileHelper {
    private static final String TXT_EXT = "txt";

    public static RemoteDoc getFileObjectFromFile(File file) {
//        String filePath = file.getAbsolutePath();
//        FileType fileType;
//        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
//        if (file.isDirectory()) {
//            fileType = FileType.Directory;
//        } else {
//            String fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
//            fileType = fileExt.equals(TXT_EXT) ? FileType.Txt : FileType.Other;
//        }
//        return new RemoteDoc(filePath.substring(filePath.indexOf("documents")), fileName, fileType);
        return null;
    }


    public static RemoteDoc[] getAllFilesFromDir(File directory) {
        File[] files = directory.listFiles();
        RemoteDoc[] remoteDocs = new RemoteDoc[files.length];
        Integer i = 0;
        for (File file : files) {
            remoteDocs[i] = getFileObjectFromFile(file);
            i++;
        }
        return remoteDocs;
    }

    public static void editDoc(File file, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static String getContentOfTxtDocument(String docAddress) throws IOException {
        InputStream inputStream = new FileInputStream(docAddress);
        StringBuilder stringBuilder = new StringBuilder();
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        int c;
        while ((c = reader.read()) != -1) {
            stringBuilder.append((char) c);
        }
        return stringBuilder.toString();
    }
    private static String makeBackLink(String link ){
        return link.substring(0, link.lastIndexOf(File.separator));
    }
}
