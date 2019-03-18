package io.moatwel.eddsa.internal;

import java.math.BigInteger;

public class PointInternal {

    private BigInteger x;
    private BigInteger y;

    public PointInternal(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
