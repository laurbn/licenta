package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FOPENandOpenTranslator implements Translator {
    int[] defaultDeviceNumbers = new int[31];

    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("Open") & instruction.contains("Open");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("FOPEN") & instruction.contains("FOPEN");

    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        //WRITE INSTRUCTIONs for BOTH disk and serial logical units
        //1. DISK checked, working
        if (instructions.get(index).contains("\\Write")) {
            if (instructions.get(index).contains("\\File:=")) {
                Pattern p1 = Pattern.compile("(?<=Open \")(.*)(?=:\" )");
                Pattern p2 = Pattern.compile("(?<=File:= \")(.*)(?=\",)");
                Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                String logicalUnitName = getObjectName(instructions, index, p4);
                int logicalUnit = assignDiskLogicalUnit();
                String objectName1 = getObjectName(instructions, index, p1);
                String objectName2 = getObjectName(instructions, index, p2);
                instructions.set(index, "FOPENW (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
                addWriteFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName1, objectName2);
            }
            //2.FILE without \File checked, working
            else {
                Pattern p3 = Pattern.compile("(?<=Open \")(.*)(?=\",)");
                Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                String logicalUnitName = getObjectName(instructions, index, p4);
                if (!getObjectName(instructions, index, p3).contains("com2:")||!getObjectName(instructions, index, p3).contains("com3:")||!getObjectName(instructions, index, p3).contains("com1:")) {
                    int logicalUnit = assignDiskLogicalUnit();
                    String objectName3 = getObjectName(instructions, index, p3);
                    instructions.set(index, "FOPENW (" + logicalUnit + ") \"" + objectName3+"\"");
                    addWriteFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName3, null);
                    //3. SERIAL checked, working
                } else {
                    int logicalUnit = assignSerialLogicalUnit();
                    String objectName3 = getObjectName(instructions, index, p3);
                    instructions.set(index, "END");
                    addWriteSerialFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName3, null);


                }
            }
        } else {
            //READ INSTRUCTIONs for BOTH disk and serial logical units

            if (instructions.get(index).contains("\\Read")) {
//contains \File, checked, working
                if (instructions.get(index).contains("\\File:=")) {
                    Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                    String logicalUnitName = getObjectName(instructions, index, p4);
                    Pattern p1 = Pattern.compile("(?<=Open \")(.*)(?=:\" )");
                    Pattern p2 = Pattern.compile("(?<=File:= \")(.*)(?=\",)");
                    int logicalUnit = assignDiskLogicalUnit();
                    String objectName1 = getObjectName(instructions, index, p1);
                    String objectName2 = getObjectName(instructions, index, p2);
                    instructions.set(index, "END");
                    addReadFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName1, objectName2);
                } else {
                    //checcked working
                    Pattern p3 = Pattern.compile("(?<=Open \")(.*)(?=\",)");
                    Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                    String logicalUnitName = getObjectName(instructions, index, p4);
                    if (!getObjectName(instructions, index, p3).contains("com2:")||!getObjectName(instructions, index, p3).contains("com3:")||!getObjectName(instructions, index, p3).contains("com1:")) {
                        int logicalUnit = assignDiskLogicalUnit();
                        String objectName3 = getObjectName(instructions, index, p3);
                        instructions.set(index, "END");
                        addReadFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName3, null);
                        //3. SERIAL checked working
                    } else {
                        int logicalUnit = assignSerialLogicalUnit();
                        String objectName3 = getObjectName(instructions, index, p3);
                        instructions.set(index, "END");
                        addReadSerialFileVerification(instructions, index, logicalUnit, logicalUnitName, objectName3, null);
                        instructions.add(index, "FOPENR (" + logicalUnit + ") \"" + objectName3 + "\"");
                    }
                }
            } else {
                //APPEND
                //contains \File
                if (instructions.get(index).contains("\\Append")||instructions.get(index).contains("\\Bin")||!instructions.get(index).contains("(\\Write)|(\\Append)|(\\Read)")) {
                    if (instructions.get(index).contains("\\File:=")) {
                        Pattern p1 = Pattern.compile("(?<=Open \")(.*)(?=:\" )");
                        Pattern p2 = Pattern.compile("(?<=File:= \")(.*)(?=\",)");
                        Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                        String logicalUnitName = getObjectName(instructions, index, p4);
                        int logicalUnit = assignDiskLogicalUnit();
                        String objectName1 = getObjectName(instructions, index, p1);
                        String objectName2 = getObjectName(instructions, index, p2);
                        instructions.set(index, "FOPENA (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
                        instructions.add(index, logicalUnitName + " = " + logicalUnit);

                    } else {
                        //doesn't contain \File -> long path
                        Pattern p3 = Pattern.compile("(?<=Open \")(.*)(?=\",)");
                        Pattern p4 = Pattern.compile("(?<=\", )(.*)(?= )");
                        String logicalUnitName = getObjectName(instructions, index, p4);
                        if (!getObjectName(instructions, index, p3).contains("com2:")||!getObjectName(instructions, index, p3).contains("com3:")||!getObjectName(instructions, index, p3).contains("com1:")) {
                            int logicalUnit = assignDiskLogicalUnit();
                            String objectName3 = getObjectName(instructions, index, p3);
                            instructions.set(index, "FOPENA (" + logicalUnitName + ") \"" + objectName3 + "\"");
                            instructions.add(index, "ATTACH (" + logicalUnit + ", 4)");
                            instructions.add(index, logicalUnitName + " = " + logicalUnit);

                            //3. SERIAL
                        } else {
                            int logicalUnit = assignSerialLogicalUnit();
                            String objectName3 = getObjectName(instructions, index, p3);
                            instructions.set(index, "FOPENA (" + logicalUnitName + ") \"" + objectName3 + "\"");
                            instructions.add(index, "ATTACH (" + logicalUnit + ", 4)");
                            instructions.add(index, logicalUnitName + " = " + logicalUnit);

                        }
                    }

                }

            }
        }
    }




    @Override
    public void translateVPlus(List<String> instructions, Integer index) {

        if (instructions.get(index).startsWith("FOPENW")) {
            boolean applicable=true;
            Pattern p = Pattern.compile("(?<=\\()(.*)(?=\\))");
            String logicalUnit = getLogicalUnit(instructions, index, p);
            if (   logicalUnit.matches(("-?\\d+"))) {
                if ((Integer.parseInt(logicalUnit) >= 5 && Integer.parseInt(logicalUnit) <= 8) || (Integer.parseInt(logicalUnit) >= 17 && Integer.parseInt(logicalUnit) <= 19)) {
                    logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);
                    applicable=false;

                }
                if(applicable) {
                    if ((Integer.parseInt(logicalUnit) >= 10 && Integer.parseInt(logicalUnit) <= 15)) {
                        logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);

                    }
                }
            }

            Pattern p2 = Pattern.compile("(?<=\")(.*)(?=\")");
            String fileName = getFileName(instructions, index, p2);
            instructions.set(index, "Open \""+fileName+ "\", "+logicalUnit+" \\Write");
        }
        else{
            if(instructions.get(index).startsWith("FOPENR")){
                boolean applicable=true;
                Pattern p = Pattern.compile("(?<=\\()(.*)(?=\\))");
                String logicalUnit = getLogicalUnit(instructions, index, p);
                if (   logicalUnit.matches(("-?\\d+"))) {
                    if ((Integer.parseInt(logicalUnit) >= 5 && Integer.parseInt(logicalUnit) <= 8) || (Integer.parseInt(logicalUnit) >= 17 && Integer.parseInt(logicalUnit) <= 19)) {
                        logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);
                        applicable=false;

                    }
                    if(applicable) {
                        if ((Integer.parseInt(logicalUnit) >= 10 && Integer.parseInt(logicalUnit) <= 15)) {
                            logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);

                        }
                    }
                }

                Pattern p2 = Pattern.compile("(?<=\")(.*)(?=\")");
                String fileName = getFileName(instructions, index, p2);
                instructions.set(index, "Open \""+fileName+ "\", "+logicalUnit+" \\Read");
            }
            else{
                if(instructions.get(index).startsWith("FOPENA")){
                    boolean applicable=true;
                    Pattern p = Pattern.compile("(?<=\\()(.*)(?=\\))");
                    String logicalUnit = getLogicalUnit(instructions, index, p);
                    if (   logicalUnit.matches(("-?\\d+"))) {
                        if ((Integer.parseInt(logicalUnit) >= 5 && Integer.parseInt(logicalUnit) <= 8) || (Integer.parseInt(logicalUnit) >= 17 && Integer.parseInt(logicalUnit) <= 19)) {
                            logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);
                            applicable=false;

                        }
                        if(applicable) {
                            if ((Integer.parseInt(logicalUnit) >= 10 && Integer.parseInt(logicalUnit) <= 15)) {
                                logicalUnit = "logicalUnit" + Integer.parseInt(logicalUnit);

                            }
                        }
                    }

                    Pattern p2 = Pattern.compile("(?<=\")(.*)(?=\")");
                    String fileName = getFileName(instructions, index, p2);
                    instructions.set(index, "Open \""+fileName+ "\", "+logicalUnit+" \\Append");
                }
            }
        }
    }
    //FOPEND

    private String getFileName(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String object = new String();
        while (m.find()) {
            object = m.group();
            break;
        }
        return object;
    }

    private String getLogicalUnit(List<String> instructions, Integer index, Pattern pattern) {
        Matcher m = pattern.matcher(instructions.get(index));
        String object = new String();
        while (m.find()) {
            object=(m.group());
            break;
        }
        return object;
    }

    private String getObjectName(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String object = new String();
        while (m.find()) {
            object = m.group();
            break;
        }
        return object;
    }

    private int assignDiskLogicalUnit(){
        int deviceNumber = 0;
        boolean checkDeviceAvailability = false;
        for (int i = 5; i <= 8; i++) {
            if (defaultDeviceNumbers[i] == 0) {
                deviceNumber = i;
                checkDeviceAvailability = true;
                defaultDeviceNumbers[i]++;
                break;
            }
        }
        if(!checkDeviceAvailability) {
            for (int i = 17; i <= 19; i++) {
                if (defaultDeviceNumbers[i] == 0) {
                    deviceNumber = i;
                    checkDeviceAvailability = true;
                    defaultDeviceNumbers[i]++;
                    break;
                }
            }
        }
        if (!checkDeviceAvailability) {
            System.out.println("V+ can not work with more than [insert max number of devices] at the same time");
        }
        return deviceNumber;
    }
    private int assignSerialLogicalUnit(){
        int deviceNumber=0;
        boolean checkDeviceAvailability = false;
        for(int i=10; i<15; i++){
            if(defaultDeviceNumbers[i]==0){
                deviceNumber=i;
                checkDeviceAvailability=true;
                defaultDeviceNumbers[i]++;
                break;

            }
        }
        if (!checkDeviceAvailability) {
            System.out.println("V+ can not work with more than [insert max number of devices] at the same time");
        }
        return deviceNumber;
    }
    private void addWriteFileVerification(List<String> instructions, Integer index, Integer logicalUnit, String logicalUnitName, String objectName1, String objectName2){
        instructions.add(index, "END");
        if(objectName2!=null) {

            instructions.add(index, "   FDELETE (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
            instructions.add(index, "   FCLOSE (" + logicalUnitName + ")");
            instructions.add(index, "IF IOSTAT(" + logicalUnitName + ") < 0 THEN ");
            instructions.add(index, "FOPENW (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
        }
        else {
        //checked,  working
            instructions.add(index, "   FDELETE (" + logicalUnitName + ") \"" + objectName1 + "\"");
            instructions.add(index, "   FCLOSE (" + logicalUnitName + ")");
            instructions.add(index, "IF IOSTAT(" + logicalUnitName + ") < 0 THEN ");
            instructions.add(index, "FOPENW (" + logicalUnitName + ") \"" + objectName1 +"\"");
        }
        instructions.add(index, "   ATTACH ("+logicalUnitName+", 4)");
        instructions.add(index, logicalUnitName + " = " + logicalUnit);
    }
    private void addReadFileVerification(List<String> instructions, Integer index, Integer logicalUnit, String logicalUnitName, String objectName1, String objectName2){
        if(objectName2!=null) {

            instructions.add(index, "   HALT");
            instructions.add(index, "   TYPE \"Error opening file or serial\"");
            instructions.add(index, "IF IOSTAT(" + logicalUnitName + ") < 0 THEN ");
            instructions.add(index, "FOPENR (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
            instructions.add(index, "ATTACH (" + logicalUnitName + ", 4)");
        }
        else{

            instructions.add(index, "   HALT");
            instructions.add(index, "   TYPE \"Error opening file or serial\"");
            instructions.add(index, "IF IOSTAT(" + logicalUnitName + ") < 0 THEN ");
            instructions.add(index, "FOPENR (" + logicalUnitName + ") \"" + objectName1 + "\"");
            instructions.add(index, "ATTACH (" + logicalUnitName + ", 4)");
        }
        instructions.add(index, logicalUnitName + " = " + logicalUnit);
    }
    private void addWriteSerialFileVerification(List<String> instructions, Integer index, Integer logicalUnit,String logicalUnitName, String objectName1, String objectName2){

        if(objectName2!=null) {

          instructions.add(index, "   HALT");
         instructions.add(index, "   TYPE \"Error opening serial\"");
        instructions.add(index, "IF IOSTAT("+logicalUnitName+") < 0 THEN ");
        instructions.add(index, "FOPENW (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
            instructions.add(index, "ATTACH ("+logicalUnitName+", 4)");

    }
        else {

            instructions.add(index, "   HALT");
            instructions.add(index, "   TYPE \"Error opening serial\"");
            instructions.add(index, "IF IOSTAT("+logicalUnitName+") < 0 THEN ");
            instructions.add(index, "FOPENW (" + logicalUnitName + ") \"" + objectName1 +"\"");
            instructions.add(index, "ATTACH ("+logicalUnitName+", 4)");
        }

        instructions.add(index, logicalUnitName + " = " + logicalUnit);
    }

    private void addReadSerialFileVerification(List<String> instructions, Integer index, Integer logicalUnit, String logicalUnitName, String objectName1, String objectName2){

        if(objectName2!=null) {

            instructions.add(index, "   HALT");
            instructions.add(index, "   TYPE \"Error opening serial\"");
            instructions.add(index, "IF IOSTAT("+logicalUnitName+") < 0 THEN ");
            instructions.add(index, "FOPENR (" + logicalUnitName + ") \"" + objectName1 + ":/" + objectName2 + "\"");
            instructions.add(index, "ATTACH ("+logicalUnitName+", 4)");

        }
        else {

            instructions.add(index, "   HALT");
            instructions.add(index, "   TYPE \"Error opening serial\"");
            instructions.add(index, "IF IOSTAT("+logicalUnitName+") < 0 THEN ");
            System.out.println(objectName1);
            instructions.add(index, "FOPENR (" + logicalUnitName + ") \"" + objectName1 +"\"");
            instructions.add(index, "ATTACH ("+logicalUnitName+", 4)");
        }
        instructions.add(index, logicalUnitName + " = " + logicalUnit);

    }
}




