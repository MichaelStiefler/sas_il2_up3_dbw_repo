// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Reverb.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.io.PrintStream;

// Referenced classes of package com.maddox.sound:
//            BaseObject, ReverbFX

public class Reverb extends com.maddox.sound.BaseObject
{

    public Reverb(int i)
    {
        handle = 0;
        engine = 0;
        rfx = null;
        engine = i;
        handle = com.maddox.sound.Reverb.jniCreate(i);
    }

    public int getEngine()
    {
        return engine;
    }

    public float get(int i)
    {
        return com.maddox.sound.Reverb.jniGet(handle, i);
    }

    public void set(int i, float f)
    {
        com.maddox.sound.Reverb.jniSet(handle, i, f);
    }

    public int getType()
    {
        return com.maddox.sound.Reverb.jniGetType(handle);
    }

    public void setType(int i)
    {
        com.maddox.sound.Reverb.jniSetType(handle, i);
    }

    protected void finalize()
        throws java.lang.Throwable
    {
        if(handle != 0)
            com.maddox.sound.Reverb.jniDestroy(handle);
        super.finalize();
    }

    protected java.lang.String getEngineName()
    {
        if(engine == 0)
            return "eax1";
        if(engine == 1)
            return "eax2";
        if(engine == 2)
            return "ial2";
        if(engine == 3)
            return "eax3";
        else
            return "";
    }

    protected void set(java.lang.String s, java.lang.String s1)
    {
        if(engine == 0)
            if(s.compareToIgnoreCase("type") == 0)
            {
                for(int i = 0; i < 26; i++)
                {
                    if(envTab[i].compareToIgnoreCase(s1) != 0)
                        continue;
                    setType(i);
                    break;
                }

            } else
            if(s.compareToIgnoreCase("room") == 0)
                set(1, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("decay") == 0)
                set(3, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("hf") == 0)
            {
                set(4, java.lang.Float.parseFloat(s1));
            } else
            {
                java.lang.String s2 = "valid type values are : ";
                java.lang.System.out.println("valid keys are: type room decay hf");
                for(int k = 0; k < 26; k++)
                    s2 = s2 + envTab[k];

                java.lang.System.out.println(s2);
            }
        if(engine == 1 || engine == 2)
            if(s.compareToIgnoreCase("type") == 0)
            {
                for(int j = 0; j < 26; j++)
                {
                    if(envTab[j].compareToIgnoreCase(s1) != 0)
                        continue;
                    setType(j);
                    break;
                }

            } else
            if(s.compareToIgnoreCase("room") == 0)
                set(1, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("roomhf") == 0)
                set(1, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("decay") == 0)
                set(3, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("decayhf") == 0)
                set(4, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("early") == 0)
                set(5, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("earlydelay") == 0)
                set(6, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("reverb") == 0)
                set(7, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("reverbdelay") == 0)
                set(8, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("size") == 0)
                set(9, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("diffusion") == 0)
                set(10, java.lang.Float.parseFloat(s1));
            else
            if(s.compareToIgnoreCase("absorption") == 0)
            {
                set(11, java.lang.Float.parseFloat(s1));
            } else
            {
                java.lang.String s3 = "valid type values are : ";
                java.lang.System.out.println("valid keys are: <type room roomhf decay decayhf early ");
                java.lang.System.out.println("....earlydelay reverb reverbdelay size diffusion absorption>");
                for(int l = 0; l < 26; l++)
                    s3 = s3 + envTab[l];

                java.lang.System.out.println(s3);
            }
    }

    protected boolean load(com.maddox.rts.SectFile sectfile)
    {
        if(engine == 0)
        {
            int i = -1;
            java.lang.String s = sectfile.get("eax1", "Type", (java.lang.String)null);
            for(int k = 0; k < 26; k++)
            {
                if(envTab[k].compareToIgnoreCase(s) != 0)
                    continue;
                i = k;
                break;
            }

            if(i == -1)
                return false;
            setType(i);
            set(1, sectfile.get("eax1", "Room", 0.0F));
            set(3, sectfile.get("eax1", "Decay", 0.0F));
            set(4, sectfile.get("eax1", "DecayHf", 0.0F));
            if(rfx != null)
                rfx.load(sectfile);
            return true;
        }
        if(engine == 1 || engine == 2)
        {
            int j = -1;
            java.lang.String s1 = engine != 2 ? "eax2" : "ial2";
            java.lang.String s2 = sectfile.get(s1, "Type", (java.lang.String)null);
            for(int l = 0; l < 26; l++)
            {
                if(envTab[l].compareToIgnoreCase(s2) != 0)
                    continue;
                j = l;
                break;
            }

            if(j == -1)
                return false;
            setType(j);
            set(1, sectfile.get("eax2", "Room", 0.0F));
            set(2, sectfile.get("eax2", "RoomHf", 0.0F));
            set(3, sectfile.get("eax2", "Decay", 0.0F));
            set(4, sectfile.get("eax2", "DecayHf", 0.0F));
            set(5, sectfile.get("eax2", "Early", 0.0F));
            set(6, sectfile.get("eax2", "EarlyDelay", 0.0F));
            set(7, sectfile.get("eax2", "Reverb", 0.0F));
            set(8, sectfile.get("eax2", "ReverbDelay", 0.0F));
            set(9, sectfile.get("eax2", "Size", 0.0F));
            set(10, sectfile.get("eax2", "Diffusion", 0.0F));
            set(11, sectfile.get("eax2", "Absorption", 0.0F));
            if(rfx != null)
                rfx.load(sectfile);
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean save(com.maddox.rts.SectFile sectfile)
    {
        if(engine == 0)
        {
            sectfile.set("eax1", "Type", envTab[getType()]);
            sectfile.set("eax1", "Room", get(1));
            sectfile.set("eax1", "Decay", get(3));
            sectfile.set("eax1", "DecayHf", get(4));
            return true;
        }
        if(engine == 1 || engine == 2)
        {
            java.lang.String s = engine != 2 ? "eax2" : "ial2";
            sectfile.set(s, "Type", envTab[getType()]);
            sectfile.set("eax2", "Room", get(1));
            sectfile.set("eax2", "RoomHf", get(2));
            sectfile.set("eax2", "Decay", get(3));
            sectfile.set("eax2", "DecayHf", get(4));
            sectfile.set("eax2", "Early", get(5));
            sectfile.set("eax2", "EarlyDelay", get(6));
            sectfile.set("eax2", "Reverb", get(7));
            sectfile.set("eax2", "ReverbDelay", get(8));
            sectfile.set("eax2", "Size", get(9));
            sectfile.set("eax2", "Diffusion", get(10));
            sectfile.set("eax2", "Absorption", get(11));
            return true;
        } else
        {
            return false;
        }
    }

    protected void apply()
    {
        com.maddox.sound.Reverb.jniApply(handle, true);
    }

    protected static native int jniCreate(int i);

    protected static native void jniDestroy(int i);

    protected static native void jniApply(int i, boolean flag);

    protected static native float jniGet(int i, int j);

    protected static native void jniSet(int i, int j, float f);

    protected static native int jniGetType(int i);

    protected static native void jniSetType(int i, int j);

    public static final int ENGINE_EAX1 = 0;
    public static final int ENGINE_EAX2 = 1;
    public static final int ENGINE_IAL2 = 2;
    public static final int ENGINE_EAX3 = 3;
    public static final int NUM_ENGINES = 4;
    public static final int REVERB_GENERIC = 0;
    public static final int REVERB_PADDEDCELL = 1;
    public static final int REVERB_ROOM = 2;
    public static final int REVERB_BATHROOM = 3;
    public static final int REVERB_LIVINGROOM = 4;
    public static final int REVERB_STONEROOM = 5;
    public static final int REVERB_AUDITORIUM = 6;
    public static final int REVERB_CONCERTHALL = 7;
    public static final int REVERB_CAVE = 8;
    public static final int REVERB_ARENA = 9;
    public static final int REVERB_HANGAR = 10;
    public static final int REVERB_CARPETEDHALLWAY = 11;
    public static final int REVERB_HALLWAY = 12;
    public static final int REVERB_STONECORRIDOR = 13;
    public static final int REVERB_ALLEY = 14;
    public static final int REVERB_FOREST = 15;
    public static final int REVERB_CITY = 16;
    public static final int REVERB_MOUNTAINS = 17;
    public static final int REVERB_QUARRY = 18;
    public static final int REVERB_PLAIN = 19;
    public static final int REVERB_PARKINGLOT = 20;
    public static final int REVERB_SEWERPIPE = 21;
    public static final int REVERB_UNDERWATER = 22;
    public static final int REVERB_DRUGGED = 23;
    public static final int REVERB_DIZZY = 24;
    public static final int REVERB_PSYCHOTIC = 25;
    public static final int ROOM = 1;
    public static final int ROOM_HF = 2;
    public static final int DECAY = 3;
    public static final int DECAY_HF = 4;
    public static final int EARLY = 5;
    public static final int EARLY_DELAY = 6;
    public static final int REVERB = 7;
    public static final int REVERB_DELAY = 8;
    public static final int SIZE = 9;
    public static final int DIFFUSION = 10;
    public static final int ABSORPTION = 11;
    public static final int ROOM_ATTN = 100;
    protected int handle;
    protected int engine;
    protected com.maddox.sound.ReverbFX rfx;
    protected static java.lang.String envTab[];

    static 
    {
        envTab = new java.lang.String[26];
        envTab[0] = "GENERIC";
        envTab[1] = "PADDEDCELL";
        envTab[2] = "ROOM";
        envTab[3] = "BATHROOM";
        envTab[4] = "LIVINGROOM";
        envTab[5] = "STONEROOM";
        envTab[6] = "AUDITORIUM";
        envTab[7] = "CONCERTHALL";
        envTab[8] = "CAVE";
        envTab[9] = "ARENA";
        envTab[10] = "HANGAR";
        envTab[11] = "CARPETEDHALLWAY";
        envTab[12] = "HALLWAY";
        envTab[13] = "STONECORRIDOR";
        envTab[14] = "ALLEY";
        envTab[15] = "FOREST";
        envTab[16] = "CITY";
        envTab[17] = "MOUNTAINS";
        envTab[18] = "QUARRY";
        envTab[19] = "PLAIN";
        envTab[20] = "PARKINGLOT";
        envTab[21] = "SEWERPIPE";
        envTab[22] = "UNDERWATER";
        envTab[23] = "DRUGGED";
        envTab[24] = "DIZZY";
        envTab[25] = "PSYCHOTIC";
    }
}
