package com.maddox.rts;

import java.util.ArrayList;

public class MessageCache
{
  private Class cls;
  private ArrayList cache;
  private int icache;

  public Message get()
  {
    for (int i = 0; i < this.cache.size(); i++) {
      if (!((Message)this.cache.get(this.icache)).busy())
        return (Message)this.cache.get(this.icache);
      this.icache = ((this.icache + 1) % this.cache.size());
    }
    this.icache = 0;
    try {
      Message localMessage = (Message)this.cls.newInstance();
      localMessage._flags |= 2;
      this.cache.add(localMessage);
      return localMessage;
    } catch (Exception localException) {
      localException.printStackTrace();
    }return null;
  }

  public MessageCache(Class paramClass)
  {
    this.cls = paramClass;
    this.cache = new ArrayList();
    this.icache = 0;
  }
}