package tf.ssf.sfort.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tf.ssf.sfort.LadderBlockItem;

@Mixin(Items.class)
public abstract class MixinItems {
	//public static Item LADDER = Registry.register(Registry.ITEM, Registry.BLOCK.getId(Blocks.LADDER), new LadderBlockItem(Blocks.LADDER, (new Item.Settings()).group(ItemGroup.DECORATIONS)));
	@Shadow
	public static Item LADDER = Registry.register(Registry.ITEM, 186, Registry.BLOCK.getId(Blocks.LADDER).toString(), new LadderBlockItem(Blocks.LADDER, new Item.Settings().group(ItemGroup.DECORATIONS)));
}
