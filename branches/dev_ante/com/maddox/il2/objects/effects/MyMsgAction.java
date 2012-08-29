package com.maddox.il2.objects.effects;

import com.maddox.rts.MsgAction;

class MyMsgAction extends MsgAction
{
  Object obj2;

  public void doAction(Object paramObject)
  {
  }

  public MyMsgAction(double paramDouble, Object paramObject1, Object paramObject2)
  {
    super(paramDouble, paramObject1);
    this.obj2 = paramObject2;
  }
}