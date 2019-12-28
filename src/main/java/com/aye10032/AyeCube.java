package com.aye10032;

import java.util.Random;
import java.util.Stack;

public class AyeCube {

    private Stack<String> cubeStack = new Stack<String>();
    private String[] cubarr = new String[]{"F", "B", "U", "D", "R", "L"};
    private String[] statusarr = new String[]{"","","'","'","2"};
    private int step = 20;
    private String cuberandom = "";

/*    public static void main(String[] args) {
        System.out.println(new AyeCube().getCuberandom());
    }*/

    public AyeCube() {
        Random random = new Random();
        int num = random.nextInt(5);
        step += num;
        for (int i = 0; i < step; i++) {
            String thisStep = cubarr[random.nextInt(cubarr.length)] + statusarr[random.nextInt(statusarr.length)];
            cubeStack.push(thisStep);
        }

        String temp = "";
        while (!cubeStack.empty()){
            temp += cubeStack.pop() + " ";
        }

        setCuberandom(temp);
    }

    public void setCuberandom(String cuberandom){
        this.cuberandom = cuberandom;
    }

    public String getCuberandom(){
        return this.cuberandom;
    }

}
