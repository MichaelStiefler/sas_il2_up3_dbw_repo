package com.maddox.rts;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;

public class NetMsgOutput extends OutputStream
  implements DataOutput
{
  protected NetObj _sender;
  protected int _len;
  private int _lockCount;
  protected ArrayList objects = null;
  protected int count;
  protected byte[] buf = null;

  public List objects() {
    return this.objects;
  }
  public byte[] data() { return this.buf; } 
  public int dataLength() {
    return this.count;
  }

  public boolean isLocked() {
    return this._lockCount > 0;
  }

  public int lockCount() {
    return this._lockCount;
  }

  public void checkLock() throws IOException {
    if (this._lockCount > 0)
      throw new IOException("Message is LOCKED");
  }

  public void lockInc()
  {
    this._lockCount += 1;
  }

  public void lockDec()
  {
    this._lockCount -= 1;
    if (this._lockCount < 0)
      throw new RuntimeException("Lock counter in network output message is negative");
    if (this._lockCount == 0)
      unLocking();
  }

  public void unLocking()
  {
  }

  public int size()
  {
    if (this.objects == null) return this.count;
    return this.count + this.objects.size() * 2;
  }

  public void clear() throws IOException {
    if (this._lockCount > 0)
      throw new IOException("Message is LOCKED");
    this.count = 0;
    if (this.objects != null)
      this.objects.clear();
  }

  public void writeNetObj(NetObj paramNetObj) throws IOException
  {
    if (this._lockCount > 0)
      throw new IOException("Message is LOCKED");
    if (this.objects == null)
      this.objects = new ArrayList(2);
    this.objects.add(paramNetObj);
  }
  public static int netObjReferenceLen() {
    return 2;
  }

  public void write(int paramInt)
    throws IOException
  {
    if (this._lockCount > 0)
      throw new IOException("Message is LOCKED");
    int i = this.count + 1;
    if (i > this.buf.length) {
      byte[] arrayOfByte = new byte[Math.max(this.buf.length << 1, i)];
      System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
      this.buf = arrayOfByte;
    }
    this.buf[this.count] = (byte)paramInt;
    this.count = i;
  }

  public void writeBoolean(boolean paramBoolean) throws IOException {
    write(paramBoolean ? 1 : 0);
  }

  public void writeByte(int paramInt) throws IOException {
    write(paramInt);
  }

  public void writeShort(int paramInt) throws IOException {
    write(paramInt >>> 8 & 0xFF);
    write(paramInt >>> 0 & 0xFF);
  }

  public void writeChar(int paramInt) throws IOException {
    write(paramInt >>> 8 & 0xFF);
    write(paramInt >>> 0 & 0xFF);
  }

  public void writeInt(int paramInt) throws IOException {
    write(paramInt >>> 24 & 0xFF);
    write(paramInt >>> 16 & 0xFF);
    write(paramInt >>> 8 & 0xFF);
    write(paramInt >>> 0 & 0xFF);
  }

  public void writeLong(long paramLong) throws IOException {
    write((int)(paramLong >>> 56) & 0xFF);
    write((int)(paramLong >>> 48) & 0xFF);
    write((int)(paramLong >>> 40) & 0xFF);
    write((int)(paramLong >>> 32) & 0xFF);
    write((int)(paramLong >>> 24) & 0xFF);
    write((int)(paramLong >>> 16) & 0xFF);
    write((int)(paramLong >>> 8) & 0xFF);
    write((int)(paramLong >>> 0) & 0xFF);
  }

  public void writeFloat(float paramFloat) throws IOException {
    writeInt(Float.floatToIntBits(paramFloat));
  }

  public void writeDouble(double paramDouble) throws IOException {
    writeLong(Double.doubleToLongBits(paramDouble));
  }

  public void writeBytes(String paramString) throws IOException {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
      write((byte)paramString.charAt(j));
  }

  public void writeChars(String paramString) throws IOException {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      int k = paramString.charAt(j);
      write(k >>> 8 & 0xFF);
      write(k >>> 0 & 0xFF);
    }
  }

  public void writeUTF(String paramString) throws IOException {
    int i = paramString.length();
    int j = 0;

    for (int k = 0; k < i; k++) {
      m = paramString.charAt(k);
      if ((m >= 1) && (m <= 127))
        j++;
      else if (m > 2047)
        j += 3;
      else {
        j += 2;
      }
    }

    if (j > 65535) {
      throw new UTFDataFormatException();
    }
    write(j >>> 8 & 0xFF);
    write(j >>> 0 & 0xFF);
    for (int m = 0; m < i; m++) {
      int n = paramString.charAt(m);
      if ((n >= 1) && (n <= 127)) {
        write(n);
      } else if (n > 2047) {
        write(0xE0 | n >> 12 & 0xF);
        write(0x80 | n >> 6 & 0x3F);
        write(0x80 | n >> 0 & 0x3F);
      } else {
        write(0xC0 | n >> 6 & 0x1F);
        write(0x80 | n >> 0 & 0x3F);
      }
    }
  }

  public static int len255(String paramString) {
    int i = paramString.length();
    int j = 0;

    for (int k = 0; k < i; k++) {
      int m = paramString.charAt(k);
      if ((m >= 1) && (m <= 127))
        j++;
      else if (m > 2047)
        j += 3;
      else {
        j += 2;
      }
    }
    return j + 1;
  }

  public void write255(String paramString) throws IOException
  {
    int i = paramString.length();
    int j = 0;

    for (int k = 0; k < i; k++) {
      m = paramString.charAt(k);
      if ((m >= 1) && (m <= 127))
        j++;
      else if (m > 2047)
        j += 3;
      else {
        j += 2;
      }
    }

    if (j > 255) {
      throw new UTFDataFormatException();
    }
    write(j & 0xFF);
    for (int m = 0; m < i; m++) {
      int n = paramString.charAt(m);
      if ((n >= 1) && (n <= 127)) {
        write(n);
      } else if (n > 2047) {
        write(0xE0 | n >> 12 & 0xF);
        write(0x80 | n >> 6 & 0x3F);
        write(0x80 | n >> 0 & 0x3F);
      } else {
        write(0xC0 | n >> 6 & 0x1F);
        write(0x80 | n >> 0 & 0x3F);
      }
    }
  }

  public void writeMsg(NetMsgInput paramNetMsgInput, int paramInt) throws IOException {
    if (this._lockCount > 0)
      throw new IOException("Message is LOCKED");
    int i = paramNetMsgInput.pos;
    int j = paramNetMsgInput.end;
    try {
      paramNetMsgInput.reset();
      if ((paramInt > 0) && (this.objects == null))
        this.objects = new ArrayList(2);
      while (paramInt-- > 0) {
        this.objects.add(paramNetMsgInput.readNetObj());
      }
      int k = paramNetMsgInput.available();
      while (k-- > 0)
        write(paramNetMsgInput.read());
    }
    catch (IOException localIOException) {
      paramNetMsgInput.pos = i; paramNetMsgInput.end = j;
      throw localIOException;
    }
    paramNetMsgInput.pos = i; paramNetMsgInput.end = j;
  }

  protected NetMsgOutput()
  {
    this(16);
  }

  protected NetMsgOutput(byte[] paramArrayOfByte) {
    this.buf = paramArrayOfByte;
  }

  protected NetMsgOutput(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("Negative initial size: " + paramInt);
    this.buf = new byte[paramInt];
  }

  protected NetMsgOutput(NetMsgInput paramNetMsgInput, int paramInt) throws IOException
  {
    int i = paramNetMsgInput.pos;
    int j = paramNetMsgInput.end;
    try {
      paramNetMsgInput.reset();
      while (paramInt-- > 0) {
        writeNetObj(paramNetMsgInput.readNetObj());
      }
      if (paramNetMsgInput.available() > 0) {
        this.count = paramNetMsgInput.available();
        if (paramNetMsgInput.pos == 0) {
          this.buf = paramNetMsgInput.buf;
        } else {
          this.buf = new byte[this.count];
          paramNetMsgInput.readFully(this.buf);
        }
      }
    } catch (IOException localIOException) {
      paramNetMsgInput.pos = i; paramNetMsgInput.end = j;
      throw localIOException;
    }
    paramNetMsgInput.pos = i; paramNetMsgInput.end = j;
  }
}