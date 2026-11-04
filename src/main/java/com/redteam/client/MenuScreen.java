package com.redteam.client;

import com.redteam.client.modules.Module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/** Простое меню: ЛКМ по кнопке - включить/выключить, ПКМ - сменить режим. */
public class MenuScreen extends Screen {
    public MenuScreen() { super(Text.literal("RedTeam Test Client")); }

    @Override
    protected void init() {
        int y = 40;
        for (Module m : ModuleManager.MODULES) {
            addDrawableChild(new ModuleButton(width / 2 - 100, y, 200, 20, m));
            y += 24;
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, "RedTeam Test Client (ЛКМ - вкл/выкл, ПКМ - режим)", width / 2, 20, 0xFFFFFF);
    }

    private static class ModuleButton extends ButtonWidget {
        private final Module module;

        ModuleButton(int x, int y, int w, int h, Module module) {
            super(x, y, w, h, Text.empty(), b -> {}, DEFAULT_NARRATION_SUPPLIER);
            this.module = module;
            refresh();
        }

        private void refresh() {
            String d = module.getDetails();
            setMessage(Text.literal(module.name + ": " + (module.isEnabled() ? "ВКЛ" : "выкл") + (d.isEmpty() ? "" : " [" + d + "]")));
        }

        @Override
        public boolean mouseClicked(double mx, double my, int button) {
            if (!active || !visible || !isMouseOver(mx, my)) return false;
            if (button == 0) module.toggle();
            else if (button == 1) module.cycleMode();
            else return false;
            refresh();
            return true;
        }
    }
}
