package org.alter.plugins.content.area.tutorial_island.objs.door_module

import org.alter.game.model.Tile

data class TutorialDoor(var obj: Int = -1, var replaceObj: Int = -1, var replaceRot: Int = 1, var replaceTile: Tile = Tile(0,0,0), var doorTile: Tile = Tile(0,0,0), var requiredVarpState: Int = -1, var insideTile: Tile = Tile(0,0,0), var outsideTile: Tile = Tile(0, 0, 0), var afterActionVarpState: Int = 0, var afterActionVarpBar: Int = 0, var errorMsg: String? = null, var openSound: Int = -1)

fun init_tutorial_door(init: TutorialDoorDsl.Builder.() -> Unit) {
    val builder = TutorialDoorDsl.Builder()
    init(builder)

    TutorialDoorManager.tutorialDoors.add(builder.build())
}

object TutorialDoorDsl {
    @DslMarker
    annotation class TutorialDoorDslMarker

    @TutorialDoorDslMarker
    class Builder {
        private val tutorialDoor = TutorialDoor()

        fun build() = tutorialDoor

        fun config(init: ConfigBuilder.() -> Unit) {
            val builder = ConfigBuilder()
            init(builder)

            tutorialDoor.obj = builder.obj
            tutorialDoor.replaceObj = builder.replaceObj
            tutorialDoor.replaceRot = builder.replaceRot
            tutorialDoor.replaceTile = builder.replaceTile
            tutorialDoor.doorTile = builder.doorTile
            tutorialDoor.requiredVarpState = builder.requiredVarpState
            tutorialDoor.insideTile = builder.insideTile
            tutorialDoor.outsideTile = builder.outsideTile
            tutorialDoor.afterActionVarpState = builder.afterActionVarpState
            tutorialDoor.afterActionVarpBar = builder.afterActionVarpBar
            tutorialDoor.errorMsg = builder.errorMsg
            tutorialDoor.openSound = builder.openSound

        }
    }

    class ConfigBuilder {
        var obj: Int = -1

        var replaceObj: Int = -1

        var replaceRot: Int = 1

        var doorTile: Tile = Tile(0,0,0)

        var replaceTile = Tile(0,0,0)

        var requiredVarpState: Int = -1

        var insideTile: Tile = Tile(0,0,0)

        var outsideTile: Tile = Tile(0, 0, 0)

        var afterActionVarpState: Int = -1

        var afterActionVarpBar: Int = -1

        var errorMsg: String? = null

        var openSound: Int = -1
    }
}