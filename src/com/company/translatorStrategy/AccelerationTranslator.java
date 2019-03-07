package com.company.translatorStrategy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccelerationTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("AccSet");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ACCEL");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        int accelerationValue = getAccelerationValue(instructions, index);
        instructions.set(index, "ACCEL " + accelerationValue + ", " + accelerationValue);
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        int accelerationValue = getAccelerationValue(instructions, index);
        instructions.set(index, "AccSet " + accelerationValue + ", " + "100");
    }

    private int getAccelerationValue(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(instructions.get(index));
        int accelerationValue = 0;
        while (m.find()) {
            accelerationValue = Integer.parseInt(m.group());
            break;
        }
        return accelerationValue;
    }
}
