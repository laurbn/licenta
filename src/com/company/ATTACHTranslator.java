package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATTACHTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return false;
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ATTACH")&&instruction.contains("ATTACH");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {

    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\()(.*)(?=,)");
        String logicalUnit = getLogicalUnit(instructions, index, p);
        if (   logicalUnit.matches(("-?\\d+"))){
            instructions.set(index,"VAR iodev logicalUnit"+logicalUnit);
        }
        else
        {
            instructions.set(index,"VAR iodev "+logicalUnit);
        }
    }

    private String getLogicalUnit(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String object = new String();
        while (m.find()) {
            object = m.group();
            break;
        }
        return object;
    }
}
