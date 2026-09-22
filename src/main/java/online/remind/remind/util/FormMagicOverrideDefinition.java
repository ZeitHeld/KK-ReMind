package online.remind.remind.util;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record FormMagicOverrideDefinition(
        ResourceLocation form,
        List<ResourceLocation> spells,
        ResourceLocation shotlock,
        boolean lockEquipment
) {}