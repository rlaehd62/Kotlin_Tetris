package kid.game

fun Array<Array<Int>>.copy() = Array(size) { get(it).clone() }
fun isOutOfBounds(x: Int, y: Int): Boolean
{
    if(x in 0 until x_size && y < y_size) return false
    return true
}

fun intersects(x: Int, y: Int, isRotated: Boolean = false): Boolean
{
    val currentBlock = GameMap.currentBlock
    val gameMap = GameMap.map

    val block = if(isRotated) currentBlock.preRotate() else currentBlock.data
    for(i in block.indices)
        for(j in block[0].indices)
        {
            val posX = x + j
            val posY = y + i

            val exists = block[i][j] >= 1
            if(exists && (isOutOfBounds(posX, posY) || gameMap[posY][posX] >= 1)) return true
        }

    return false
}

fun merges(data: Array<Array<Int>>)
{
    for(i in data.indices)
        for(j in data[i].indices)
        {
            val posX = GameMap.x + j
            val posY = GameMap.y + i
            val exists = data[i][j] >= 1
            if(exists && GameMap.map[posY][posX] == 0) GameMap.map[posY][posX] = 1
        }
}