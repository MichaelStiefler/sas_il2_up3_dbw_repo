package com.maddox.rts;

public class HotKeyCmdRun extends HotKeyCmd
{
  private CmdEnv env;

  public void begin()
  {
    this.env.exec(name());
  }

  public HotKeyCmdRun(CmdEnv paramCmdEnv, boolean paramBoolean, String paramString)
  {
    super(paramBoolean, paramString);
    this.env = paramCmdEnv;
  }
}