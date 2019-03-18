package io.moatwel.eddsa.internal.ed448;

import io.moatwel.eddsa.internal.PointInternal;

import java.math.BigInteger;

public class PointEd448Helper {

    private static final ThreadLocal<PointEd448Helper> INSTANCE = new ThreadLocal<PointEd448Helper>() {
        @Override
        protected PointEd448Helper initialValue() {
            return new PointEd448Helper();
        }
    };

    private PointEd448Helper() {
        // no-op
    }

    public static PointInternal scalarMultiply(PointInternal point, BigInteger integer) {
        return new PointInternal(BigInteger.ZERO, BigInteger.ZERO);
    }
}
