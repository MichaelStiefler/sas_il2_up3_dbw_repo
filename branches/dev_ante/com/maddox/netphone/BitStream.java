package com.maddox.netphone;

import java.util.Iterator;
import java.util.Vector;

public class BitStream extends MixBase
{
  public static final int BSERR_OK = 0;
  public static final int BSERR_NODATA = 2;
  protected int rp = 0;
  protected BsData bdt = null;
  protected int err = 0;

  public BitStream(int paramInt)
  {
    if (paramInt <= 0) {
      error("Invalid argument");
      return;
    }

    int i = 1;
    while (i < paramInt) i *= 2;
    paramInt = i;

    this.bdt = new BsData(paramInt);
    this.rp = this.bdt.wp;
    this.bdt.links.add(this);
  }

  public BitStream(BitStream paramBitStream)
  {
    if (paramBitStream == null) {
      error("Invalid parameter");
      return;
    }

    this.bdt = paramBitStream.bdt;
    this.rp = this.bdt.wp;
    this.bdt.links.add(this);
  }

  public void clear()
  {
    if (this.bdt != null) {
      this.bdt.wp = 0;
      this.bdt.maxlen = 0;
      this.bdt.rdflag = false;

      Iterator localIterator = this.bdt.links.iterator();
      while (localIterator.hasNext()) {
        BitStream localBitStream = (BitStream)localIterator.next();
        localBitStream.rp = this.bdt.wp;
      }
    } else {
      this.rp = 0;
    }
  }

  public void destroy() {
    if (this.bdt != null) {
      this.bdt.links.remove(this);
      this.bdt = null;
    }
  }

  public int bitlen()
  {
    return this.bdt.wp - this.rp & this.bdt.size - 1;
  }

  public void reset()
  {
    this.rp = this.bdt.wp;
  }

  public int len(int paramInt)
  {
    return bitlen() / paramInt;
  }

  public int size()
  {
    return (bitlen() + 7) / 8;
  }

  protected int bitspace()
  {
    if (this.bdt.rdflag) {
      int j = 0;
      Iterator localIterator = this.bdt.links.iterator();
      while (localIterator.hasNext()) {
        BitStream localBitStream = (BitStream)localIterator.next();
        int i = localBitStream.bitlen();
        if (i <= j) continue; j = i;
      }
      this.bdt.maxlen = j;
      this.bdt.rdflag = false;
    }
    return this.bdt.size - this.bdt.maxlen - 1;
  }

  public int space(int paramInt)
  {
    return bitspace() / paramInt;
  }

  public int alignSpace()
  {
    int i = bitspace() / 8;
    return i > 1 ? i - 1 : 0;
  }

  public int put(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= 0) {
      return 0;
    }
    int i = bitspace();
    if (i < paramInt2) {
      error("No space - put");
      return 0;
    }

    for (int j = 0; j < paramInt2; j++) {
      if ((paramInt1 & 1 << j) != 0)
      {
        int tmp55_54 = (this.bdt.wp >>> 3);
        byte[] tmp55_43 = this.bdt.data; tmp55_43[tmp55_54] = (byte)(tmp55_43[tmp55_54] | 1 << (this.bdt.wp & 0x7));
      }
      else
      {
        int tmp91_90 = (this.bdt.wp >>> 3);
        byte[] tmp91_79 = this.bdt.data; tmp91_79[tmp91_90] = (byte)(tmp91_79[tmp91_90] & (1 << (this.bdt.wp & 0x7) ^ 0xFFFFFFFF));
      }
      this.bdt.wp = (this.bdt.wp + 1 & this.bdt.size - 1);
    }

    this.bdt.maxlen += paramInt2;

    return paramInt2;
  }

  public int putBegin(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= 0) {
      return 0;
    }
    if (bitspace() < paramInt2) {
      this.err = 1;
      error("No space - putBegin");
      return 0;
    }

    for (int i = paramInt2 - 1; i >= 0; i--) {
      this.rp = (this.rp - 1 & this.bdt.size - 1);
      if ((paramInt1 & 1 << i) != 0)
      {
        int tmp75_74 = (this.rp >>> 3);
        byte[] tmp75_66 = this.bdt.data; tmp75_66[tmp75_74] = (byte)(tmp75_66[tmp75_74] | 1 << (this.rp & 0x7));
      }
      else
      {
        int tmp105_104 = (this.rp >>> 3);
        byte[] tmp105_96 = this.bdt.data; tmp105_96[tmp105_104] = (byte)(tmp105_96[tmp105_104] & (1 << (this.rp & 0x7) ^ 0xFFFFFFFF));
      }
    }
    this.bdt.maxlen += paramInt2;
    this.bdt.rdflag = true;

    return paramInt2;
  }

  public int get(int paramInt)
  {
    int i = 0;

    if (bitlen() < paramInt) {
      this.err = 2;
      error("No data - get");
    }
    if (this.err != 0) return 0;

    for (int j = 0; j < paramInt; j++) {
      if ((this.bdt.data[(this.rp >>> 3)] & 1 << (this.rp & 0x7)) != 0) i |= 1 << j;
      this.rp = (this.rp + 1 & this.bdt.size - 1);
    }
    this.bdt.rdflag = true;

    return i;
  }

  public int probe(int paramInt1, int paramInt2)
  {
    if (bitlen() < paramInt1) {
      this.err = 2;
      error("No data - probe");
      return 0;
    }
    if (this.err != 0) return 0;

    int i = 0; int j = this.rp + paramInt2 & this.bdt.size - 1;

    for (int k = 0; k < paramInt1; k++) {
      if ((this.bdt.data[(j >>> 3)] & 1 << (j & 0x7)) != 0) i |= 1 << k;
      j = j + 1 & this.bdt.size + 1;
    }

    return i;
  }

  public int putBytes(byte[] paramArrayOfByte, int paramInt)
  {
    if (space(8) < paramInt) {
      paramInt = space(8);
    }
    for (int i = 0; i < paramInt; i++) put(paramArrayOfByte[i], 8);

    return paramInt;
  }

  public int getBytes(byte[] paramArrayOfByte, int paramInt)
  {
    if (len(8) < paramInt) {
      paramInt = len(8);
    }
    for (int i = 0; i < paramInt; i++) paramArrayOfByte[i] = (byte)get(8);

    return paramInt;
  }

  public void skip(int paramInt)
  {
    if (bitlen() >= paramInt) this.rp = (this.rp + paramInt & this.bdt.size - 1); else
      error("No data - skip");
  }

  public int getAligned(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramInt <= 0)) return 0;

    int i = paramInt * 8;
    int j = bitlen() + 3;

    if (j > i) {
      putBegin(0, 3);
    } else {
      int k = j % 8;
      paramInt = j / 8;
      if (k > 0) {
        k = 8 - k;
        paramInt++;
      }
      putBegin(k, 3);
      put(0, k);
    }
    return getBytes(paramArrayOfByte, paramInt);
  }

  public int putAligned(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramInt <= 0)) return 0;

    int i = paramArrayOfByte[0];
    int j = i & 0x7;
    int k = paramInt * 8 - j - 3;
    int m = k > 5 ? 5 : k;

    if (bitspace() < k) {
      return 0;
    }

    put(i >>> 3, m);
    k -= m;

    for (int n = 1; k > 0; n++) {
      m = k > 8 ? 8 : k;
      put(paramArrayOfByte[n], m);
      k -= m;
    }

    return paramInt;
  }

  public int getError()
  {
    int i = this.err;
    this.err = 0;
    return i;
  }

  public void errClear()
  {
    this.err = 0;
  }
}