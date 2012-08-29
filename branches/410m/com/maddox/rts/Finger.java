// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Finger.java

package com.maddox.rts;

import java.io.File;
import java.io.FileInputStream;

// Referenced classes of package com.maddox.rts:
//            HomePath, SFSInputStream

public final class Finger
{

    public static final long Long(java.lang.String s)
    {
        return com.maddox.rts.Finger.incLong(0L, s);
    }

    public static final native long Long(byte abyte0[]);

    public static final native long Long(short aword0[]);

    public static final native long Long(char ac[]);

    public static final native long Long(int ai[]);

    public static final native long Long(float af[]);

    public static final native long Long(double ad[]);

    public static final int Int(java.lang.String s)
    {
        return com.maddox.rts.Finger.incInt(0, s);
    }

    public static final native int Int(byte abyte0[]);

    public static final native int Int(short aword0[]);

    public static final native int Int(char ac[]);

    public static final native int Int(int ai[]);

    public static final native int Int(float af[]);

    public static final native int Int(double ad[]);

    public static final native long incLong(long l, byte abyte0[]);

    public static final native long incLong(long l, short aword0[]);

    public static final native long incLong(long l, char ac[]);

    public static final native long incLong(long l, int ai[]);

    public static final native long incLong(long l, float af[]);

    public static final native long incLong(long l, double ad[]);

    public static final native int incInt(int i, byte abyte0[]);

    public static final native int incInt(int i, short aword0[]);

    public static final native int incInt(int i, char ac[]);

    public static final native int incInt(int i, int ai[]);

    public static final native int incInt(int i, float af[]);

    public static final native int incInt(int i, double ad[]);

    public static final long Long(boolean flag)
    {
        _i[0] = flag ? 1 : 0;
        return com.maddox.rts.Finger.Long(_i);
    }

    public static final long Long(int i)
    {
        _i[0] = i;
        return com.maddox.rts.Finger.Long(_i);
    }

    public static final long Long(long l)
    {
        _l[0] = (int)(l & -1L);
        _l[1] = (int)(l >> 32 & -1L);
        return com.maddox.rts.Finger.Long(_l);
    }

    public static final long Long(double d)
    {
        _d[0] = d;
        return com.maddox.rts.Finger.Long(_d);
    }

    public static final int Int(boolean flag)
    {
        _i[0] = flag ? 1 : 0;
        return com.maddox.rts.Finger.Int(_i);
    }

    public static final int Int(int i)
    {
        _i[0] = i;
        return com.maddox.rts.Finger.Int(_i);
    }

    public static final int Int(long l)
    {
        _l[0] = (int)(l & -1L);
        _l[1] = (int)(l >> 32 & -1L);
        return com.maddox.rts.Finger.Int(_l);
    }

    public static final int Int(double d)
    {
        _d[0] = d;
        return com.maddox.rts.Finger.Int(_d);
    }

    public static final long incLong(long l, boolean flag)
    {
        _i[0] = flag ? 1 : 0;
        return com.maddox.rts.Finger.incLong(l, _i);
    }

    public static final long incLong(long l, int i)
    {
        _i[0] = i;
        return com.maddox.rts.Finger.incLong(l, _i);
    }

    public static final long incLong(long l, long l1)
    {
        _l[0] = (int)(l1 & -1L);
        _l[1] = (int)(l1 >> 32 & -1L);
        return com.maddox.rts.Finger.incLong(l, _l);
    }

    public static final long incLong(long l, double d)
    {
        _d[0] = d;
        return com.maddox.rts.Finger.incLong(l, _d);
    }

    public static final int incInt(int i, boolean flag)
    {
        _i[0] = flag ? 1 : 0;
        return com.maddox.rts.Finger.incInt(i, _i);
    }

    public static final int incInt(int i, int j)
    {
        _i[0] = j;
        return com.maddox.rts.Finger.incInt(i, _i);
    }

    public static final int incInt(int i, long l)
    {
        _l[0] = (int)(l & -1L);
        _l[1] = (int)(l >> 32 & -1L);
        return com.maddox.rts.Finger.incInt(i, _l);
    }

    public static final int incInt(int i, double d)
    {
        _d[0] = d;
        return com.maddox.rts.Finger.incInt(i, _d);
    }

    public static final long file(long l, java.lang.String s, int i)
    {
        java.lang.String s1 = com.maddox.rts.HomePath.toFileSystemName(s, 0);
        java.io.File file1 = new File(s1);
        int j = (int)file1.length();
        if(j == 0)
            return 0L;
        if(i == -1)
            i = j;
        byte abyte0[] = new byte[1024];
        try
        {
            java.io.FileInputStream fileinputstream = new FileInputStream(s1);
            int k;
            for(; i > 0; i -= k)
            {
                k = abyte0.length;
                if(k > i)
                {
                    k = i;
                    abyte0 = new byte[k];
                }
                fileinputstream.read(abyte0, 0, k);
                l = com.maddox.rts.Finger.incLong(l, abyte0);
            }

            fileinputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            return 0L;
        }
        return l;
    }

    private Finger()
    {
    }

    public static final long incLong(long l, java.lang.String s)
    {
        int i = (int)(l & 0xffffffffL);
        int j = (int)(l >>> 32 & 0xffffffffL);
        int k = s.length();
        for(int i1 = 0; i1 < k; i1++)
        {
            char c = s.charAt(i1);
            int j1 = c & 0xff;
            int k1 = i >>> 24;
            i <<= 8;
            i |= j1;
            i ^= FPaTable[k1];
            k1 = j >>> 24;
            j <<= 8;
            j |= j1;
            j ^= FPbTable[k1];
            j1 = c >> 8 & 0xff;
            k1 = i >>> 24;
            i <<= 8;
            i |= j1;
            i ^= FPaTable[k1];
            k1 = j >>> 24;
            j <<= 8;
            j |= j1;
            j ^= FPbTable[k1];
        }

        return (long)i & 0xffffffffL | (long)j << 32;
    }

    public static final int incInt(int i, java.lang.String s)
    {
        int j = i;
        int k = s.length();
        for(int l = 0; l < k; l++)
        {
            char c = s.charAt(l);
            int i1 = c & 0xff;
            int j1 = j >>> 24;
            j <<= 8;
            j |= i1;
            j ^= FPaTable[j1];
            i1 = c >> 8 & 0xff;
            j1 = j >>> 24;
            j <<= 8;
            j |= i1;
            j ^= FPaTable[j1];
        }

        return j;
    }

    public static final long LongFN(long l, java.lang.String s)
    {
        return com.maddox.rts.Finger.LongFN(l, s, s.length());
    }

    public static final long LongFN(long l, java.lang.String s, int i)
    {
        int j = (int)(l & 0xffffffffL);
        int k = (int)(l >>> 32 & 0xffffffffL);
        for(int i1 = 0; i1 < i; i1++)
        {
            int j1 = s.charAt(i1);
            if(j1 > 96 && j1 < 123)
                j1 &= 0xdf;
            else
            if(j1 == 47)
                j1 = 92;
            int k1 = j >>> 24;
            j <<= 8;
            j |= j1;
            j ^= FPaTable[k1];
            k1 = k >>> 24;
            k <<= 8;
            k |= j1;
            k ^= FPbTable[k1];
        }

        return (long)j & 0xffffffffL | (long)k << 32;
    }

    private static int _l[] = new int[2];
    private static int _i[] = new int[1];
    private static double _d[] = new double[1];
    private static int FPaTable[] = {
        0, 0x23788d5e, 0x46f11abc, 0x658997e2, 0xde23578, 0x2e9ab826, 0x4b132fc4, 0x686ba29a, 0x38bce7ae, 0x1bc46af0, 
        0x7e4dfd12, 0x5d35704c, 0x355ed2d6, 0x16265f88, 0x73afc86a, 0x50d74534, 0x7179cf5c, 0x52014202, 0x3788d5e0, 0x14f058be, 
        0x7c9bfa24, 0x5fe3777a, 0x3a6ae098, 0x19126dc6, 0x49c528f2, 0x6abda5ac, 0xf34324e, 0x2c4cbf10, 0x44271d8a, 0x675f90d4, 
        0x2d60736, 0x21ae8a68, 0x62f39eb8, 0x418b13e6, 0x24028404, 0x77a095a, 0x6f11abc0, 0x4c69269e, 0x29e0b17c, 0xa983c22, 
        0x5a4f7916, 0x7937f448, 0x1cbe63aa, 0x3fc6eef4, 0x57ad4c6e, 0x74d5c130, 0x115c56d2, 0x3224db8c, 0x138a51e4, 0x30f2dcba, 
        0x557b4b58, 0x7603c606, 0x1e68649c, 0x3d10e9c2, 0x58997e20, 0x7be1f37e, 0x2b36b64a, 0x84e3b14, 0x6dc7acf6, 0x4ebf21a8, 
        0x26d48332, 0x5ac0e6c, 0x6025998e, 0x435d14d0, 0x669fb02e, 0x45e73d70, 0x206eaa92, 0x31627cc, 0x6b7d8556, 0x48050808, 
        0x2d8c9fea, 0xef412b4, 0x5e235780, 0x7d5bdade, 0x18d24d3c, 0x3baac062, 0x53c162f8, 0x70b9efa6, 0x15307844, 0x3648f51a, 
        0x17e67f72, 0x349ef22c, 0x511765ce, 0x726fe890, 0x1a044a0a, 0x397cc754, 0x5cf550b6, 0x7f8ddde8, 0x2f5a98dc, 0xc221582, 
        0x69ab8260, 0x4ad30f3e, 0x22b8ada4, 0x1c020fa, 0x6449b718, 0x47313a46, 0x46c2e96, 0x2714a3c8, 0x429d342a, 0x61e5b974, 
        0x98e1bee, 0x2af696b0, 0x4f7f0152, 0x6c078c0c, 0x3cd0c938, 0x1fa84466, 0x7a21d384, 0x59595eda, 0x3132fc40, 0x124a711e, 
        0x77c3e6fc, 0x54bb6ba2, 0x7515e1ca, 0x566d6c94, 0x33e4fb76, 0x109c7628, 0x78f7d4b2, 0x5b8f59ec, 0x3e06ce0e, 0x1d7e4350, 
        0x4da90664, 0x6ed18b3a, 0xb581cd8, 0x28209186, 0x404b331c, 0x6333be42, 0x6ba29a0, 0x25c2a4fe, 0x6e47ed02, 0x4d3f605c, 
        0x28b6f7be, 0xbce7ae0, 0x63a5d87a, 0x40dd5524, 0x2554c2c6, 0x62c4f98, 0x56fb0aac, 0x758387f2, 0x100a1010, 0x33729d4e, 
        0x5b193fd4, 0x7861b28a, 0x1de82568, 0x3e90a836, 0x1f3e225e, 0x3c46af00, 0x59cf38e2, 0x7ab7b5bc, 0x12dc1726, 0x31a49a78, 
        0x542d0d9a, 0x775580c4, 0x2782c5f0, 0x4fa48ae, 0x6173df4c, 0x420b5212, 0x2a60f088, 0x9187dd6, 0x6c91ea34, 0x4fe9676a, 
        0xcb473ba, 0x2fccfee4, 0x4a456906, 0x693de458, 0x15646c2, 0x222ecb9c, 0x47a75c7e, 0x64dfd120, 0x34089414, 0x1770194a, 
        0x72f98ea8, 0x518103f6, 0x39eaa16c, 0x1a922c32, 0x7f1bbbd0, 0x5c63368e, 0x7dcdbce6, 0x5eb531b8, 0x3b3ca65a, 0x18442b04, 
        0x702f899e, 0x535704c0, 0x36de9322, 0x15a61e7c, 0x45715b48, 0x6609d616, 0x38041f4, 0x20f8ccaa, 0x48936e30, 0x6bebe36e, 
        0xe62748c, 0x2d1af9d2, 0x8d85d2c, 0x2ba0d072, 0x4e294790, 0x6d51cace, 0x53a6854, 0x2642e50a, 0x43cb72e8, 0x60b3ffb6, 
        0x3064ba82, 0x131c37dc, 0x7695a03e, 0x55ed2d60, 0x3d868ffa, 0x1efe02a4, 0x7b779546, 0x580f1818, 0x79a19270, 0x5ad91f2e, 
        0x3f5088cc, 0x1c280592, 0x7443a708, 0x573b2a56, 0x32b2bdb4, 0x11ca30ea, 0x411d75de, 0x6265f880, 0x7ec6f62, 0x2494e23c, 
        0x4cff40a6, 0x6f87cdf8, 0xa0e5a1a, 0x2976d744, 0x6a2bc394, 0x49534eca, 0x2cdad928, 0xfa25476, 0x67c9f6ec, 0x44b17bb2, 
        0x2138ec50, 0x240610e, 0x5297243a, 0x71efa964, 0x14663e86, 0x371eb3d8, 0x5f751142, 0x7c0d9c1c, 0x19840bfe, 0x3afc86a0, 
        0x1b520cc8, 0x382a8196, 0x5da31674, 0x7edb9b2a, 0x16b039b0, 0x35c8b4ee, 0x5041230c, 0x7339ae52, 0x23eeeb66, 0x966638, 
        0x651ff1da, 0x46677c84, 0x2e0cde1e, 0xd745340, 0x68fdc4a2, 0x4b8549fc
    };
    private static int FPbTable[] = {
        0, 0x1434182e, 0x3c5c2872, 0x2868305c, 0x6c8c48ca, 0x78b850e4, 0x50d060b8, 0x44e47896, 0x4d2c89ba, 0x59189194, 
        0x7170a1c8, 0x6544b9e6, 0x21a0c170, 0x3594d95e, 0x1dfce902, 0x9c8f12c, 0x1a591374, 0xe6d0b5a, 0x26053b06, 0x32312328, 
        0x76d55bbe, 0x62e14390, 0x4a8973cc, 0x5ebd6be2, 0x57759ace, 0x434182e0, 0x6b29b2bc, 0x7f1daa92, 0x3bf9d204, 0x2fcdca2a, 
        0x7a5fa76, 0x1391e258, 0x20863ec6, 0x34b226e8, 0x1cda16b4, 0x8ee0e9a, 0x4c0a760c, 0x583e6e22, 0x70565e7e, 0x64624650, 
        0x6daab77c, 0x799eaf52, 0x51f69f0e, 0x45c28720, 0x126ffb6, 0x1512e798, 0x3d7ad7c4, 0x294ecfea, 0x3adf2db2, 0x2eeb359c, 
        0x68305c0, 0x12b71dee, 0x56536578, 0x42677d56, 0x6a0f4d0a, 0x7e3b5524, 0x77f3a408, 0x63c7bc26, 0x4baf8c7a, 0x5f9b9454, 
        0x1b7fecc2, 0xf4bf4ec, 0x2723c4b0, 0x3317dc9e, 0x553865a2, 0x410c7d8c, 0x69644dd0, 0x7d5055fe, 0x39b42d68, 0x2d803546, 
        0x5e8051a, 0x11dc1d34, 0x1814ec18, 0xc20f436, 0x2448c46a, 0x307cdc44, 0x7498a4d2, 0x60acbcfc, 0x48c48ca0, 0x5cf0948e, 
        0x4f6176d6, 0x5b556ef8, 0x733d5ea4, 0x6709468a, 0x23ed3e1c, 0x37d92632, 0x1fb1166e, 0xb850e40, 0x24dff6c, 0x1679e742, 
        0x3e11d71e, 0x2a25cf30, 0x6ec1b7a6, 0x7af5af88, 0x529d9fd4, 0x46a987fa, 0x75be5b64, 0x618a434a, 0x49e27316, 0x5dd66b38, 
        0x193213ae, 0xd060b80, 0x256e3bdc, 0x315a23f2, 0x3892d2de, 0x2ca6caf0, 0x4cefaac, 0x10fae282, 0x541e9a14, 0x402a823a, 
        0x6842b266, 0x7c76aa48, 0x6fe74810, 0x7bd3503e, 0x53bb6062, 0x478f784c, 0x36b00da, 0x175f18f4, 0x3f3728a8, 0x2b033086, 
        0x22cbc1aa, 0x36ffd984, 0x1e97e9d8, 0xaa3f1f6, 0x4e478960, 0x5a73914e, 0x721ba112, 0x662fb93c, 0x3e44d36a, 0x2a70cb44, 
        0x218fb18, 0x162ce336, 0x52c89ba0, 0x46fc838e, 0x6e94b3d2, 0x7aa0abfc, 0x73685ad0, 0x675c42fe, 0x4f3472a2, 0x5b006a8c, 
        0x1fe4121a, 0xbd00a34, 0x23b83a68, 0x378c2246, 0x241dc01e, 0x3029d830, 0x1841e86c, 0xc75f042, 0x489188d4, 0x5ca590fa, 
        0x74cda0a6, 0x60f9b888, 0x693149a4, 0x7d05518a, 0x556d61d6, 0x415979f8, 0x5bd016e, 0x11891940, 0x39e1291c, 0x2dd53132, 
        0x1ec2edac, 0xaf6f582, 0x229ec5de, 0x36aaddf0, 0x724ea566, 0x667abd48, 0x4e128d14, 0x5a26953a, 0x53ee6416, 0x47da7c38, 
        0x6fb24c64, 0x7b86544a, 0x3f622cdc, 0x2b5634f2, 0x33e04ae, 0x170a1c80, 0x49bfed8, 0x10afe6f6, 0x38c7d6aa, 0x2cf3ce84, 
        0x6817b612, 0x7c23ae3c, 0x544b9e60, 0x407f864e, 0x49b77762, 0x5d836f4c, 0x75eb5f10, 0x61df473e, 0x253b3fa8, 0x310f2786, 
        0x196717da, 0xd530ff4, 0x6b7cb6c8, 0x7f48aee6, 0x57209eba, 0x43148694, 0x7f0fe02, 0x13c4e62c, 0x3bacd670, 0x2f98ce5e, 
        0x26503f72, 0x3264275c, 0x1a0c1700, 0xe380f2e, 0x4adc77b8, 0x5ee86f96, 0x76805fca, 0x62b447e4, 0x7125a5bc, 0x6511bd92, 
        0x4d798dce, 0x594d95e0, 0x1da9ed76, 0x99df558, 0x21f5c504, 0x35c1dd2a, 0x3c092c06, 0x283d3428, 0x550474, 0x14611c5a, 
        0x508564cc, 0x44b17ce2, 0x6cd94cbe, 0x78ed5490, 0x4bfa880e, 0x5fce9020, 0x77a6a07c, 0x6392b852, 0x2776c0c4, 0x3342d8ea, 
        0x1b2ae8b6, 0xf1ef098, 0x6d601b4, 0x12e2199a, 0x3a8a29c6, 0x2ebe31e8, 0x6a5a497e, 0x7e6e5150, 0x5606610c, 0x42327922, 
        0x51a39b7a, 0x45978354, 0x6dffb308, 0x79cbab26, 0x3d2fd3b0, 0x291bcb9e, 0x173fbc2, 0x1547e3ec, 0x1c8f12c0, 0x8bb0aee, 
        0x20d33ab2, 0x34e7229c, 0x70035a0a, 0x64374224, 0x4c5f7278, 0x586b6a56
    };
    public static byte kTable[] = {
        42, 37, -49, 48, 35, 120, -115, 94, 70, -15, 
        26, -68, 101, -119, -105, -30, 13, -30, 53, 120, 
        46, -102, -72, 38, 75, 19, 47, -60, 104, 107, 
        -94, -102, 56, -68, -25, -82, 27, -60, 106, -16, 
        126, 77, -3, 18, 93, 53, 112, 76, 53, 94, 
        -46, -42, 22, 38, 95, -120, 115, -81, -56, 106, 
        80, -41, 69, 52, 113, 121, -49, 92, 82, 1, 
        66, 2, 55, -120, -43, -32, 20, -16, 88, -66, 
        124, -101, -6, 36, 95, -29, 119, 122, 58, 106, 
        -32, -104, 25, 18, 109, -58, 73, -59, 40, -14, 
        106, -67, -91, -84, 15, 52, 50, 78, 44, 76, 
        -65, 16, 68, 39, 29, -118, 103, 95, -112, -44, 
        2, -42, 7, 54, 33, -82, -118, 104, 98, -13, 
        -98, -72, 65, -117, 19, -26, 36, 2, -124, 4, 
        7, 122, 9, 90, 111, 17, -85, -64, 76, 105, 
        38, -98, 41, -32, -79, 124, 10, -104, 60, 34, 
        90, 79, 121, 22, 121, 55, -12, 72, 28, -66, 
        99, -86, 63, -58, -18, -12, 87, -83, 76, 110, 
        116, -43, -63, 48, 17, 92, 86, -46, 50, 36, 
        -37, -116, 19, -118, 81, -28, 48, -14, -36, -70, 
        85, 123, 75, 88, 118, 3, -58, 6, 30, 104, 
        100, -100, 61, 16, -23, -62, 88, -103, 126, 32, 
        123, -31, -13, 126, 43, 54, -74, 74, 8, 78, 
        59, 20, 109, -57, -84, -10, 78, -65, 33, -88, 
        38, -44, -125, 50, 5, -84, 14, 108, 96, 37, 
        -103, -114, 67, 93, 20, -48, 102, -97, -80, 46, 
        69, -25, 61, 112, 32, 110, -86, -110, 3, 22, 
        39, -52, 107, 125, -123, 86, 72, 5, 8, 8, 
        45, -116, -97, -22, 14, -12, 18, -76, 94, 35, 
        87, -128, 125, 91, -38, -34, 24, -46, 77, 60, 
        59, -86, -64, 98, 83, -63, 98, -8, 112, -71, 
        -17, -90, 21, 48, 120, 68, 54, 72, -11, 26, 
        23, -26, 127, 114, 52, -98, -14, 44, 81, 23, 
        101, -50, 114, 111, -24, -112, 26, 4, 74, 10, 
        57, 124, -57, 84, 92, -11, 80, -74, 127, -115, 
        -35, -24, 47, 90, -104, -36, 12, 34, 21, -126, 
        105, -85, -126, 96, 74, -45, 15, 62, 34, -72, 
        -83, -92, 1, -64, 32, -6, 100, 73, -73, 24, 
        71, 49, 58, 70, 4, 108, 46, -106, 39, 20, 
        -93, -56, 66, -99, 52, 42, 97, -27, -71, 116, 
        9, -114, 27, -18, 42, -10, -106, -80, 79, 127, 
        1, 82, 108, 7, -116, 12, 60, -48, -55, 56, 
        31, -88, 68, 102, 122, 33, -45, -124, 89, 89, 
        94, -38, 49, 50, -4, 64, 18, 74, 113, 30, 
        119, -61, -26, -4, 84, -69, 107, -94, 117, 21, 
        -31, -54, 86, 109, 108, -108, 51, -28, -5, 118, 
        16, -100, 118, 40, 120, -9, -44, -78, 91, -113, 
        89, -20, 62, 6, -50, 14, 29, 126, 67, 80, 
        77, -87, 6, 100, 110, -47, -117, 58, 11, 88, 
        28, -40, 40, 32, -111, -122, 64, 75, 51, 28, 
        99, 51, -66, 66, 6, -70, 41, -96, 37, -62, 
        -92, -2, 110, 71, -19, 2, 77, 63, 96, 92, 
        40, -74, -9, -66, 11, -50, 122, -32, 99, -91, 
        -40, 122, 64, -35, 85, 36, 37, 84, -62, -58, 
        6, 44, 79, -104, 86, -5, 10, -84, 117, -125, 
        -121, -14, 16, 10, 16, 16, 51, 114, -99, 78, 
        91, 25, 63, -44, 120, 97, -78, -118, 29, -24, 
        37, 104, 62, -112, -88, 54, 31, 62, 34, 94, 
        60, 70, -81, 0, 89, -49, 56, -30, 122, -73, 
        -75, -68, 18, -36, 23, 38, 49, -92, -102, 120, 
        84, 45, 13, -102, 119, 85, -128, -60, 39, -126, 
        -59, -16, 4, -6, 72, -82, 97, 115, -33, 76, 
        66, 11, 82, 18, 42, 96, -16, -120, 9, 24, 
        125, -42, 108, -111, -22, 52, 79, -23, 103, 106, 
        12, -76, 115, -70, 47, -52, -2, -28, 74, 69, 
        105, 6, 105, 61, -28, 88, 1, 86, 70, -62, 
        34, 46, -53, -100, 71, -89, 92, 126, 100, -33, 
        -47, 32, 52, 8, -108, 20, 23, 112, 25, 74, 
        114, -7, -114, -88, 81, -127, 3, -10, 57, -22, 
        -95, 108, 26, -110, 44, 50, 127, 27, -69, -48, 
        92, 99, 54, -114, 125, -51, -68, -26, 94, -75, 
        49, -72, 59, 60, -90, 90, 24, 68, 43, 4, 
        112, 47, -119, -98, 83, 87, 4, -64, 54, -34, 
        -109, 34, 21, -90, 30, 124, 69, 113, 91, 72, 
        102, 9, -42, 22, 3, -128, 65, -12, 32, -8, 
        -52, -86, 72, -109, 110, 48, 107, -21, -29, 110, 
        14, 98, 116, -116, 45, 26, -7, -46, 8, -40, 
        93, 44, 43, -96, -48, 114, 78, 41, 71, -112, 
        109, 81, -54, -50, 5, 58, 104, 84, 38, 66, 
        -27, 10, 67, -53, 114, -24, 96, -77, -1, -74, 
        48, 100, -70, -126, 19, 28, 55, -36, 118, -107, 
        -96, 62, 85, -19, 45, 96, 61, -122, -113, -6, 
        30, -2, 2, -92, 123, 119, -107, 70, 88, 15, 
        24, 24, 121, -95, -110, 112, 90, -39, 31, 46, 
        63, 80, -120, -52, 28, 40, 5, -110, 116, 67, 
        -89, 8, 87, 59, 42, 86, 50, -78, -67, -76, 
        17, -54, 48, -22, 65, 29, 117, -34, 98, 101, 
        -8, -128, 7, -20, 111, 98, 36, -108, -30, 60, 
        76, -1, 64, -90, 111, -121, -51, -8, 10, 14, 
        90, 26, 41, 118, -41, 68, 106, 43, -61, -108, 
        73, 83, 78, -54, 44, -38, -39, 40, 15, -94, 
        84, 118, 103, -55, -10, -20, 68, -79, 123, -78, 
        33, 56, -20, 80, 2, 64, 97, 14, 82, -105, 
        36, 58, 113, -17, -87, 100, 20, 102, 62, -122, 
        55, 30, -77, -40, 95, 117, 17, 66, 124, 13, 
        -100, 28, 25, -124, 11, -2, 58, -4, -122, -96, 
        27, 82, 12, -56, 56, 42, -127, -106, 93, -93, 
        22, 116, 126, -37, -101, 42, 22, -80, 57, -80, 
        53, -56, -76, -18, 80, 65, 35, 12, 115, 57, 
        -82, 82, 35, -18, -21, 102, 0, -106, 102, 56, 
        101, 31, -15, -38, 70, 103, 124, -124, 46, 12, 
        -34, 30, 13, 116, 83, 64, 104, -3, -60, -94, 
        75, -123, 73, -4
    };

    static 
    {
        com.maddox.rts.SFSInputStream.loadNative();
    }
}
