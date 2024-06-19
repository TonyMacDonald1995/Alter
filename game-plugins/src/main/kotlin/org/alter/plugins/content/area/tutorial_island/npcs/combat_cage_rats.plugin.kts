import org.alter.game.model.combat.NpcCombatDef

val RATS = listOf(
    Npc(Npcs.GIANT_RAT_3313, Tile(3100, 9521, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3103, 9521, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3107, 9521, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3098, 9519, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3103, 9518, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3105, 9518, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3109, 9518, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3100, 9517, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3107, 9516, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3099, 9514, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3105, 9514, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3105, 9521, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3102, 9513, 0), world),
    Npc(Npcs.GIANT_RAT_3313, Tile(3104, 9512, 0), world)
)

set_combat_def(Npcs.GIANT_RAT_3313) {
    aggro {
        neverAggro()
    }
    anims {
        this.attack = Animation.GIANT_CRYPT_RAT_ATTACK
        this.block = Animation.GIANT_CRYPT_RAT_DEFEND
        this.death = Animation.GIANT_CRYPT_RAT_DEATH
    }
    bonuses {
        attackStab = 0
        attackSlash = 0
        attackCrush = 0
        attackBonus = 0
        strengthBonus = 0
        attackMagic = 0
        magicDamageBonus = 0
        attackRanged = 0
        rangedStrengthBonus = 0
        defenceStab = -100
        defenceSlash = -100
        defenceCrush = -100
        defenceMagic = 0
        defenceRanged = -100
    }
    configs {
        attackSpeed = 4
        poisonChance = 0.0
        venomChance = 0.0
        respawnDelay = 30 //TODO Based of normal giant rants
    }
    drops {
        always { add(Items.BONES, 1, 1) }
    }
    sound {
        attackArea = false
        attackSound = Sound.RAT_ATTACK
        attackVolume = 1
        blockArea = false
        blockSound = Sound.RAT_HIT
        blockVolume = 1
        deathArea = false
        deathSound = Sound.RAT_DEATH
        deathVolume = 1
    }
    stats {
        hitpoints = 3
        attack = 1
        strength = 1
        defence = 1
        magic = 1
        ranged = 1
    }
    build()
}

RATS.forEach {
    it.respawns = true
    it.walkRadius = 5
    world.spawn(it)
}