package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return false;
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("DEPART");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {

    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        if (instructions.get(index).contains("DEPART ")) {
            Pattern p = Pattern.compile("(?<=DEPART )(.*)$");
            Matcher m = p.matcher(instructions.get(index));
            String distance = new String();
            while (m.find()) {
                distance = m.group();
                break;
            }
            instructions.set(index, "currentPosition := CPos();");
            instructions.add(index + 1, "MoveL Offs(currentPosition, 0, 0, " + distance + ");");
        } else {
            if (instructions.get(index).contains("DEPARTS ")) {
                Pattern p = Pattern.compile("(?<=DEPARTS )(.*)$");
                Matcher m = p.matcher(instructions.get(index));
                String distance = new String();
                while (m.find()) {
                    distance = m.group();
                    break;
                }
                instructions.set(index, "currentPosition := CPos();");
                instructions.add(index + 1, "MoveJ Offs(currentPosition, 0, 0, " + distance + ");");
            }
        }
    }
}