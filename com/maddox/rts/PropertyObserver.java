package com.maddox.rts;

import java.util.ArrayList;

class PropertyObserver extends Property
{
  private static ArrayList invokeStack = new ArrayList();
  private static int invokeStackPtr = -1;

  private ArrayList listeners = new ArrayList();

  protected void addListener(Object paramObject, boolean paramBoolean1, boolean paramBoolean2)
  {
    checkListeners();
    int i = findListener(paramObject);
    if (i < 0)
      this.listeners.add(new PropertyListener(paramObject, paramBoolean1, paramBoolean2));
  }

  protected void removeListener(Object paramObject) {
    checkListeners();
    int i = findListener(paramObject);
    if (i >= 0) {
      PropertyListener localPropertyListener = (PropertyListener)this.listeners.get(i);
      this.listeners.remove(i);
      localPropertyListener.clear();
    }
  }

  protected void invokeListeners(int paramInt, Property paramProperty) {
    if (this.listeners.size() == 0) return;
    checkListeners();
    if (this.listeners.size() == 0) return;
    invokeStackPtr += 1;
    Object[] arrayOfObject1 = null;
    if (invokeStackPtr == invokeStack.size()) {
      arrayOfObject1 = this.listeners.toArray();
      invokeStack.add(arrayOfObject1);
    } else {
      Object[] arrayOfObject2 = (Object[])invokeStack.get(invokeStackPtr);
      arrayOfObject1 = this.listeners.toArray(arrayOfObject2);
      if (arrayOfObject1 != arrayOfObject2)
        this.listeners.set(invokeStackPtr, arrayOfObject1);
    }
    for (int i = 0; i < arrayOfObject1.length; i++) {
      PropertyListener localPropertyListener = (PropertyListener)arrayOfObject1[i];
      if (localPropertyListener == null) break;
      Object localObject = localPropertyListener.listener();
      if (localObject != null)
        MsgProperty.post(localPropertyListener.isRealTime(), localPropertyListener.isSend(), localObject, paramProperty, paramInt);
      arrayOfObject1[i] = null;
    }
    invokeStackPtr -= 1;
  }

  protected void checkListeners()
  {
    for (int i = 0; i < this.listeners.size(); i++) {
      PropertyListener localPropertyListener = (PropertyListener)this.listeners.get(i);
      if (localPropertyListener.listener() == null) {
        this.listeners.remove(i);
        i--;
      }
    }
  }

  protected int findListener(Object paramObject) {
    for (int i = 0; i < this.listeners.size(); i++) {
      PropertyListener localPropertyListener = (PropertyListener)this.listeners.get(i);
      if (localPropertyListener.listener() == paramObject)
        return i;
    }
    return -1;
  }

  protected PropertyObserver(Object paramObject) {
    super(paramObject, "OBSERVER");
  }
}