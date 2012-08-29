package com.maddox.rts;

import java.util.Vector;

public class Listeners
{
  private Vector vlisteners;
  private Object[] listeners;

  public Object[] get()
  {
    return this.listeners;
  }

  public void insListener(Object paramObject)
  {
    if ((paramObject != null) && 
      (-1 == this.vlisteners.indexOf(paramObject))) {
      this.vlisteners.insertElementAt(paramObject, 0);
      Object[] arrayOfObject = new Object[this.vlisteners.size()];
      this.vlisteners.copyInto(arrayOfObject);
      this.listeners = arrayOfObject;
    }
  }

  public void addListener(Object paramObject)
  {
    if ((paramObject != null) && 
      (-1 == this.vlisteners.indexOf(paramObject))) {
      this.vlisteners.addElement(paramObject);
      Object[] arrayOfObject = new Object[this.vlisteners.size()];
      this.vlisteners.copyInto(arrayOfObject);
      this.listeners = arrayOfObject;
    }
  }

  public void removeListener(Object paramObject)
  {
    if (paramObject != null) {
      this.vlisteners.removeElement(paramObject);
      if (this.vlisteners.size() > 0) {
        Object[] arrayOfObject = new Object[this.vlisteners.size()];
        this.vlisteners.copyInto(arrayOfObject);
        this.listeners = arrayOfObject;
      } else {
        this.listeners = null;
      }
    }
  }

  public Listeners()
  {
    this.vlisteners = new Vector();
    this.listeners = null;
  }
}