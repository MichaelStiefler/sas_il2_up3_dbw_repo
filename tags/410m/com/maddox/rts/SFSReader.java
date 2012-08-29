// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SFSReader.java

package com.maddox.rts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

// Referenced classes of package com.maddox.rts:
//            SFSInputStream, KryptoInputFilter

public class SFSReader extends java.io.InputStreamReader
{

    public SFSReader(java.lang.String s)
        throws java.io.FileNotFoundException
    {
        super(new SFSInputStream(s));
    }

    public SFSReader(java.io.File file)
        throws java.io.FileNotFoundException
    {
        super(new SFSInputStream(file));
    }

    public SFSReader(java.lang.String s, java.lang.String s1)
        throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException
    {
        super(new SFSInputStream(s), s1);
    }

    public SFSReader(java.io.File file, java.lang.String s)
        throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException
    {
        super(new SFSInputStream(file), s);
    }

    public SFSReader(java.lang.String s, int ai[])
        throws java.io.FileNotFoundException
    {
        super(new KryptoInputFilter(new SFSInputStream(s), ai));
    }

    public SFSReader(java.io.File file, int ai[])
        throws java.io.FileNotFoundException
    {
        super(new KryptoInputFilter(new SFSInputStream(file), ai));
    }

    public SFSReader(java.lang.String s, java.lang.String s1, int ai[])
        throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException
    {
        super(new KryptoInputFilter(new SFSInputStream(s), ai), s1);
    }

    public SFSReader(java.io.File file, java.lang.String s, int ai[])
        throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException
    {
        super(new KryptoInputFilter(new SFSInputStream(file), ai), s);
    }
}
