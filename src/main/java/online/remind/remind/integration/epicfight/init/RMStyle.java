package online.remind.remind.integration.epicfight.init;

import yesman.epicfight.world.capabilities.item.Style;

public enum RMStyle implements Style {
    XEPHIRO_SINGLE(false),
    XEPHIRO_DUAL(true),
    RAGE_FORM(false),
    TWILIGHT_FORM(true),
    DARK_FORM(false),
    LIGHT_FORM(false);

    private final boolean canUseOffhand;
    private final int id;

    private RMStyle(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }

    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }

    public int universalOrdinal() {
        return this.id;
    }
}
