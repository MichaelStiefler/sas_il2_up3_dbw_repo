package com.maddox.rts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SFSInputStream extends InputStream
{
  private int fd = -1;

  private FileInputStream fis = null;

  public static int oo = 0;

  private static short[] ss = new short[2];

  private static boolean libLoaded = false;

  public SFSInputStream(long paramLong)
    throws FileNotFoundException
  {
    this.fd = _open(paramLong);

    if (this.fd == -1)
      throw new FileNotFoundException();
  }

  public SFSInputStream(String paramString)
    throws FileNotFoundException
  {
    SecurityManager localSecurityManager = System.getSecurityManager();
    if (localSecurityManager != null) {
      localSecurityManager.checkRead(paramString);
    }
    if (paramString == null) {
      throw new FileNotFoundException("file name == null");
    }
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (paramString.charAt(j) >= '') {
        this.fis = new FileInputStream(HomePath.toFileSystemName(paramString, 0));
        return;
      }
    }
    this.fd = openn(paramString, 0);
    if (this.fd == -1)
    {
      throw new FileNotFoundException();
    }
  }

  public SFSInputStream(File paramFile)
    throws FileNotFoundException
  {
    this(paramFile.getPath());
  }

  private static int openn(String paramString, int paramInt)
  {
    long l = Finger.LongFN(0L, paramString);
    int i = -1;
    if ((paramInt & 0xF0F) == 0)
      i = _open(l);
    if (i == -1) {
      int j = paramString.indexOf('/');
      if (j == -1)
        j = paramString.indexOf('\\');
      if (j == -1) {
        if ((paramString.length() >= 3) && 
          (Finger.LongFN(0L, paramString, 3) != 13038296648012355L) && (l != -6421204881873290404L))
        {
          i = open(paramString, paramInt);
        }
      } else {
        l = Finger.LongFN(0L, paramString, j);

        if ((l != 14430329908708431L) && (l != 5566818933115277395L) && (l != -6119807675216072777L) && (l != 20078495372105033L) && (l != 18946015575101261L) && (l != 5066354229678919252L) && (l != 18945976920395588L) && (l != -8278240959328555125L))
        {
          i = open(paramString, paramInt);
        }
      }

      if ((i != -1) && ((i & 0x8000) == 0)) {
        ss[0] = (short)(i & 0xFFFF);
        ss[1] = (short)(i >>> 16 & 0xFFFF);
        oo += Finger.Int(ss) - 12;
      }
    }
    return i;
  }

  private static int _open(long paramLong)
  {
    int i = openf(paramLong);
    if ((i != -1) && ((i & 0x8000) == 0)) {
      ss[0] = (short)(i & 0xFFFF);
      ss[1] = (short)(i >>> 16 & 0xFFFF);
      oo += Finger.Int(ss) - 28;
    }
    return i;
  }

  private static native int openf(long paramLong);

  private static native int open(String paramString, int paramInt);

  private static native int crc(int paramInt1, int paramInt2)
    throws IOException;

  public int crc(int paramInt)
    throws IOException
  {
    if (this.fis != null) {
      return 0;
    }
    return crc(this.fd, paramInt);
  }

  public int read()
    throws IOException
  {
    if (this.fis != null) {
      return this.fis.read();
    }
    return read(this.fd);
  }

  private native int read(int paramInt)
    throws IOException;

  private native int readBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException;

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    if (this.fis != null) {
      return this.fis.read(paramArrayOfByte);
    }
    return readBytes(this.fd, paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.fis != null) {
      return this.fis.read(paramArrayOfByte, paramInt1, paramInt2);
    }
    return readBytes(this.fd, paramArrayOfByte, paramInt1, paramInt2);
  }

  public long skip(long paramLong)
    throws IOException
  {
    if (this.fis != null) {
      return this.fis.skip(paramLong);
    }
    return skip(this.fd, paramLong);
  }

  private native long skip(int paramInt, long paramLong)
    throws IOException;

  public int available()
    throws IOException
  {
    if (this.fis != null) {
      return this.fis.available();
    }
    return available(this.fd);
  }

  private native int available(int paramInt)
    throws IOException;

  public void close()
    throws IOException
  {
    if (this.fis != null) {
      this.fis.close();
      this.fis = null;
    } else {
      close(this.fd);
      this.fd = -1;
    }
  }

  private native void close(int paramInt)
    throws IOException;

  protected static final void loadNative()
  {
  }

  public static final void _loadNative()
  {
    if (!libLoaded) {
      System.loadLibrary("rts");
      libLoaded = true;
    }
  }

  protected void finalize()
    throws IOException
  {
    if ((this.fis != null) || (this.fd != -1))
      close();
  }

  private SFSInputStream()
  {
  }

  static
  {
    loadNative();
  }
}