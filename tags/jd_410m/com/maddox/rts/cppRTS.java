package com.maddox.rts;

public class cppRTS
{
  public static final int SFS_EHEAD = 100;
  public static final int SFS_EVER = 101;
  public static final int SFS_ENMOUNT = 102;
  public static final int SFS_EOPENS = 103;
  public static final int SFS_FLAG_SYSTEM_BUFFERING = 0;
  public static final int SFS_FLAG_NO_BUFFERING = 1;
  public static final int SFS_FLAG_INTERNAL_BUFFERING = 2;
  public static final int SFS_FLAG_MAPPING = 3;
  public static final int SFS_CACHE_PRIMARY = 0;
  public static final int SFS_CACHE_SECONDARY = 1;

  public long RTS_GetCurrentGameTime()
  {
    return 0L;
  }

  public long RTS_GetCurrentRealTime()
  {
    return 0L;
  }

  public int RTS_GetRealTime()
  {
    return 0;
  }

  public int Finger_Int(byte[] paramArrayOfByte, int paramInt)
  {
    return 0;
  }

  public long Finger_Long(byte[] paramArrayOfByte, int paramInt)
  {
    return 0L;
  }

  public int Finger_IncInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    return 0;
  }

  public long Finger_IncLong(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    return 0L;
  }

  public int Finger_StrInt(String paramString)
  {
    return 0;
  }

  public int Finger_StrNoCaseInt(String paramString)
  {
    return 0;
  }

  public long Finger_StrLong(String paramString)
  {
    return 0L;
  }

  public long Finger_StrNoSaceLong(String paramString)
  {
    return 0L;
  }

  public String RTS_GetHomePath(int paramInt)
  {
    return null;
  }

  public void RTS_AddHomePath(String paramString)
  {
  }

  public void RTS_RemHomePath(String paramString)
  {
  }

  public int SFS_errno()
  {
    return 0;
  }

  public int SFS_open(String paramString, int paramInt)
  {
    return -1;
  }

  public int SFS_read(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    return -1;
  }

  public int SFS_write(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    return -1;
  }

  public int SFS_lseek(int paramInt1, int paramInt2, int paramInt3)
  {
    return -1;
  }

  public int SFS_close(int paramInt)
  {
    return -1;
  }

  public int SFS_IsFile(String paramString, int paramInt)
  {
    return -1;
  }

  public int SFS_Mount(String paramString, int paramInt1, int paramInt2)
  {
    return -1;
  }

  public int SFS_MountAs(String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    return -1;
  }

  public int SFS_MountRes(int paramInt1, int paramInt2, String paramString)
  {
    return -1;
  }

  public int SFS_UnMount(String paramString)
  {
    return -1;
  }

  public int SFS_UnMountPath(String paramString)
  {
    return -1;
  }

  public void SFS_SetCacheBlockSize(int paramInt1, int paramInt2)
  {
  }

  public void SFS_GetCacheBlockSize(int paramInt, int[] paramArrayOfInt)
  {
  }

  public void SFS_SetCacheSize(int paramInt1, int paramInt2)
  {
  }

  public void SFS_GetCacheSize(int paramInt, int[] paramArrayOfInt)
  {
  }
}