package com.maddox.rts;

public class RTSProfile
{
  public int frames;
  public int messages;
  protected int countMessages;

  public static void get(RTSProfile paramRTSProfile)
  {
    paramRTSProfile.frames = RTSConf.cur.profile.frames;
    paramRTSProfile.messages = RTSConf.cur.profile.messages;

    RTSConf.cur.profile.frames = 0;
    RTSConf.cur.profile.messages = 0;
  }

  protected void endFrame() {
    this.frames += 1;
    this.messages += this.countMessages;
    this.countMessages = 0;
  }
}