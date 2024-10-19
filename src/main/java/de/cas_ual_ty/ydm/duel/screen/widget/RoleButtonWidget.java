package de.cas_ual_ty.ydm.duel.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.duel.PlayerRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class RoleButtonWidget extends Button
{
    @Override
    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }
    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    }
    public Supplier<Boolean> available;
    public PlayerRole role;
    
    public RoleButtonWidget(int xIn, int yIn, int widthIn, int heightIn, Component text, Button.OnPress onPress, Supplier<Boolean> available, PlayerRole role)
    {
        super(xIn, yIn, widthIn, heightIn, text, onPress, createNarration);
        this.available = available;
        this.role = role;
    }
    
    public void render(GuiGraphics ms, int mouseX, int mouseY, float partial)
    {
        active = available.get();
        super.render(ms, mouseX, mouseY, partial);
    }
}