// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRecordSave.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;

// Referenced classes of package com.maddox.il2.gui:
//            GUILoadSave, GUIClient

public class GUIRecordSave extends com.maddox.il2.gui.GUILoadSave
{

    public java.lang.String getPathFiles()
    {
        return "records";
    }

    public java.lang.String getFileExtension()
    {
        return ".trk";
    }

    public java.lang.String getInfo()
    {
        return i18n("loadsave.recordSaveInfo");
    }

    public java.lang.String getListName()
    {
        return com.maddox.il2.game.I18N.gui("loadsave.TrackFiles");
    }

    public java.lang.String getEditName()
    {
        return i18n("loadsave.TrackName");
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

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        _enter();
        wTable.setSelect(-1, 0);
        wEdit.setValue("");
    }

    public boolean execute(java.lang.String s, boolean flag)
    {
        _fileName = s;
        if(!flag)
        {
            doSave();
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
                        doSave();
                        com.maddox.il2.game.Main.stateStack().pop();
                        return;
                    }
                }

            }
;
            return false;
        }
    }

    protected void doSave()
    {
        com.maddox.il2.game.Main3D.cur3D().saveRecordedMission(getPathFiles() + "/" + appendExtension(_fileName));
    }

    public GUIRecordSave(com.maddox.gwindow.GWindowRoot gwindowroot, int i)
    {
        super(i);
        init(gwindowroot);
    }

    public GUIRecordSave(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(9);
        init(gwindowroot);
    }

    protected java.lang.String _fileName;
}
