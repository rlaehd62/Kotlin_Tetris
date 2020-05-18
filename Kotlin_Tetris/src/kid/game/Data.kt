package kid.game

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.properties.Delegates


abstract class BlockBase
{
    abstract var data: Array<Array<Int>> protected set
    protected abstract fun createBlock() : Array<Array<Int>>

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
        override lateinit var data: Array<Array<Int>>
        init { data = createBlock() }
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(0, 1, 0),
                    arrayOf(1, 1, 1),
                    arrayOf(0, 0, 0)
            )
        }
    }

    class Line: BlockBase()
    {
        override lateinit var data: Array<Array<Int>>
        init { data = createBlock() }
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0),
                    arrayOf(0, 1, 0, 0)
            )
        }
    }

    class Square : BlockBase()
    {
        override lateinit var data: Array<Array<Int>>
        init { data = createBlock() }
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(1, 1),
                    arrayOf(1, 1)
            )
        }
    }

    class L : BlockBase()
    {
        override lateinit var data: Array<Array<Int>>
        init { data = createBlock() }
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(1, 1, 0),
                    arrayOf(0, 1, 0),
                    arrayOf(0, 1, 0)
            )
        }
    }

    class J : BlockBase()
    {
        override lateinit var data: Array<Array<Int>>
        init { data = createBlock() }
        override fun createBlock(): Array<Array<Int>>
        {
            return arrayOf(
                    arrayOf(0, 1, 1),
                    arrayOf(0, 1, 0),
                    arrayOf(0, 1, 0)
            )
        }
    }

    // [TODO-1] 나머지 종류의 블럭 클래스 만들기
}


object GameMap
{
    // [TODO-0] http://oopsilon.com/06/texts/tetris.html 참고하기
    // [TODO-2] 블럭 점차적으로 내려오게 만들기
    // [TODO-3] 충돌하면 블럭 데이터 맵에 등록하기
    // [TODO-4] 다음에 나올 블럭 설정 및 랜더링하기
    // [TODO-5] 새로운 블럭 소환하고 좌표 초기화하기
    // [TODO-6] 한 줄 완성되면 클리어하고 블럭 떨어트리기

    val map = Array(y_size) {IntArray(x_size)}
    var currentBlock: BlockBase = Block.random()
    private set

    var x = (x_size / 3)
    var y = 0

    init
    {
        map[3][5] = 1
        map[4][5] = 1
        map[4][6] = 1
        map[4][7] = 1
    }

    fun moveTo(dir: Direction)
    {
        when(dir)
        {
            Direction.LEFT, Direction.RIGHT -> if(!intersects(x+dir.value, y)) x += dir.value
            else -> if(!intersects(x, y+dir.value)) y += dir.value

        }

        if(intersects(x, y))
        {
            if(x < 0) x++
            else if(x >= x_size) x--
        }
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