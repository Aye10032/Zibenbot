package com.aye10032.Functions;

/**
 * @author Dazo66
 */
public interface IFunc {


    //bot功能的初始化在这里 传入bot本体
    void setUp();

    //bot功能的运行在这里
    void run(CQMsg CQmsg);


}
