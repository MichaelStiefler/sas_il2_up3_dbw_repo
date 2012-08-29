// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SamplePool.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

// Referenced classes of package com.maddox.sound:
//            BaseObject, Sample

public class SamplePool extends com.maddox.sound.BaseObject
{

    public SamplePool()
    {
        sources = null;
        handle = com.maddox.sound.SamplePool.jniCreate();
    }

    public SamplePool(java.lang.String s)
    {
        sources = null;
        try
        {
            com.maddox.rts.SectFile sectfile = new SectFile("presets/sounds/" + s + ".prs");
            handle = com.maddox.sound.SamplePool.jniCreate();
            if(handle != 0)
                load(sectfile);
        }
        catch(java.lang.Exception exception)
        {
            errmsg("Cannot load sample pool: " + s);
        }
    }

    public void load(com.maddox.rts.SectFile sectfile)
        throws java.lang.Exception
    {
        int i = sectfile.sectionIndex("samples");
        if(i >= 0)
        {
            if(sources == null)
                sources = new ArrayList(32);
            for(int j = 0; j < sectfile.vars(i); j++)
            {
                java.lang.String s = sectfile.line(i, j);
                com.maddox.sound.Sample sample = new Sample(s);
                sample.load(sectfile, "sample." + s);
                sources.add(sample);
                com.maddox.sound.SamplePool.jniAddSample(handle, sample.handle);
            }

        } else
        {
            throw new Exception("Cannot load preset");
        }
    }

    protected static native int jniCreate();

    protected static native void jniAddSample(int i, int j);

    protected int handle;
    protected java.util.ArrayList sources;
}
