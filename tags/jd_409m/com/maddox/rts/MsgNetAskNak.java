package com.maddox.rts;

public class MsgNetAskNak extends Message
{
  private boolean bAsk;
  private NetMsgGuaranted guaranted;
  private static MessageCache cache = new MessageCache(MsgNetAskNak.class);

  public static void postReal(long paramLong, Object paramObject, boolean paramBoolean, NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel)
  {
    MsgNetAskNak localMsgNetAskNak = (MsgNetAskNak)cache.get();
    localMsgNetAskNak.bAsk = paramBoolean;
    localMsgNetAskNak.guaranted = paramNetMsgGuaranted;
    localMsgNetAskNak.jdField__sender_of_type_JavaLangObject = paramNetChannel;
    localMsgNetAskNak.post(64, paramObject, paramLong);
  }

  public static void postGame(long paramLong, Object paramObject, boolean paramBoolean, NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel)
  {
    MsgNetAskNak localMsgNetAskNak = (MsgNetAskNak)cache.get();
    localMsgNetAskNak.bAsk = paramBoolean;
    localMsgNetAskNak.guaranted = paramNetMsgGuaranted;
    localMsgNetAskNak.jdField__sender_of_type_JavaLangObject = paramNetChannel;
    localMsgNetAskNak.post(0, paramObject, paramLong);
  }

  public void clean() {
    super.clean();
    this.guaranted = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgNetAskNakListener)) {
      if (this.bAsk) ((MsgNetAskNakListener)paramObject).msgNetAsk(this.guaranted, (NetChannel)this.jdField__sender_of_type_JavaLangObject); else
        ((MsgNetAskNakListener)paramObject).msgNetNak(this.guaranted, (NetChannel)this.jdField__sender_of_type_JavaLangObject);
      return true;
    }
    return false;
  }
}