package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FCLOSEandCloseTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("Close")&instruction.contains("Close");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("FCLOSE")&instruction.contains("FCLOSE");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=Close )(.*)$");
        instructions.set(index, "FCLOSE ("+getLogicalUnitName(instructions, index, p)+")");

    }

    private String getLogicalUnitName(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String object = new String();
        while (m.find()) {
            object = m.group();
            break;
        }
        return object;
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=FCLOSE \\()(.*)(?=\\))");
        if(getLogicalUnitName(instructions, index, p).matches("-?\\d+")) {
            instructions.set(index, "Close (logicalUnit" + getLogicalUnitName(instructions, index, p) + ")");
        }
        else{
            instructions.set(index, "Close (" + getLogicalUnitName(instructions, index, p) + ")");
        }

    }
}
