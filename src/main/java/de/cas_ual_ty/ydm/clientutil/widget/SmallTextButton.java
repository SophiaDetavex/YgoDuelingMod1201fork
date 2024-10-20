package de.cas_ual_ty.ydm.clientutil.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;


public class SmallTextButton extends Button
{
    public <OnTooltip> SmallTextButton(int x, int y, int width, int height, Component title, OnPress pressedAction, OnTooltip onTooltip)
    {
        super(x, y, width, height, title, pressedAction, (CreateNarration) onTooltip);
    }
    
    public SmallTextButton(int x, int y, int width, int height, Component title, OnPress pressedAction)
    {
        super(x, y, width, height, title, pressedAction, (CreateNarration) createNarration);
    }
    
    public void renderButton(PoseStack ms, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        RenderSystem.setShaderTexture(0, AbstractWidget.WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int i = getYImage(isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(ms, getX(), getY(), 0, 46 + i * 20, width / 2, height / 2);
        blit(ms, getX() + width / 2, getY(), 200 - width / 2, 46 + i * 20, width / 2, height / 2);
        blit(ms, getX(), getY() + height / 2, 0, 46 + (i + 1) * 20 - height / 2, width / 2, height / 2);
        blit(ms, getX() + width / 2, getY() + height / 2, 200 - width / 2, 46 + (i + 1) * 20 - height / 2, width / 2, height / 2);
        renderBg(ms, minecraft, mouseX, mouseY);
        
        ms.pushPose();
        ms.scale(0.5F, 0.5F, 1F);
        int j = getFGColor();
        Screen.drawCenteredString(ms, fontrenderer, getMessage(), (getX() + width / 2) * 2, (getY() + height / 2) * 2 - fontrenderer.lineHeight / 2, j | Mth.ceil(alpha * 255.0F) << 24);
        ms.popPose();
        
        if(isHoveredOrFocused())
        {
            renderToolTip(ms, mouseX, mouseY);
        }
    }
}
