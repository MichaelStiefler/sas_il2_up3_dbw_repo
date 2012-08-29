package com.maddox.sound;

import com.maddox.rts.CmdEnv;

public class CfgMusState extends SoundFlags
{
  public String name()
  {
    return "MusState";
  }
  public int countFlags() {
    return 3;
  }
  public String nameFlag(int paramInt) {
    switch (paramInt) { case 0:
      return "takeoff";
    case 1:
      return "inflight";
    case 2:
      return "crash";
    }
    return null;
  }

  public int apply(int paramInt) {
    CmdEnv.top().exec("music APPLY");
    return 0;
  }
}