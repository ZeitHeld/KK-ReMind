package online.remind.remind.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import online.kingdomkeys.kingdomkeys.client.gui.elements.HUD.HUDDataStorage;
import online.kingdomkeys.kingdomkeys.client.gui.elements.HUD.HUDElement;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.config.ClientConfigRM;
import online.remind.remind.config.ModConfigs;

import java.util.List;
import java.util.UUID;

public class ClientUtilsRM {
    public static final HUDElement DREAM_EATER_ELEMENT = new HUDElement("DreamEater", KingdomKeysReMind.MODID, ClientConfigRM.DREAM_EATER_HUD_DEFAULTS, new HUDDataStorage() {

        @Override
        public List<? extends Number> load() {
            return ModConfigs.getDreamEaterHUDData();
        }

        @Override
        public void save(List<Float> data) {
            ModConfigs.setDreamEaterHUDData(data);
        }
    });

    public static void initHUD() {
        DREAM_EATER_ELEMENT.loadFromConfig();
    }

    public static Entity getEntityByUUIDClient(UUID uuid) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;

        if (level == null)
            return null;

        for (Entity entity : level.entitiesForRendering()) {
            if (entity.getUUID().equals(uuid)) {
                return entity;
            }
        }
        return null;
    }


}
