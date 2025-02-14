package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.util.BarrelHandler;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

;

public abstract class AbstractBarrelTile extends TileEntity implements INamedContainerProvider {

  protected final int width,height;

  public AbstractBarrelTile(TileEntityType<?> tileEntityType, int width, int height) {
    super(tileEntityType);
    this.width = width;
    this.height = height;
    handler = new BarrelHandler(this.width * this.height,this);
  }

  public BarrelHandler handler;

  protected int field_213967_b;

  @Nonnull
  @Override
  public CompoundNBT write(CompoundNBT tag) {
    CompoundNBT compound = this.handler.serializeNBT();
    tag.put("inv", compound);
    return super.write(tag);
  }

  @Override
  public void read(CompoundNBT tag) {
    CompoundNBT invTag = tag.getCompound("inv");
    handler.deserializeNBT(invTag);
    super.read(tag);
  }

  protected boolean isCrystal(){
    return false;
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? LazyOptional.of(() -> handler).cast() : super.getCapability(cap, side);
  }
}