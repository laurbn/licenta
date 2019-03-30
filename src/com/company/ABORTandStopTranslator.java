package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;

public class ABORTandStopTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("Stop");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ABORT");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        instructions.set(index, "ABORT");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        instructions.set(index, "Stop");
    }
}
