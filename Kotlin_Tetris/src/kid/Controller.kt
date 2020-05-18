package kid

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import kid.game.Direction
import kid.game.GameRenderer
import kid.game.GameMap
import java.net.URL
import java.util.*
import kotlin.properties.Delegates

class Controller : Initializable
{
    @FXML
    lateinit var label: Label

    @FXML
    lateinit var main: Canvas

    @FXML
    lateinit var sub: Canvas

    companion object
    {
        lateinit var mainTool: GraphicsContext
        lateinit var subTool: GraphicsContext
        var width by Delegates.notNull<Double>()
        var height by Delegates.notNull<Double>()
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?)
    {
        mainTool = main.graphicsContext2D
        subTool = sub.graphicsContext2D
        width = mainTool.canvas.width
        height = mainTool.canvas.height
    }
}
