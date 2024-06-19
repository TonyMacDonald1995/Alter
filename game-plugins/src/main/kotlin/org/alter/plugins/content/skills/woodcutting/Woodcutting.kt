package org.alter.plugins.content.skills.woodcutting

import org.alter.api.Skills
import org.alter.api.cfg.Items
import org.alter.api.cfg.Sound
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.fs.def.ItemDef
import org.alter.game.model.attr.AttributeKey
import org.alter.game.model.entity.DynamicObject
import org.alter.game.model.entity.GameObject
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask
import org.alter.plugins.content.area.tutorial_island.Tutorial_island_plugin

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Woodcutting {

    private val infernalAxe = AttributeKey<Int>("Infernal Axe Charges")

    data class Tree(val type: TreeType, val obj: Int, val trunk: Int)

    suspend fun chopDownTree(it: QueueTask, obj: GameObject, tree: TreeType, trunkId: Int) {
        val p = it.player

        if (!canChop(p, obj, tree)) {
            return
        }

        val logName = p.world.definitions.get(ItemDef::class.java, tree.log).name
        val axe = AxeType.values.firstOrNull { p.getSkills().getBaseLevel(Skills.WOODCUTTING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item)) }!!
        var depleteTask: QueueTask? = null

        if (it.player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 1000) {
            p.filterableMessage("You swing your axe at the tree.")
        } else {
            p.triggerEvent(Tutorial_island_plugin.ChopTreeEvent)
        }

        if (tree.depleteTime != null) {
            p.world.queue {
                depleteTask = this
                wait(tree.depleteTime)
                val trunk = DynamicObject(obj, trunkId)
                p.world.spawn(trunk)
                p.playSound(Sound.TREE_FALL)
                wait(tree.respawnTime.random())
                p.world.spawn(DynamicObject(obj))
            }
        }

        it.terminateAction = {
            depleteTask?.terminate()
            depleteTask = null
        }

        while (true) {

            p.animate(axe.animation)
            it.wait(2)

            if (!canChop(p, obj, tree)) {
                p.animate(-1)
                break
            }

            val level = p.getSkills().getCurrentLevel(Skills.WOODCUTTING)
            if (level.interpolateCheck(minChance = (tree.lowChance * axe.modifier).toInt(), maxChance = (tree.highChance * axe.modifier).toInt())) {

                if(p.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 1000) {
                    p.queue {
                        itemMessageBox(message = "You manage to cut some logs.", item = Items.LOGS_2511, amountOrZoom = 400)
                    }
                    p.triggerEvent(Tutorial_island_plugin.TreeCutDownEvent)
                }

                if(p.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 1000) {
                    p.filterableMessage("You get some ${logName.pluralSuffix(2)}.")
                }

                p.inventory.add(tree.log)
                p.addXp(Skills.WOODCUTTING, tree.xp)

                if (tree.depleteTime == null) {
                    p.world.queue {
                        val trunk = DynamicObject(obj, trunkId)
                        p.world.spawn(trunk)
                        p.playSound(Sound.TREE_FALL)
                        wait(tree.respawnTime.random())
                        p.world.spawn(DynamicObject(obj))
                    }
                }
            }
            it.wait(2)
        }
    }

    private fun canChop(p: Player, obj: GameObject, tree: TreeType): Boolean {
        if (!p.world.isSpawned(obj)) {
            return false
        }

        if (p.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 70) {
            p.queue { messageBox("You cannot cut down this tree yet. You must progress further in the tutorial.") }
            return false
        }

        if (p.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 120) {
            p.queue { messageBox("Perhaps you've done enough woodcutting now.") }
            return false
        }

        val axe = AxeType.values.firstOrNull { p.getSkills().getBaseLevel(Skills.WOODCUTTING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item)) }
        if (axe == null) {
            p.message("You need an axe to chop down this tree.")
            p.message("You do not have an axe which you have the woodcutting level to use.")
            return false
        }

        if (p.getSkills().getBaseLevel(Skills.WOODCUTTING) < tree.level) {
            if (p.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 1000) {
                p.queue { messageBox("You won't be able to chop oak trees until you have a Woodcutting level of 15. You'll advance to higher Woodcutting levels by chopping down normal trees. At higher Woodcutting levels you will find even more trees you can cut.", lineSpacing = 21) }
            } else {
                p.message("You need a Woodcutting level of ${tree.level} to chop down this tree.")
            }
            return false
        }

        if (p.inventory.isFull) {
            p.message("Your inventory is too full to hold any more logs.")
            return false
        }

        return true
    }

    fun createAxe(player: Player) {
        if (player.getSkills().getBaseLevel(Skills.WOODCUTTING) >= 61 && player.getSkills().getBaseLevel(Skills.FIREMAKING) >= 85) {
            player.inventory.remove(Items.DRAGON_AXE)
            player.inventory.remove(Items.SMOULDERING_STONE)
            player.inventory.add(Items.INFERNAL_AXE)

            player.attr.put(infernalAxe, 5000)

            player.animate(id = 4511, delay = 2)
            player.graphic(id = 1240, height = 2)

            player.addXp(Skills.FIREMAKING, 350.0)
            player.addXp(Skills.WOODCUTTING, 200.0)
        } else if (player.getSkills().getBaseLevel(Skills.FIREMAKING) < 85 || player.getSkills().getBaseLevel(Skills.WOODCUTTING) < 61 &&
            player.getSkills().getBaseLevel(Skills.FIREMAKING) >= 85 || player.getSkills().getBaseLevel(Skills.WOODCUTTING) >= 61) {
            player.message("You need 61 Woodcutting and 85 Firemaking to make this")
        }
    }

    fun checkCharges(p: Player) {
        p.message("Your infernal axe currently has ${p.attr[infernalAxe]} charges left.")
    }
}