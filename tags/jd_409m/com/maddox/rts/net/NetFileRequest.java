package com.maddox.rts.net;

import com.maddox.rts.Destroy;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetException;
import com.maddox.rts.NetObj;

public class NetFileRequest
{
  public static final int STATE_INIT = 2;
  public static final int STATE_PROGRESS = 1;
  public static final int STATE_SUCCESS = 0;
  public static final int STATE_ERR_OWNER_DISCONNECT = -1;
  public static final int STATE_ERR_NOT_FOUND = -2;
  public static final int STATE_ERR_IO = -3;
  public static final int STATE_USER_CANCEL = -4;
  public static final int PRIOR_MIN = 0;
  public static final int PRIOR_MAX = 255;
  private NetFileClient client;
  private NetFileServer server;
  protected Object serverData;
  private int prior;
  private NetObj owner;
  protected String ownerFileName;
  private int state = 2;
  private String localFileName;
  private float complete = 0.0F;
  public Object _serverIn;
  public Object _serverOut;

  public NetFileClient client()
  {
    return this.client; } 
  public NetFileServer server() { return this.server; } 
  public Object serverData() { return this.serverData; } 
  public int prior() { return this.prior; } 
  public NetObj owner() { return this.owner; } 
  public String ownerFileName() { return this.ownerFileName; } 
  public int state() { return this.state; } 
  public String localFileName() { return this.localFileName; } 
  public float complete() { return this.complete; } 
  public boolean isEnded() {
    return this.state != 1;
  }

  public String localFullFileName(String paramString1, String paramString2) {
    if (this.localFileName == null) return null;
    if (this.ownerFileName.equals(this.localFileName)) {
      if (paramString1 != null) {
        return paramString1 + "/" + this.localFileName;
      }
      return this.localFileName;
    }
    if (paramString2 != null) {
      return paramString2 + "/" + this.localFileName;
    }
    return this.localFileName;
  }

  public void doRequest()
  {
    if (((this instanceof Destroy)) && (((Destroy)this).isDestroyed()))
      throw new NetException("NetFileRequest is destroyed");
    if (state() == 1)
      throw new NetException("Request in progress");
    if (client() == null)
      throw new NetException("Client == null");
    if (((client() instanceof Destroy)) && (((Destroy)client()).isDestroyed()))
      throw new NetException("Client is destroyed");
    if ((prior() < 0) || (prior() > 255))
      throw new NetException("BAD parameter Proir");
    if (owner() == null)
      throw new NetException("Owner == null");
    if (((owner() instanceof Destroy)) && (owner().isDestroyed()))
      throw new NetException("Owner is destroyed");
    if ((ownerFileName() == null) || (ownerFileName().length() == 0))
      throw new NetException("OwnerFileName is empty");
    if (HomePath.isFileSystemName(ownerFileName())) {
      throw new NetException("Bad OwnerFileName");
    }
    this.state = 2;
    this.localFileName = null;
    this.complete = 0.0F;

    if (((server() instanceof Destroy)) && (((Destroy)server()).isDestroyed()))
      throw new NetException("Server is destroyed");
    if (!(server() instanceof NetObj))
      throw new NetException("Server NOT NetObj");
    server().doRequest(this);
  }

  public void doCancel() {
    if (((this instanceof Destroy)) && (((Destroy)this).isDestroyed()))
      throw new NetException("NetFileRequest is destroyed");
    if (state() != 1)
      throw new NetException("Request NOT in progress");
    if (this.state == 2)
      return;
    if (((server() instanceof Destroy)) && (((Destroy)server()).isDestroyed()))
      throw new NetException("Server is destroyed");
    server().doCancel(this);
  }

  public void doAnswer() {
    if (((client() instanceof Destroy)) && (((Destroy)client()).isDestroyed()))
      return;
    client().netFileAnswer(this);
  }
  public void setState(int paramInt) { this.state = paramInt; } 
  public void setLocalFileName(String paramString) { this.localFileName = paramString; } 
  public void setComplete(float paramFloat) { this.complete = paramFloat; }

  public String toString() {
    String str = null;
    switch (this.state) { case 2:
      str = "init"; break;
    case 1:
      str = "progress " + (int)(100.0F * this.complete); break;
    case 0:
      str = "success"; break;
    case -1:
      str = "owner disconnected"; break;
    case -2:
      str = "not found"; break;
    case -3:
      str = "io error"; break;
    case -4:
      str = "canceled"; break;
    default:
      str = "UNKNOWN";
    }
    if (localFileName() != null) {
      return owner() + ":" + ownerFileName() + " (" + localFileName() + ") state = " + str;
    }
    return owner() + ":" + ownerFileName() + " state = " + str;
  }

  public int hashCode() {
    return this.owner.hashCode() + this.ownerFileName.hashCode();
  }

  public boolean equals(Object paramObject) {
    if (paramObject == this) return true;
    if (paramObject == null) return false;
    if (!(paramObject instanceof NetFileRequest)) return false;
    NetFileRequest localNetFileRequest = (NetFileRequest)paramObject;
    if (this.serverData != null) {
      if (!this.serverData.equals(localNetFileRequest.serverData))
        return false;
    } else if (localNetFileRequest.serverData != null) {
      return false;
    }
    return (localNetFileRequest.owner == this.owner) && (localNetFileRequest.ownerFileName.equals(this.ownerFileName));
  }

  public NetFileRequest(NetFileClient paramNetFileClient, NetFileServer paramNetFileServer, int paramInt, NetObj paramNetObj, String paramString)
  {
    this(paramNetFileClient, paramNetFileServer, null, paramInt, paramNetObj, paramString);
  }

  public NetFileRequest(NetFileClient paramNetFileClient, int paramInt, NetObj paramNetObj, String paramString)
  {
    this(paramNetFileClient, null, null, paramInt, paramNetObj, paramString);
  }

  public NetFileRequest(NetFileClient paramNetFileClient, NetFileServer paramNetFileServer, Object paramObject, int paramInt, NetObj paramNetObj, String paramString)
  {
    this.client = paramNetFileClient;
    this.server = paramNetFileServer;
    this.serverData = paramObject;
    this.prior = paramInt;
    this.owner = paramNetObj;
    this.ownerFileName = paramString;
    if (this.server == null)
      this.server = NetEnv.cur().fileServerDef;
  }
}