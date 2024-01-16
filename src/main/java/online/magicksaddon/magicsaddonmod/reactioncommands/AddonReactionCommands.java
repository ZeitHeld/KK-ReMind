package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.RegistryBuilder;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.kingdomkeys.kingdomkeys.reactioncommands.ModReactionCommands;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

import java.util.function.Supplier;

public class AddonReactionCommands extends ModReactionCommands {
    public static DeferredRegister<Magic> REACTION_COMMANDS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "reaction_command"), "magicksaddon");

    /*
    public static Supplier<IForgeRegistry<ReactionCommand>> registry = REACTION_COMMANDS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<ReactionCommand>
        RISKCHARGE = REACTION_COMMANDS.register(StringsX.riskCharge, () -> new ReactionCommand(MagicksAddonMod.MODID+":"+StringsX.riskchargeRC, StringsX.riskCharge, StringsX.rageForm) {


            @Override
        public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {

        }

        @Override
        public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
            return false;
        }
    });
    */


}
