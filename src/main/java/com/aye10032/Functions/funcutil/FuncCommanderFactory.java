package com.aye10032.Functions.funcutil;

import com.dazo66.command.interfaces.CommanderFactory;

/**
 * @author Dazo66
 */
public class FuncCommanderFactory implements CommanderFactory {
    @Override
    public FuncCommander build() {
        return new FuncCommander();
    }
}
