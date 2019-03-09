package com.company.translatorStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.contains("FOR")&instruction.contains("FROM");

    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.contains("FOR")&instruction.contains("=");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        List<Integer> iterationRange = getIndexRange(instructions, index);
        Pattern p = Pattern.compile("(?<=(FOR ))(.+?)(?=( FROM))");
        String iteration = getIndexValue(instructions, index, p);
        instructions.set(index, "FOR " + iteration + " = " + iterationRange.get(0) + " TO " + iterationRange.get(1));
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        List<Integer> iterationRange = getIndexRange(instructions, index);
        Pattern p = Pattern.compile("(?<=(FOR ))(.+?)(?=( =))");
        String iteration = getIndexValue(instructions, index, p);
        instructions.set(index, "FOR " + iteration + " FROM " + iterationRange.get(0) + " TO " + iterationRange.get(1) + " DO");
    }


    private List<Integer> getIndexRange(List<String> instructions, Integer index) { //shift f6
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(instructions.get(index));
        List<Integer> iterationValues = new ArrayList<>();
        while (m.find()) {
            iterationValues.add(Integer.parseInt(m.group()));

        }
        return iterationValues;
    }

    private String getIndexValue(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String iterator = new String();
        while (m.find()) {
            iterator = m.group();
            break;
        }
        return iterator;
    }
}




