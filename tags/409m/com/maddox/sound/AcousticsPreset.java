// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AcousticsPreset.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.sound:
//            BaseObject

public class AcousticsPreset extends com.maddox.sound.BaseObject
{

    public AcousticsPreset(java.lang.String s)
    {
        ini = null;
        list = new ArrayList();
        name = s;
        if(com.maddox.sound.BaseObject.enabled)
            ini = new SectFile("presets/acoustics/" + s + ".prs", 1);
        map.put(s, this);
    }

    public static com.maddox.sound.AcousticsPreset get(java.lang.String s)
    {
        com.maddox.sound.AcousticsPreset acousticspreset = (com.maddox.sound.AcousticsPreset)map.get(s);
        if(acousticspreset == null)
            acousticspreset = new AcousticsPreset(s);
        return acousticspreset;
    }

    protected java.lang.String name;
    protected com.maddox.rts.SectFile ini;
    protected java.util.ArrayList list;
    protected static com.maddox.util.HashMapExt map = new HashMapExt();

}
