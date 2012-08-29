// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SocksUdpSocket.java

package com.maddox.rts.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class SocksUdpSocket extends java.net.DatagramSocket
{

    public SocksUdpSocket()
        throws java.net.SocketException
    {
        this(0, null);
    }

    public SocksUdpSocket(int i)
        throws java.net.SocketException
    {
        this(i, null);
    }

    public SocksUdpSocket(int i, java.net.InetAddress inetaddress)
        throws java.net.SocketException
    {
        super(i, inetaddress);
        clientSocket = null;
        socksAddress = null;
        socksPort = proxyPort;
        address = null;
        username = null;
        password = null;
        serverBoundAddress = null;
        serverBoundPort = 0;
        try
        {
            impl_new();
        }
        catch(java.lang.Exception exception)
        {
            close();
            throw new SocketException(exception.getMessage());
        }
    }

    public void close()
    {
        super.close();
        try
        {
            impl_close();
        }
        catch(java.io.IOException ioexception) { }
    }

    protected void finalize()
    {
        close();
    }

    public void connect(java.net.InetAddress inetaddress, int i)
    {
        try
        {
            impl_connect(inetaddress, i);
        }
        catch(java.io.IOException ioexception)
        {
            close();
        }
    }

    public void disconnect()
    {
        impl_disconnect();
    }

    public java.net.InetAddress getInetAddress()
    {
        return impl_getInetAddress();
    }

    public int getPort()
    {
        return impl_getPort();
    }

    public void send(java.net.DatagramPacket datagrampacket)
        throws java.io.IOException
    {
        impl_send(datagrampacket);
    }

    public void receive(java.net.DatagramPacket datagrampacket)
        throws java.io.IOException
    {
        impl_receive(datagrampacket);
    }

    public static java.lang.String getProxyHost()
    {
        return proxyHost;
    }

    public static int getProxyPort()
    {
        return proxyPort;
    }

    public static java.lang.String getProxyUser()
    {
        return proxyUser;
    }

    public static java.lang.String getProxyPassword()
    {
        return proxyPassword;
    }

    public static boolean isProxyEnable()
    {
        return proxyEnable && proxyHost != null;
    }

    public static void setProxyHost(java.lang.String s)
    {
        proxyHost = s;
    }

    public static void setProxyPort(int i)
    {
        proxyPort = i;
    }

    public static void setProxyUser(java.lang.String s)
    {
        proxyUser = s;
    }

    public static void setProxyPassword(java.lang.String s)
    {
        proxyPassword = s;
    }

    public static void setProxyEnable(boolean flag)
    {
        proxyEnable = flag;
    }

    public java.net.InetAddress getSocksAddress()
    {
        return socksAddress;
    }

    public int getSocksPort()
    {
        return socksPort;
    }

    private java.net.InetAddress impl_getInetAddress()
    {
        return address;
    }

    private int impl_getPort()
    {
        return port;
    }

    private void impl_new()
        throws java.lang.Exception
    {
        if(!com.maddox.rts.net.SocksUdpSocket.isProxyEnable())
            return;
        socksAddress = java.net.InetAddress.getByName(proxyHost);
        outputBuffer = new ByteArrayOutputStream(2048);
        if(proxyUser != null)
            username = proxyUser.getBytes();
        if(proxyPassword != null)
            password = proxyPassword.getBytes();
        doSocksConnect();
    }

    private void impl_connect(java.net.InetAddress inetaddress, int i)
        throws java.io.IOException
    {
        if(socksAddress == null)
            super.connect(inetaddress, i);
        address = inetaddress;
        port = i;
    }

    private void impl_disconnect()
    {
        address = null;
        port = 0;
        if(socksAddress == null)
            super.disconnect();
    }

    private void impl_close()
        throws java.io.IOException
    {
        if(clientSocket != null)
        {
            try
            {
                clientSocket.close();
            }
            catch(java.io.IOException ioexception) { }
            clientSocket = null;
        }
    }

    private void impl_send(java.net.DatagramPacket datagrampacket)
        throws java.io.IOException
    {
        if(socksAddress == null)
        {
            super.send(datagrampacket);
            return;
        }
        java.net.InetAddress inetaddress = datagrampacket.getAddress();
        int i = datagrampacket.getPort();
        if(address != null && inetaddress == null)
        {
            inetaddress = address;
            i = port;
        }
        if(inetaddress == null)
        {
            throw new IllegalArgumentException("Both of remote address and packet address are null.");
        } else
        {
            outputBuffer.reset();
            outputBuffer.write(0);
            outputBuffer.write(0);
            outputBuffer.write(0);
            outputBuffer.write(1);
            outputBuffer.write(com.maddox.rts.net.SocksUdpSocket.getAddress(inetaddress));
            outputBuffer.write(com.maddox.rts.net.SocksUdpSocket.getPort(i));
            outputBuffer.write(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
            byte abyte0[] = outputBuffer.toByteArray();
            datagrampacket = new DatagramPacket(abyte0, abyte0.length, serverBoundAddress, serverBoundPort);
            super.send(datagrampacket);
            return;
        }
    }

    protected void impl_receive(java.net.DatagramPacket datagrampacket)
        throws java.io.IOException
    {
        super.receive(datagrampacket);
        if(socksAddress == null)
            return;
        if(serverBoundPort != datagrampacket.getPort())
            return;
        if(!serverBoundAddress.equals(datagrampacket.getAddress()))
        {
            if(!serverBoundAddress.getHostAddress().equals("127.0.0.1"))
                return;
            if(!datagrampacket.getAddress().equals(java.net.InetAddress.getLocalHost()))
                return;
        }
        byte abyte0[] = datagrampacket.getData();
        int i = datagrampacket.getLength();
        int j = datagrampacket.getOffset();
        if(i < 10 || abyte0[j + 0] != 0 || abyte0[j + 1] != 0 || abyte0[j + 2] != 0 || abyte0[j + 3] != 1)
        {
            throw new IOException("Unkown socks datagram packet: " + new String(abyte0));
        } else
        {
            java.net.InetAddress inetaddress = com.maddox.rts.net.SocksUdpSocket.getInetAddress(abyte0[j + 3], abyte0, j + 4);
            int k = com.maddox.rts.net.SocksUdpSocket.getPortValue(abyte0, j + 8);
            datagrampacket.setData(abyte0, j + 10, i - 10);
            datagrampacket.setAddress(inetaddress);
            datagrampacket.setPort(k);
            return;
        }
    }

    private static final byte[] getAddress(java.net.InetAddress inetaddress)
    {
        if(inetaddress == null)
            return new byte[4];
        else
            return inetaddress.getAddress();
    }

    private static final byte[] getPort(int i)
    {
        byte abyte0[] = new byte[2];
        abyte0[0] = (byte)(i >>> 8 & 0xff);
        abyte0[1] = (byte)(i & 0xff);
        return abyte0;
    }

    private static final byte[] getPort(byte abyte0[], int i)
    {
        byte abyte1[] = new byte[2];
        abyte1[0] = abyte0[i];
        abyte1[1] = abyte0[i + 1];
        return abyte1;
    }

    private static final int getPortValue(byte abyte0[], int i)
    {
        int j = abyte0[i] >= 0 ? ((int) (abyte0[i])) : (short)abyte0[i] + 256;
        j <<= 8;
        i++;
        j += abyte0[i] >= 0 ? ((int) (abyte0[i])) : (short)abyte0[i] + 256;
        return j;
    }

    private static final java.net.InetAddress getInetAddress(int i, byte abyte0[], int j)
        throws java.io.IOException
    {
        java.lang.String s;
        switch(i)
        {
        case 1: // '\001'
            java.lang.StringBuffer stringbuffer = new StringBuffer(16);
            for(int k = j; k < j + 4; k++)
            {
                stringbuffer.append(abyte0[k] >= 0 ? ((int) ((short)abyte0[k])) : ((int) ((short)(abyte0[k] + 256))));
                if(k < j + 3)
                    stringbuffer.append('.');
            }

            s = stringbuffer.toString();
            break;

        case 3: // '\003'
            int l = abyte0[j++];
            if(l < 0)
                l += 256;
            s = new String(abyte0, j, l);
            break;

        case 4: // '\004'
            throw new IOException("Error: IPV6 is not supported.");

        case 2: // '\002'
        default:
            throw new IOException("Error: Unknown IP address type.");
        }
        return java.net.InetAddress.getByName(s);
    }

    private synchronized void doSocksConnect()
        throws java.io.IOException
    {
        for(int i = 0; i < 5; i++)
            try
            {
                clientSocket = new Socket(socksAddress, socksPort, getLocalAddress(), 0);
                clientSocket.setSoTimeout(15000);
                break;
            }
            catch(java.io.IOException ioexception)
            {
                if(i < 4)
                {
                    try
                    {
                        java.lang.Thread.sleep(200L);
                    }
                    catch(java.lang.InterruptedException interruptedexception) { }
                } else
                {
                    close();
                    throw ioexception;
                }
            }

        connectSocksV5();
        requestSocks_COMMAND_UDP_ASSOCIATE();
        replySocks_COMMAND_UDP_ASSOCIATE();
        clientSocket.setSoTimeout(0);
    }

    private java.lang.String Username_AND_Password_Authentication_of_V5()
        throws java.io.IOException
    {
        outputBuffer.reset();
        outputBuffer.write(1);
        if(username == null)
        {
            outputBuffer.write(0);
        } else
        {
            outputBuffer.write(username.length);
            outputBuffer.write(username);
        }
        if(password == null)
        {
            outputBuffer.write(0);
        } else
        {
            outputBuffer.write(password.length);
            outputBuffer.write(password);
        }
        java.io.OutputStream outputstream = clientSocket.getOutputStream();
        outputBuffer.writeTo(outputstream);
        outputstream.flush();
        java.lang.String s = null;
        byte abyte0[] = new byte[2];
        java.io.InputStream inputstream = clientSocket.getInputStream();
        do
        {
            int i;
            if((i = inputstream.read(abyte0)) < 0)
                break;
            if(i == 0)
                continue;
            if(i < 2 || abyte0[0] != 1)
                s = "failed to parse the authentication reply from the socks server.";
            else
            if(abyte0[1] != 0)
                s = "failed to through the authentication reply from the socks server.";
            break;
        } while(true);
        return s;
    }

    private void connectSocksV5()
        throws java.io.IOException
    {
        outputBuffer.reset();
        outputBuffer.write(5);
        outputBuffer.write(2);
        outputBuffer.write(0);
        outputBuffer.write(2);
        java.io.OutputStream outputstream = clientSocket.getOutputStream();
        outputBuffer.writeTo(outputstream);
        outputstream.flush();
        byte abyte0[] = new byte[2];
        java.io.InputStream inputstream = clientSocket.getInputStream();
        do
        {
            int i;
            if((i = inputstream.read(abyte0)) < 0)
                break;
            java.lang.String s = null;
            if(i == 0)
                continue;
            if(i < 2 || abyte0[0] != 5)
                s = "failed to parse the reply from the socks server.";
            else
                switch(abyte0[1])
                {
                case 1: // '\001'
                    s = "GSSAPI negotiation hasn't been still complemented.";
                    break;

                case 2: // '\002'
                    s = Username_AND_Password_Authentication_of_V5();
                    break;

                case 3: // '\003'
                    s = "CHAP negotiation hasn't been still complemented.";
                    break;

                case -1: 
                    s = "No acceptable negotiation method.";
                    break;

                default:
                    s = "The negotiation method with a METHOD number of " + abyte0[1] + " hasn't been still complemented.";
                    break;

                case 0: // '\0'
                    break;
                }
            if(s != null)
            {
                close();
                throw new IOException("(" + socksAddress.getHostAddress() + ":" + socksPort + ") " + s);
            }
            break;
        } while(true);
    }

    private void requestSocks_COMMAND_UDP_ASSOCIATE()
        throws java.io.IOException
    {
        outputBuffer.reset();
        outputBuffer.write(5);
        outputBuffer.write(3);
        outputBuffer.write(0);
        outputBuffer.write(1);
        outputBuffer.write(com.maddox.rts.net.SocksUdpSocket.getAddress(getLocalAddress()));
        outputBuffer.write(com.maddox.rts.net.SocksUdpSocket.getPort(getLocalPort()));
        java.io.OutputStream outputstream = clientSocket.getOutputStream();
        outputBuffer.writeTo(outputstream);
        outputstream.flush();
    }

    private void replySocks_COMMAND_UDP_ASSOCIATE()
        throws java.io.IOException
    {
        byte abyte0[] = new byte[64];
        java.io.InputStream inputstream = clientSocket.getInputStream();
        do
        {
            int i;
            if((i = inputstream.read(abyte0)) < 0)
                break;
            java.lang.String s = null;
            if(i == 0)
                continue;
            if(i < 8 || abyte0[0] != 5)
                s = "failed to parse the reply from the socks server.";
            else
                switch(abyte0[1])
                {
                case 0: // '\0'
                    serverBoundAddressType = abyte0[3];
                    int j = 4;
                    serverBoundAddress = com.maddox.rts.net.SocksUdpSocket.getInetAddress(serverBoundAddressType, abyte0, j);
                    if(serverBoundAddress.getHostAddress().equals("0.0.0.0"))
                        serverBoundAddress = getLocalAddress();
                    switch(serverBoundAddressType)
                    {
                    case 3: // '\003'
                        j += (abyte0[j + 1] >= 0 ? abyte0[j + 1] : abyte0[j + 1] + 256) + 1;
                        break;

                    case 1: // '\001'
                        j += 4;
                        break;

                    case 2: // '\002'
                    case 4: // '\004'
                    default:
                        j += 16;
                        break;
                    }
                    serverBoundPort = com.maddox.rts.net.SocksUdpSocket.getPortValue(abyte0, j);
                    break;

                case 1: // '\001'
                    s = "general SOCKS server failure";
                    break;

                case 2: // '\002'
                    s = "connection not allowed by ruleset";
                    break;

                case 3: // '\003'
                    s = "Network unreachable";
                    break;

                case 4: // '\004'
                    s = "Host unreachable";
                    break;

                case 5: // '\005'
                    s = "Connection refused";
                    break;

                case 6: // '\006'
                    s = "TTL expired";
                    break;

                case 7: // '\007'
                    s = "Command not supported";
                    break;

                case 8: // '\b'
                    s = "Address type not supported";
                    break;

                case 9: // '\t'
                    s = "Invalid address";
                    break;

                default:
                    s = "unknown reply code (" + abyte0[1] + ")";
                    break;
                }
            if(s != null)
            {
                close();
                throw new IOException("(" + socksAddress.getHostAddress() + ":" + socksPort + ") " + s);
            }
            break;
        } while(true);
    }

    static final boolean bLog = false;
    static final java.lang.String PROMPT = "socksUdp";
    public static final int SOCKS_PORT = 1080;
    public static final int SOCKS_VERSION_5 = 5;
    public static final int COMMAND_CONNECT = 1;
    public static final int COMMAND_BIND = 2;
    public static final int COMMAND_UDP_ASSOCIATE = 3;
    public static final int NO_AUTHENTICATION_REQUIRED = 0;
    public static final int GSSAPI = 1;
    public static final int USERNAME_AND_PASSWORD = 2;
    public static final int CHAP = 3;
    public static final int NO_ACCEPTABLE_METHODS = 255;
    public static final int IP_V4 = 1;
    public static final int DOMAINNAME = 3;
    public static final int IP_V6 = 4;
    public static final int NULL = 0;
    public static final int REQUEST_GRANTED = 90;
    public static final int REQUEST_REJECTED = 91;
    public static final int REQUEST_REJECTED_NO_IDENTD = 92;
    public static final int REQUEST_REJECTED_DIFF_IDENTS = 93;
    public static final int SUCCEEDED = 0;
    public static final int FAILURE = 1;
    public static final int NOT_ALLOWED = 2;
    public static final int NETWORK_UNREACHABLE = 3;
    public static final int HOST_UNREACHABLE = 4;
    public static final int REFUSED = 5;
    public static final int TTL_EXPIRED = 6;
    public static final int COMMAND_NOT_SUPPORTED = 7;
    public static final int ADDRESS_TYPE_NOT_SUPPORTED = 8;
    public static final int INVALID_ADDRESS = 9;
    private static java.lang.String proxyHost;
    private static int proxyPort = 1080;
    private static java.lang.String proxyUser;
    private static java.lang.String proxyPassword;
    private static boolean proxyEnable = true;
    private java.net.Socket clientSocket;
    private java.net.InetAddress socksAddress;
    private int socksPort;
    private java.net.InetAddress address;
    private int port;
    private java.io.ByteArrayOutputStream outputBuffer;
    private byte username[];
    private byte password[];
    private int serverBoundAddressType;
    private java.net.InetAddress serverBoundAddress;
    private int serverBoundPort;

}
