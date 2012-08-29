// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileRequest.java

package com.maddox.rts.net;

import com.maddox.rts.Destroy;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetException;
import com.maddox.rts.NetObj;

// Referenced classes of package com.maddox.rts.net:
//            NetFileServer, NetFileClient

public class NetFileRequest
{

    public com.maddox.rts.net.NetFileClient client()
    {
        return client;
    }

    public com.maddox.rts.net.NetFileServer server()
    {
        return server;
    }

    public java.lang.Object serverData()
    {
        return serverData;
    }

    public int prior()
    {
        return prior;
    }

    public com.maddox.rts.NetObj owner()
    {
        return owner;
    }

    public java.lang.String ownerFileName()
    {
        return ownerFileName;
    }

    public int state()
    {
        return state;
    }

    public java.lang.String localFileName()
    {
        return localFileName;
    }

    public float complete()
    {
        return complete;
    }

    public boolean isEnded()
    {
        return state != 1;
    }

    public java.lang.String localFullFileName(java.lang.String s, java.lang.String s1)
    {
        if(localFileName == null)
            return null;
        if(ownerFileName.equals(localFileName))
            if(s != null)
                return s + "/" + localFileName;
            else
                return localFileName;
        if(s1 != null)
            return s1 + "/" + localFileName;
        else
            return localFileName;
    }

    public void doRequest()
    {
        if((this instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)this).isDestroyed())
            throw new NetException("NetFileRequest is destroyed");
        if(state() == 1)
            throw new NetException("Request in progress");
        if(client() == null)
            throw new NetException("Client == null");
        if((client() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)client()).isDestroyed())
            throw new NetException("Client is destroyed");
        if(prior() < 0 || prior() > 255)
            throw new NetException("BAD parameter Proir");
        if(owner() == null)
            throw new NetException("Owner == null");
        if((owner() instanceof com.maddox.rts.Destroy) && owner().isDestroyed())
            throw new NetException("Owner is destroyed");
        if(ownerFileName() == null || ownerFileName().length() == 0)
            throw new NetException("OwnerFileName is empty");
        if(com.maddox.rts.HomePath.isFileSystemName(ownerFileName()))
            throw new NetException("Bad OwnerFileName");
        state = 2;
        localFileName = null;
        complete = 0.0F;
        if((server() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)server()).isDestroyed())
            throw new NetException("Server is destroyed");
        if(!(server() instanceof com.maddox.rts.NetObj))
        {
            throw new NetException("Server NOT NetObj");
        } else
        {
            server().doRequest(this);
            return;
        }
    }

    public void doCancel()
    {
        if((this instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)this).isDestroyed())
            throw new NetException("NetFileRequest is destroyed");
        if(state() != 1)
            throw new NetException("Request NOT in progress");
        if(state == 2)
            return;
        if((server() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)server()).isDestroyed())
        {
            throw new NetException("Server is destroyed");
        } else
        {
            server().doCancel(this);
            return;
        }
    }

    public void doAnswer()
    {
        if((client() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)client()).isDestroyed())
        {
            return;
        } else
        {
            client().netFileAnswer(this);
            return;
        }
    }

    public void setState(int i)
    {
        state = i;
    }

    public void setLocalFileName(java.lang.String s)
    {
        localFileName = s;
    }

    public void setComplete(float f)
    {
        complete = f;
    }

    public java.lang.String toString()
    {
        java.lang.String s = null;
        switch(state)
        {
        case 2: // '\002'
            s = "init";
            break;

        case 1: // '\001'
            s = "progress " + (int)(100F * complete);
            break;

        case 0: // '\0'
            s = "success";
            break;

        case -1: 
            s = "owner disconnected";
            break;

        case -2: 
            s = "not found";
            break;

        case -3: 
            s = "io error";
            break;

        case -4: 
            s = "canceled";
            break;

        default:
            s = "UNKNOWN";
            break;
        }
        if(localFileName() != null)
            return owner() + ":" + ownerFileName() + " (" + localFileName() + ") state = " + s;
        else
            return owner() + ":" + ownerFileName() + " state = " + s;
    }

    public int hashCode()
    {
        return owner.hashCode() + ownerFileName.hashCode();
    }

    public boolean equals(java.lang.Object obj)
    {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof com.maddox.rts.net.NetFileRequest))
            return false;
        com.maddox.rts.net.NetFileRequest netfilerequest = (com.maddox.rts.net.NetFileRequest)obj;
        if(serverData != null)
        {
            if(!serverData.equals(netfilerequest.serverData))
                return false;
        } else
        if(netfilerequest.serverData != null)
            return false;
        return netfilerequest.owner == owner && netfilerequest.ownerFileName.equals(ownerFileName);
    }

    public NetFileRequest(com.maddox.rts.net.NetFileClient netfileclient, com.maddox.rts.net.NetFileServer netfileserver, int i, com.maddox.rts.NetObj netobj, java.lang.String s)
    {
        this(netfileclient, netfileserver, null, i, netobj, s);
    }

    public NetFileRequest(com.maddox.rts.net.NetFileClient netfileclient, int i, com.maddox.rts.NetObj netobj, java.lang.String s)
    {
        this(netfileclient, null, null, i, netobj, s);
    }

    public NetFileRequest(com.maddox.rts.net.NetFileClient netfileclient, com.maddox.rts.net.NetFileServer netfileserver, java.lang.Object obj, int i, com.maddox.rts.NetObj netobj, java.lang.String s)
    {
        state = 2;
        complete = 0.0F;
        client = netfileclient;
        server = netfileserver;
        serverData = obj;
        prior = i;
        owner = netobj;
        ownerFileName = s;
        if(server == null)
            server = com.maddox.rts.NetEnv.cur().fileServerDef;
    }

    public static final int STATE_INIT = 2;
    public static final int STATE_PROGRESS = 1;
    public static final int STATE_SUCCESS = 0;
    public static final int STATE_ERR_OWNER_DISCONNECT = -1;
    public static final int STATE_ERR_NOT_FOUND = -2;
    public static final int STATE_ERR_IO = -3;
    public static final int STATE_USER_CANCEL = -4;
    public static final int PRIOR_MIN = 0;
    public static final int PRIOR_MAX = 255;
    private com.maddox.rts.net.NetFileClient client;
    private com.maddox.rts.net.NetFileServer server;
    protected java.lang.Object serverData;
    private int prior;
    private com.maddox.rts.NetObj owner;
    protected java.lang.String ownerFileName;
    private int state;
    private java.lang.String localFileName;
    private float complete;
    public java.lang.Object _serverIn;
    public java.lang.Object _serverOut;
}
