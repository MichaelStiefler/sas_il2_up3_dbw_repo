// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRecordNetSave.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.net.NetMissionTrack;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRecordSave

public class GUIRecordNetSave extends com.maddox.il2.gui.GUIRecordSave
{

    public java.lang.String getFileExtension()
    {
        return ".ntrk";
    }

    protected void doSave()
    {
        float f = (float)com.maddox.il2.engine.Config.cur.netSpeed / 1000F + 5F;
        com.maddox.il2.net.NetMissionTrack.startRecording(getPathFiles() + "/" + appendExtension(_fileName), f);
    }

    public GUIRecordNetSave(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(gwindowroot, 59);
    }
}
