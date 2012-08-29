package com.maddox.sound;

public class SoundList
{
  protected SoundFX first;

  public SoundList()
  {
    this.first = null;
  }

  public SoundFX get()
  {
    return this.first;
  }

  public void clear()
  {
    while (this.first != null) this.first.remove();
  }

  public void destroy()
  {
    while (this.first != null) this.first.destroy();
  }
}