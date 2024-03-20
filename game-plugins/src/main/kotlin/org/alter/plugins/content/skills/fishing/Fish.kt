package org.alter.plugins.content.skills.fishing

import org.alter.api.cfg.Items

enum class Fish(val fishItem: Int, val level: Int, val xp: Double, val lowChance: Int, val highChance: Int) {
    //Small fishing net
    SHRIMP(Items.RAW_SHRIMPS, 1, 10.0, 48, 256),
    ANCHOVIES(Items.RAW_ANCHOVIES, 15, 40.0, 24, 128),
    //Big fishing net

    //Fishing rod
    SARDINE(Items.RAW_SARDINE, 5, 20.0, 32, 192),

    //Fly-fishing rod

    //Barbarian rod

    //Oily fishing rod

    //Lobster pot

    //Harpoon

    //
}