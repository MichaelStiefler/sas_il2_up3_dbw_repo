// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgInput.java

package com.maddox.rts;

import com.maddox.util.HashMapInt;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

// Referenced classes of package com.maddox.rts:
//            NetObj, NetChannel, NetEnv

public class NetMsgInput extends java.io.InputStream
    implements java.io.DataInput
{

    public NetMsgInput()
    {
        bGuaranted = false;
    }

    public com.maddox.rts.NetChannel channel()
    {
        return channel;
    }

    public boolean isGuaranted()
    {
        return bGuaranted;
    }

    public static int netObjReferenceLen()
    {
        return 2;
    }

    public com.maddox.rts.NetObj readNetObj()
    {
        if(available() < 2)
            return null;
        end -= 2;
        int i = (buf[end] & 0xff) << 8 | buf[end + 1] & 0xff;
        if((i & 0x8000) != 0)
        {
            i &= 0xffff7fff;
            return (com.maddox.rts.NetObj)channel.objects.get(i);
        } else
        {
            return (com.maddox.rts.NetObj)com.maddox.rts.NetEnv.cur().objects.get(i);
        }
    }

    public int read()
    {
        return pos >= end ? -1 : buf[pos++] & 0xff;
    }

    public int available()
    {
        return end - pos;
    }

    public void readFully(byte abyte0[])
        throws java.io.IOException
    {
        readFully(abyte0, 0, abyte0.length);
    }

    public void readFully(byte abyte0[], int i, int j)
        throws java.io.IOException
    {
        int l;
        for(int k = 0; k < j; k += l)
        {
            l = read(abyte0, i + k, j - k);
            if(l < 0)
                throw new EOFException();
        }

    }

    public int skipBytes(int i)
        throws java.io.IOException
    {
        int j = 0;
        for(int k = 0; j < i && (k = (int)skip(i - j)) > 0; j += k);
        return j;
    }

    public boolean readBoolean()
        throws java.io.IOException
    {
        int i = read();
        if(i < 0)
            throw new EOFException();
        else
            return i != 0;
    }

    public byte readByte()
        throws java.io.IOException
    {
        int i = read();
        if(i < 0)
            throw new EOFException();
        else
            return (byte)i;
    }

    public int readUnsignedByte()
        throws java.io.IOException
    {
        int i = read();
        if(i < 0)
            throw new EOFException();
        else
            return i;
    }

    public short readShort()
        throws java.io.IOException
    {
        int i = read();
        int j = read();
        if((i | j) < 0)
            throw new EOFException();
        else
            return (short)((i << 8) + (j << 0));
    }

    public int readUnsignedShort()
        throws java.io.IOException
    {
        int i = read();
        int j = read();
        if((i | j) < 0)
            throw new EOFException();
        else
            return (i << 8) + (j << 0);
    }

    public char readChar()
        throws java.io.IOException
    {
        int i = read();
        int j = read();
        if((i | j) < 0)
            throw new EOFException();
        else
            return (char)((i << 8) + (j << 0));
    }

    public int readInt()
        throws java.io.IOException
    {
        int i = read();
        int j = read();
        int k = read();
        int l = read();
        if((i | j | k | l) < 0)
            throw new EOFException();
        else
            return (i << 24) + (j << 16) + (k << 8) + (l << 0);
    }

    public long readLong()
        throws java.io.IOException
    {
        return ((long)readInt() << 32) + ((long)readInt() & 0xffffffffL);
    }

    public float readFloat()
        throws java.io.IOException
    {
        return java.lang.Float.intBitsToFloat(readInt());
    }

    public double readDouble()
        throws java.io.IOException
    {
        return java.lang.Double.longBitsToDouble(readLong());
    }

    public java.lang.String readLine()
        throws java.io.IOException
    {
        char ac[] = lineBuffer;
        if(ac == null)
            ac = lineBuffer = new char[128];
        int i = ac.length;
        int j = 0;
        int k;
label0:
        do
            switch(k = read())
            {
            case -1: 
            case 10: // '\n'
                break label0;

            case 13: // '\r'
                int l = read();
                if(l != 10 && l != -1)
                    pos--;
                break label0;

            default:
                if(--i < 0)
                {
                    ac = new char[j + 128];
                    i = buf.length - j - 1;
                    java.lang.System.arraycopy(lineBuffer, 0, ac, 0, j);
                    lineBuffer = ac;
                }
                ac[j++] = (char)k;
                break;
            }
        while(true);
        if(k == -1 && j == 0)
            return null;
        else
            return java.lang.String.copyValueOf(ac, 0, j);
    }

    public java.lang.String readUTF()
        throws java.io.IOException
    {
        return java.io.DataInputStream.readUTF(this);
    }

    public java.lang.String read255()
        throws java.io.IOException
    {
        int i = readUnsignedByte();
        char ac[] = new char[i];
        int j = 0;
        int k = 0;
        while(j < i) 
        {
            int l = readUnsignedByte();
            switch(l >> 4)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                j++;
                ac[k++] = (char)l;
                break;

            case 12: // '\f'
            case 13: // '\r'
                if((j += 2) > i)
                    throw new UTFDataFormatException();
                int i1 = readUnsignedByte();
                if((i1 & 0xc0) != 128)
                    throw new UTFDataFormatException();
                ac[k++] = (char)((l & 0x1f) << 6 | i1 & 0x3f);
                break;

            case 14: // '\016'
                if((j += 3) > i)
                    throw new UTFDataFormatException();
                int j1 = readUnsignedByte();
                int k1 = readUnsignedByte();
                if((j1 & 0xc0) != 128 || (k1 & 0xc0) != 128)
                    throw new UTFDataFormatException();
                ac[k++] = (char)((l & 0xf) << 12 | (j1 & 0x3f) << 6 | (k1 & 0x3f) << 0);
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            default:
                throw new UTFDataFormatException();
            }
        }
        return new String(ac, 0, k);
    }

    public void reset()
    {
        pos = _pos;
        end = _end;
    }

    public void fixed()
    {
        _pos = pos;
        _end = end;
    }

    public void setData(com.maddox.rts.NetChannel netchannel, boolean flag, byte abyte0[], int i, int j)
    {
        if(j < 0 || i < 0)
            throw new IllegalArgumentException("illegal length or offset");
        if(abyte0 == null)
        {
            if(j != 0 || i != 0)
                throw new IllegalArgumentException("illegal length or offset");
        } else
        if(j + i > abyte0.length)
            throw new IllegalArgumentException("illegal length or offset");
        channel = netchannel;
        bGuaranted = flag;
        buf = abyte0;
        _pos = pos = i;
        if(abyte0 != null)
            _end = end = i + j;
        else
            _end = end = 0;
    }

    protected com.maddox.rts.NetChannel channel;
    protected boolean bGuaranted;
    protected byte buf[];
    protected int pos;
    protected int _pos;
    protected int end;
    protected int _end;
    private static char lineBuffer[];
}
