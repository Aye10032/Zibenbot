package com.dazo66.command.interfaces;

/**
 * 异常处理器
 * @author Dazo66
 */
@FunctionalInterface
public interface ExceptionHandler {
    void cetch(Exception e);
}
