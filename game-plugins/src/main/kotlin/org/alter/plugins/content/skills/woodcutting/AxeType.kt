package org.alter.plugins.content.skills.woodcutting

import org.alter.api.cfg.Animation
import org.alter.api.cfg.Items

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class AxeType(val item: Int, val level: Int, val animation: Int, val modifier: Double) {
    BRONZE(item = Items.BRONZE_AXE, level = 1, animation = Animation.WOODCUTTING_BRONZE_AXE, modifier = 1.0),
    IRON(item = Items.IRON_AXE, level = 1, animation = Animation.WOODCUTTING_IRON_AXE, modifier = 1.5),
    STEEL(item = Items.STEEL_AXE, level = 6, animation = Animation.WOODCUTTING_STEEL_AXE, modifier = 2.0),
    BLACK(item = Items.BLACK_AXE, level = 11, animation = Animation.WOODCUTTING_BLACK_AXE, modifier = 2.25),
    MITHRIL(item = Items.MITHRIL_AXE, level = 21, animation = Animation.WOODCUTTING_MITHRIL_AXE, modifier = 2.5),
    ADAMANT(item = Items.ADAMANT_AXE, level = 31, animation = Animation.WOODCUTTING_ADAMANT_AXE, modifier = 3.0),
    RUNE(item = Items.RUNE_AXE, level = 41, animation = Animation.WOODCUTTING_RUNE_AXE, modifier = 3.5),
    DRAGON(item = Items.DRAGON_AXE, level = 61, animation = Animation.WOODCUTTING_DRAGON_AXE, modifier = 3.85),
    INFERNAL(item = Items.INFERNAL_AXE, level = 61, animation = Animation.WOODCUTTING_INFERNAL_AXE, modifier = 3.85),
    THIRDAGE(item = Items._3RD_AGE_AXE, level = 61, animation = Animation.WOODCUTTING_THIRDAGE_AXE, modifier = 3.85),
    CRYSTAL(item = Items.CRYSTAL_AXE, level = 71, animation = Animation.WOODCUTTING_CRYSTAL_AXE, modifier = 4.025);


    companion object {
        val values = enumValues<AxeType>()
    }
}