package com.maddox.il2.engine;

import com.maddox.rts.Message;

public class MsgCollisionRequest extends Message
{
  private static MsgCollisionRequest msg = new MsgCollisionRequest();
  private static boolean[] res = new boolean[1];

  protected static boolean on(Actor paramActor1, Actor paramActor2)
  {
    msg.jdField__sender_of_type_JavaLangObject = paramActor2;
    res[0] = true;
    msg.send(paramActor1);
    msg.jdField__sender_of_type_JavaLangObject = null;
    if (res[0] == 0)
      return false;
    msg.jdField__sender_of_type_JavaLangObject = paramActor1;
    res[0] = true;
    msg.send(paramActor2);
    msg.jdField__sender_of_type_JavaLangObject = null;
    return res[0];
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgCollisionRequestListener)) {
      ((MsgCollisionRequestListener)paramObject).msgCollisionRequest((Actor)this.jdField__sender_of_type_JavaLangObject, res);
      return true;
    }
    return false;
  }
}