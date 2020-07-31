package com.dazo66.commandstream.interfaces;

/**
 * @author Dazo66
 */
@FunctionalInterface
public interface ExceptionHandler {
    void cetch(Exception e);
}
