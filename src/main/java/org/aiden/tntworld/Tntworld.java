package org.aiden.tntworld;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Tntworld.MODID)
public class Tntworld {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "tntworld";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Tntworld() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.getLevel().isClientSide()) {
            BlockState state = event.getLevel().getBlockState(event.getPos());
            if (state.getBlock() != Blocks.AIR) {
                // Replace the block with air
                event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
                // Create and spawn a primed TNT entity at the position
                PrimedTnt tntEntity = new PrimedTnt(event.getLevel(), event.getPos().getX() + 0.5, event.getPos().getY(), event.getPos().getZ() + 0.5, null);
                event.getLevel().addFreshEntity(tntEntity);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}

