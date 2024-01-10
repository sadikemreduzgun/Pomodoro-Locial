package com.sadikemreduzgun.tests;
import com.sadikemreduzgun.timer.PomodoroTimer;

import java.util.*;

public class RunTimeListTest {

    // like dictionary in Python
    public static Map<String, Integer> PomodoroMapper = new HashMap<>();

    public static void main(String[] args){
        PomodoroMapper.put("asd", 12);
        PomodoroMapper.put("asd2", 12);
        PomodoroMapper.put("wasdas", 12);
        PomodoroMapper.put("a", 12);

        System.out.println(PomodoroMapper.keySet());
    }

}
