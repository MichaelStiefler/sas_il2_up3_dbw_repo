package com.maddox.netphone;

import java.util.Vector;

public class BsData
{
  protected int size;
  protected int wp;
  protected int maxlen;
  protected boolean rdflag;
  protected byte[] data;
  protected Vector links = new Vector();

  public BsData(int paramInt)
  {
    this.size = (paramInt * 8);
    this.wp = 0;
    this.data = new byte[paramInt];
    for (int i = 0; i < paramInt; i++) this.data[i] = 0;
    this.maxlen = 0;
    this.rdflag = false;
  }
}