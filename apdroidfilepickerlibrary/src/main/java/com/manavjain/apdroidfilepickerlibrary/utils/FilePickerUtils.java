package com.manavjain.apdroidfilepickerlibrary.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by YourFather on 05-01-2018.
 */

public class FilePickerUtils {

    public static final String RESULT_PATH_KEY = "result_path";

    public static ArrayList<File> getFileListByPath(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files == null) {
            return new ArrayList<>();
        }

        ArrayList<File> result = new ArrayList<>(Arrays.asList(files));
        Collections.sort(result, new FileComparator());
        return result;
    }
}
