// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetUSGSControl.java

package com.maddox.il2.net;

import com.maddox.rts.NetControl;
import com.maddox.rts.NetControlReal;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetObj;
import java.util.List;

public class NetUSGSControl
    implements com.maddox.rts.NetControlReal
{

    public void msgNewClient(com.maddox.rts.NetObj netobj, int i, java.lang.String s, int j)
    {
        control.doRequest(netobj, i, "NM");
    }

    public void msgAnswer(com.maddox.rts.NetObj netobj, int i, java.lang.String s)
    {
        if(s.indexOf("NM") >= 0)
        {
            int j = s.indexOf('"', s.indexOf("NM"));
            int k = s.lastIndexOf('"');
            if(j >= 0 && j + 1 < k)
            {
                java.lang.String s1 = s.substring(j + 1, k);
                if(s1.equals(com.maddox.rts.NetEnv.host().shortName()))
                {
                    control.doNak(netobj, i, "user alredy connected");
                    return;
                }
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                for(int l = 0; l < list.size(); l++)
                    if(s1.equals(((com.maddox.rts.NetHost)list.get(l)).shortName()))
                    {
                        control.doNak(netobj, i, "user alredy connected");
                        return;
                    }

                control.doAsk(netobj, i);
                return;
            }
        }
        control.doNak(netobj, i, "unknown user");
    }

    public void destroy()
    {
        if(control != null)
        {
            control.destroy();
            control = null;
        }
    }

    public NetUSGSControl()
    {
        control = new NetControl(this);
    }

    private com.maddox.rts.NetControl control;
}
