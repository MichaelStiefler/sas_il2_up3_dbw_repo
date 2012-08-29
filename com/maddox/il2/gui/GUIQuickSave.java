// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIQuickSave.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.gui:
//            GUILoadSave, GUIClient

public class GUIQuickSave extends com.maddox.il2.gui.GUILoadSave
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
        return i18n("loadsave.quickSaveInfo");
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
        return i18n("loadsave.Save");
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
        _fileName = s;
        if(!flag)
        {
            sect.saveFile(getPathFiles() + "/" + appendExtension(s));
            return true;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(client.root, 20F, true, i18n("warning.Warning"), i18n("warning.ReplaceFile"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i != 3)
                    {
                        return;
                    } else
                    {
                        sect.saveFile(getPathFiles() + "/" + appendExtension(_fileName));
                        com.maddox.il2.game.Main.stateStack().pop();
                        return;
                    }
                }

            }
;
            return false;
        }
    }

    public GUIQuickSave(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(24);
        init(gwindowroot);
    }

    public com.maddox.rts.SectFile sect;
    private java.lang.String _fileName;

}
