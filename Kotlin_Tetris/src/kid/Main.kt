package kid

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import kid.game.*
import kid.game.GameMap.moveTo
import kid.game.GameRenderer.render
import kid.game.GameRenderer.renderMap

class Main : Application()
{
    @Throws(Exception::class)
    override fun start(primaryStage: Stage)
    {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("game.fxml"))
        val scene = Scene(root)

        primaryStage.title = "Tetris written in Kotlin"
        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.show()

        scene.addEventFilter(KeyEvent.KEY_PRESSED) { keyEvent: KeyEvent ->
            when (keyEvent.code)
            {
                KeyCode.W -> if(!intersects(GameMap.x, GameMap.y, true)) GameMap.currentBlock.rotate()
                KeyCode.A -> moveTo(Direction.LEFT)
                KeyCode.D -> moveTo(Direction.RIGHT)
                KeyCode.S -> moveTo(Direction.DOWN)
            }
        }

        GameFlowManager().start()
    }

    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            launch(Main::class.java)
        }
    }
}