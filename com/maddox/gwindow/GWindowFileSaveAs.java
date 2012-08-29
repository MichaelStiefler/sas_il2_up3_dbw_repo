// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFileSaveAs.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowFileBox, GCaption, GWindow, GWindowLookAndFeel, 
//            GWindowButton, GFileFilter

public class GWindowFileSaveAs extends com.maddox.gwindow.GWindowFileBox
{

    public GWindowFileSaveAs(com.maddox.gwindow.GWindow gwindow, boolean flag, java.lang.String s, com.maddox.gwindow.GFileFilter agfilefilter[])
    {
        super(gwindow, flag, gwindow.lAF().i18n("Save_As"), s, agfilefilter);
        wOk.cap = new GCaption(lAF().i18n("&Save"));
    }

    public GWindowFileSaveAs(com.maddox.gwindow.GWindow gwindow, boolean flag, java.lang.String s, java.lang.String s1, com.maddox.gwindow.GFileFilter agfilefilter[])
    {
        super(gwindow, flag, s, s1, agfilefilter);
        wOk.cap = new GCaption(lAF().i18n("&Save"));
    }
}
