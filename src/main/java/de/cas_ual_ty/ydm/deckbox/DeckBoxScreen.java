package de.cas_ual_ty.ydm.deckbox;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.YDM;
import de.cas_ual_ty.ydm.clientutil.ScreenUtil;
import de.cas_ual_ty.ydm.clientutil.YdmBlitUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;


public class DeckBoxScreen extends AbstractContainerScreen<DeckBoxContainer>
{
    public static final ResourceLocation DECK_BOX_GUI_TEXTURE = new ResourceLocation(YDM.MOD_ID, "textures/gui/deck_box.png");
    
    public DeckBoxScreen(DeckBoxContainer screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);
    }
    
    @Override
    protected void init()
    {
        imageWidth = 284;
        imageHeight = 250;
        super.init();
    }
    public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        renderTooltip(ms, mouseX, mouseY);
    }
    
    
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY)
    {
        ScreenUtil.white();
        RenderSystem.setShaderTexture(0, DeckBoxScreen.DECK_BOX_GUI_TEXTURE);
        YdmBlitUtil.blit(ms, leftPos, topPos, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight, 512, 256);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    }
}
