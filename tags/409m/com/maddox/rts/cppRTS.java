// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   cppRTS.java

package com.maddox.rts;


public class cppRTS
{

    public cppRTS()
    {
    }

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

    public int Finger_Int(byte abyte0[], int i)
    {
        return 0;
    }

    public long Finger_Long(byte abyte0[], int i)
    {
        return 0L;
    }

    public int Finger_IncInt(int i, byte abyte0[], int j)
    {
        return 0;
    }

    public long Finger_IncLong(long l, byte abyte0[], int i)
    {
        return 0L;
    }

    public int Finger_StrInt(java.lang.String s)
    {
        return 0;
    }

    public int Finger_StrNoCaseInt(java.lang.String s)
    {
        return 0;
    }

    public long Finger_StrLong(java.lang.String s)
    {
        return 0L;
    }

    public long Finger_StrNoSaceLong(java.lang.String s)
    {
        return 0L;
    }

    public java.lang.String RTS_GetHomePath(int i)
    {
        return null;
    }

    public void RTS_AddHomePath(java.lang.String s)
    {
    }

    public void RTS_RemHomePath(java.lang.String s)
    {
    }

    public int SFS_errno()
    {
        return 0;
    }

    public int SFS_open(java.lang.String s, int i)
    {
        return -1;
    }

    public int SFS_read(int i, byte abyte0[], int j)
    {
        return -1;
    }

    public int SFS_write(int i, byte abyte0[], int j)
    {
        return -1;
    }

    public int SFS_lseek(int i, int j, int k)
    {
        return -1;
    }

    public int SFS_close(int i)
    {
        return -1;
    }

    public int SFS_IsFile(java.lang.String s, int i)
    {
        return -1;
    }

    public int SFS_Mount(java.lang.String s, int i, int j)
    {
        return -1;
    }

    public int SFS_MountAs(java.lang.String s, int i, java.lang.String s1, int j)
    {
        return -1;
    }

    public int SFS_MountRes(int i, int j, java.lang.String s)
    {
        return -1;
    }

    public int SFS_UnMount(java.lang.String s)
    {
        return -1;
    }

    public int SFS_UnMountPath(java.lang.String s)
    {
        return -1;
    }

    public void SFS_SetCacheBlockSize(int i, int j)
    {
    }

    public void SFS_GetCacheBlockSize(int i, int ai[])
    {
    }

    public void SFS_SetCacheSize(int i, int j)
    {
    }

    public void SFS_GetCacheSize(int i, int ai[])
    {
    }

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
}
