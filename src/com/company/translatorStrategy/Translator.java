package com.company.translatorStrategy;

import java.util.List;

public interface Translator {
    boolean isApplicableToRAPID(String instruction);
    boolean isApplicableToVPlus(String instruction);

    void translateRAPID(List<String> instructions, Integer index);
    void translateVPlus(List<String> instructions, Integer index);
}
