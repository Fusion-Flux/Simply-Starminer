package com.fusionflux.starminer.client.screen;

import com.fusionflux.starminer.screen.StarCoreScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static com.fusionflux.starminer.SimplyStarminer.MODID;

public class StarCoreScreen extends HandledScreen<StarCoreScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(MODID,"textures/gui/star_heart.png");

    public StarCoreScreen(StarCoreScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0f, 1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int uwu = (this.width - this.backgroundWidth) / 2; // yes we'll keep these
        int owo = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, uwu, owo, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices,mouseX,mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.copiedBuffer(this.title.getString().getBytes()));
        String radius = String.valueOf(buf.readInt());
        //noinspection ConstantConditions
        this.textRenderer.drawWithShadow(matrices, Text.of("Radius:"),(52f - this.textRenderer.getWidth("Radius:")) / 2 + 62, 20, Formatting.WHITE.getColorValue());
        this.textRenderer.drawWithShadow(matrices, Text.of(radius),(52f - this.textRenderer.getWidth(radius)) / 2 + 62, 35, Formatting.WHITE.getColorValue());
    }
}

// this is the screen :3
// it only exists on the client <3