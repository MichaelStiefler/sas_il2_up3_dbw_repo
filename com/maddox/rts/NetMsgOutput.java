// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgOutput.java

package com.maddox.rts;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetMsgInput, NetObj

public class NetMsgOutput extends java.io.OutputStream
    implements java.io.DataOutput
{

    public java.util.List objects()
    {
        return objects;
    }

    public byte[] data()
    {
        return buf;
    }

    public int dataLength()
    {
        return count;
    }

    public boolean isLocked()
    {
        return _lockCount > 0;
    }

    public int lockCount()
    {
        return _lockCount;
    }

    public void checkLock()
        throws java.io.IOException
    {
        if(_lockCount > 0)
            throw new IOException("Message is LOCKED");
        else
            return;
    }

    public void lockInc()
    {
        _lockCount++;
    }

    public void lockDec()
    {
        _lockCount--;
        if(_lockCount < 0)
            throw new RuntimeException("Lock counter in network output message is negative");
        if(_lockCount == 0)
            unLocking();
    }

    public void unLocking()
    {
    }

    public int size()
    {
        if(objects == null)
            return count;
        else
            return count + objects.size() * 2;
    }

    public void clear()
        throws java.io.IOException
    {
        if(_lockCount > 0)
            throw new IOException("Message is LOCKED");
        count = 0;
        if(objects != null)
            objects.clear();
    }

    public void writeNetObj(com.maddox.rts.NetObj netobj)
        throws java.io.IOException
    {
        if(_lockCount > 0)
            throw new IOException("Message is LOCKED");
        if(objects == null)
            objects = new ArrayList(2);
        objects.add(netobj);
    }

    public static int netObjReferenceLen()
    {
        return 2;
    }

    public void write(int i)
        throws java.io.IOException
    {
        if(_lockCount > 0)
            throw new IOException("Message is LOCKED");
        int j = count + 1;
        if(j > buf.length)
        {
            byte abyte0[] = new byte[java.lang.Math.max(buf.length << 1, j)];
            java.lang.System.arraycopy(buf, 0, abyte0, 0, count);
            buf = abyte0;
        }
        buf[count] = (byte)i;
        count = j;
    }

    public void writeBoolean(boolean flag)
        throws java.io.IOException
    {
        write(flag ? 1 : 0);
    }

    public void writeByte(int i)
        throws java.io.IOException
    {
        write(i);
    }

    public void writeShort(int i)
        throws java.io.IOException
    {
        write(i >>> 8 & 0xff);
        write(i >>> 0 & 0xff);
    }

    public void writeChar(int i)
        throws java.io.IOException
    {
        write(i >>> 8 & 0xff);
        write(i >>> 0 & 0xff);
    }

    public void writeInt(int i)
        throws java.io.IOException
    {
        write(i >>> 24 & 0xff);
        write(i >>> 16 & 0xff);
        write(i >>> 8 & 0xff);
        write(i >>> 0 & 0xff);
    }

    public void writeLong(long l)
        throws java.io.IOException
    {
        write((int)(l >>> 56) & 0xff);
        write((int)(l >>> 48) & 0xff);
        write((int)(l >>> 40) & 0xff);
        write((int)(l >>> 32) & 0xff);
        write((int)(l >>> 24) & 0xff);
        write((int)(l >>> 16) & 0xff);
        write((int)(l >>> 8) & 0xff);
        write((int)(l >>> 0) & 0xff);
    }

    public void writeFloat(float f)
        throws java.io.IOException
    {
        writeInt(java.lang.Float.floatToIntBits(f));
    }

    public void writeDouble(double d)
        throws java.io.IOException
    {
        writeLong(java.lang.Double.doubleToLongBits(d));
    }

    public void writeBytes(java.lang.String s)
        throws java.io.IOException
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
            write((byte)s.charAt(j));

    }

    public void writeChars(java.lang.String s)
        throws java.io.IOException
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            write(c >>> 8 & 0xff);
            write(c >>> 0 & 0xff);
        }

    }

    public void writeUTF(java.lang.String s)
        throws java.io.IOException
    {
        int i = s.length();
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            char c = s.charAt(k);
            if(c >= '\001' && c <= '\177')
                j++;
            else
            if(c > '\u07FF')
                j += 3;
            else
                j += 2;
        }

        if(j > 65535)
            throw new UTFDataFormatException();
        write(j >>> 8 & 0xff);
        write(j >>> 0 & 0xff);
        for(int l = 0; l < i; l++)
        {
            char c1 = s.charAt(l);
            if(c1 >= '\001' && c1 <= '\177')
                write(c1);
            else
            if(c1 > '\u07FF')
            {
                write(0xe0 | c1 >> 12 & 0xf);
                write(0x80 | c1 >> 6 & 0x3f);
                write(0x80 | c1 >> 0 & 0x3f);
            } else
            {
                write(0xc0 | c1 >> 6 & 0x1f);
                write(0x80 | c1 >> 0 & 0x3f);
            }
        }

    }

    public static int len255(java.lang.String s)
    {
        int i = s.length();
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            char c = s.charAt(k);
            if(c >= '\001' && c <= '\177')
                j++;
            else
            if(c > '\u07FF')
                j += 3;
            else
                j += 2;
        }

        return j + 1;
    }

    public void write255(java.lang.String s)
        throws java.io.IOException
    {
        int i = s.length();
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            char c = s.charAt(k);
            if(c >= '\001' && c <= '\177')
                j++;
            else
            if(c > '\u07FF')
                j += 3;
            else
                j += 2;
        }

        if(j > 255)
            throw new UTFDataFormatException();
        write(j & 0xff);
        for(int l = 0; l < i; l++)
        {
            char c1 = s.charAt(l);
            if(c1 >= '\001' && c1 <= '\177')
                write(c1);
            else
            if(c1 > '\u07FF')
            {
                write(0xe0 | c1 >> 12 & 0xf);
                write(0x80 | c1 >> 6 & 0x3f);
                write(0x80 | c1 >> 0 & 0x3f);
            } else
            {
                write(0xc0 | c1 >> 6 & 0x1f);
                write(0x80 | c1 >> 0 & 0x3f);
            }
        }

    }

    public void writeMsg(com.maddox.rts.NetMsgInput netmsginput, int i)
        throws java.io.IOException
    {
        if(_lockCount > 0)
            throw new IOException("Message is LOCKED");
        int j = netmsginput.pos;
        int k = netmsginput.end;
        try
        {
            netmsginput.reset();
            if(i > 0 && objects == null)
                objects = new ArrayList(2);
            while(i-- > 0) 
                objects.add(netmsginput.readNetObj());
            for(int l = netmsginput.available(); l-- > 0;)
                write(netmsginput.read());

        }
        catch(java.io.IOException ioexception)
        {
            netmsginput.pos = j;
            netmsginput.end = k;
            throw ioexception;
        }
        netmsginput.pos = j;
        netmsginput.end = k;
    }

    protected NetMsgOutput()
    {
        this(16);
    }

    protected NetMsgOutput(byte abyte0[])
    {
        objects = null;
        buf = null;
        buf = abyte0;
    }

    protected NetMsgOutput(int i)
    {
        objects = null;
        buf = null;
        if(i < 0)
        {
            throw new IllegalArgumentException("Negative initial size: " + i);
        } else
        {
            buf = new byte[i];
            return;
        }
    }

    protected NetMsgOutput(com.maddox.rts.NetMsgInput netmsginput, int i)
        throws java.io.IOException
    {
        objects = null;
        buf = null;
        int j = netmsginput.pos;
        int k = netmsginput.end;
        try
        {
            netmsginput.reset();
            while(i-- > 0) 
                writeNetObj(netmsginput.readNetObj());
            if(netmsginput.available() > 0)
            {
                count = netmsginput.available();
                if(netmsginput.pos == 0)
                {
                    buf = netmsginput.buf;
                } else
                {
                    buf = new byte[count];
                    netmsginput.readFully(buf);
                }
            }
        }
        catch(java.io.IOException ioexception)
        {
            netmsginput.pos = j;
            netmsginput.end = k;
            throw ioexception;
        }
        netmsginput.pos = j;
        netmsginput.end = k;
    }

    protected com.maddox.rts.NetObj _sender;
    protected int _len;
    private int _lockCount;
    protected java.util.ArrayList objects;
    protected int count;
    protected byte buf[];
}
