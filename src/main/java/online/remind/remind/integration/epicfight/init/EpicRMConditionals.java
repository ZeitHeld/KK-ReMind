package online.remind.remind.integration.epicfight.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.integration.epicfight.enums.HandStyle;
import online.kingdomkeys.kingdomkeys.integration.epicfight.style.KKFightingStyle;
import online.kingdomkeys.kingdomkeys.integration.epicfight.style.KKStyleRegistry;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.organization.IOrgWeapon;
import online.kingdomkeys.kingdomkeys.lib.KKSupplier;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.driveform.ModDriveFormsRM;
import online.remind.remind.integration.epicfight.style.ModFightingStylesRM;
import yesman.epicfight.api.ex_cap.provider.ProviderConditional;
import yesman.epicfight.registry.deferred.ProviderConditionalRegister;
import yesman.epicfight.registry.deferred.holders.DeferredConditional;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class EpicRMConditionals {
    public static final ProviderConditionalRegister CONDITIONALS = ProviderConditionalRegister.create(KingdomKeysReMind.MODID);

    private static boolean isWeapon(ItemStack stack) {
        return stack.getItem() instanceof KeybladeItem || stack.getItem() instanceof IOrgWeapon;
    }

    private static boolean armed(Player player) {
        return isWeapon(player.getMainHandItem()) || isWeapon(player.getOffhandItem());
    }

    private static Player playerOf(LivingEntityPatch<?> livingEntityPatch) {
        return livingEntityPatch != null && livingEntityPatch.getOriginal() instanceof Player player ? player : null;
    }

    private static boolean inForm(LivingEntityPatch<?> livingEntityPatch, KKSupplier<DriveForm> form) {
        Player player = playerOf(livingEntityPatch);

        if (player == null) {
            return false;
        }

        PlayerData data = PlayerData.get(player);
        return data != null && data.isFormActive(form) && armed(player);
    }

    public static boolean styleActive(LivingEntityPatch<?> livingEntityPatch, ResourceLocation id) {
        Player player = playerOf(livingEntityPatch);
        KKFightingStyle style = KKStyleRegistry.get(id);

        if (player == null || style == null) {
            return false;
        }

        PlayerData data = PlayerData.get(player);

        if (data == null || !data.noFormActive() || !KKStyleRegistry.isChosen(player, id)) {
            return false;
        }

        // Dual wield needs a keyblade in the off-hand, single wield needs the off-hand free
        boolean offhandKeyblade = player.getOffhandItem().getItem() instanceof KeybladeItem;
        return offhandKeyblade == (style.getHand() == HandStyle.DUAL);
    }


    public static final DeferredConditional TWILIGHT_FORM_STYLE = CONDITIONALS.registerConditional("twilight_form_style", () ->
            ProviderConditional.createCustom(RMStyle.TWILIGHT_FORM, livingEntityPatch -> inForm(livingEntityPatch, ModDriveFormsRM.TWILIGHT), true)
    );
    public static final DeferredConditional LIGHT_FORM_STYLE = CONDITIONALS.registerConditional("light_form_style", () ->
            ProviderConditional.createCustom(RMStyle.LIGHT_FORM, livingEntityPatch -> inForm(livingEntityPatch, ModDriveFormsRM.LIGHT), true)
    );
    public static final DeferredConditional DARK_FORM_STYLE = CONDITIONALS.registerConditional("dark_form_style", () ->
            ProviderConditional.createCustom(RMStyle.DARK_FORM, livingEntityPatch -> inForm(livingEntityPatch, ModDriveFormsRM.DARK), true)
    );
    public static final DeferredConditional RAGE_FORM_STYLE = CONDITIONALS.registerConditional("rage_form_style", () ->
            ProviderConditional.createCustom(RMStyle.RAGE_FORM, livingEntityPatch -> inForm(livingEntityPatch, ModDriveFormsRM.RAGE), true)
    );

    public static final DeferredConditional XEPHIRO_STYLE = CONDITIONALS.registerConditional("xephiro_style", () ->
            ProviderConditional.createCustom(RMStyle.XEPHIRO_SINGLE, livingEntityPatch -> styleActive(livingEntityPatch, ModFightingStylesRM.XEPHIRO), true)
    );

}
