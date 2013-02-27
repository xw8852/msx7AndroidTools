package com.msx7.core;

import com.msx7.core.cmd.ICommand;

public class Manager {

    protected Manager() {

    }

    public void registerCommand(int cmdId, String className) {

    }

    public void registerCommand(int cmdId, Class<? extends ICommand> cls) {

    }

    public void execute(int cmdId, Object params) {

    }
}
