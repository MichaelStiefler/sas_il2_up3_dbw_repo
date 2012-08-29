// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSound.java

package com.maddox.sound;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package com.maddox.sound:
//            SoundPreset

public class CmdSound extends com.maddox.rts.Cmd
{

    public CmdSound()
    {
        prs = null;
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
                    for(java.util.Map.Entry entry = com.maddox.sound.SoundPreset.map.nextEntry(null); entry != null; entry = com.maddox.sound.SoundPreset.map.nextEntry(entry))
                    {
                        com.maddox.sound.SoundPreset soundpreset = (com.maddox.sound.SoundPreset)entry.getValue();
                        java.lang.System.out.println("preset : " + soundpreset.name);
                    }

                }
                if(map.containsKey("select"))
                {
                    java.lang.String s = com.maddox.rts.Cmd.arg(map, "select", 0);
                    prs = (com.maddox.sound.SoundPreset)com.maddox.sound.SoundPreset.map.get(s);
                }
                if(map.containsKey("set"))
                    if(prs == null)
                        java.lang.System.out.println("sound preset not selected");
                    else
                        prs.set(com.maddox.rts.Cmd.arg(map, "set", 0), com.maddox.rts.Cmd.arg(map, "set", 1));
            }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public static final java.lang.String LIST = "list";
    public static final java.lang.String SELECT = "select";
    public static final java.lang.String SET = "set";
    public static final java.lang.String SAVE = "save";
    public com.maddox.sound.SoundPreset prs;
}
