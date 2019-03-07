package com.company;

import com.company.input.ProgramReader;
import com.company.input.VPlusReader;
import com.company.translatorStrategy.AccelerationTranslator;
import com.company.translatorStrategy.Translator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static List<Translator> translators = new ArrayList<>();

    public static void main(String[] args) {
        File filePath = new File("C:/Users/lrnts/IdeaProjects/licentatest/src/com/company/program");
        ProgramReader programReader = new VPlusReader();
        List<String> instructions = null;
        instructions = getInstructions(filePath, programReader);

        NestedStatements.solveNesting(instructions);
        lineByLineTranslation(instructions);
    }

    private static List<String> getInstructions(File filePath, ProgramReader programReader) {
        String content = null;
        try {
            content = programReader.readFileToString(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programReader.getInstructions(content);
    }

    private static void lineByLineTranslation(List<String> instructions) {
        initializeTranslatorList();
        for (int i1 = 0; i1 < instructions.size(); i1++) {
            String instruction = instructions.get(i1);
            for (int i = 0; i < translators.size(); i++) {
                Translator translator = translators.get(i);
                if (translator.isApplicableToRAPID(instruction)) {
                    translator.translateRAPID(instructions, i1);
                }
            }
        }
    }

    private static void initializeTranslatorList() {
        translators.add(new AccelerationTranslator());
    }
}
