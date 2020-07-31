package com.dazo66.commandstream.interfaces;

import com.aye10032.Functions.FuncCommander;

/**
 * @author Dazo66
 */
@FunctionalInterface
public interface CommanderFactory {
    FuncCommander build();

}
