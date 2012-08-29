// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Acoustics.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.maddox.sound:
//            BaseObject, AcousticsGeometry, Reverb, ReverbFX, 
//            AcousticsPreset, SoundListener, AudioDevice, ReverbFXRoom

public class Acoustics extends com.maddox.sound.BaseObject
{

    public Acoustics(java.lang.String s)
    {
        prs = null;
        handle = 0;
        rcaps = 0;
        envNum = 0;
        objects = null;
        reverbs = null;
        eaxMix = -1F;
        occlusion = 0.0F;
        occlLF = 0.0F;
        obstruction = 0.0F;
        obstrLF = 0.0F;
        eaxRoom = 0.0F;
        globFX = null;
        prs = com.maddox.sound.AcousticsPreset.get(s);
        if(prs.ini != null)
        {
            handle = com.maddox.sound.Acoustics.jniCreate();
            load(prs.ini);
        }
        prs.list.add(this);
        if(com.maddox.sound.SoundListener.acc == null)
            com.maddox.sound.SoundListener.setAcoustics(this);
    }

    public Acoustics(com.maddox.sound.AcousticsPreset acousticspreset)
    {
        prs = null;
        handle = 0;
        rcaps = 0;
        envNum = 0;
        objects = null;
        reverbs = null;
        eaxMix = -1F;
        occlusion = 0.0F;
        occlLF = 0.0F;
        obstruction = 0.0F;
        obstrLF = 0.0F;
        eaxRoom = 0.0F;
        globFX = null;
        if(enabled)
        {
            handle = com.maddox.sound.Acoustics.jniCreate();
            load(acousticspreset.ini);
        }
        if(com.maddox.sound.SoundListener.acc == null)
            com.maddox.sound.SoundListener.setAcoustics(this);
    }

    public int getEnvNum()
    {
        return envNum;
    }

    public void setParent(com.maddox.sound.Acoustics acoustics)
    {
        com.maddox.sound.Acoustics.jniSetParent(handle, acoustics.handle);
    }

    public void setPosition(double d, double d1, double d2)
    {
        com.maddox.sound.Acoustics.jniSetPosition(handle, d, d1, d2);
    }

    public void setVelocity(float f, float f1, float f2)
    {
        com.maddox.sound.Acoustics.jniSetVelocity(handle, f, f1, f2);
    }

    public void setOrientation(float f, float f1, float f2, float f3, float f4, float f5)
    {
        com.maddox.sound.Acoustics.jniSetOrientation(handle, f, f1, f2, f3, f4, f5);
    }

    public float getControl(int i)
    {
        return com.maddox.sound.Acoustics.jniGetControl(handle, i);
    }

    public void setControl(int i, float f)
    {
        com.maddox.sound.Acoustics.jniSetControl(handle, i, f);
    }

    public com.maddox.sound.AcousticsGeometry getObject(java.lang.String s)
    {
        return objects != null ? (com.maddox.sound.AcousticsGeometry)objects.get(s) : null;
    }

    public void add(com.maddox.sound.AcousticsGeometry acousticsgeometry)
    {
        if(objects == null)
            objects = new HashMap();
        objects.put(acousticsgeometry.name, acousticsgeometry);
    }

    public void add(com.maddox.sound.Reverb reverb)
    {
        if(reverbs == null)
        {
            reverbs = new com.maddox.sound.Reverb[4];
            for(int i = 0; i < 4; i++)
                reverbs[i] = null;

        }
        reverbs[reverb.getEngine()] = reverb;
    }

    public boolean load(com.maddox.rts.SectFile sectfile)
    {
        if(!enabled)
            return true;
        if(!sectfile.sectionExist("common"))
        {
            errmsg("invalid acoustics preset format.");
            return false;
        }
        envNum = sectfile.get("common", "envnum", 0);
        eaxMix = sectfile.get("common", "eaxMix", -1F);
        occlusion = sectfile.get("common", "occlusion", 0.0F);
        occlLF = sectfile.get("common", "occlLF", 0.0F);
        obstruction = sectfile.get("common", "obstruction", 0.0F);
        obstrLF = sectfile.get("common", "obstrLF", 0.0F);
        eaxRoom = sectfile.get("common", "eaxRoom", 0.0F);
        com.maddox.sound.Acoustics.jniSetup(handle, envNum);
        com.maddox.sound.Acoustics.jniSetEAX(handle, eaxMix, occlusion, occlLF, eaxRoom, obstruction, obstrLF);
        boolean flag = sectfile.get("common", "hcontrol", false);
        if(sectfile.sectionExist("eax1"))
        {
            com.maddox.sound.Reverb reverb = new Reverb(0);
            if(flag)
                reverb.rfx = new ReverbFX(reverb);
            if(!reverb.load(sectfile))
                return false;
            add(reverb);
        }
        if(sectfile.sectionExist("eax2"))
        {
            com.maddox.sound.Reverb reverb1 = new Reverb(1);
            if(flag)
                reverb1.rfx = new ReverbFX(reverb1);
            if(!reverb1.load(sectfile))
                return false;
            add(reverb1);
        }
        return true;
    }

    public boolean save()
    {
        return save(prs.ini);
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        sectfile.set("common", "envnum", envNum);
        for(int i = 0; i < reverbs.length; i++)
            if(reverbs[i] != null)
                reverbs[i].save(sectfile);

        sectfile.saveFile();
        return true;
    }

    protected void finalize()
        throws java.lang.Throwable
    {
        if(handle != 0)
            com.maddox.sound.Acoustics.jniDestroy(handle);
        super.finalize();
    }

    protected void flush(float f)
    {
        if(reverbs != null)
        {
            int i = com.maddox.sound.AudioDevice.getAcousticsCaps();
            if((i & 4) != 0 && reverbs[1] != null)
            {
                com.maddox.sound.Reverb reverb = reverbs[1];
                reverb.apply();
                if(reverb.rfx != null)
                    reverb.rfx.tick(f);
                if(globFX != null)
                    globFX.tick(reverb);
            } else
            if((i & 8) != 0 && reverbs[2] != null)
            {
                com.maddox.sound.Reverb reverb1 = reverbs[2];
                reverb1.apply();
                if(reverb1.rfx != null)
                    reverb1.rfx.tick(f);
                if(globFX != null)
                    globFX.tick(reverb1);
            } else
            if((i & 2) != 0 && reverbs[0] != null)
            {
                com.maddox.sound.Reverb reverb2 = reverbs[0];
                reverb2.apply();
                if(reverb2.rfx != null)
                    reverb2.rfx.tick(f);
                if(globFX != null)
                    globFX.tick(reverb2);
            }
        }
    }

    protected static native int jniCreate();

    protected static native void jniDestroy(int i);

    protected static native void jniSetParent(int i, int j);

    protected static native void jniSetup(int i, int j);

    protected static native void jniSetEAX(int i, float f, float f1, float f2, float f3, float f4, float f5);

    protected static native void jniSetPosition(int i, double d, double d1, double d2);

    protected static native void jniSetVelocity(int i, float f, float f1, float f2);

    protected static native void jniSetOrientation(int i, float f, float f1, float f2, float f3, float f4, float f5);

    protected static native float jniGetControl(int i, int j);

    protected static native void jniSetControl(int i, int j, float f);

    protected com.maddox.sound.AcousticsPreset prs;
    protected int handle;
    protected int rcaps;
    protected int envNum;
    protected java.util.HashMap objects;
    protected com.maddox.sound.Reverb reverbs[];
    protected float eaxMix;
    protected float occlusion;
    protected float occlLF;
    protected float obstruction;
    protected float obstrLF;
    protected float eaxRoom;
    public com.maddox.sound.ReverbFXRoom globFX;
    private static final java.lang.String cmn = "common";
}
