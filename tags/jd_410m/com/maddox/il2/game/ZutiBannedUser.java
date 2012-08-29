package com.maddox.il2.game;

import com.maddox.rts.Time;

public class ZutiBannedUser
{
  private String name;
  private String IP;
  private long duration;
  private boolean permanent;

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getIP()
  {
    return this.IP;
  }

  public void setIP(String paramString)
  {
    this.IP = paramString;
  }

  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }

  public void setPermanent(boolean paramBoolean)
  {
    this.permanent = paramBoolean;
  }

  public boolean isMatch(String paramString1, String paramString2)
  {
    return (this.name.trim().equalsIgnoreCase(paramString1.trim())) && (this.IP.trim().equalsIgnoreCase(paramString2.trim()));
  }

  public boolean isBanned()
  {
    if (this.permanent) {
      return true;
    }

    return Time.current() < this.duration;
  }
}