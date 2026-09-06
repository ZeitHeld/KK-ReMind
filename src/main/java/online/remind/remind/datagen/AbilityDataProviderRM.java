package online.remind.remind.datagen;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import online.kingdomkeys.kingdomkeys.ability.Ability.AbilityType;
import online.kingdomkeys.kingdomkeys.datagen.builder.AbilityBuilder;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.lib.StringsRM;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AbilityDataProviderRM implements DataProvider {
	private final PackOutput.PathProvider pathProvider;

	public AbilityDataProviderRM(PackOutput output) {
		this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "abilities");
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Map<String, JsonObject> abilities = new LinkedHashMap<>();
		int order = 100;

		abilities.put(StringsRM.ABMA_Prefix + "dark_passage", new AbilityBuilder().apCost(3).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "rage_awakened", new AbilityBuilder().apCost(3).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "way_to_light", new AbilityBuilder().apCost(3).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "road_to_dawn", new AbilityBuilder().apCost(3).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "dark_power", new AbilityBuilder().apCost(3).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "riskcharge", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "renewal_block", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "focus_block", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "stop_block", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "poison_block", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "confusion_block", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "royal_guard", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "counter_hammer", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "counter_blast", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "counter_rush", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "light_step", new AbilityBuilder().apCost(0).type(AbilityType.GROWTH).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "dark_step", new AbilityBuilder().apCost(0).type(AbilityType.GROWTH).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "darkness_boost", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "darkness_within", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "light_boost", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "light_within", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "hp_boost", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_boost", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "situation_boost", new AbilityBuilder().apCost(2).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "cure_converter", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_shield", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "vehemence", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "adrenaline", new AbilityBuilder().apCost(4).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "critical_surge", new AbilityBuilder().apCost(4).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "dedication", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "hearts_power", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "friends_power", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "spellblade", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "ultima_weapon", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "munny_magic", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "block_replenisher", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "light_infusion", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "dark_infusion", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "twilight_infusion", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "seeker_mine", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "chirithy", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "hp_walker", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_walker", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "focus_walker", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "heart_walker", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "exp_walker", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "attack_haste", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_slow", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_slowra", new AbilityBuilder().apCost(4).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "mp_slowga", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "one_hp", new AbilityBuilder().apCost(5).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "ribbon", new AbilityBuilder().apCost(10).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "tidus", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "jecht", new AbilityBuilder().apCost(3).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "lyric1", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "lyric2", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "xephiro", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "regen", new AbilityBuilder().apCost(0).type(AbilityType.SUPPORT).order(order++).build());
		abilities.put(StringsRM.ABMA_Prefix + "exceed", new AbilityBuilder().apCost(0).type(AbilityType.ACTION).order(order++).build());

		CompletableFuture<?>[] futures = abilities.entrySet().stream().map(entry -> {
			Path path = pathProvider.json(ResourceLocation.fromNamespaceAndPath(KingdomKeysReMind.MODID, entry.getKey()));
			return DataProvider.saveStable(cache, entry.getValue(), path);
		}).toArray(CompletableFuture[]::new);
		return CompletableFuture.allOf(futures);
	}

	@Override
	public String getName() {
		return "Kingdom Keys Re:Mind Ability Data";
	}
}
