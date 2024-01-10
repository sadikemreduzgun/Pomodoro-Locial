package com.sadikemreduzgun.network;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicStorage {

    // pomodoro counts are daily and checked locally so no need to remove
    // just adding enough
    private static Map<String, String> dynamicUserInfoMapper = new HashMap<>();
    // private static Map<String, String> testMap = new HashMap<>();

    public static void addNew(String uName, String pomodoroCount){

        if (dynamicUserInfoMapper.containsKey(uName)){
            dynamicUserInfoMapper.put(uName, pomodoroCount);
        }
    }
    /*public static void addTestMapper(String uName, String pomodoroCount){
        testMap.put(uName, pomodoroCount);
    }*/

    public int getLen(){
        return dynamicUserInfoMapper.keySet().toArray().length;
    }

    // getting keys are enough
    public static Object[] getUserNames(){
        return dynamicUserInfoMapper.keySet().toArray();
    }

    // there won't be any misleading of username and pomodoro count when map and keys are used together
    public static Map<String, String> getDynamicMap(){
        return dynamicUserInfoMapper;
    }

    // next version logics no time
    // when new node joins, asks everyone how many they know. Chooses the one knowing largest number of nodes.
    // wants the largest knowing node to send its info of other nodes. Largest knowing node broadcasts its knowledge.
    public static String getSeperatedData(){
        Object[] uNames = getUserNames();
        int lengthArray = uNames.length;
        Map<String, String> peopleData = getDynamicMap();

        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();

        // built sent data text using map
        for (int i=0; i<lengthArray;i++) {

            if (i!=lengthArray-1){
                stringBuilder.append(uNames[i]);
                stringBuilder.append(":");
                stringBuilder.append(peopleData.get(uNames[i]));
                stringBuilder.append(";");
            }
            else {
                stringBuilder.append(uNames[i]);
                stringBuilder.append(":");
                stringBuilder.append(peopleData.get(uNames[i]));
            }

        }

        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    /*
    public static String getTestSep(){
        Object[] uNames = getUserNames();
        int lengthArray = uNames.length;
        Map<String, String> peopleData = testMap;

        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();

        // built sent data text using map
        for (int i=0; i<lengthArray;i++) {

            if (i!=lengthArray-1){
                stringBuilder.append(uNames[i]);
                stringBuilder.append(":");
                stringBuilder.append(peopleData.get(uNames[i]));
                stringBuilder.append(";");
            }
            else {
                stringBuilder.append(uNames[i]);
                stringBuilder.append(":");
                stringBuilder.append(peopleData.get(uNames[i]));
            }

        }

        System.out.println(stringBuilder);
        return stringBuilder.toString();

    }*/

    // next version logics
    public static void handleComingData(String data){

        String[] toPairSplit = data.split(";");
        System.out.println(data);

        for (int i = 0; i < toPairSplit.length; i++) {

            System.out.println(toPairSplit[i]);
            String[] pairSplit = toPairSplit[i].split(":");
            addNew(pairSplit[0], pairSplit[1]);
            // addTestMapper(pairSplit[0], pairSplit[1]);

        }

    }

    public static void main(String[] args){

        // test it working fine
        /*addNew("a", "b");
        addNew("an", "ba");
        addNew("ansad", "bsaag");
        addNew("aasdagsadas", "bafasvf");
        addNew("ascasd", "bsadasd");

        handleComingData(getSeperatedData());
        // getTestSep();*/

    }
}
