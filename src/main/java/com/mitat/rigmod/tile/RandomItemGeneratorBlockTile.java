package com.mitat.rigmod.tile;

import com.mitat.rigmod.setup.Config;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import static com.mitat.rigmod.Rigmod.RIG_TILE;

public class RandomItemGeneratorBlockTile extends TileEntity {

	private int durability;

	public RandomItemGeneratorBlockTile() {
		super(RIG_TILE.get());
		durability = Config.DURABILITY.get();
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		durability = nbt.getInt("durability");
		super.read(state, nbt);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putInt("durability", durability);
		return super.write(compound);
	}

	public void useDurability() {
		int maxDurability = Config.DURABILITY.get();
		if (maxDurability > 0 && durability > maxDurability)
			durability = maxDurability;

		durability--;

		markDirty();
	}

	public boolean hasDurability() {
		return durability > 0;
	}

	public int getDurability() {
		return durability;
	}
}
