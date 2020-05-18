package kid.game

fun isOutOfBounds(x: Int, y: Int): Boolean
{
    if(x in 0 until x_size && y < y_size) return false
    return true
}

fun intersects(x: Int, y: Int, isRotated: Boolean = false): Boolean
{
    val block = if(isRotated) GameMap.currentBlock.preRotate() else GameMap.currentBlock.data
    val map = GameMap.map

    for(i in block.indices)
        for(j in block[0].indices)
        {
            val posX = x + j
            val posY = y + i
            if(block[i][j] >= 1 && (isOutOfBounds(posX, posY) || map[posY][posX] >= 1)) return true
        }

    return false
}