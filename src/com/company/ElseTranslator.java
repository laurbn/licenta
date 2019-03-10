package com.company;

import com.company.translatorStrategy.Translator;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.company.Main.translators;

public class ElseTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("ELSEIF");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ELSE");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        String temporaryString;
        temporaryString = instructions.get(index);
        temporaryString = temporaryString.replaceAll("ELSEIF", "ELSEseparatorIF");
        List<String> separatedIfInstruction = new ArrayList<>(Arrays.asList(temporaryString.split("separator")));

        instructions.set(index, separatedIfInstruction.get(0));
        instructions.add(index+1,"will place new if here");
        IfTranslator ifTranslator = new IfTranslator();
        instructions.set(index +1, separatedIfInstruction.get(1));
        ifTranslator.translateRAPID(instructions, index + 1);

        Integer jumpOverInstructions = 0;
        while (!instructions.get(index + 2 + jumpOverInstructions).startsWith("ENDIF")) {
            System.out.println(instructions.get(index+2+jumpOverInstructions));
            jumpOverInstructions++;
        }
        instructions.add(index+1+jumpOverInstructions,"ENDIF");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {

    }
}
