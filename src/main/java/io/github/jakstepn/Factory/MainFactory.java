package io.github.jakstepn.Factory;

import io.github.jakstepn.Main;

import javax.annotation.Nullable;

public class MainFactory {
    public static Main main;

    public void init(Main main) {
        MainFactory.main = main;
    }

    @Nullable
    public static Main getPlugin() {
        return main;
    }
}
