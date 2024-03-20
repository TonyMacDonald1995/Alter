package org.alter.plugins.content.skills.mining

import org.alter.api.cfg.Animation
import org.alter.api.cfg.Items

/**
 * @author Fritz <frikkipafi@gmail.com>
 * @CloudS3c = Just need to find the correct anims
 */
enum class PickaxeType(val item: Int, val level: Int, val ticksPerRoll: Int, val animation: Int) {
    BRONZE(Items.BRONZE_PICKAXE, level = 1, ticksPerRoll = 8, Animation.MINING_BRONZE_PICKAXE),
    IRON(Items.IRON_PICKAXE, level = 1, ticksPerRoll = 7, Animation.MINING_IRON_PICKAXE),
    STEEL(Items.STEEL_PICKAXE, level = 6, ticksPerRoll = 6, Animation.MINING_STEEL_PICKAXE),
    BLACK(Items.BLACK_PICKAXE, level = 11, ticksPerRoll = 5, Animation.MINING_BLACK_PICKAXE),
    MITHRIL(Items.MITHRIL_PICKAXE, level = 21, ticksPerRoll = 5, Animation.MINING_MITHRIL_PICKAXE),
    ADAMANT(Items.ADAMANT_PICKAXE, level = 31, ticksPerRoll = 4, Animation.MINING_ADAMANT_PICKAXE),
    RUNE(Items.RUNE_PICKAXE, level = 41, ticksPerRoll = 3, Animation.MINING_RUNE_PICKAXE),
    GILDED(Items.GILDED_PICKAXE, level = 41, ticksPerRoll = 3, Animation.MINING_RUNE_PICKAXE),
    THIRDAGE(Items._3RD_AGE_PICKAXE, level = 61, ticksPerRoll = 3, Animation.MINING_THIRDAGE_PICKAXE),
    DRAGON(Items.DRAGON_PICKAXE, level = 61, ticksPerRoll = 3, Animation.MINING_DRAGON_PICKAXE),
    DRAGON_U(Items.DRAGON_PICKAXE_12797, level = 61, ticksPerRoll = 3, Animation.MINING_DRAGON_PICKAXE),
    DRAGON_OR(Items.DRAGON_PICKAXE_OR, level = 61, ticksPerRoll = 3, Animation.MINING_DRAGON_PICKAXE),
    DRAGON_OR_TRAILBLAZER(Items.DRAGON_PICKAXE_OR_25376, level = 61, ticksPerRoll = 3, Animation.MINING_DRAGON_PICKAXE),
    INFERNAL(Items.INFERNAL_PICKAXE, level = 61, ticksPerRoll = 3, Animation.MINING_INFERNAL_PICKAXE),
    INFERNAL_PICKAXE_OR(Items.INFERNAL_PICKAXE_OR, level = 61, ticksPerRoll = 3, Animation.MINING_INFERNAL_PICKAXE),
    CRYSTAL(Items.CRYSTAL_PICKAXE, level = 71, ticksPerRoll = 3, Animation.MINING_CRYSTAL_PICKAXE);
    companion object {
        val values = enumValues<PickaxeType>()
    }
}


