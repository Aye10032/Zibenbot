package com.dazo66.command.interfaces;

import com.aye10032.Functions.funcutil.FuncCommander;

/**
 * Commander 的工厂类
 * 用于创建一个Commander
 * @author Dazo66
 */
@FunctionalInterface
public interface CommanderFactory {
    FuncCommander build();

}
