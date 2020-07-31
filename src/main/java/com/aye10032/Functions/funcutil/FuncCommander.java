package com.aye10032.Functions.funcutil;

import com.dazo66.commandstream.Commander;

/**
 * @author Dazo66
 */
public class FuncCommander extends Commander {
    public void execute(CQMsg cqMsg) {
        super.execute(cqMsg.msg);
    }
}
