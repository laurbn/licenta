package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApproTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return false;
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("APPRO");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {

    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        if(instructions.get(index).contains("APPRO ")) {
            Pattern p = Pattern.compile("(?<=APPRO )(.*)(?=,)");
            Matcher m = p.matcher(instructions.get(index));
            String place = new String();
            while (m.find()) {
                place = m.group();
                break;
            }

            Pattern p2 = Pattern.compile("(?<=,)(.*)$");
            Matcher m2 = p2.matcher(instructions.get(index));
            String offset = new String();
            while (m2.find()) {
                offset = m2.group();
                break;
            }
            instructions.set(index, "MoveL Offs(" + place + ", 0, 0, " + offset + ");");
        }
        else {
            if(instructions.get(index).contains("APPROS ")){
                Pattern p = Pattern.compile("(?<=APPROS )(.*)(?=,)");
                Matcher m = p.matcher(instructions.get(index));
                String place = new String();
                while (m.find()) {
                    place = m.group();
                    break;
                }

                Pattern p2 = Pattern.compile("(?<=,)(.*)$");
                Matcher m2 = p2.matcher(instructions.get(index));
                String offset = new String();
                while (m2.find()) {
                    offset = m2.group();
                    break;
                }
                instructions.set(index, "MoveJ Offs(" + place + ", 0, 0, " + offset + ");");
            }
        }
    }
}
