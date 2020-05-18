package kid.game

enum class BlockType
{
    LINE { override val block: BlockBase get() = Block.Line() },
    SQUARE { override val block: BlockBase get() = Block.Square() },
    L { override val block: BlockBase get() = Block.L() },
    J { override val block: BlockBase get() = Block.J() },
    T { override val block: BlockBase get() = Block.T() },
    Z { override val block: BlockBase get() = Block.L() },  // TODO
    S { override val block: BlockBase get() = Block.L() };  // TODO
    abstract val block: BlockBase
}

enum class Direction
{
    LEFT { override val value: Int get() = -1 },
    RIGHT { override val value: Int get() = +1 },
    DOWN  { override val value: Int get() = +1 };
    abstract val value: Int
}