import org.alter.plugins.content.area.tutorial_island.TutorialIsland


val COMBAT_INSTRUCTOR = Npc(Npcs.COMBAT_INSTRUCTOR, Tile(3106, 9509, 0), world)
COMBAT_INSTRUCTOR.respawns = true
COMBAT_INSTRUCTOR.walkRadius = 5
world.spawn(COMBAT_INSTRUCTOR)
TutorialIsland.COMBAT_INSTRUCTOR_NPC_INDEX = COMBAT_INSTRUCTOR.index