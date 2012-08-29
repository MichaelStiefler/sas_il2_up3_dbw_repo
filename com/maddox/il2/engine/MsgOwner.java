package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

public class MsgOwner extends Message
{
  private int _id;
  private Actor _oldOwner;
  private static final int ATTACH = 0;
  private static final int DETACH = 1;
  private static final int CHANGE = 2;
  private static final int DEAD = 3;
  private static final int TASK_COMPLETE = 4;
  private static MessageCache cache = new MessageCache(MsgOwner.class);

  public MsgOwner()
  {
    this._id = -1;
    this._oldOwner = null;
  }

  public void clean() {
    super.clean();
    this._oldOwner = null;
  }

  protected static void attach(Actor paramActor1, Actor paramActor2) {
    if (paramActor1.isRealTime()) cacheGet(0, null).post(64, paramActor1, Time.currentReal(), paramActor2);
    else if (!RTSConf.isResetGame()) cacheGet(0, null).post(0, paramActor1, Time.current(), paramActor2); 
  }

  protected static void detach(Actor paramActor1, Actor paramActor2)
  {
    if (paramActor1.isRealTime()) cacheGet(1, null).post(64, paramActor1, Time.currentReal(), paramActor2);
    else if (!RTSConf.isResetGame()) cacheGet(1, null).post(0, paramActor1, Time.current(), paramActor2); 
  }

  protected static void died(Actor paramActor1, Actor paramActor2)
  {
    if (paramActor1.isRealTime()) cacheGet(3, null).post(64, paramActor1, Time.currentReal(), paramActor2);
    else if (!RTSConf.isResetGame()) cacheGet(3, null).post(0, paramActor1, Time.current(), paramActor2); 
  }

  protected static void taskComplete(Actor paramActor1, Actor paramActor2)
  {
    if (paramActor1.isRealTime()) cacheGet(4, null).post(64, paramActor1, Time.currentReal(), paramActor2);
    else if (!RTSConf.isResetGame()) cacheGet(4, null).post(0, paramActor1, Time.current(), paramActor2); 
  }

  protected static void change(Actor paramActor1, Actor paramActor2, Actor paramActor3)
  {
    if (paramActor1.isRealTime()) cacheGet(2, paramActor3).post(64, paramActor2, Time.currentReal(), paramActor1);
    else if (!RTSConf.isResetGame()) cacheGet(2, paramActor3).post(0, paramActor1, Time.current(), paramActor2); 
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgOwnerListener)) {
      switch (this._id) { case 0:
        ((MsgOwnerListener)paramObject).msgOwnerAttach((Actor)this.jdField__sender_of_type_JavaLangObject); break;
      case 1:
        ((MsgOwnerListener)paramObject).msgOwnerDetach((Actor)this.jdField__sender_of_type_JavaLangObject); break;
      case 3:
        ((MsgOwnerListener)paramObject).msgOwnerDied((Actor)this.jdField__sender_of_type_JavaLangObject); break;
      case 4:
        ((MsgOwnerListener)paramObject).msgOwnerTaskComplete((Actor)this.jdField__sender_of_type_JavaLangObject); break;
      case 2:
        ((MsgOwnerListener)paramObject).msgOwnerChange((Actor)this.jdField__sender_of_type_JavaLangObject, this._oldOwner);
      }
      return true;
    }
    return false;
  }

  private static MsgOwner cacheGet(int paramInt, Actor paramActor) {
    MsgOwner localMsgOwner = (MsgOwner)cache.get();
    localMsgOwner._id = paramInt;
    localMsgOwner._oldOwner = paramActor;
    return localMsgOwner;
  }
}