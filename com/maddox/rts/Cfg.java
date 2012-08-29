// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cfg.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            IniFile

public interface Cfg
{

    public abstract java.lang.String name();

    public abstract boolean isPermanent();

    public abstract boolean isEnabled();

    public abstract void load(com.maddox.rts.IniFile inifile, java.lang.String s);

    public abstract com.maddox.rts.IniFile loadedSectFile();

    public abstract java.lang.String loadedSectName();

    public abstract void save();

    public abstract void save(com.maddox.rts.IniFile inifile, java.lang.String s);

    public abstract int apply();

    public abstract int applyStatus();

    public abstract void applyExtends(int i);

    public abstract void reset();

    public static final int RELOAD_MATERIALS = 4;
    public static final int RELOAD_MESHES = 8;
    public static final int RELOAD_OPENGL = 16;
}
