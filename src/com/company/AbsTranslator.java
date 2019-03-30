package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbsTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("Abs")&instruction.contains("Abs");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ABS")&instruction.contains("ABS");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\()(.*)(?=\\))");
        Matcher m = p.matcher(instructions.get(index));
        String value = new String();
        while (m.find()) {
            value = m.group();
            break;
        }
        instructions.set(index, "ABS("+value+")");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\()(.*)(?=\\))");
        Matcher m = p.matcher(instructions.get(index));
        String value = new String();
        while (m.find()) {
            value = m.group();
            break;
        }
        instructions.set(index, "Abs("+value+")");
    }
}
