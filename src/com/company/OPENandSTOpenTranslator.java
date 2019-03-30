package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OPENandSTOpenTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("STOpen");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("OPEN");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        if(instructions.get(index).contains("\\Conc")) {
            instructions.set(index, "OPEN");}
            else
            {
                instructions.set(index, "OPENI");
            }
        }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        if (instructions.get(index).contains("OPENI")){
            instructions.set(index, "STOpen mechanicalUnit;");
            instructions.add(index, "mechanicalUnit := GetMecUnitName(T_ROB1)");
            instructions.add(index, "VAR string mechanicalUnit;");

        }
        else {
            instructions.set(index, "STOpen mechanicalUnit \\Conc;");
            instructions.add(index, "mechanicalUnit := GetMecUnitName(T_ROB1");
            instructions.add(index, "VAR string mechanicalUnit;");
        }

    }
}
