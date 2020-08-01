package com.aye10032.Functions.funcutil;

import com.dazo66.command.Commander;

/**
 * Commander的Func方法 推荐在Func中使用此对象
 * 通过{@link FuncCommanderFactory} 和 {@link com.dazo66.command.CommanderBuilder}
 * 进行构建
 * @author Dazo66
 */
public class FuncCommander extends Commander {
    /**
     * 重写了执行语句 可以直接执行CQMsg对象
     * @param cqMsg
     */
    public void execute(CQMsg cqMsg) {
        super.execute(cqMsg.msg);
    }
}
