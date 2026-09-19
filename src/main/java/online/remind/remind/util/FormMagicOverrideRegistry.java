package online.remind.remind.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FormMagicOverrideRegistry
        implements ResourceManagerReloadListener {

    public static final FormMagicOverrideRegistry INSTANCE =
            new FormMagicOverrideRegistry();

    private static final String DIRECTORY =
            "form_magic_overrides";

    private static Map<ResourceLocation, FormMagicOverrideDefinition> DEFINITIONS =
            Map.of();

    private FormMagicOverrideRegistry() {
    }

    public static FormMagicOverrideDefinition get(
            ResourceLocation form
    ) {
        if (form == null) {
            return null;
        }

        return DEFINITIONS.get(form);
    }

    public static boolean has(ResourceLocation form) {
        return form != null && DEFINITIONS.containsKey(form);
    }

    @Override
    public void onResourceManagerReload(
            ResourceManager resourceManager
    ) {
        Map<ResourceLocation, FormMagicOverrideDefinition> loaded =
                new HashMap<>();

        Map<ResourceLocation, Resource> resources =
                resourceManager.listResources(
                        DIRECTORY,
                        location -> location.getPath().endsWith(".json")
                );

        for (Map.Entry<ResourceLocation, Resource> entry
                : resources.entrySet()) {

            ResourceLocation fileId = entry.getKey();

            try (Reader reader = entry.getValue().openAsReader()) {

                JsonObject json =
                        JsonParser.parseReader(reader).getAsJsonObject();

                if (!json.has("form")) {
                    throw new IllegalArgumentException(
                            "Missing 'form' in " + fileId
                    );
                }

                ResourceLocation formId =
                        ResourceLocation.parse(
                                json.get("form").getAsString()
                        );

                List<ResourceLocation> spells =
                        new ArrayList<>();

                JsonArray spellArray =
                        json.getAsJsonArray("spells");

                if (spellArray != null) {
                    for (JsonElement element : spellArray) {
                        spells.add(
                                ResourceLocation.parse(
                                        element.getAsString()
                                )
                        );
                    }
                }

                boolean lockEquipment =
                        !json.has("lock_equipment")
                                || json.get("lock_equipment").getAsBoolean();

                loaded.put(
                        formId,
                        new FormMagicOverrideDefinition(
                                formId,
                                List.copyOf(spells),
                                lockEquipment
                        )
                );

                DEFINITIONS = Map.copyOf(loaded);

                System.out.println(
                        "[KKReMind] Total form magic overrides loaded: "
                                + DEFINITIONS.size()
                );

            } catch (Exception e) {
                throw new IllegalStateException(
                        "Failed to load form magic override: "
                                + fileId,
                        e
                );
            }
        }

        DEFINITIONS = Map.copyOf(loaded);
    }

    private static ResourceLocation formIdFromFile(
            ResourceLocation fileId
    ) {
        String path = fileId.getPath();

        String prefix =
                DIRECTORY + "/";

        if (!path.startsWith(prefix)
                || !path.endsWith(".json")) {
            throw new IllegalArgumentException(
                    "Invalid form magic override path: "
                            + fileId
            );
        }

        String formPath = path.substring(
                prefix.length(),
                path.length() - ".json".length()
        );

        return ResourceLocation.fromNamespaceAndPath(
                fileId.getNamespace(),
                formPath
        );
    }
}