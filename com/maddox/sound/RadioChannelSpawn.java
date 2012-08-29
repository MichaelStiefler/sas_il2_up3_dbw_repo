// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RadioChannelSpawn.java

package com.maddox.sound;

import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Spawn;
import java.io.PrintStream;
import java.util.Vector;

// Referenced classes of package com.maddox.sound:
//            RadioChannel, AudioDevice

public class RadioChannelSpawn
    implements com.maddox.rts.NetSpawn
{

    public RadioChannelSpawn()
    {
        channels = new Vector();
        com.maddox.rts.Spawn.add(com.maddox.sound.RadioChannel.class, this);
    }

    public static boolean getUseHRChannels()
    {
        return hrChannels;
    }

    public static void useHRChannels(boolean flag)
    {
        hrChannels = flag;
    }

    public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
    {
        if(!hrChannels)
            return;
        try
        {
            com.maddox.sound.RadioChannel radiochannel = new RadioChannel(netmsginput, i, this);
            channels.addElement(radiochannel);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio_onCreated(radiochannel.name);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return;
    }

    public void create(java.lang.String s, int i)
    {
        if(findChannel(s) != null)
        {
            java.lang.System.out.println("Channel " + s + " already exists !");
            return;
        }
        try
        {
            com.maddox.sound.RadioChannel radiochannel = new RadioChannel(s, this, i);
            channels.addElement(radiochannel);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio_onCreated(s);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return;
    }

    protected int getChannelIndex(java.lang.String s)
    {
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            if(radiochannel.name.equals(s))
                return i;
        }

        return -1;
    }

    public boolean isExistChannel(java.lang.String s)
    {
        return findChannel(s) != null;
    }

    protected com.maddox.sound.RadioChannel findChannel(java.lang.String s)
    {
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            if(radiochannel.name.equals(s))
                return (com.maddox.sound.RadioChannel)channels.elementAt(i);
        }

        return null;
    }

    public void kill(java.lang.String s)
    {
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            if(radiochannel.name.equals(s))
            {
                if(radiochannel.isMirror())
                {
                    java.lang.System.out.println("Cannot kill mirror channel !");
                    return;
                }
                radiochannel.destroy();
            }
        }

    }

    protected void onDestroyChannel(com.maddox.sound.RadioChannel radiochannel)
    {
        channels.removeElement(radiochannel);
    }

    public void killMasterChannels()
    {
        for(int i = 0; i < channels.size();)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            if(radiochannel.isMaster())
                radiochannel.destroy();
            else
                i++;
        }

    }

    public boolean set(java.lang.String s)
    {
        com.maddox.sound.RadioChannel radiochannel = null;
        if(s != null)
            radiochannel = findChannel(s);
        if(s != null && radiochannel == null)
        {
            java.lang.System.out.println("Cannot find radio channel " + s);
            return false;
        }
        if(radiochannel != null && radiochannel == com.maddox.sound.RadioChannel.activeChannel)
            return true;
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel1 = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            if(radiochannel1 != radiochannel)
                radiochannel1.setActive(false);
        }

        if(radiochannel == null)
            com.maddox.sound.AudioDevice.setInput(0);
        else
            radiochannel.setActive(true);
        return true;
    }

    public int getNumChannels()
    {
        return channels.size();
    }

    public java.lang.String getChannelName(int i)
    {
        if(i < 0 || i >= channels.size())
        {
            java.lang.System.out.println("Invalid channel index !" + i);
            return null;
        } else
        {
            return ((com.maddox.sound.RadioChannel)channels.elementAt(i)).name;
        }
    }

    public com.maddox.sound.RadioChannel getChannel(int i)
    {
        if(i < 0 || i >= channels.size())
        {
            java.lang.System.out.println("Invalid channel index !" + i);
            return null;
        } else
        {
            return (com.maddox.sound.RadioChannel)channels.elementAt(i);
        }
    }

    public void list()
    {
        java.lang.System.out.println("Channel list:");
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            java.lang.System.out.println("  " + i + " -> " + radiochannel.name);
        }

    }

    public void printInfo()
    {
        byte byte0 = -1;
        for(int i = 0; i < channels.size(); i++)
        {
            com.maddox.sound.RadioChannel radiochannel = (com.maddox.sound.RadioChannel)channels.elementAt(i);
            radiochannel.printInfo();
        }

    }

    protected static java.util.Vector channels = new Vector();
    protected static boolean hrChannels = true;

}
