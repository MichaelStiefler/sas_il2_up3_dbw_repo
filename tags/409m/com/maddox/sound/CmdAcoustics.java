// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdAcoustics.java

package com.maddox.sound;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package com.maddox.sound:
//            AcousticsPreset, SoundListener, Acoustics, Reverb

public class CmdAcoustics extends com.maddox.rts.Cmd
{

    public CmdAcoustics()
    {
        rev = null;
        param.put("list", null);
        param.put("set", null);
        param.put("save", null);
        param.put("select", null);
        _properties.put("NAME", "acc");
        _levelAccess = 0;
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        if(!map.isEmpty())
            if(map.containsKey("_$$"))
            {
                java.lang.System.out.println("Unknown command :" + com.maddox.rts.Cmd.arg(map, "_$$", 0));
            } else
            {
                if(map.containsKey("list"))
                {
                    for(java.util.Map.Entry entry = com.maddox.sound.AcousticsPreset.map.nextEntry(null); entry != null; entry = com.maddox.sound.AcousticsPreset.map.nextEntry(entry))
                    {
                        com.maddox.sound.AcousticsPreset acousticspreset = (com.maddox.sound.AcousticsPreset)entry.getValue();
                        java.lang.System.out.println("preset : " + acousticspreset.name + " , objects : " + acousticspreset.list.size());
                    }

                }
                if(map.containsKey("select"))
                {
                    java.lang.String s = com.maddox.rts.Cmd.arg(map, "select", 0);
                    rev = null;
                    if(com.maddox.sound.SoundListener.acc.reverbs != null)
                        if(s.compareToIgnoreCase("eax1") == 0)
                            rev = com.maddox.sound.SoundListener.acc.reverbs[0];
                        else
                        if(s.compareToIgnoreCase("eax2") == 0)
                        {
                            rev = com.maddox.sound.SoundListener.acc.reverbs[1];
                        } else
                        {
                            java.lang.System.out.println("Invalid reverb name : " + s);
                            return com.maddox.rts.CmdEnv.RETURN_OK;
                        }
                    if(com.maddox.sound.SoundListener.acc.reverbs == null)
                        java.lang.System.out.println("Listener reverb " + s + " is null");
                }
                if(map.containsKey("set"))
                    rev.set(com.maddox.rts.Cmd.arg(map, "set", 0), com.maddox.rts.Cmd.arg(map, "set", 1));
                if(map.containsKey("save"))
                    if(com.maddox.sound.SoundListener.acc == null)
                        java.lang.System.out.println("Listener acoustics is null");
                    else
                        com.maddox.sound.SoundListener.acc.save();
            }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public static final java.lang.String LIST = "list";
    public static final java.lang.String SELECT = "select";
    public static final java.lang.String SET = "set";
    public static final java.lang.String SAVE = "save";
    protected com.maddox.sound.Reverb rev;
}
