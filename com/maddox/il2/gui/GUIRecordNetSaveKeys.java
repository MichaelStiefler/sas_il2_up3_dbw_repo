// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRecordNetSaveKeys.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.rts.InOutStreams;
import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRecordSave

public class GUIRecordNetSaveKeys extends com.maddox.il2.gui.GUIRecordSave
{

    public java.lang.String getFileExtension()
    {
        return ".ntrk";
    }

    protected void doSave()
    {
    }

    private void streamCopy(com.maddox.rts.InOutStreams inoutstreams, com.maddox.rts.InOutStreams inoutstreams1, java.lang.String s)
        throws java.lang.Exception
    {
        java.io.InputStream inputstream = inoutstreams.openStream(s);
        java.io.OutputStream outputstream = inoutstreams1.createStream(s);
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.available();
            if(i != 0)
            {
                if(i > abyte0.length)
                    i = abyte0.length;
                inputstream.read(abyte0, 0, i);
                outputstream.write(abyte0, 0, i);
            } else
            {
                inputstream.close();
                outputstream.close();
                return;
            }
        } while(true);
    }

    public GUIRecordNetSaveKeys(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(gwindowroot, 60);
    }

    public static java.lang.String trackFileName;
    private byte buf[];
}
