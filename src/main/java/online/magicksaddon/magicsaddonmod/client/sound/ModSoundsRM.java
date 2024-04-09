package online.magicksaddon.magicsaddonmod.client.sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.entity.magic.DrainEntity;

public class ModSoundsRM {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KingdomKeysReMind.MODID);

    public static final RegistryObject<SoundEvent>
            HASTE = registerSound("haste"),
            SLOW = registerSound("slow"),
            HOLY = registerSound("holy"),
            RUIN = registerSound("ruin"),
            BALLOON = registerSound("balloon"),
            BALLOON_BOUNCE = registerSound("balloon_bounce"),
            PLAYER_CAST = registerSound("player_cast"),
            ULTIMA_CAST = registerSound("ultima_cast"),
            ULTIMA_EXPLOSION = registerSound("ultima_explosion"),
            BERSERK = registerSound("berserk"),
            BERSERK2 = registerSound("berserk2"),
            AUTOLIFE = registerSound("auto_life"),
            DARKSTEP1 = registerSound("darkstep1"),
            DARKSTEP2 = registerSound("darkstep2"),
            LIGHTSTEP1 = registerSound("lightstep1"),
            LIGHTSTEP2 = registerSound("lightstep2"),
            RISKCHARGE = registerSound("riskcharge"),
            DRAIN = registerSound("drain"),
            OSMOSE = registerSound("osmose"),
            SILENCEHIT = registerSound("silencehit"),
            WARPHITPLAYER = registerSound("warp_hit_player");


    public static RegistryObject<SoundEvent> registerSound(String name) {
        final ResourceLocation soundID = new ResourceLocation(KingdomKeysReMind.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(soundID));
    }

}
