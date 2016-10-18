package hku.cs.four4fun.util;

/**
 * Created by Niko Feng on 10/18/2016.
 */

public class ChessTuple<A, B, C> {
    private A row;
    private B column;
    private C role;

    public ChessTuple(A row, B column, C role) {
        this.row = row;
        this.column = column;
        this.role = role;
    }

    public A getRow() {
        return row;
    }

    public B getColumn() {
        return column;
    }

    public C getRole() {
        return role;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
