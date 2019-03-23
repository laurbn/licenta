package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;

public class anyTranslator implements Translator {

    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("DEFAULT");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("ANY");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        instructions.set(index, "ANY");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        instructions.set(index,"DEFAULT :");

    }
}
