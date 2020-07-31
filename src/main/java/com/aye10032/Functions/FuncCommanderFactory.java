package com.aye10032.Functions;

import com.dazo66.commandstream.interfaces.CommanderFactory;

/**
 * @author Dazo66
 */
public class FuncCommanderFactory implements CommanderFactory {
    @Override
    public FuncCommander build() {
        return new FuncCommander();
    }
}
