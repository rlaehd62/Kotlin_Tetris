package kid.game

import kid.Controller
import java.util.*


abstract class BlockBase
{
    var data: Array<Array<Int>> protected set
    protected abstract fun createBlock() : Array<Array<Int>>

    init { data = createBlock() }
    companion object
    {
        fun copy(from: BlockBase, to: BlockBase) { to.data = from.data.copy() }
    }

    private fun blankBlock(): Array<Array<Int>> = Array(data.size) { Array(data[0].size) {0} }
    operator fun get(index: Int): Array<Int> = data[index]

    fun rotate()
    {
        val newBlock = blankBlock()
        val rows = newBlock.size

        for (i in data.indices) for (j in data.indices) newBlock[i][j] = data[rows-1-j][i]
        data = newBlock
    }

    fun preRotate(): Array<Array<Int>>
    {
        val newBlock = blankBlock()
        val rows = newBlock.size

        for (i in data.indices) for (j in data.indices) newBlock[i][j] = data[rows-1-j][i]
        return newBlock;
    }

}

sealed class Block
{
    companion object
    {
        fun random() : BlockBase
        {
            val index = Random().nextInt(BlockType.values().size)
            return BlockType.values()[index].block
        }
    }

    class T  : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf (
                    arrayOf(0, 1, 0),
                    arrayOf(1, 1, 1),
                    arrayOf(0, 0, 0)
            )
        }
    }

    class Line: BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf (
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0)
            )
        }
    }

    class Square : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf (
                    arrayOf(1, 1),
                    arrayOf(1, 1)
            )
        }
    }

    class L : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf (
                    arrayOf(1, 1, 0),
                    arrayOf(0, 1, 0),
                    arrayOf(0, 1, 0)
            )
        }
    }

    class J : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(0, 1, 1),
                    arrayOf(0, 1, 0),
                    arrayOf(0, 1, 0)
            )
        }
    }

    class Z : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(0, 1, 0),
                    arrayOf(1, 1, 0),
                    arrayOf(1, 0, 0)
            )
        }
    }

    class S : BlockBase()
    {
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(1, 0, 0),
                    arrayOf(1, 1, 0),
                    arrayOf(0, 1, 0)
            )
        }
    }
}

const val x_size: Int = 19
const val y_size: Int = 20
object GameMap
{
    // [TODO-0] http://oopsilon.com/06/texts/tetris.html 참고하기
    // [TODO-2] ~ [TODO-8] Completed

    val map = Array(y_size) {IntArray(x_size)}
    var currentBlock: BlockBase = Block.random()
        private set

    var nextBlock: BlockBase = Block.random()
        private set

    var x = (x_size / 3)
        private set

    var y = 0
        private set

    fun moveTo(dir: Direction, only_move: Boolean = false)
    {
        when(dir)
        {
            Direction.LEFT, Direction.RIGHT -> if(!intersects(x+dir.value, y)) x += dir.value
            else -> if(!intersects(x, y+dir.value)) y += dir.value
        }

        if(only_move) return
        else if(intersects(x, y+1)) merge()
    }

    private fun merge()
    {
        val currBlock = currentBlock.data
        merges(currBlock)

        if(isFull()) GameFlowManager.reset()
        else
        {
            reset()
            next()
            clear()
        }

    }

    private tailrec fun clear()
    {
        fun clearRow(y: Int) { map[y] = IntArray(x_size) { 0 } }
        fun isLined(y: Int): Boolean
        {
            for(x in map[y].indices) if(map[y][x] <= 0) return false
            return true
        }

        var count = 0
        for(y in map.indices.reversed())
        {
            if(isLined(y))
            {
                clearRow(y)
                drop(y)
                count++
            }
        }

        if(count > 0)
        {
            Controller.addScore(count * 100)
            Controller.updateScore()
            clear()
        }
    }

    private fun drop(y1: Int)
    {
        var queue: Queue<Int> = LinkedList()
        for(x in map[0].indices)
        {
            for(y in 0 until y1)
            {
                queue.offer(map[y][x])
                map[y][x] = 0
            }

            queue = LinkedList(queue.reversed())
            for(y in y1 downTo 0)
            {
                if(queue.isEmpty()) break
                else map[y][x] = queue.poll()
            }

            queue.removeAll(queue)
        }
    }

    fun reset(totally: Boolean = false)
    {
        x = (x_size) / 3
        y = -1

        if(totally)
        {
            currentBlock = Block.random()
            nextBlock = Block.random()
            resetMap()
        }
    }



    private fun resetMap()
    {
        for(y in map.indices) for(x in map[y].indices) map[y][x] = 0
    }

    private fun next()
    {
        BlockBase.copy(nextBlock, currentBlock)
        BlockBase.copy(Block.random(), nextBlock)
    }

    private fun isFull(): Boolean
    {
        for(y in 3 downTo 0)
            for(x in map[y].indices)
                if(map[y][x] >= 1) return true

        return false
    }

    fun calculateX(value: Int): Int
    {
        return x +value
    }

    fun calculateY(value: Int): Int
    {
        return y +value
    }
}