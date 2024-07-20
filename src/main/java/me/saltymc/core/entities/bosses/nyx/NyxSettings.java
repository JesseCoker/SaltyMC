package me.saltymc.core.entities.bosses.nyx;

public class NyxSettings
{
    public static final float FANG_DAMAGE = 18.0f;
    public static final float VEX_DAMAGE = 18.0f;
    public static final float ARROW_DAMAGE = 14.0f;
    public static final float LIGHTNING_DAMAGE = 12.0f;
    public static final float TNT_DAMAGE = 12.0f;
    public static final float TRIDENT_DAMAGE = 18.0f;
    public static final float LINGERING_POTION_DAMAGE = 10.0f;

    public static final int TICKS_BETWEEN_MOVES = 100;
    public static final int TICKS_BETWEEN_TELEPORTS = 140;
    public static final int MAX_TELEPORT_DISTANCE = 28;
    public static final int TELEPORT_GLOW_DURATION = 14;

    public static final double CIRCLE_OFFSET = 3.0;
    public static final double DISTANCE_PER_CIRCLE = 1.5;
    public static final int NUMBER_OF_FANGS_PER_CIRCLE = 30;
    public static final int NUMBER_OF_FANG_CIRCLES = 5;

    public static final int ARROW_OFFSET = 3;
    public static final int NUMBER_OF_ARROWS_PER_CIRCLE = 40;
    public static final int NUMBER_OF_ARROW_WAVES = 7;
    public static final long TIME_BETWEEN_ARROW_WAVES = 14L;
    public static final long ARROW_GRAVITY_TIME = 55L;
    public static final int ANGLE_ADJUSTMENT_PER_ARROW_WAVE = 4;

    public static final double STARTING_LIGHTNING_OFFSET = 38.0;
    public static final double LIGHTNING_OFFSET_SUBTRACTION_PER_WAVE = 4.0;
    public static final int NUMBER_OF_LIGHTNING_STRIKES_PER_CIRCLE = 80;
    public static final int NUMBER_OF_LIGHTNING_WAVES = 7;
    public static final long TIME_BETWEEN_LIGHTNING_WAVES = 18L;

    public static final int NUMBER_OF_LAVA = 250;
    public static final int MAX_LAVA_DISTANCE = 40;
    public static final int LAVA_HEIGHT = 10;
    public static final long LAVA_REMOVE_DELAY = 60L;

    public static final int NUMBER_OF_TNT = 100;
    public static final int MAX_TNT_DISTANCE = 50;
    public static final int TNT_HEIGHT = 20;
    public static final double TNT_DAMAGE_RANGE = 3.5;

    public static final String ENTITY_KEY = "NYX-ENTITY-KEY";

    public static final int TRIDENT_OFFSET = 4;
    public static final int NUMBER_OF_TRIDENTS_PER_CIRCLE = 20;
    public static final int NUMBER_OF_TRIDENT_WAVES = 5;
    public static final long TIME_BETWEEN_TRIDENT_WAVES = 14L;
    public static final float TRIDENT_SPEED = 1.2f;

    public static final int LINGERING_POTION_RANGE = 80;
    public static final int LINGERING_POTION_SPACING = 10;
    public static final int LINGERING_POTION_HEIGHT = 20;
    public static final int MAX_LINGERING_POTION_STALL = 40;
}
