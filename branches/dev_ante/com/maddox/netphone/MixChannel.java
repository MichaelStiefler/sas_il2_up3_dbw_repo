package com.maddox.netphone;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class MixChannel extends MixBase
{
  protected BitStream out;
  protected BitStream bitBuf;
  protected NetMixer owner;
  protected CodecIO rd;
  protected ArrayList outCache = new ArrayList();
  protected Vector inList = new Vector();
  protected Vector outList = new Vector();
  protected boolean act = false;
  protected int iid;
  protected int priority = 4;
  protected int slCnt;
  protected boolean isVirtual = false;
  private boolean wasReset = false;

  protected MixChannel(NetMixer paramNetMixer, int paramInt1, int paramInt2)
  {
    this.bitBuf = new BitStream(128);
    this.owner = paramNetMixer;
    this.iid = paramInt1;
    this.slCnt = 0;

    if (paramInt2 == 2) this.rd = new GsmIO(); else {
      this.rd = new LpcmIO();
    }
    this.out = new BitStream(this.rd.getMaxStreamSize());

    Iterator localIterator = paramNetMixer.channels.iterator();
    while (localIterator.hasNext()) {
      MixChannel localMixChannel = (MixChannel)localIterator.next();
      new HBitStr(this, localMixChannel);
      new HBitStr(localMixChannel, this);
    }

    paramNetMixer.channels.add(this);
  }

  public void destroy()
  {
    this.owner.channels.remove(this);
    HBitStr localHBitStr;
    while (!this.inList.isEmpty()) {
      localHBitStr = (HBitStr)this.inList.firstElement();
      localHBitStr.destroy();
    }

    while (!this.outList.isEmpty()) {
      localHBitStr = (HBitStr)this.outList.firstElement();
      localHBitStr.destroy();
    }

    this.out.destroy();
    this.bitBuf.destroy();

    print("NETMIXER::DELCH");
  }

  public int getPriority()
  {
    return this.priority;
  }

  public void setPriority(int paramInt)
  {
    this.priority = paramInt;
  }

  public boolean isActive()
  {
    return this.act;
  }

  public boolean wasReset()
  {
    boolean bool = this.wasReset;
    this.wasReset = false;
    return bool;
  }

  public void setActive(boolean paramBoolean)
  {
    if ((paramBoolean) && 
      (!this.act))
    {
      Iterator localIterator = this.inList.iterator();
      while (localIterator.hasNext()) {
        HBitStr localHBitStr = (HBitStr)localIterator.next();
        localHBitStr.bs.clear();
      }
      this.out.clear();
      this.wasReset = true;
    }

    this.act = paramBoolean;
  }

  public int getFreeSpace()
  {
    if ((!this.act) || (this.inList.isEmpty())) return 0;
    HBitStr localHBitStr = (HBitStr)this.inList.firstElement();
    return localHBitStr.bs.space(8);
  }

  public int put(byte[] paramArrayOfByte, int paramInt)
  {
    if ((!this.act) || (this.inList.isEmpty())) return 0;
    HBitStr localHBitStr = (HBitStr)this.inList.firstElement();
    return localHBitStr.bs.putAligned(paramArrayOfByte, paramInt);
  }

  public int putNA(byte[] paramArrayOfByte, int paramInt)
  {
    if ((!this.act) || (this.inList.isEmpty())) return 0;
    HBitStr localHBitStr = (HBitStr)this.inList.firstElement();
    return localHBitStr.bs.putBytes(paramArrayOfByte, paramInt);
  }

  public int getDataLength()
  {
    int i = this.out.bitlen();
    return i > 0 ? (i + 3 + 8) / 8 : 0;
  }

  public int getNALength()
  {
    return this.out.len(8);
  }

  public int get(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (paramInt - 8) * 8;
    if (this.out.bitlen() == 0) return 0;
    if (this.out.bitlen() <= i) return this.out.getAligned(paramArrayOfByte, paramInt); do
    {
      int k = this.out.get(1);
      if (k == 0) {
        this.bitBuf.put(0, 1);
      } else {
        this.bitBuf.put(1, 1);
        this.rd.read(this.out);
        this.rd.write(this.bitBuf);
      }
    }
    while ((this.out.bitlen() > 0) && (this.bitBuf.bitlen() < this.rd.getMinOutSpace()));

    int j = this.bitBuf.getAligned(paramArrayOfByte, paramInt);
    if (this.bitBuf.bitlen() > 0) {
      System.out.println("Netmixer internal error at output, len = " + this.bitBuf.bitlen());
      this.bitBuf.clear();
    }
    return j;
  }

  public int getNA(byte[] paramArrayOfByte, int paramInt)
  {
    return this.out.getBytes(paramArrayOfByte, paramInt);
  }

  public boolean isWait()
  {
    return this.slCnt > 0;
  }
}