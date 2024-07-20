package me.saltymc.core.entities.bosses.herobrine;

public class HerobrineSettings
{
    public static class Speak
    {
        public static final long TICKS_BETWEEN_SPEECH = 100L;
    }

    public static class Attack
    {
        public static final float REACH = 3.6f;
    }

    public static class Block
    {
        public static final int BLOCK_ATTACK_CHANCE = 3;
        public static final double SIDE_HOP_HORIZONTAL_VELOCITY = 1.9f;
        public static final double SIDE_HOP_VERTICAL_VELOCITY = 0.3f;
    }

    public static class Teleport
    {
        public static final long TICKS_BETWEEN_RANDOM_TELEPORTS = 80L;
        public static final int TELEPORT_AROUND_RADIUS = 10;
        public static final int MAX_HEIGHT_DIFFERENCE = 5;
    }

    public static class Ability
    {
        public static final int USE_ABILITY_CHANCE = 100;

        public static final int LAVA_PLACE_COUNT = 3;
        public static final int LAVA_PLACE_RANGE = 100;
        public static final int MIN_LAVA_PLACE_HEIGHT = 20;
        public static final int MAX_LAVA_PLACE_HEIGHT = 80;
        public static final int MAX_LAVA_PLACE_DELAY = 80;

        public static final int TNT_PLACE_COUNT = 12;
        public static final int TNT_PLACE_RANGE = 60;
        public static final int MIN_TNT_PLACE_HEIGHT = 22;
        public static final int MAX_TNT_PLACE_HEIGHT = 35;
        public static final int MAX_TNT_PLACE_DELAY = 120;

        public static final int LIGHTNING_STRIKE_COUNT = 16;
        public static final int MAX_LIGHTNING_STRIKE_RADIUS = 40;
        public static final int MAX_LIGHTNING_STRIKE_DELAY = 50;

        public static final float BOW_POWER = 2.1f;
    }

    public static class BlockPunish
    {
        public static final int BLOCK_PUNISH_CHANCE = 5;
    }

    public static class NetherSpread
    {
        public static final int SPREAD_RADIUS = 60;
        public static final int MAX_SPORES = 30;
    }
}
