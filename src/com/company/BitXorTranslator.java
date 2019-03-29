package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitXorTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.contains("BitXor");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.contains(" BXOR ");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\()(.*)(?=,)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue1 = new String();
        while (m.find()) {
            testValue1 = m.group();
            break;
        }
        Pattern p2 = Pattern.compile("(?<=, )(.*)(?=\\))");
        Matcher m2 = p2.matcher(instructions.get(index));
        String testValue2 = new String();
        while (m2.find()) {
            testValue2 = m2.group();
            break;
        }
        System.out.println(testValue1);
        System.out.println(testValue2);
        instructions.set(index, testValue1 + " BXOR " + testValue2);

    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("\\w+(?=\\s+BXOR)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue1 = new String();
        while (m.find()) {
            testValue1 = m.group();
            break;
        }
        Pattern p2 = Pattern.compile("(?<=\\bBXOR\\s)(\\w+)$");
        Matcher m2 = p2.matcher(instructions.get(index));
        String testValue2 = new String();
        while (m2.find()) {
            testValue2 = m2.group();
            break;
        }
        System.out.println(testValue1);
        System.out.println(testValue2);
        instructions.set(index, "BitXOr("+testValue1+", "+testValue2+")");


    }
}
