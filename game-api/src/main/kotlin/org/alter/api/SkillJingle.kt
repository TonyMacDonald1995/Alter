package org.alter.api

enum class SkillJingle(val skillId: Int, val jingleId: Int, val jingleIdUnlock: Int? = null) {
    ATTACK(0, 29, 30),
    DEFENCE(1, 37, 38),
    STRENGTH(2, 33, 34),

    /**
     * Levels 11-49 || Levels 50-99
     */
    HITPOINTS(3, 47, 48),
    RANGED(4, 57, 58),
    PRAYER(5, 55, 56),
    MAGIC(6, 51, 52),
    COOKING(7, 33, 34),
    WOODCUTTING(8, 69, 70),
    FLETCHING(9, 43, 44),
    FISHING(10, 41, 42),
    FIREMAKING(11, 39, 40),
    CRAFTING(12, 35, 36),
    SMITHING(13, 63, 64),
    MINING(14, 53, 54),
    HERBLORE(15, 45, 46),
    AGILITY(16, 28),
    THIEVING(17, 67, 68),
    SLAYER(18, 61, 62),
    FARMING(19, 10, 11),
    RUNECRAFTING(20, 59, 60),

    /**
     * Even-numbered levels  ||  Odd-numbered levels
     */
    HUNTER(21, 49, 50),
    CONSTRUCTION(22, 31, 32);

    companion object {
        fun getForSkill(skillId: Int): SkillJingle {
            return SkillJingle.values().first { it.skillId == skillId }
        }
    }
}