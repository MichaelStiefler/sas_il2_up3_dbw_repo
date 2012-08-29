// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GFileFilterName.java

package com.maddox.gwindow;

import com.maddox.util.StrMath;
import java.io.File;

// Referenced classes of package com.maddox.gwindow:
//            GFileFilter

public class GFileFilterName
    implements com.maddox.gwindow.GFileFilter
{

    public boolean accept(java.io.File file)
    {
        java.lang.String s = file.getName().toLowerCase();
        for(int i = 0; i < patterns.length; i++)
        {
            java.lang.String s1 = patterns[i];
            if(com.maddox.util.StrMath.simple(s1, s))
                return true;
        }

        return false;
    }

    public java.lang.String getDescription()
    {
        return description;
    }

    public GFileFilterName(java.lang.String s, java.lang.String as[])
    {
        description = s;
        patterns = as;
        for(int i = 0; i < patterns.length; i++)
            patterns[i] = patterns[i].toLowerCase();

    }

    public java.lang.String description;
    public java.lang.String patterns[];
}
