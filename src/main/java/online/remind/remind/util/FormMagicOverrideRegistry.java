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

    public static boolean has(
            ResourceLocation form
    ) {
        return form != null
                && DEFINITIONS.containsKey(form);
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
                        location ->
                                location.getPath().endsWith(".json")
                );

        for (Map.Entry<ResourceLocation, Resource> entry
                : resources.entrySet()) {

            ResourceLocation fileId =
                    entry.getKey();

            try (Reader reader =
                         entry.getValue().openAsReader()) {

                JsonObject json =
                        JsonParser
                                .parseReader(reader)
                                .getAsJsonObject();


                // -----------------------------------------------------
                // Form
                // -----------------------------------------------------

                if (!json.has("form")) {
                    throw new IllegalArgumentException(
                            "Missing 'form' in " + fileId
                    );
                }

                ResourceLocation formId =
                        ResourceLocation.parse(
                                json
                                        .get("form")
                                        .getAsString()
                        );


                // -----------------------------------------------------
                // Spells
                // -----------------------------------------------------

                List<ResourceLocation> spells =
                        new ArrayList<>();

                JsonArray spellArray =
                        json.getAsJsonArray("spells");

                if (spellArray != null) {

                    for (JsonElement element
                            : spellArray) {

                        spells.add(
                                ResourceLocation.parse(
                                        element.getAsString()
                                )
                        );
                    }
                }


                // -----------------------------------------------------
                // Optional Shotlock
                // -----------------------------------------------------

                ResourceLocation shotlock =
                        null;

                if (json.has("shotlock")
                        && !json.get("shotlock").isJsonNull()) {

                    String shotlockString =
                            json
                                    .get("shotlock")
                                    .getAsString();

                    if (!shotlockString.isBlank()) {
                        shotlock =
                                ResourceLocation.parse(
                                        shotlockString
                                );
                    }
                }


                // -----------------------------------------------------
                // Equipment lock
                //
                // Defaults to true if omitted.
                // -----------------------------------------------------

                boolean lockEquipment =
                        !json.has("lock_equipment")
                                || json
                                .get("lock_equipment")
                                .getAsBoolean();


                // -----------------------------------------------------
                // Register definition
                // -----------------------------------------------------

                FormMagicOverrideDefinition definition =
                        new FormMagicOverrideDefinition(
                                formId,
                                List.copyOf(spells),
                                shotlock,
                                lockEquipment
                        );

                loaded.put(
                        formId,
                        definition
                );

                System.out.println(
                        "[KKReMind/FormMagic] Loaded "
                                + formId
                                + " from "
                                + fileId
                                + " | spells="
                                + spells.size()
                                + " | shotlock="
                                + (
                                shotlock != null
                                        ? shotlock
                                        : "none"
                        )
                                + " | locked="
                                + lockEquipment
                );

            } catch (Exception e) {

                throw new IllegalStateException(
                        "Failed to load form magic override: "
                                + fileId,
                        e
                );
            }
        }


        // ---------------------------------------------------------
        // Only replace the live registry after EVERYTHING loaded
        // successfully.
        // ---------------------------------------------------------

        DEFINITIONS =
                Map.copyOf(loaded);

        System.out.println(
                "[KKReMind/FormMagic] Total form magic overrides loaded: "
                        + DEFINITIONS.size()
        );
    }
}