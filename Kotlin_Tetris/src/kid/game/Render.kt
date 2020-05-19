package kid.game

import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.scene.canvas.GraphicsContext
import kid.Controller
import kotlin.math.max

const val size: Double = 20.0
object GameRenderer
{
    private val KEY = Any()
    fun render(gc: GraphicsContext)
    {
        Platform.runLater {
            renderMap(gc)
            renderBlock(gc)
            renderNextBlock(Controller.subTool)
        }
    }

    fun renderMap(gc: GraphicsContext)
    {
        val maxWidth = gc.canvas.width
        val maxHeight = gc.canvas.height

        gc.clearRect(0.0, 0.0, maxWidth, maxHeight)
        for (y in 0 until y_size) gc.strokeLine(0.0, y*size, maxWidth, y*size)
        for (x in 0 until x_size) gc.strokeLine(x*size, 0.0, x*size, maxHeight)

    }

    private fun renderBlock(gc: GraphicsContext)
    {
        val temp = GameMap.currentBlock.data
        for(Y in temp.indices step 1)
        {
            for (X in temp[Y].indices step 1)
            {
                val totalX = (GameMap.calculateX(X))
                val totalY = (GameMap.calculateY(Y))
                if (temp[Y][X] >= 1) gc.fillRect(totalX*size, totalY*size, size - 1.0, size - 1.0)
            }
        }

        for(Y in GameMap.map.indices)
        {
            for (X in GameMap.map[0].indices)
            {
                if (GameMap.map[Y][X] >= 1) gc.fillRect(X*size, Y*size, size - 1.0, size - 1.0)
            }
        }
    }

    private fun renderNextBlock(gc: GraphicsContext)
    {
        val temp = GameMap.nextBlock.data
        val maxWidth = gc.canvas.width
        val maxHeight = gc.canvas.height
        gc.clearRect(0.0, 0.0, maxWidth, maxHeight)

        for(Y in temp.indices step 1)
        {
            for (X in temp[Y].indices step 1)
            {
                val totalX = 4 + X
                val totalY = 3 + Y
                if (temp[Y][X] >= 1) gc.fillRect(totalX*size, totalY*size, size - 1.0, size - 1.0)
            }
        }
    }
}