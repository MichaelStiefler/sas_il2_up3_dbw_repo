package com.maddox.sound;

import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Spawn;
import java.io.PrintStream;
import java.util.Vector;

public class RadioChannelSpawn
  implements NetSpawn
{
  protected static Vector channels = new Vector();

  protected static boolean hrChannels = true;

  public RadioChannelSpawn()
  {
    channels = new Vector();
    Spawn.add(RadioChannel.class, this);
  }

  public static boolean getUseHRChannels()
  {
    return hrChannels;
  }

  public static void useHRChannels(boolean paramBoolean)
  {
    hrChannels = paramBoolean;
  }

  public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
  {
    try {
      if (!hrChannels) return;
      RadioChannel localRadioChannel = new RadioChannel(paramNetMsgInput, paramInt, this);
      channels.addElement(localRadioChannel);
      ((NetUser)NetEnv.host()).radio_onCreated(localRadioChannel.name);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void create(String paramString, int paramInt)
  {
    try
    {
      if (findChannel(paramString) != null) {
        System.out.println("Channel " + paramString + " already exists !");
        return;
      }
      RadioChannel localRadioChannel = new RadioChannel(paramString, this, paramInt);
      channels.addElement(localRadioChannel);
      ((NetUser)NetEnv.host()).radio_onCreated(paramString);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  protected int getChannelIndex(String paramString)
  {
    for (int i = 0; i < channels.size(); i++) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(i);
      if (localRadioChannel.name.equals(paramString)) return i;
    }
    return -1;
  }

  public boolean isExistChannel(String paramString)
  {
    return findChannel(paramString) != null;
  }

  protected RadioChannel findChannel(String paramString)
  {
    for (int i = 0; i < channels.size(); i++) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(i);
      if (localRadioChannel.name.equals(paramString)) return (RadioChannel)channels.elementAt(i);
    }
    return null;
  }

  public void kill(String paramString)
  {
    for (int i = 0; i < channels.size(); i++) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(i);
      if (localRadioChannel.name.equals(paramString)) {
        if (localRadioChannel.isMirror()) {
          System.out.println("Cannot kill mirror channel !");
          return;
        }
        localRadioChannel.destroy();
      }
    }
  }

  protected void onDestroyChannel(RadioChannel paramRadioChannel)
  {
    channels.removeElement(paramRadioChannel);
  }

  public void killMasterChannels()
  {
    int i = 0;
    while (i < channels.size()) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(i);
      if (localRadioChannel.isMaster()) localRadioChannel.destroy(); else
        i++;
    }
  }

  public boolean set(String paramString)
  {
    RadioChannel localRadioChannel1 = null;
    if (paramString != null)
      localRadioChannel1 = findChannel(paramString);
    if ((paramString != null) && (localRadioChannel1 == null)) {
      System.out.println("Cannot find radio channel " + paramString);
      return false;
    }
    if ((localRadioChannel1 != null) && (localRadioChannel1 == RadioChannel.activeChannel))
      return true;
    for (int i = 0; i < channels.size(); i++) {
      RadioChannel localRadioChannel2 = (RadioChannel)channels.elementAt(i);
      if (localRadioChannel2 == localRadioChannel1) continue; localRadioChannel2.setActive(false);
    }
    if (localRadioChannel1 == null) AudioDevice.setInput(0); else
      localRadioChannel1.setActive(true);
    return true;
  }

  public int getNumChannels()
  {
    return channels.size();
  }

  public String getChannelName(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= channels.size()))
    {
      System.out.println("Invalid channel index !" + paramInt);
      return null;
    }
    return ((RadioChannel)channels.elementAt(paramInt)).name;
  }

  public RadioChannel getChannel(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= channels.size())) {
      System.out.println("Invalid channel index !" + paramInt);
      return null;
    }
    return (RadioChannel)channels.elementAt(paramInt);
  }

  public void list()
  {
    System.out.println("Channel list:");
    for (int i = 0; i < channels.size(); i++) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(i);
      System.out.println("  " + i + " -> " + localRadioChannel.name);
    }
  }

  public void printInfo()
  {
    int i = -1;
    for (int j = 0; j < channels.size(); j++) {
      RadioChannel localRadioChannel = (RadioChannel)channels.elementAt(j);
      localRadioChannel.printInfo();
    }
  }
}