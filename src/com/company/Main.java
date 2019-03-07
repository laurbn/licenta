package com.company;

import com.company.input.ProgramReader;
import com.company.input.VPlusReader;

import java.io.File;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        File filePath = new File("C:/Users/lrnts/IdeaProjects/licentatest/src/com/company/program");
        ProgramReader programReader = new VPlusReader();
        try {
            String content = programReader.readFileToString(filePath);
            List<String> instructions = programReader.getInstructions(content);
            System.out.println(instructions.get(1));
            NestedStatements.solveNesting(instructions);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
