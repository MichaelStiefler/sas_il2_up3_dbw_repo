package com.maddox.rts;

import com.maddox.util.HashMapInt;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

public class NetMsgInput extends InputStream
  implements DataInput
{
  protected NetChannel channel;
  protected boolean bGuaranted = false;
  protected byte[] buf;
  protected int pos;
  protected int _pos;
  protected int end;
  protected int _end;
  private static char[] lineBuffer;

  public NetChannel channel()
  {
    return this.channel;
  }

  public boolean isGuaranted()
  {
    return this.bGuaranted;
  }

  public static int netObjReferenceLen() {
    return 2;
  }

  public NetObj readNetObj() {
    if (available() < 2)
      return null;
    this.end -= 2;
    int i = (this.buf[this.end] & 0xFF) << 8 | this.buf[(this.end + 1)] & 0xFF;
    if ((i & 0x8000) != 0) {
      i &= -32769;
      return (NetObj)this.channel.objects.get(i);
    }
    return (NetObj)NetEnv.cur().objects.get(i);
  }

  public int read()
  {
    return this.pos < this.end ? this.buf[(this.pos++)] & 0xFF : -1;
  }

  public int available()
  {
    return this.end - this.pos;
  }

  public void readFully(byte[] paramArrayOfByte) throws IOException {
    readFully(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
    int i = 0;
    while (i < paramInt2) {
      int j = read(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      if (j < 0)
        throw new EOFException();
      i += j;
    }
  }

  public int skipBytes(int paramInt) throws IOException {
    int i = 0;
    int j = 0;
    while ((i < paramInt) && ((j = (int)skip(paramInt - i)) > 0)) {
      i += j;
    }
    return i;
  }

  public boolean readBoolean() throws IOException {
    int i = read();
    if (i < 0)
      throw new EOFException();
    return i != 0;
  }

  public byte readByte() throws IOException {
    int i = read();
    if (i < 0)
      throw new EOFException();
    return (byte)i;
  }

  public int readUnsignedByte() throws IOException {
    int i = read();
    if (i < 0)
      throw new EOFException();
    return i;
  }

  public short readShort() throws IOException {
    int i = read();
    int j = read();
    if ((i | j) < 0)
      throw new EOFException();
    return (short)((i << 8) + (j << 0));
  }

  public int readUnsignedShort() throws IOException {
    int i = read();
    int j = read();
    if ((i | j) < 0)
      throw new EOFException();
    return (i << 8) + (j << 0);
  }

  public char readChar() throws IOException {
    int i = read();
    int j = read();
    if ((i | j) < 0)
      throw new EOFException();
    return (char)((i << 8) + (j << 0));
  }

  public int readInt() throws IOException {
    int i = read();
    int j = read();
    int k = read();
    int m = read();
    if ((i | j | k | m) < 0)
      throw new EOFException();
    return (i << 24) + (j << 16) + (k << 8) + (m << 0);
  }

  public long readLong() throws IOException {
    return (readInt() << 32) + (readInt() & 0xFFFFFFFF);
  }

  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readInt());
  }

  public double readDouble() throws IOException {
    return Double.longBitsToDouble(readLong());
  }

  public String readLine() throws IOException
  {
    char[] arrayOfChar = lineBuffer;

    if (arrayOfChar == null) {
      arrayOfChar = NetMsgInput.lineBuffer = new char[''];
    }
int i = arrayOfChar.length;
    int j = 0;
    int k;
    while (true) switch (k = read()) {
      case -1:
      case 10:
        break;
      case 13:
        int m = read();
        if ((m == 10) || (m == -1)) break;
        this.pos -= 1; break;
      default:
        i--; if (i < 0) {
          arrayOfChar = new char[j + 128];
          i = this.buf.length - j - 1;
          System.arraycopy(lineBuffer, 0, arrayOfChar, 0, j);
          lineBuffer = arrayOfChar;
        }
        arrayOfChar[(j++)] = (char)k;
      }


    if ((k == -1) && (j == 0)) {
      return null;
    }
    return String.copyValueOf(arrayOfChar, 0, j);
  }

  public String readUTF() throws IOException {
    return DataInputStream.readUTF(this);
  }

  public String read255() throws IOException
  {
    int i = readUnsignedByte();
    char[] arrayOfChar = new char[i];
    int j = 0;
    int k = 0;
    while (j < i) {
      int m = readUnsignedByte();
      int n;
      switch (m >> 4) { case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        j++;
        arrayOfChar[(k++)] = (char)m;
        break;
      case 12:
      case 13:
        j += 2;
        if (j > i)
          throw new UTFDataFormatException();
        n = readUnsignedByte();
        if ((n & 0xC0) != 128)
          throw new UTFDataFormatException();
        arrayOfChar[(k++)] = (char)((m & 0x1F) << 6 | n & 0x3F);
        break;
      case 14:
        j += 3;
        if (j > i)
          throw new UTFDataFormatException();
        n = readUnsignedByte();
        int i1 = readUnsignedByte();
        if (((n & 0xC0) != 128) || ((i1 & 0xC0) != 128))
          throw new UTFDataFormatException();
        arrayOfChar[(k++)] = (char)((m & 0xF) << 12 | (n & 0x3F) << 6 | (i1 & 0x3F) << 0);

        break;
      case 8:
      case 9:
      case 10:
      case 11:
      default:
        throw new UTFDataFormatException();
      }
    }
    return new String(arrayOfChar, 0, k);
  }

  public void reset()
  {
    this.pos = this._pos;
    this.end = this._end;
  }

  public void fixed()
  {
    this._pos = this.pos;
    this._end = this.end;
  }

  public void setData(NetChannel paramNetChannel, boolean paramBoolean, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt1 < 0))
      throw new IllegalArgumentException("illegal length or offset");
    if (paramArrayOfByte == null) {
      if ((paramInt2 != 0) || (paramInt1 != 0))
        throw new IllegalArgumentException("illegal length or offset");
    } else if (paramInt2 + paramInt1 > paramArrayOfByte.length) {
      throw new IllegalArgumentException("illegal length or offset");
    }
    this.channel = paramNetChannel;
    this.bGuaranted = paramBoolean;
    this.buf = paramArrayOfByte;
    this._pos = (this.pos = paramInt1);
    if (paramArrayOfByte != null)
      this._end = (this.end = paramInt1 + paramInt2);
    else
      this._end = (this.end = 0);
  }
}