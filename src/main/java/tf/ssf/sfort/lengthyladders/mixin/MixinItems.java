package tf.ssf.sfort.lengthyladders.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tf.ssf.sfort.lengthyladders.LadderBlockItem;

@Mixin(Items.class)
public abstract class MixinItems {
	@Shadow @Final
	public static Item LADDER = Registry.register(Registry.ITEM, Registry.BLOCK.getKey(Blocks.LADDER), new LadderBlockItem(Blocks.LADDER, (new Item.Properties()).group(ItemGroup.DECORATIONS)));
}
