package com.tfar.metalbarrels.screen;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.CopperBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CopperBarrelScreen extends AbstractBarrelScreen<CopperBarrelContainer> {


  private static final ResourceLocation COPPER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/copper.png");
  public CopperBarrelScreen(CopperBarrelContainer copperBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(copperBarrelContainer, playerInventory, component, COPPER, 176, 204);
  }


  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 113, 0x404040);
    this.font.drawString(this.title.getFormattedText(), 8, -13, 0x404040);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   */
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    this.blit(i, j, 0, 0, this.xSize, this.ySize);
  }
}
