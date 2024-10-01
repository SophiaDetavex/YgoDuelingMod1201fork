package de.cas_ual_ty.ydm.clientutil.widget;

import com.mojang.blaze3d.vertex.PoseStack;

public interface ITooltip<Widget>
{
    void onTooltip(Widget widget, PoseStack ms, int mouseX, int mouseY);
}