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

public class SocksUdpSocket extends DatagramSocket
{
  static final boolean bLog = false;
  static final String PROMPT = "socksUdp";
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
  private static String proxyHost;
  private static int proxyPort = 1080;
  private static String proxyUser;
  private static String proxyPassword;
  private static boolean proxyEnable = true;

  private Socket clientSocket = null;

  private InetAddress socksAddress = null;
  private int socksPort = proxyPort;

  private InetAddress address = null;
  private int port;
  private ByteArrayOutputStream outputBuffer;
  private byte[] username = null;
  private byte[] password = null;
  private int serverBoundAddressType;
  private InetAddress serverBoundAddress = null;
  private int serverBoundPort = 0;

  public SocksUdpSocket()
    throws SocketException
  {
    this(0, null);
  }

  public SocksUdpSocket(int paramInt)
    throws SocketException
  {
    this(paramInt, null);
  }

  public SocksUdpSocket(int paramInt, InetAddress paramInetAddress)
    throws SocketException
  {
    super(paramInt, paramInetAddress);
    try {
      impl_new();
    } catch (Exception localException) {
      close();
      throw new SocketException(localException.getMessage());
    }
  }

  public void close()
  {
    super.close();
    try {
      impl_close(); } catch (IOException localIOException) {
    }
  }

  protected void finalize() {
    close();
  }

  public void connect(InetAddress paramInetAddress, int paramInt)
  {
    try
    {
      impl_connect(paramInetAddress, paramInt);
    } catch (IOException localIOException) {
      close();
    }
  }

  public void disconnect()
  {
    impl_disconnect();
  }

  public InetAddress getInetAddress()
  {
    return impl_getInetAddress();
  }

  public int getPort()
  {
    return impl_getPort();
  }

  public void send(DatagramPacket paramDatagramPacket)
    throws IOException
  {
    impl_send(paramDatagramPacket);
  }

  public void receive(DatagramPacket paramDatagramPacket)
    throws IOException
  {
    impl_receive(paramDatagramPacket);
  }

  public static String getProxyHost()
  {
    return proxyHost; } 
  public static int getProxyPort() { return proxyPort; } 
  public static String getProxyUser() { return proxyUser; } 
  public static String getProxyPassword() { return proxyPassword; } 
  public static boolean isProxyEnable() { return (proxyEnable) && (proxyHost != null); } 
  public static void setProxyHost(String paramString) {
    proxyHost = paramString; } 
  public static void setProxyPort(int paramInt) { proxyPort = paramInt; } 
  public static void setProxyUser(String paramString) { proxyUser = paramString; } 
  public static void setProxyPassword(String paramString) { proxyPassword = paramString; } 
  public static void setProxyEnable(boolean paramBoolean) { proxyEnable = paramBoolean;
  }

  public InetAddress getSocksAddress()
  {
    return this.socksAddress;
  }

  public int getSocksPort() {
    return this.socksPort;
  }

  private InetAddress impl_getInetAddress()
  {
    return this.address; } 
  private int impl_getPort() { return this.port;
  }

  private void impl_new()
    throws Exception
  {
    if (!isProxyEnable())
      return;
    this.socksAddress = InetAddress.getByName(proxyHost);
    this.outputBuffer = new ByteArrayOutputStream(2048);
    if (proxyUser != null) this.username = proxyUser.getBytes();
    if (proxyPassword != null) this.password = proxyPassword.getBytes();
    doSocksConnect();
  }
  private void impl_connect(InetAddress paramInetAddress, int paramInt) throws IOException {
    if (this.socksAddress == null)
      super.connect(paramInetAddress, paramInt);
    this.address = paramInetAddress;
    this.port = paramInt;
  }
  private void impl_disconnect() {
    this.address = null;
    this.port = 0;
    if (this.socksAddress == null)
      super.disconnect(); 
  }

  private void impl_close() throws IOException {
    if (this.clientSocket != null) {
      try {
        this.clientSocket.close(); } catch (IOException localIOException) {
      }
      this.clientSocket = null;
    }
  }

  private void impl_send(DatagramPacket paramDatagramPacket) throws IOException {
    if (this.socksAddress == null) {
      super.send(paramDatagramPacket);
      return;
    }

    InetAddress localInetAddress = paramDatagramPacket.getAddress();
    int i = paramDatagramPacket.getPort();
    if ((this.address != null) && (localInetAddress == null)) {
      localInetAddress = this.address;
      i = this.port;
    }
    if (localInetAddress == null) {
      throw new IllegalArgumentException("Both of remote address and packet address are null.");
    }

    this.outputBuffer.reset();
    this.outputBuffer.write(0);
    this.outputBuffer.write(0);
    this.outputBuffer.write(0);

    this.outputBuffer.write(1);
    this.outputBuffer.write(getAddress(localInetAddress));
    this.outputBuffer.write(getPort(i));
    this.outputBuffer.write(paramDatagramPacket.getData(), paramDatagramPacket.getOffset(), paramDatagramPacket.getLength());

    byte[] arrayOfByte = this.outputBuffer.toByteArray();
    paramDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.serverBoundAddress, this.serverBoundPort);
    super.send(paramDatagramPacket);
  }

  protected void impl_receive(DatagramPacket paramDatagramPacket) throws IOException {
    super.receive(paramDatagramPacket);

    if (this.socksAddress == null)
      return;
    if (this.serverBoundPort != paramDatagramPacket.getPort())
      return;
    if (!this.serverBoundAddress.equals(paramDatagramPacket.getAddress())) {
      if (!this.serverBoundAddress.getHostAddress().equals("127.0.0.1"))
        return;
      if (!paramDatagramPacket.getAddress().equals(InetAddress.getLocalHost())) {
        return;
      }
    }
    byte[] arrayOfByte = paramDatagramPacket.getData();
    int i = paramDatagramPacket.getLength();
    int j = paramDatagramPacket.getOffset();
    if ((i < 10) || (arrayOfByte[(j + 0)] != 0) || (arrayOfByte[(j + 1)] != 0) || (arrayOfByte[(j + 2)] != 0) || (arrayOfByte[(j + 3)] != 1))
    {
      throw new IOException("Unkown socks datagram packet: " + new String(arrayOfByte));
    }
    InetAddress localInetAddress = getInetAddress(arrayOfByte[(j + 3)], arrayOfByte, j + 4);
    int k = getPortValue(arrayOfByte, j + 8);

    paramDatagramPacket.setData(arrayOfByte, j + 10, i - 10);
    paramDatagramPacket.setAddress(localInetAddress);
    paramDatagramPacket.setPort(k);
  }

  private static final byte[] getAddress(InetAddress paramInetAddress)
  {
    if (paramInetAddress == null) {
      return new byte[4];
    }
    return paramInetAddress.getAddress();
  }
  private static final byte[] getPort(int paramInt) {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = (byte)(paramInt >>> 8 & 0xFF);
    arrayOfByte[1] = (byte)(paramInt & 0xFF);
    return arrayOfByte;
  }
  private static final byte[] getPort(byte[] paramArrayOfByte, int paramInt) {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = paramArrayOfByte[paramInt];
    arrayOfByte[1] = paramArrayOfByte[(paramInt + 1)];
    return arrayOfByte;
  }
  private static final int getPortValue(byte[] paramArrayOfByte, int paramInt) {
    int i = paramArrayOfByte[paramInt] < 0 ? (short)paramArrayOfByte[paramInt] + 256 : paramArrayOfByte[paramInt];
    i <<= 8;
    paramInt++;
    i += (paramArrayOfByte[paramInt] < 0 ? (short)paramArrayOfByte[paramInt] + 256 : paramArrayOfByte[paramInt]);
    return i;
  }

  private static final InetAddress getInetAddress(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
    throws IOException
  {
    int i;
    String str;
    switch (paramInt1) {
    case 1:
      StringBuffer localStringBuffer = new StringBuffer(16);
      for (i = paramInt2; i < paramInt2 + 4; i++) {
        localStringBuffer.append(paramArrayOfByte[i] < 0 ? (short)(paramArrayOfByte[i] + 256) : (short)paramArrayOfByte[i]);
        if (i < paramInt2 + 3)
          localStringBuffer.append('.');
      }
      str = localStringBuffer.toString();
      break;
    case 3:
      i = paramArrayOfByte[(paramInt2++)];
      if (i < 0) i += 256;
      str = new String(paramArrayOfByte, paramInt2, i);
      break;
    case 4:
      throw new IOException("Error: IPV6 is not supported.");
    case 2:
    default:
      throw new IOException("Error: Unknown IP address type.");
    }
    return InetAddress.getByName(str);
  }

  private synchronized void doSocksConnect() throws IOException {
    for (int i = 0; i < 5; i++) {
      try {
        this.clientSocket = new Socket(this.socksAddress, this.socksPort, getLocalAddress(), 0);
        this.clientSocket.setSoTimeout(15000);
      }
      catch (IOException localIOException) {
        if (i < 4) {
          try {
            Thread.sleep(200L); } catch (InterruptedException localInterruptedException) {
          }
        } else {
          close();
          throw localIOException;
        }
      }
    }
    connectSocksV5();
    requestSocks_COMMAND_UDP_ASSOCIATE();
    replySocks_COMMAND_UDP_ASSOCIATE();
    this.clientSocket.setSoTimeout(0);
  }

  private String Username_AND_Password_Authentication_of_V5() throws IOException {
    this.outputBuffer.reset();
    this.outputBuffer.write(1);
    if (this.username == null) {
      this.outputBuffer.write(0);
    } else {
      this.outputBuffer.write(this.username.length);
      this.outputBuffer.write(this.username);
    }
    if (this.password == null) {
      this.outputBuffer.write(0);
    } else {
      this.outputBuffer.write(this.password.length);
      this.outputBuffer.write(this.password);
    }

    OutputStream localOutputStream = this.clientSocket.getOutputStream();
    this.outputBuffer.writeTo(localOutputStream);
    localOutputStream.flush();

    String str = null;
    byte[] arrayOfByte = new byte[2];
    InputStream localInputStream = this.clientSocket.getInputStream();
    int i;
    while ((i = localInputStream.read(arrayOfByte)) >= 0) {
      if (i == 0)
        continue;
      if ((i < 2) || (arrayOfByte[0] != 1)) {
        str = "failed to parse the authentication reply from the socks server."; } else {
        if (arrayOfByte[1] == 0) break;
        str = "failed to through the authentication reply from the socks server.";
      }
    }

    return str;
  }

  private void connectSocksV5() throws IOException {
    this.outputBuffer.reset();
    this.outputBuffer.write(5);

    this.outputBuffer.write(2);
    this.outputBuffer.write(0);
    this.outputBuffer.write(2);

    OutputStream localOutputStream = this.clientSocket.getOutputStream();
    this.outputBuffer.writeTo(localOutputStream);
    localOutputStream.flush();

    byte[] arrayOfByte = new byte[2];
    InputStream localInputStream = this.clientSocket.getInputStream();
    int i;
    while ((i = localInputStream.read(arrayOfByte)) >= 0) {
      String str = null;
      if (i == 0)
        continue;
      if ((i < 2) || (arrayOfByte[0] != 5))
        str = "failed to parse the reply from the socks server.";
      else {
        switch (arrayOfByte[1]) {
        case 0:
          break;
        case 1:
          str = "GSSAPI negotiation hasn't been still complemented.";
          break;
        case 2:
          str = Username_AND_Password_Authentication_of_V5();
          break;
        case 3:
          str = "CHAP negotiation hasn't been still complemented.";
          break;
        case -1:
          str = "No acceptable negotiation method.";
          break;
        default:
          str = "The negotiation method with a METHOD number of " + arrayOfByte[1] + " hasn't been still complemented.";
        }
      }

      if (str == null) break;
      close();
      throw new IOException("(" + this.socksAddress.getHostAddress() + ":" + this.socksPort + ") " + str);
    }
  }

  private void requestSocks_COMMAND_UDP_ASSOCIATE()
    throws IOException
  {
    this.outputBuffer.reset();

    this.outputBuffer.write(5);
    this.outputBuffer.write(3);
    this.outputBuffer.write(0);
    this.outputBuffer.write(1);
    this.outputBuffer.write(getAddress(getLocalAddress()));
    this.outputBuffer.write(getPort(getLocalPort()));
    OutputStream localOutputStream = this.clientSocket.getOutputStream();
    this.outputBuffer.writeTo(localOutputStream);
    localOutputStream.flush();
  }

  private void replySocks_COMMAND_UDP_ASSOCIATE() throws IOException
  {
    byte[] arrayOfByte = new byte[64];
    InputStream localInputStream = this.clientSocket.getInputStream();
    int i;
    while ((i = localInputStream.read(arrayOfByte)) >= 0) {
      String str = null;
      if (i == 0)
        continue;
      if ((i < 8) || (arrayOfByte[0] != 5))
        str = "failed to parse the reply from the socks server.";
      else {
        switch (arrayOfByte[1]) {
        case 0:
          this.serverBoundAddressType = arrayOfByte[3];
          int j = 4;
          this.serverBoundAddress = getInetAddress(this.serverBoundAddressType, arrayOfByte, j);
          if (this.serverBoundAddress.getHostAddress().equals("0.0.0.0"))
            this.serverBoundAddress = getLocalAddress();
          switch (this.serverBoundAddressType) {
          case 3:
            j += (arrayOfByte[(j + 1)] < 0 ? arrayOfByte[(j + 1)] + 256 : arrayOfByte[(j + 1)]) + 1;
            break;
          case 1:
            j += 4;
            break;
          case 2:
          case 4:
          default:
            j += 16;
          }

          this.serverBoundPort = getPortValue(arrayOfByte, j);

          break;
        case 1:
          str = "general SOCKS server failure";
          break;
        case 2:
          str = "connection not allowed by ruleset";
          break;
        case 3:
          str = "Network unreachable";
          break;
        case 4:
          str = "Host unreachable";
          break;
        case 5:
          str = "Connection refused";
          break;
        case 6:
          str = "TTL expired";
          break;
        case 7:
          str = "Command not supported";
          break;
        case 8:
          str = "Address type not supported";
          break;
        case 9:
          str = "Invalid address";
          break;
        default:
          str = "unknown reply code (" + arrayOfByte[1] + ")";
        }
      }

      if (str == null) break;
      close();
      throw new IOException("(" + this.socksAddress.getHostAddress() + ":" + this.socksPort + ") " + str);
    }
  }
}