package de.cas_ual_ty.ydm.duel.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.duel.DuelContainer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DuelScreenBase<E extends DuelContainer> extends DuelContainerScreen<E>
{
    public DuelScreenBase(E screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    protected void renderLabels(Font ms, int x, int y)
    {
         GuiGraphics.drawString(ms, "Waiting for server...", 8.0F, 6.0F, 0x404040);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    }
}
