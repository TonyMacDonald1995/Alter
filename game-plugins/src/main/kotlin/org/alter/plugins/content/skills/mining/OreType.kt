package org.alter.plugins.content.skills.mining

import org.alter.api.cfg.Items

enum class OreType(val level: Int, val xp: Double, val ore: Int, val respawnTime: Int) {
    // TODO: Need to add Rune Essence Mine area with portal support
    // TODO: Need to add teleports into Rune Essence Mine
    RUNE_ESSENCE(level = 1, xp = 5.0, ore = Items.RUNE_ESSENCE, respawnTime = 0),
    CLAY(level = 1, xp = 5.0, ore = Items.CLAY, respawnTime = 2),
    COPPER(level = 1, xp = 17.5, ore = Items.COPPER_ORE, respawnTime = 4),
    TIN(level = 1, xp = 17.5, ore = Items.TIN_ORE, respawnTime = 4),
    BLURITE(level = 10, xp = 17.5, ore = Items.BLURITE_ORE, respawnTime = 42),
    LIMESTONE(level = 10, xp = 26.5, ore = Items.LIMESTONE, respawnTime = 9), // WIP
    IRON(level = 15, xp = 35.0, ore = Items.IRON_ORE, respawnTime = 9),
    SILVER(level = 20, xp = 40.0, ore = Items.SILVER_ORE, respawnTime = 100),
    COAL(level = 30, xp = 50.0, ore = Items.COAL, respawnTime = 50),
    PURE_ESSENCE(level = 30, xp = 5.0, ore = Items.PURE_ESSENCE, respawnTime = 0),
    SANDSTONE1(level = 35, xp = 30.0, ore = Items.SANDSTONE_1KG, respawnTime = 8),
    SANDSTONE2(level = 35, xp = 40.0, ore = Items.SANDSTONE_2KG, respawnTime = 8),
    SANDSTONE3(level = 35, xp = 50.0, ore = Items.SANDSTONE_5KG, respawnTime = 8),
    SANDSTONE4(level = 35, xp = 60.0, ore = Items.SANDSTONE_10KG, respawnTime = 8),
    GOLD(level = 40, xp = 65.0, ore = Items.GOLD_ORE, respawnTime = 100),
    GEMSTONE1(level = 40, xp = 65.0, ore = Items.UNCUT_OPAL, respawnTime = 99),
    GEMSTONE2(level = 40, xp = 65.0, ore = Items.UNCUT_JADE, respawnTime = 99),
    GEMSTONE3(level = 40, xp = 65.0, ore = Items.UNCUT_RED_TOPAZ, respawnTime = 99),
    GEMSTONE4(level = 40, xp = 65.0, ore = Items.UNCUT_SAPPHIRE, respawnTime = 99),
    GEMSTONE5(level = 40, xp = 65.0, ore = Items.UNCUT_EMERALD, respawnTime = 99),
    GEMSTONE6(level = 40, xp = 65.0, ore = Items.UNCUT_RUBY, respawnTime = 99),
    GEMSTONE7(level = 40, xp = 65.0, ore = Items.UNCUT_DIAMOND, respawnTime = 99),
    GRANITE1(level = 45, xp = 50.0, ore = Items.GRANITE_500G, respawnTime = 8),
    GRANITE2(level = 45, xp = 60.0, ore = Items.GRANITE_2KG, respawnTime = 8),
    GRANITE3(level = 45, xp = 75.0, ore = Items.GRANITE_5KG, respawnTime = 8),
    MITHRIL(level = 55, xp = 80.0, ore = Items.MITHRIL_ORE, respawnTime = 200),
    LOVAKITE(level = 65, xp = 60.0, ore = Items.LOVAKITE_ORE, respawnTime = 58),
    ADAMANTITE(level = 70, xp = 95.0, ore = Items.ADAMANTITE_ORE, respawnTime = 400),
    TE_SALT(level = 72, xp = 5.0, ore = Items.TE_SALT, respawnTime = 9),
    EFH_SALT(level = 72, xp = 5.0, ore = Items.EFH_SALT, respawnTime = 9),
    URT_SALT(level = 72, xp = 5.0, ore = Items.URT_SALT, respawnTime = 9),
    RUNITE(level = 85, xp = 125.0, ore = Items.RUNITE_ORE, respawnTime = 1200),
}