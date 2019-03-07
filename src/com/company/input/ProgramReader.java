package com.company.input;

import java.io.File;
import java.util.List;

public interface ProgramReader {
    String readFileToString(File filePath) throws Exception;
    List<String> getInstructions(String content);
}
