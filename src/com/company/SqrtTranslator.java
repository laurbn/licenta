package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqrtTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("Sqrt");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("SQRT")||instruction.contains("SQR");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        instructions.set(index, "SQRT("+getSqrtValue(instructions,index)+")");


    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        instructions.set(index, "Sqrt("+getSqrtValue(instructions,index)+")");
    }
    private int getSqrtValue(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(instructions.get(index));
        int sqrtValue = 0;
        while (m.find()) {
            sqrtValue = Integer.parseInt(m.group());
            break;
        }
        return sqrtValue;
    }
}
