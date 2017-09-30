package android.daehoshin.com.tetris.blocks;

/**
 * Created by daeho on 2017. 9. 29..
 */

public enum BlockType {
    UNKNOWN(0),
    I(1),
    J(2),
    L(3),
    O(4),
    S(5),
    T(6),
    Z(7);

    private final int value;
    BlockType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
