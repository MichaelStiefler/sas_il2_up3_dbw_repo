// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetUserRegiment.java

package com.maddox.il2.net;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.game.Main;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.net:
//            NetFileServerReg

public class NetUserRegiment extends com.maddox.il2.ai.Regiment
{

    public java.lang.String name()
    {
        if(fileNameTga.length() > 0)
            return "UserRegiment" + fileNameTga;
        else
            return "UserRegiment";
    }

    public java.lang.String fileName()
    {
        return null;
    }

    public boolean isUserDefined()
    {
        return true;
    }

    private void setBranch(java.lang.String s)
    {
        branch = s.toLowerCase().intern();
        if(com.maddox.il2.ai.Regiment.branchMap.containsKey(branch))
            country = (java.lang.String)com.maddox.il2.ai.Regiment.branchMap.get(branch);
        else
            country = branch;
    }

    public java.lang.String fileNameTga()
    {
        if(fileNameTga.length() == 0)
            return "";
        else
            return "../Cache/" + fileNameTga;
    }

    public java.lang.String ownerFileNameBmp()
    {
        return ownerFileNameBmp;
    }

    public boolean isEmpty()
    {
        return "".equals(fileNameTga);
    }

    public void setId(char ac[])
    {
        id = ac;
        sid = new String(id);
    }

    public void setGruppeNumber(int i)
    {
        gruppeNumber = i;
    }

    public boolean equals(java.lang.String s, java.lang.String s1, char ac[], int i)
    {
        if(!s.equals(branch))
            return false;
        if(!s1.equals(ownerFileNameBmp))
            return false;
        if(ac[0] != id[0] || ac[1] != id[1])
            return false;
        return i == gruppeNumber;
    }

    public void setLocalFileNameBmp(java.lang.String s)
    {
        localFileNameBmp = s;
        if(s == null || "".equals(s))
        {
            fileNameTga = "";
            return;
        }
        s = s.toLowerCase();
        int i = s.lastIndexOf('/');
        int j = s.lastIndexOf('\\');
        if(i >= 0)
        {
            if(j > 0 && j < i)
                i = j;
        } else
        {
            i = j;
        }
        if(i >= 0)
            fileNameTga = s.substring(i + 1);
        else
            fileNameTga = s;
        i = fileNameTga.lastIndexOf(".bmp");
        if(i >= 0)
            fileNameTga = fileNameTga.substring(0, i);
        fileNameTga = fileNameTga + ".tga";
        java.lang.String s1 = null;
        com.maddox.il2.net.NetFileServerReg netfileserverreg = com.maddox.il2.game.Main.cur().netFileServerReg;
        if(ownerFileNameBmp.equalsIgnoreCase(localFileNameBmp))
            s1 = netfileserverreg.primaryPath() + "/" + localFileNameBmp;
        else
            s1 = netfileserverreg.alternativePath() + "/" + localFileNameBmp;
        if(!com.maddox.il2.engine.BmpUtils.bmp8PalToTGA4(s1, "PaintSchemes/Cache/" + fileNameTga))
            fileNameTga = "";
    }

    public void set(java.lang.String s, java.lang.String s1, char ac[], int i)
    {
        if(s1 == null)
            s1 = "";
        if(equals(s, s1, ac, i))
        {
            return;
        } else
        {
            setBranch(s);
            ownerFileNameBmp = s1;
            localFileNameBmp = null;
            setId(ac);
            setGruppeNumber(i);
            return;
        }
    }

    public void destroy()
    {
        if(netFileRequest != null)
        {
            netFileRequest.doCancel();
            netFileRequest = null;
        }
        super.destroy();
    }

    public NetUserRegiment()
    {
        ownerFileNameBmp = "";
        fileNameTga = "";
        flags |= 0x4000;
        set("ru", "", new char[] {
            '0', '0'
        }, 1);
    }

    protected com.maddox.rts.net.NetFileRequest netFileRequest;
    protected java.lang.String ownerFileNameBmp;
    protected java.lang.String localFileNameBmp;
    protected java.lang.String fileNameTga;
}
