// Source File Name: TypeAIM9Carrier.java
// Author:           Storebror
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.air;

public interface TypeAIM9Carrier {

  public abstract com.maddox.il2.engine.Actor getAIM9target();

  public abstract boolean hasMissiles();

  public abstract int getAIM9lockState();

  public abstract com.maddox.JGP.Point3f getAIM9targetOffset();
}
