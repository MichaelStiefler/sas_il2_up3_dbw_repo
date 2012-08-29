package com.maddox.rts;

import java.util.Comparator;

class NetChannelTimeComparator
  implements Comparator
{
  public int compare(Object paramObject1, Object paramObject2)
  {
    NetMsgFiltered localNetMsgFiltered1 = (NetMsgFiltered)paramObject1;
    NetMsgFiltered localNetMsgFiltered2 = (NetMsgFiltered)paramObject2;
    if (localNetMsgFiltered1._time < localNetMsgFiltered2._time) return -1;
    if ((localNetMsgFiltered1._time == localNetMsgFiltered2._time) && (localNetMsgFiltered1._timeStamp < localNetMsgFiltered2._timeStamp)) return -1;
    return 1;
  }
}