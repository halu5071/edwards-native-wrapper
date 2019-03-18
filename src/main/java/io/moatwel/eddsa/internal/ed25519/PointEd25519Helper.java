package io.moatwel.eddsa.internal.ed25519;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import io.moatwel.eddsa.internal.LibPoint.mpz_t;
import io.moatwel.eddsa.internal.PointInternal;

import java.math.BigInteger;

import static io.moatwel.eddsa.internal.LibPoint.__gmpz_clear;
import static io.moatwel.eddsa.internal.LibPoint.__gmpz_cmp_si;
import static io.moatwel.eddsa.internal.LibPoint.__gmpz_export;
import static io.moatwel.eddsa.internal.LibPoint.__gmpz_import;
import static io.moatwel.eddsa.internal.LibPoint.__gmpz_init;
import static io.moatwel.eddsa.internal.LibPoint.__gmpz_neg;
import static io.moatwel.eddsa.internal.LibPoint.readSizeT;
import static io.moatwel.eddsa.internal.LibPoint.scalar_mul;

public class PointEd25519Helper {

    private static final ThreadLocal<PointEd25519Helper> INSTANCE = new ThreadLocal<PointEd25519Helper>() {
        @Override
        protected PointEd25519Helper initialValue() {
            return new PointEd25519Helper();
        }
    };

    /**
     * Initial bit size of the scratch buffer.
     */
    private static final int INITIAL_BUF_BITS = 2048;
    private static final int INITIAL_BUF_SIZE = INITIAL_BUF_BITS / 8;

    private static final int MAX_OPERANDS = 5;

    private static final int SHARED_MEM_SIZE = mpz_t.SIZE * MAX_OPERANDS + Native.SIZE_T_SIZE;

    private final mpz_t[] sharedOperands = new mpz_t[MAX_OPERANDS];

    private final Memory sharedMem = new Memory(SHARED_MEM_SIZE) {
        /** Must explicitly destroy the gmp_t structs before freeing the underlying memory. */
        @Override
        protected void finalize() {
            for (mpz_t sharedOperand : sharedOperands) {
                if (sharedOperand != null) {
                    __gmpz_clear(sharedOperand);
                }
            }
            super.finalize();
        }
    };

    private PointEd25519Helper() {
        int offset = 0;
        for (int i = 0; i < MAX_OPERANDS; ++i) {
            this.sharedOperands[i] = new mpz_t(sharedMem.share(offset, mpz_t.SIZE));
            __gmpz_init(sharedOperands[i]);
            offset += mpz_t.SIZE;
        }
        this.countPtr = sharedMem.share(offset, Native.SIZE_T_SIZE);
        offset += Native.SIZE_T_SIZE;
        assert offset == SHARED_MEM_SIZE;
    }

    public static PointInternal scalarMultiply(PointInternal point, BigInteger integer) {
        return INSTANCE.get().scalarMultiplyImpl(point, integer);
    }

    private PointInternal scalarMultiplyImpl(PointInternal point, BigInteger integer) {
        mpz_t x = getPeer(point.getX(), sharedOperands[0]);
        mpz_t y = getPeer(point.getY(), sharedOperands[1]);
        mpz_t seed = getPeer(integer, sharedOperands[2]);

        scalar_mul(sharedOperands[3], sharedOperands[4], x, y, seed);

        // TODO: Calculate
        int requireSize = 32;
        BigInteger scalardX = new BigInteger(mpzSgn(sharedOperands[3]), mpzExport(sharedOperands[3], requireSize));
        BigInteger scalardY = new BigInteger(mpzSgn(sharedOperands[4]), mpzExport(sharedOperands[4], requireSize));

        return new PointInternal(scalardX, scalardY);
    }

    private static final NativeLong ZERO = new NativeLong();
    /**
     * Reusable scratch buffer for moving data between byte[] and mpz_t.
     */
    private Memory scratchBuf = new Memory(INITIAL_BUF_SIZE);
    private final Pointer countPtr;

    private mpz_t getPeer(BigInteger value, mpz_t sharedPeer) {
        mpzImport(sharedPeer, value.signum(), value.abs().toByteArray());
        return sharedPeer;
    }

    void mpzImport(mpz_t ptr, int signum, byte[] bytes) {
        int expectedLength = bytes.length;
        ensureBufferSize(expectedLength);
        scratchBuf.write(0, bytes, 0, bytes.length);
        __gmpz_import(ptr, bytes.length, 1, 1, 1, 0, scratchBuf);
        if (signum < 0) {
            __gmpz_neg(ptr, ptr);
        }
    }

    private byte[] mpzExport(mpz_t ptr, int requiredSize) {
        ensureBufferSize(requiredSize);
        __gmpz_export(scratchBuf, countPtr, 1, 1, 1, 0, ptr);

        int count = readSizeT(countPtr);
        byte[] result = new byte[count];
        scratchBuf.read(0, result, 0, count);
        return result;
    }

    int mpzSgn(mpz_t ptr) {
        int result = __gmpz_cmp_si(ptr, ZERO);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    private void ensureBufferSize(int size) {
        if (scratchBuf.size() < size) {
            long newSize = scratchBuf.size();
            while (newSize < size) {
                newSize <<= 1;
            }
            scratchBuf = new Memory(newSize);
        }
    }
}
