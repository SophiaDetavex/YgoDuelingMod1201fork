package de.cas_ual_ty.ydm.clientutil.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.clientutil.ScreenUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class ReadyCheckboxWidget extends Button
{
    public Supplier<Boolean> isChecked;
    public Supplier<Boolean> isActive;
    
    public ReadyCheckboxWidget(int xIn, int yIn, int widthIn, int heightIn, String msg, OnPress onPress, Supplier<Boolean> isChecked, Supplier<Boolean> isActive)
    {
        super(xIn, yIn, widthIn, heightIn, Component.empty(), onPress, createNarration);
        this.isChecked = isChecked;
        this.isActive = isActive;
    }
    
    public void render(GuiGraphics ms, int mouseX, int mouseY, float partial)
    {
        active = isActive.get();
        super.render(ms, mouseX, mouseY, partial);
    }
    
    public void renderButton(PoseStack ms, int mouseX, int mouseY, float p_renderButton_3_)
    {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, AbstractWidget.WIDGETS_LOCATION);
        ScreenUtil.white();
        int i = getYImage(isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(ms, getX(), getY(), 0, 46 + i * 20, width / 2, height);
        blit(ms, getX() + width / 2, getY(), 200 - width / 2, 46 + i * 20, width / 2, height);
        if(isChecked.get())
        {
            int j = getFGColor();
            Screen.drawCenteredString(ms, minecraft.font, Component.literal("✔"), getX() + width / 2, getY() + (height - 8) / 2, j | Mth.ceil(alpha * 255.0F) << 24);
        }
    }
}