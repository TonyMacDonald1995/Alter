package org.alter.plugins.content.skills.cooking

import org.alter.api.Skills
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.fs.DefinitionSet
import org.alter.game.fs.def.ItemDef
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask
import org.alter.plugins.content.area.tutorial_island.Tutorial_island_plugin
import org.alter.plugins.content.skills.cooking.data.CookingFood
import org.alter.plugins.content.skills.cooking.data.CookingIngredient
import org.alter.plugins.content.skills.cooking.data.CookingObj

class Cooking(private val defs: DefinitionSet) {

    val foodNames = CookingFood.values.associate { it.rawItem to defs.get(ItemDef::class.java, it.rawItem).name.lowercase() }
    val cookedFoodNames = CookingFood.values.associate { it.cookedItem to defs.get(ItemDef::class.java, it.cookedItem).name.lowercase() }

    val ingredientNames = CookingIngredient.values.associate { it.result to defs.get(ItemDef::class.java, it.result).name.lowercase() }

    suspend fun cook(task: QueueTask, food: CookingFood, amount: Int, obj: CookingObj, forceBurn: Boolean = false) {
        val player = task.player

        val name = foodNames[food.rawItem] ?: return
        val burnName = cookedFoodNames[food.cookedItem] ?: return
        var cookable: Int

        if(forceBurn) {
            cookable = food.cookedItem
        } else {
            cookable = food.rawItem
        }

        repeat(amount) {

            if(!canCook(player, food, forceBurn)) {
                return
            }

            player.animate(obj.animation)
            player.playSound(obj.sound, 1, 0)

            player.inventory.remove(cookable)
            val level = player.getSkills().getCurrentLevel(Skills.COOKING)
            if (forceBurn) {
                player.inventory.add(food.burntItem)
                player.filterableMessage("You deliberately burn some ${burnName}.")
            } else if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 90 || player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 160) {
                player.inventory.add(food.cookedItem, 1)
                player.addXp(Skills.COOKING, food.xp)
                if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 90)
                    player.triggerEvent(Tutorial_island_plugin.CookedShrimpEvent)
                else if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 160)
                    player.triggerEvent(Tutorial_island_plugin.CookedBreadEvent)
            }
            else if (level.interpolateCheck(minChance = food.lowChance, maxChance = food.highChance)) /*|| player.getVarp(TutorialIsland.COMPLETION_VARP) == 90 || player.getVarp(TutorialIsland.COMPLETION_VARP) == 160)*/ {
                player.inventory.add(food.cookedItem, 1)
                player.addXp(Skills.COOKING, food.xp)
                player.filterableMessage("You cook some ${name}.")
            } else {
                player.inventory.add(food.burntItem, 1)
                player.filterableMessage("You accidentally burn some ${name}.")
            }
            task.wait(5)
            player.animate(-1)
        }
    }

    private fun canCook(player: Player, food: CookingFood, forceBurn: Boolean): Boolean {
        var name: String
        var cookable: Int
        if(forceBurn) {
            name = cookedFoodNames[food.cookedItem] ?: return false
            cookable = food.cookedItem
        } else {
            name = foodNames[food.rawItem] ?: return false
            cookable = food.rawItem
        }

        if(!player.inventory.contains(cookable)) {
            if(!forceBurn) {
                player.filterableMessage("You don't have any more $name to cook.")
            } else {
                player.filterableMessage("You don't have any more $name to burn.")
            }
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.COOKING) < food.minLevel && !forceBurn) {
            player.filterableMessage("You need a ${Skills.getSkillName(player.world, Skills.COOKING)} level of at least ${food.minLevel} to cook ${name}.")
            return false
        }
        return true
    }

    suspend fun combine(task: QueueTask, ingredient: CookingIngredient, amount: Int) {
        val player = task.player

        val name = ingredientNames[ingredient.result] ?: return

        repeat(amount) {

            if(!canCombine(player, ingredient)) {
                return
            }

            player.inventory.remove(ingredient.item1)
            player.inventory.remove(ingredient.item2)

            if(ingredient.usedItem1 != -1) {
                player.inventory.add(ingredient.usedItem1,1)
            }
            if(ingredient.usedItem2 != -1) {
                player.inventory.add(ingredient.usedItem2, 1)
            }

            player.inventory.add(ingredient.result, 1)
            if(ingredient.xp > 0.0) {
                player.addXp(Skills.COOKING, ingredient.xp)
            }

            if(player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 150) {
                player.world.plugins.executeEvent(player, Tutorial_island_plugin.CreateBreadDoughEvent)
            } else {
                player.filterableMessage("You combine the ingredients to make $name.")
            }
            player.filterableMessage("You combine the ingredients to make $name.")
            task.wait(cycles = 3)
        }
    }


    private fun canCombine(player: Player, ingredient: CookingIngredient): Boolean {
        val name = ingredientNames[ingredient.result] ?: return false
        if(!player.inventory.contains(ingredient.item1) || !player.inventory.contains(ingredient.item2)) {
            println("Inv doesn't contain ${ingredient.item1} or ${ingredient.item2}")
            if(player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 1000) {
                player.filterableMessage("You are missing the required ingredients to make $name.")
            }
            return false
        }

        if(player.getSkills().getCurrentLevel(Skills.COOKING) < ingredient.minLevel) {
            player.filterableMessage("You must have a ${Skills.getSkillName(player.world, Skills.COOKING)} level of ${ingredient.minLevel} to make $name.")
            return false
        }

        if(player.inventory.freeSlotCount == 0) {
            player.filterableMessage("You don't have enough inventory space to make $name.")
            return false
        }
        return true
    }
}