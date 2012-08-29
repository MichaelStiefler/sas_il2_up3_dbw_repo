// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIQuickLoad.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.I18N;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.gui:
//            GUILoadSave, GUIQuick

public class GUIQuickLoad extends com.maddox.il2.gui.GUILoadSave
{

    public java.lang.String getPathFiles()
    {
        return "quicks";
    }

    public java.lang.String getFileExtension()
    {
        return ".quick";
    }

    public java.lang.String getInfo()
    {
        return i18n("loadsave.quickLoadInfo");
    }

    public java.lang.String getListName()
    {
        return com.maddox.il2.game.I18N.gui("loadsave.QuickFiles");
    }

    public java.lang.String getEditName()
    {
        return i18n("loadsave.QuickName");
    }

    public java.lang.String getDelName()
    {
        return i18n("loadsave.Delete");
    }

    public java.lang.String getDoName()
    {
        return i18n("loadsave.Load");
    }

    public java.lang.String getBackName()
    {
        return i18n("loadsave.Done");
    }

    public boolean exitOnDo()
    {
        return true;
    }

    public boolean execute(java.lang.String s, boolean flag)
    {
        if(!flag)
        {
            return false;
        } else
        {
            sect = new SectFile(getPathFiles() + "/" + appendExtension(s), 0);
            com.maddox.il2.gui.GUIQuick guiquick = (com.maddox.il2.gui.GUIQuick)com.maddox.il2.game.GameState.get(14);
            guiquick.ssect = sect;
            return true;
        }
    }

    public GUIQuickLoad(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(25);
        init(gwindowroot);
        wEdit.setEditable(false);
    }

    public com.maddox.rts.SectFile sect;
}
