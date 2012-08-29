package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

public class MsgBase extends Message
{
  private int _id;
  private Hook _hook;
  private Actor _oldBase;
  private Hook _oldHook;
  public static final int ATTACH = 0;
  public static final int DETACH = 1;
  public static final int CHANGE = 2;
  private static MessageCache cache = new MessageCache(MsgBase.class);

  public MsgBase()
  {
    this._id = -1;
    this._hook = null;
    this._oldBase = null;
    this._oldHook = null;
  }

  public void clean() {
    super.clean();
    this._hook = null;
    this._oldBase = null;
    this._oldHook = null;
  }

  protected static void attach(Actor paramActor1, Actor paramActor2) {
    if (RTSConf.isResetGame()) return;
    if (paramActor1.isRealTime()) cacheGet(0, null, null, null).post(64, paramActor1, Time.currentReal(), paramActor2); else
      cacheGet(0, null, null, null).post(0, paramActor1, Time.current(), paramActor2);
  }

  protected static void detach(Actor paramActor1, Actor paramActor2) {
    if (RTSConf.isResetGame()) return;
    if (paramActor1.isRealTime()) cacheGet(1, null, null, null).post(64, paramActor1, Time.currentReal(), paramActor2); else
      cacheGet(1, null, null, null).post(0, paramActor1, Time.current(), paramActor2);
  }

  protected static void change(Actor paramActor1, Actor paramActor2, Hook paramHook1, Actor paramActor3, Hook paramHook2) {
    if (RTSConf.isResetGame()) return;
    if (paramActor1.isRealTime()) cacheGet(2, paramHook1, paramActor3, paramHook2).post(64, paramActor1, Time.currentReal(), paramActor2); else
      cacheGet(2, paramHook1, paramActor3, paramHook2).post(0, paramActor1, Time.current(), paramActor2);
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgBaseListener)) {
      switch (this._id) { case 0:
        ((MsgBaseListener)paramObject).msgBaseAttach((Actor)this._sender); break;
      case 1:
        ((MsgBaseListener)paramObject).msgBaseDetach((Actor)this._sender); break;
      case 2:
        ((MsgBaseListener)paramObject).msgBaseChange((Actor)this._sender, this._hook, this._oldBase, this._oldHook);
      }
      return true;
    }
    return false;
  }

  private static MsgBase cacheGet(int paramInt, Hook paramHook1, Actor paramActor, Hook paramHook2) {
    MsgBase localMsgBase = (MsgBase)cache.get();
    localMsgBase._id = paramInt;
    localMsgBase._hook = paramHook1;
    localMsgBase._oldBase = paramActor;
    localMsgBase._oldHook = paramHook2;
    return localMsgBase;
  }
}