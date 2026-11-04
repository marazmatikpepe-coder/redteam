package com.redteam.client;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class RedTeamClient implements ClientModInitializer {
    private static KeyBinding menuKey;

    @Override
    public void onInitializeClient() {
        // F2 в Minecraft занят скриншотом, поэтому по умолчанию Right Shift.
        // Клавишу можно поменять: Настройки -> Управление -> категория "RedTeam".
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Открыть меню RedTeam", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "RedTeam"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) client.setScreen(new MenuScreen());
            ModuleManager.tick();
        });
    }
}
