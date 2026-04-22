package net.marios271.forge;

import net.minecraftforge.fml.common.Mod;

import net.marios271.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
