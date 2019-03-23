package com.company;

import com.company.input.ProgramReader;
import com.company.input.RapidReader;
import com.company.input.VPlusReader;
import com.company.translatorStrategy.AccelerationTranslator;
import com.company.translatorStrategy.ForTranslator;
import com.company.translatorStrategy.Translator;
import com.company.translatorStrategy.WhileTranslator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static List<Translator> translators = new ArrayList<>();

    public static void main(String[] args) {
        File filePath = new File("C:/Users/lrnts/IdeaProjects/licentatest/src/com/company/program");

        /*Scanner programmingLanguage = new Scanner(System.in);
        System.out.print("Insert 'V' for VPLus or 'R' for RAPID");
        String programmingChoice = programmingLanguage.next();*/
        String programmingChoice = "V";
        ProgramReader programReader;
        List<String> instructions = null;
        switch (programmingChoice) {
            case "V":
                programReader = new VPlusReader();
                instructions = getInstructions(filePath, programReader);

                NestedStatements.solveNesting(instructions);
                lineByLineTranslation(instructions, programmingChoice);
                break;
            case "R":
                programReader = new VPlusReader();
                instructions = getInstructions(filePath, programReader);

                //NestedStatements.solveSimplifiedNesting(instructions);
                lineByLineTranslation(instructions, programmingChoice);
                break;
            default:
                System.out.println("Looking forward to the Weekend");
        }
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

    private static void lineByLineTranslation(List<String> instructions, String programmingChoice) {
        initializeTranslatorList();
        int i1=0;
        while(i1<instructions.size()) {
            String instruction = instructions.get(i1);
            for (int i = 0; i < translators.size(); i++) {
                Translator translator = translators.get(i);
                if (translator.isApplicableToRAPID(instruction)&programmingChoice.contains("R")) {
                    translator.translateRAPID(instructions, i1);
                }
                else
                    if(translator.isApplicableToVPlus(instruction)&programmingChoice.contains("V")){
                        translator.translateVPlus(instructions, i1);
                    }
            }
            i1++;
        }
        System.out.println(instructions);
    }

    private static void initializeTranslatorList() {
        translators.add(new AccelerationTranslator());
        translators.add(new ForTranslator());
        translators.add(new WhileTranslator());
        translators.add(new IfTranslator());
        translators.add(new ElseTranslator());
        translators.add(new CaseTranslator());
        translators.add(new ValueTranslator());
        translators.add(new anyTranslator());
        translators.add(new ExitTranslator());
        translators.add(new BitAndTranslator());
    }
}
