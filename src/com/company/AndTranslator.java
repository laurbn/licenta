package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndTranslator implements Translator {
    public boolean redundancyEliminator=false;
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return false;
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.contains(" AND ");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {

    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("\\w+(?=\\s+AND)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue1 = new String();
        while (m.find()) {
            testValue1 = m.group();
            break;
        }
        System.out.println(testValue1);
        Pattern p2 = Pattern.compile("(?<=\\bAND\\s)(\\w+)$");
        Matcher m2 = p2.matcher(instructions.get(index));
        String testValue2 = new String();
        while (m2.find()) {
            testValue2 = m2.group();
            break;
        }
        System.out.println(testValue2);
        instructions.set(index, "boolValue");
        instructions.add(index,"  boolValue := FALSE;");
        instructions.add(index,"ELSE;");
        instructions.add(index, "   boolValue := TRUE;");
        instructions.add(index, "IF BitCheck(value, 0) = TRUE THEN");
        instructions.add(index, "value := BitAnd("+testValue1+", "+testValue2+");");
        if (hasBeenCalled()==false) { //come back and add OrTranslator redundancyEliminator
            instructions.add(index, "VAR bool boolValue");
            instructions.add(index, "VAR byte value;");
        }
        redundancyEliminator=true;
    }
    public boolean hasBeenCalled(){return redundancyEliminator;}
}
