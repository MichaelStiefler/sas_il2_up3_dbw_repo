// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SFSInputStream.java

package com.maddox.rts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package com.maddox.rts:
//            HomePath, Finger

public class SFSInputStream extends java.io.InputStream
{

    public SFSInputStream(long l)
        throws java.io.FileNotFoundException
    {
        fd = -1;
        fis = null;
        fd = com.maddox.rts.SFSInputStream._open(l);
        if(fd == -1)
            throw new FileNotFoundException();
        else
            return;
    }

    public SFSInputStream(java.lang.String s)
        throws java.io.FileNotFoundException
    {
        fd = -1;
        fis = null;
        java.lang.SecurityManager securitymanager = java.lang.System.getSecurityManager();
        if(securitymanager != null)
            securitymanager.checkRead(s);
        if(s == null)
            throw new FileNotFoundException("file name == null");
        int i = s.length();
        for(int j = 0; j < i; j++)
            if(s.charAt(j) >= '\200')
            {
                fis = new FileInputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0));
                return;
            }

        fd = com.maddox.rts.SFSInputStream.openn(s, 0);
        if(fd == -1)
            throw new FileNotFoundException();
        else
            return;
    }

    public SFSInputStream(java.io.File file)
        throws java.io.FileNotFoundException
    {
        this(file.getPath());
    }

    private static int openn(java.lang.String s, int i)
    {
        long l = com.maddox.rts.Finger.LongFN(0L, s);
        int j = -1;
        if((i & 0xf0f) == 0)
            j = com.maddox.rts.SFSInputStream._open(l);
        if(j == -1)
        {
            int k = s.indexOf('/');
            if(k == -1)
                k = s.indexOf('\\');
            if(k == -1)
            {
                if(s.length() >= 3 && com.maddox.rts.Finger.LongFN(0L, s, 3) != 0x2e5243002e5243L && l != 0xa6e34c2e51f03b5cL)
                    j = com.maddox.rts.SFSInputStream.open(s, i);
            } else
            {
                long l1 = com.maddox.rts.Finger.LongFN(0L, s, k);
                if(l1 != 0x33444f0033444fL && l1 != 0x4d4150534d415053L && l1 != 0xab121357f1096fb7L && l1 != 0x47554900475549L && l1 != 0x434f4d00434f4dL && l1 != 0x464f4e54464f4e54L && l1 != 0x434f4400434f44L && l1 != 0x8d1dc7b51e21ef8bL)
                    j = com.maddox.rts.SFSInputStream.open(s, i);
            }
            if(j != -1 && (j & 0x8000) == 0)
            {
                ss[0] = (short)(j & 0xffff);
                ss[1] = (short)(j >>> 16 & 0xffff);
                oo += com.maddox.rts.Finger.Int(ss) - 12;
            }
        }
        return j;
    }

    private static int _open(long l)
    {
        int i = com.maddox.rts.SFSInputStream.openf(l);
        if(i != -1 && (i & 0x8000) == 0)
        {
            ss[0] = (short)(i & 0xffff);
            ss[1] = (short)(i >>> 16 & 0xffff);
            oo += com.maddox.rts.Finger.Int(ss) - 28;
        }
        return i;
    }

    private static native int openf(long l);

    private static native int open(java.lang.String s, int i);

    private static native int crc(int i, int j)
        throws java.io.IOException;

    public int crc(int i)
        throws java.io.IOException
    {
        if(fis != null)
            return 0;
        else
            return com.maddox.rts.SFSInputStream.crc(fd, i);
    }

    public int read()
        throws java.io.IOException
    {
        if(fis != null)
            return fis.read();
        else
            return read(fd);
    }

    private native int read(int i)
        throws java.io.IOException;

    private native int readBytes(int i, byte abyte0[], int j, int k)
        throws java.io.IOException;

    public int read(byte abyte0[])
        throws java.io.IOException
    {
        if(fis != null)
            return fis.read(abyte0);
        else
            return readBytes(fd, abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws java.io.IOException
    {
        if(fis != null)
            return fis.read(abyte0, i, j);
        else
            return readBytes(fd, abyte0, i, j);
    }

    public long skip(long l)
        throws java.io.IOException
    {
        if(fis != null)
            return fis.skip(l);
        else
            return skip(fd, l);
    }

    private native long skip(int i, long l)
        throws java.io.IOException;

    public int available()
        throws java.io.IOException
    {
        if(fis != null)
            return fis.available();
        else
            return available(fd);
    }

    private native int available(int i)
        throws java.io.IOException;

    public void close()
        throws java.io.IOException
    {
        if(fis != null)
        {
            fis.close();
            fis = null;
        } else
        {
            close(fd);
            fd = -1;
        }
    }

    private native void close(int i)
        throws java.io.IOException;

    protected static final void loadNative()
    {
    }

    public static final void _loadNative()
    {
        if(!libLoaded)
        {
            java.lang.System.loadLibrary("rts");
            libLoaded = true;
        }
    }

    protected void finalize()
        throws java.io.IOException
    {
        if(fis != null || fd != -1)
            close();
    }

    private SFSInputStream()
    {
        fd = -1;
        fis = null;
    }

    private int fd;
    private java.io.FileInputStream fis;
    public static int oo = 0;
    private static short ss[] = new short[2];
    private static boolean libLoaded = false;

    static 
    {
        com.maddox.rts.SFSInputStream.loadNative();
    }
}
