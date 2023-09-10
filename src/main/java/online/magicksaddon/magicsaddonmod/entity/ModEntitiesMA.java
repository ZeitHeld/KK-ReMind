package online.magicksaddon.magicsaddonmod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.entity.magic.holyEntity;

import static net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES;
import static online.kingdomkeys.kingdomkeys.entity.ModEntities.createEntityType;

public class ModEntitiesMA {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ENTITY_TYPES, MagicksAddonMod.MODID);

    public static final RegistryObject<EntityType<holyEntity>> TYPE_HOLY = createEntityType(holyEntity::create, holyEntity::new, MobCategory.MISC,"entity_holy", 0.5F, 0.5F);
}
