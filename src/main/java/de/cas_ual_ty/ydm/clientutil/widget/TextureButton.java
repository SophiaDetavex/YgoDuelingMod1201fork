package de.cas_ual_ty.ydm.clientutil.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.clientutil.YdmBlitUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static de.cas_ual_ty.ydm.clientutil.YdmBlitUtil.blit;


public class TextureButton extends Button
{
    public ResourceLocation textureLocation;
    
    public int texX;
    public int texY;
    public int texW;
    public int texH;
    
    public <OnTooltip> TextureButton(int x, int y, int width, int height, Component title, OnPress pressedAction, OnTooltip onTooltip)
    {
        super(x, y, width, height, title, pressedAction, (CreateNarration) onTooltip);
        textureLocation = null;
    }
    
    public TextureButton(int x, int y, int width, int height, Component title, OnPress pressedAction)
    {
        super(x, y, width, height, title, pressedAction, createNarration);
        textureLocation = null;
    }
    
    public TextureButton setTexture(ResourceLocation textureLocation, int texX, int texY, int texW, int texH)
    {
        this.textureLocation = textureLocation;
        this.texX = texX;
        this.texY = texY;
        this.texW = texW;
        this.texH = texH;
        return this;
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
        renderButton(ms, minecraft, mouseX, mouseY);

        if(textureLocation != null)
        {
            RenderSystem.setShaderTexture(0, textureLocation);
            blit(ms, getX(), getY(), width, height, texX, texY, texW, texH, 256, 256);
        }
        
        if(isHoveredOrFocused())
        {
            renderToolTip(ms, mouseX, mouseY);
        }
    }
}
