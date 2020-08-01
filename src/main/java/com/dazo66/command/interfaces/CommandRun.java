package com.dazo66.command.interfaces;

/**
 * 当符合分支后的运行器
 * @author Dazo66
 */
@FunctionalInterface
public interface CommandRun {
    void run(String[] command);
}
