package com.maddox.sound;

import com.maddox.rts.CmdEnv;

public class CfgMusFlags extends SoundFlags
{
  public String name()
  {
    return "MusFlags";
  }

  public int countFlags() {
    return 1;
  }

  public String nameFlag(int paramInt) {
    switch (paramInt) { case 0:
      return "play";
    }
    return null;
  }

  public int apply(int paramInt) {
    CmdEnv.top().exec("music APPLY");
    return 0;
  }
}