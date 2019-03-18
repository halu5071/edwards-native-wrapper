package io.moatwel.eddsa.internal;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import java.io.File;

public class LibPoint {

    private static final Class SIZE_T_CLASS;

    static {
        if (Native.SIZE_T_SIZE == 4) {
            SIZE_T_CLASS = SizeT4.class;
        } else if (Native.SIZE_T_SIZE == 8) {
            SIZE_T_CLASS = SizeT8.class;
        } else {
            throw new AssertionError("Unexpected Native.SIZE_T_SIZE: " + Native.SIZE_T_SIZE);
        }
    }

    static {
        loadLibPoint();
    }

    private static void loadLibPoint() {
        try {
            // Explicitly try to load the embedded version first.
            File file = Native.extractFromResourcePath("point", LibPoint.class.getClassLoader());
            load(file.getAbsolutePath());
            return;
        } catch (Exception ignored) {
        } catch (UnsatisfiedLinkError ignored) {
        }
        // Fall back to system-wide search.
        load("point");
    }

    private static void load(String name) {
        NativeLibrary library = NativeLibrary.getInstance(name, LibPoint.class.getClassLoader());
        Native.register(LibPoint.class, library);
        Native.register(SIZE_T_CLASS, library);
    }

    public static int readSizeT(Pointer ptr) {
        // TODO(scottb): make not public.
        if (SIZE_T_CLASS == SizeT4.class) {
            int result;
            result = ptr.getInt(0);
            assert result >= 0;
            return result;
        } else {
            long result = ptr.getLong(0);
            assert result >= 0;
            assert result < Integer.MAX_VALUE;
            return (int) result;
        }
    }

    public static class mpz_t extends Pointer {
        /**
         * The size, in bytes, of the native structure.
         */
        public static final int SIZE = 16;

        /**
         * Constructs an mpz_t from a native address.
         *
         * @param peer the address of a block of native memory at least {@link #SIZE} bytes large
         */
        public mpz_t(long peer) {
            super(peer);
        }

        /**
         * Constructs an mpz_t from a Pointer.
         *
         * @param from an block of native memory at least {@link #SIZE} bytes large
         */
        public mpz_t(Pointer from) {
            this(Pointer.nativeValue(from));
        }
    }

    /**
     * Used on systems with 4-byte size_t.
     */
    static class SizeT4 {
        static native void __gmpz_import(mpz_t rop, int count, int order, int size, int endian,
                                         int nails, Pointer buffer);

        static native Pointer __gmpz_export(Pointer rop, Pointer countp, int order, int size,
                                            int endian, int nails, mpz_t op);
    }

    /**
     * Used on systems with 8-byte size_t.
     */
    static class SizeT8 {
        static native void __gmpz_import(mpz_t rop, long count, int order, int size, int endian,
                                         long nails, Pointer buffer);

        static native Pointer __gmpz_export(Pointer rop, Pointer countp, int order, long size,
                                            int endian, long nails, mpz_t op);
    }

    public static void __gmpz_import(mpz_t rop, int count, int order, int size, int endian, int nails,
                                     Pointer buffer) {
        if (SIZE_T_CLASS == SizeT4.class) {
            SizeT4.__gmpz_import(rop, count, order, size, endian, nails, buffer);
        } else {
            SizeT8.__gmpz_import(rop, count, order, size, endian, nails, buffer);
        }
    }

    public static void __gmpz_export(Pointer rop, Pointer countp, int order, int size, int endian,
                                     int nails, mpz_t op) {
        if (SIZE_T_CLASS == SizeT4.class) {
            SizeT4.__gmpz_export(rop, countp, order, size, endian, nails, op);
        } else {
            SizeT8.__gmpz_export(rop, countp, order, size, endian, nails, op);
        }
    }

    public static native void scalar_mul(mpz_t dest_x, mpz_t dest_y, mpz_t point_x, mpz_t point_y, mpz_t seed);

    public static native void __gmpz_neg(mpz_t rop, mpz_t op);

    public static native void __gmpz_init(mpz_t integer);

    public static native void __gmpz_clear(mpz_t x);

    public static native int __gmpz_cmp_si(mpz_t op1, NativeLong op2);
}
