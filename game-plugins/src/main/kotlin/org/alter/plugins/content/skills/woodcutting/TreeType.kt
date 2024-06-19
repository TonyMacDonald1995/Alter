package org.alter.plugins.content.skills.woodcutting

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class TreeType(val level: Int, val xp: Double, val log: Int, val depleteTime: Int? = null, val respawnTime: IntRange, val lowChance: Int, val highChance: Int) {
    TREE(level = 1, xp = 25.0, log = 1511, respawnTime = 59..98, lowChance = 64, highChance = 200),
    ACHEY(level = 1, xp = 25.0, log = 2862, respawnTime = 59..98, lowChance = 64, highChance = 200),
    OAK(level = 15, xp = 37.5, log = 1521, depleteTime = 45, respawnTime = 14..14, lowChance = 32, highChance = 100),
    WILLOW(level = 30, xp = 67.5, log = 1519, depleteTime = 50, respawnTime = 14..14, lowChance = 16, highChance = 50),
    TEAK(level = 35, xp = 85.0, log = 6333, depleteTime = 50, respawnTime = 15..15, lowChance = 15, highChance = 46),
    MAPLE(level = 45, xp = 100.0, log = 1517, depleteTime = 100, respawnTime = 59..59, lowChance = 8, highChance = 25),
    HOLLOW(level = 45, xp = 82.5, log = 3239, depleteTime = 60, respawnTime = 43..43, lowChance = 18, highChance = 26),
    MAHOGANY(level = 50, xp = 125.0, log = 6332, depleteTime = 100, respawnTime = 14..14, lowChance = 8, highChance = 25),
    YEW(level = 60, xp = 175.0, log = 1515, depleteTime = 190, respawnTime = 99..99, lowChance = 4, highChance = 12),
    MAGIC(level = 75, xp = 250.0, log = 1513, depleteTime = 390, respawnTime = 199..199, lowChance = 2, highChance = 6),
    REDWOOD(level = 90, xp = 380.0, log = 19669, depleteTime = 440, respawnTime = 199..199, lowChance = 2, highChance = 6),
}