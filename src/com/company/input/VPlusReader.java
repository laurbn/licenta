package com.company.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class VPlusReader implements ProgramReader {
    @Override
    public String readFileToString(File filePath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String st;
        StringBuilder content = new StringBuilder(new String());

        while ((st = br.readLine()) != null) {
            content.append(st);
            content.append(System.getProperty("line.separator"));
        }
        return content.toString();
    }

    @Override
    public List<String> getInstructions(String content) {
        List<String> instructions = new ArrayList<>();
        String[] tokens = content.split(System.getProperty("line.separator"));
        for(String token: tokens){
            instructions.add(token);
        }
        return instructions;
    }
}
