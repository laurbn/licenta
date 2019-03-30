package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;

public class CLOSEandSTCloseTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("STClose");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("CLOSE");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        if(instructions.get(index).contains("\\Conc")) {
            instructions.set(index, "CLOSE");}
        else
        {
            instructions.set(index, "CLOSEI");
        }
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        if (instructions.get(index).contains("CLOSEI")){
            instructions.set(index, "STClose mechanicalUnit;");
            instructions.add(index, "mechanicalUnit := GetMecUnitName(T_ROB1)");
            instructions.add(index, "VAR string mechanicalUnit;");

        }
        else {
            instructions.set(index, "STClose mechanicalUnit \\Conc;");
            instructions.add(index, "mechanicalUnit := GetMecUnitName(T_ROB1");
            instructions.add(index, "VAR string mechanicalUnit;");
        }
    }
}
