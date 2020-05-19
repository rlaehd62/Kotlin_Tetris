package kid.game

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.properties.Delegates


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
    // [TODO-2] ~ [TODO-5] Completed
    // [TODO-6] 한 줄 완성되면 클리어하고 블럭 떨어트리기
    // [TODO-7] 완성된 줄 만큼 점수 추가하기
    // [TODO-8] 게임오버 추가하고, 게임 오버되면 리셋기능 추가하기

    val map = Array(y_size) {IntArray(x_size)}
    var currentBlock: BlockBase = Block.random()
    private set

    var nextBlock: BlockBase = Block.random()
    private set

    var x = (x_size / 3)
    var y = 0

    fun moveTo(dir: Direction)
    {
        when(dir)
        {
            Direction.LEFT, Direction.RIGHT -> if(!intersects(x+dir.value, y)) x += dir.value
            else -> if(!intersects(x, y+dir.value)) y += dir.value
        }

        if(intersects(x, y+1)) merge()
    }

    private fun merge()
    {
        val currBlock = currentBlock.data
        for(i in currBlock.indices)
            for(j in currBlock[i].indices)
            {
                val posX = x + j
                val posY = y + i

                val exists = currBlock[i][j] >= 1
                if(exists && map[posY][posX] == 0) map[posY][posX] = 1
            }

        reset()
        next()
    }


    private fun reset()
    {
        x = (x_size) / 3
        y = 0
    }

    private fun next()
    {
        BlockBase.copy(nextBlock, currentBlock)
        BlockBase.copy(Block.random(), nextBlock)
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