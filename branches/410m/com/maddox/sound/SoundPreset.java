// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundPreset.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.sound:
//            Preset, Sample, Emitter, AudioDevice, 
//            VGroup, ControlInfo, SoundControl

public class SoundPreset extends com.maddox.sound.Preset
{

    public SoundPreset(java.lang.String s)
    {
        super(s);
        sources = null;
        emitters = null;
        fxCaps = 0;
        fxFlags = 0;
        type = 0;
        eaxMix = -1F;
        occlusion = 0.0F;
        occlLF = 0.0F;
        obstruction = 0.0F;
        obstrLF = 0.0F;
        eaxRoom = 0.0F;
        controls = null;
        map.put(s, this);
        if(!enabled)
            return;
        try
        {
            com.maddox.rts.SectFile sectfile = new SectFile("presets/sounds/" + s + ".prs");
            handle = com.maddox.sound.SoundPreset.jniGet(s);
            if(handle == 0)
                handle = jniCreate(s);
            onCreate();
            load(sectfile, "");
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.sound.SoundPreset.printf("Cannot load sound preset " + s + " (" + exception + ")");
            handle = 0;
        }
        return;
    }

    public static com.maddox.sound.SoundPreset get(java.lang.String s)
    {
        com.maddox.sound.SoundPreset soundpreset = (com.maddox.sound.SoundPreset)map.get(s);
        if(soundpreset == null)
            soundpreset = new SoundPreset(s);
        return soundpreset;
    }

    public void add(com.maddox.sound.Preset preset)
    {
        sources.add(preset);
    }

    public boolean is3d()
    {
        return (fxCaps & 7) != 0;
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
        java.lang.String s2 = s + "common";
        if(!sectfile.sectionExist(s2))
            throw new Exception("Invalid preset format");
        eaxMix = sectfile.get(s2, "eaxMix", -1F);
        occlusion = sectfile.get(s2, "occlusion", 0.0F);
        occlLF = sectfile.get(s2, "occlLF", 0.0F);
        obstruction = sectfile.get(s2, "obstruction", 0.0F);
        obstrLF = sectfile.get(s2, "obstrLF", 0.0F);
        eaxRoom = sectfile.get(s2, "eaxRoom", 0.0F);
        jniSetMutable(handle, sectfile.get(s2, "mutable", false));
        int i = sectfile.sectionIndex("spl");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            int i1 = 0;
            for(int j1 = 0; j1 < j; j1++)
            {
                boolean flag = false;
                int j2 = 0;
                int k2 = 0;
                float f = 70F;
                float f1 = 0.0F;
                for(java.util.StringTokenizer stringtokenizer1 = new StringTokenizer(sectfile.line(i, j1)); stringtokenizer1.hasMoreTokens();)
                {
                    java.lang.String s7 = stringtokenizer1.nextToken();
                    if(s7.compareToIgnoreCase("*") == 0)
                    {
                        flag = true;
                        j2 = 0;
                    } else
                    if(flag)
                    {
                        k2 |= 1 << java.lang.Integer.parseInt(s7);
                    } else
                    {
                        if(j2 == 0)
                            f = java.lang.Float.parseFloat(s7);
                        else
                        if(j2 == 1)
                            f1 = java.lang.Float.parseFloat(s7);
                        else
                            throw new Exception("Invalid arguments");
                        j2++;
                    }
                }

                if(k2 == 0)
                    k2 = -1;
                if((i1 & k2) != 0)
                    throw new Exception("Invalid spl flags");
                i1 |= k2;
                jniSetSPL(handle, f, f1, k2);
            }

        }
        java.lang.String s1 = sectfile.get(s2, "mode", (java.lang.String)null);
        if(s1 != null)
        {
            if(s1.indexOf("seq") >= 0)
                fxFlags |= 0x8000;
            if(s1.indexOf("normal") >= 0)
            {
                if(s1.indexOf(" pos") >= 0)
                    fxCaps |= 1;
                if(s1.indexOf(" vel") >= 0)
                    fxCaps |= 2;
                if(s1.indexOf(" or") >= 0)
                    fxCaps |= 4;
                if(s1.indexOf(" all") >= 0)
                {
                    fxCaps |= 1;
                    fxCaps |= 2;
                    fxCaps |= 4;
                    fxCaps |= 8;
                }
                if(!is3d())
                    fxCaps |= 3;
            }
            if(s1.indexOf("relist") >= 0)
            {
                if(s1.indexOf(" pos") >= 0)
                    fxCaps |= 1;
                fxCaps |= 0x200;
            }
            if(s1.indexOf("relobj") >= 0)
            {
                if(s1.indexOf(" pos") >= 0)
                    fxCaps |= 1;
                fxCaps |= 0x100;
            }
        }
        s1 = sectfile.get(s2, "type", (java.lang.String)null);
        type = 0;
        if(sectfile.sectionExist(s + "emit"))
            type = 1;
        else
        if(s1 != null)
        {
            int k = 0;
            do
            {
                if(k >= typeList.length)
                    break;
                if(typeList[k].compareToIgnoreCase(s1) == 0)
                {
                    type = k;
                    break;
                }
                k++;
            } while(true);
        }
        if(sectfile.get(s2, "infinite", false))
            fxFlags = 1;
        if(sectfile.get(s2, "permanent", false))
            fxFlags |= 0x4000;
        if(sectfile.get(s2, "undetune", false))
            fxFlags |= 0x2000;
        if(sectfile.get(s2, "numadj", false))
            fxFlags |= 0x1000;
        if(sectfile.get(s2, "pmax", false))
            fxFlags |= 0x10000;
        if(sectfile.get(s2, "music", false))
            fxCaps |= 0x1000;
        int l = sectfile.get(s2, "forcectrl", -1);
        if(handle != 0)
        {
            jniSetProperties(handle, type, fxFlags, com.maddox.sound.AudioDevice.vObj.handle);
            jniSetEAX(handle, eaxMix, occlusion, occlLF, eaxRoom, obstruction, obstrLF);
            com.maddox.sound.VGroup vgroup = null;
            if((fxCaps & 0x1000) != 0)
                vgroup = com.maddox.sound.AudioDevice.vMusic;
            else
            if(sectfile.get(s2, "voice", false))
                vgroup = com.maddox.sound.AudioDevice.vVoice;
            else
            if((fxFlags & 0x4000) == 0)
                vgroup = com.maddox.sound.AudioDevice.vObj;
            if(vgroup != null)
                jniSetVGroup(handle, vgroup.handle);
            jniForceControl(handle, l);
        }
        java.lang.String s3 = sectfile.get(s2, "controls", (java.lang.String)null);
        if(s3 != null)
        {
            controls = new ArrayList();
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(s3);
            do
            {
                if(!stringtokenizer.hasMoreTokens())
                    break;
                java.lang.String s4 = stringtokenizer.nextToken();
                com.maddox.sound.SoundControl soundcontrol = com.maddox.sound.ControlInfo.get(s4, jniGetControlList(handle));
                if(soundcontrol != null)
                {
                    soundcontrol.load(sectfile, s + "." + s4);
                    controls.add(soundcontrol);
                }
            } while(true);
        }
        int k1 = sectfile.sectionIndex(s + "samples");
        if(k1 >= 0)
        {
            if(sources == null)
                sources = new ArrayList(32);
            for(int l1 = 0; l1 < sectfile.vars(k1); l1++)
            {
                java.lang.String s5 = sectfile.line(k1, l1);
                com.maddox.sound.Sample sample = new Sample(s5);
                sample.load(sectfile, "sample." + s5);
                sources.add(sample);
                jniAddSample(handle, sample.handle);
            }

        }
        k1 = sectfile.sectionIndex(s + "emit");
        if(k1 >= 0)
        {
            if(emitters == null)
                emitters = new ArrayList(32);
            for(int i2 = 0; i2 < sectfile.vars(k1); i2++)
            {
                java.lang.String s6 = sectfile.line(k1, i2);
                com.maddox.sound.Emitter emitter = new Emitter(s6, handle);
                emitter.load(sectfile, "emit." + s6);
                emitters.add(emitter);
            }

        }
    }

    public void save(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
        java.lang.String s1 = s + name + ".";
        sectfile.sectionClear(sectfile.sectionIndex(s + "common"));
        sectfile.sectionClear(sectfile.sectionIndex(s + "objects"));
        if(sources != null)
        {
            for(int i = 0; i < sources.size(); i++)
            {
                com.maddox.sound.Preset preset = (com.maddox.sound.Preset)sources.get(i);
                preset.save(sectfile, s1 + "object.");
            }

        }
    }

    protected void set(java.lang.String s, java.lang.String s1)
    {
        if(s.compareToIgnoreCase("mix") == 0)
            eaxMix = java.lang.Float.parseFloat(s1);
        else
        if(s.compareToIgnoreCase("occl") == 0)
            occlusion = java.lang.Float.parseFloat(s1);
        else
        if(s.compareToIgnoreCase("lf") == 0)
            occlLF = java.lang.Float.parseFloat(s1);
        else
        if(s.compareToIgnoreCase("room") == 0)
            eaxRoom = java.lang.Float.parseFloat(s1);
        else
            java.lang.System.out.println("params: mix = " + eaxMix + ", occlusion = " + occlusion + ", lf = " + occlLF + ", room = " + eaxRoom);
        if(handle != 0)
            jniSetEAX(handle, eaxMix, occlusion, occlLF, eaxRoom, obstruction, obstrLF);
    }

    protected void onCreate()
    {
    }

    protected int createObject()
    {
        if(handle == 0)
            return 0;
        int i = com.maddox.sound.SoundPreset.jniGetFreeObject(handle, fxCaps);
        if(i == 0)
            i = com.maddox.sound.SoundPreset.jniCreateObject(handle, fxCaps);
        return i;
    }

    protected native int jniCreate(java.lang.String s);

    protected native void jniSetMutable(int i, boolean flag);

    protected native void jniSetSPL(int i, float f, float f1, int j);

    protected native void jniSetProperties(int i, int j, int k, int l);

    protected native void jniSetEAX(int i, float f, float f1, float f2, float f3, float f4, float f5);

    protected native void jniSetVGroup(int i, int j);

    protected native void jniAddSample(int i, int j);

    protected native int jniGetControlList(int i);

    protected native void jniForceControl(int i, int j);

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int M3D_NONE = 0;
    public static final int M3D_NORMAL = 1;
    public static final float STD_SPL = 70F;
    protected static final int SND_NORMAL = 0;
    protected static final int SND_MIXER = 1;
    protected java.util.ArrayList sources;
    protected java.util.ArrayList emitters;
    protected int fxCaps;
    protected int fxFlags;
    protected int type;
    protected float eaxMix;
    protected float occlusion;
    protected float occlLF;
    protected float obstruction;
    protected float obstrLF;
    protected float eaxRoom;
    protected java.util.ArrayList controls;
    protected static com.maddox.util.HashMapExt map = new HashMapExt();
    protected static java.lang.Class csid[];
    protected static java.lang.Object args[] = new java.lang.Object[1];
    private static java.lang.String typeList[] = {
        "normal", "mixer"
    };

    static 
    {
        csid = new java.lang.Class[1];
        csid[0] = java.lang.String.class;
    }
}
