package com.maddox.il2.engine;

import java.util.HashMap;

public class Animates
{
  public HashMap map = new HashMap();

  public void add(AnimateMove paramAnimateMove)
  {
    this.map.put(paramAnimateMove.name, paramAnimateMove);
  }

  public AnimateMove get(String paramString)
  {
    return (AnimateMove)this.map.get(paramString);
  }

  public void created()
  {
  }

  public Animates()
  {
    created();
  }
}