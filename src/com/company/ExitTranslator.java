package com.company;
//[RAPID] ExitCycle; [V+] EXIT <num>
import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExitTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("ExitCycle");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("EXIT");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        instructions.set(index, "EXIT");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        int cyclesExitted;
        cyclesExitted = getcyclesExitted(instructions, index);
        System.out.println(cyclesExitted);
        if (cyclesExitted==1){
            instructions.set(index, "ExitCycle;");
        }
        else{
            instructions.set(index, "ExitCycle;");
            index+=1;
            while(cyclesExitted!=1){
                if(instructions.get(index).startsWith("END")){
                    instructions.add(index+1, "ExitCycle;");
                }
                index+=1;
                cyclesExitted--;
            }
        }
    }

    private int getcyclesExitted(List<String> instructions, Integer index){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(instructions.get(index));
        int getcyclesExitted = 0;
        while (m.find()) {
            getcyclesExitted = Integer.parseInt(m.group());
            break;
        }
        return getcyclesExitted;
    }
}
