package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class InOutStreams
{
  public static final int SIGNATURE = 1245391901;
  public static final int VERSION = 101;
  public static final int BLOCK_SIZE = 32768;
  public static final int MIN_BLOCK_SIZE = 2048;
  public static final int MAX_BLOCK_SIZE = 131072;
  private RandomAccessFile randomAccessFile;
  private InputStream inputFile;
  private OutputStream outputFile;
  private long outputOffset;
  private Header header;
  private Tailer tailer;
  private ArrayList indexLst;
  private HashMapExt entryHash;
  private HashMapExt entryNewHash;
  private Block listBlocks = null;
  private int readCacheSize = 32768;

  private void freeResorces()
  {
    this.header = null;
    this.tailer = null;
    this.indexLst.clear();
    this.indexLst = null;
    this.entryHash.clear();
    this.entryHash = null;
    while (this.listBlocks != null) {
      Block localBlock = this.listBlocks;
      this.listBlocks = localBlock.next;
      localBlock.next = null;
    }
    if (this.entryNewHash != null) {
      this.entryNewHash.clear();
      this.entryNewHash = null;
    }
  }

  public int getReadCacheSize() {
    return this.readCacheSize;
  }
  public void setReadCacheSize(int paramInt) { if (this.listBlocks == null) {
      this.readCacheSize = paramInt;
    } else {
      int i = this.readCacheSize / this.header.blockSize;
      if (i < 1) i = 1;
      int j = paramInt / this.header.blockSize;
      if (j < 1) j = 1;
      int k = j - i;
      if (k > 0)
        for (int m = 0; m < k; m++) {
          Block localBlock2 = new Block();
          localBlock2.buf = new byte[this.header.blockSize];
          localBlock2.next = this.listBlocks;
          this.listBlocks = localBlock2;
        }
      else if (k < 0)
        while (k < 0) {
          Block localBlock1 = this.listBlocks;
          this.listBlocks = localBlock1.next;
          localBlock1.next = null;
          k++;
        }
    } }

  private void readBlock(Entry paramEntry, int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    this.inputFile.reset(); this.inputFile.mark(this.inputFile.available());
    IndexEntry localIndexEntry = (IndexEntry)this.indexLst.get(paramEntry.index[paramInt]);
    this.inputFile.skip(localIndexEntry.offsetInFile);
    this.inputFile.read(paramArrayOfByte, 0, localIndexEntry.lenInFile);
    int i = this.header.blockSize;
    if (this.header.blockSize * (paramInt + 1) > paramEntry.len)
      i = paramEntry.len % this.header.blockSize;
    if ((this.header.compressMethod != 0) && (i > localIndexEntry.lenInFile))
      Compress.decode(this.header.compressMethod, paramArrayOfByte, localIndexEntry.lenInFile);
  }

  private byte[] getBlock(Entry paramEntry, int paramInt) throws IOException {
    Block localBlock1 = this.listBlocks;
    Block localBlock2 = null;
    if (localBlock1 == null) {
      int i = this.readCacheSize / this.header.blockSize;
      if (i < 1) i = 1;
      for (int j = 0; j < i; j++) {
        localBlock1 = new Block();
        localBlock1.buf = new byte[this.header.blockSize];
        localBlock1.next = this.listBlocks;
        this.listBlocks = localBlock1;
      }
    } else {
      do {
        if ((localBlock1.entry == paramEntry) && (localBlock1.index == paramInt))
          break;
        localBlock2 = localBlock1;
        localBlock1 = localBlock1.next;
      }
      while (localBlock1.next != null);

      if (localBlock2 != null) {
        localBlock2.next = localBlock1.next;
        localBlock1.next = this.listBlocks;
        this.listBlocks = localBlock1;
      }
    }
    if ((localBlock1.entry != paramEntry) || (localBlock1.index != paramInt)) {
      readBlock(paramEntry, paramInt, localBlock1.buf);
      localBlock1.entry = paramEntry;
      localBlock1.index = paramInt;
    }
    return localBlock1.buf;
  }

  public boolean isExistStream(String paramString)
  {
    return this.entryHash.containsKey(paramString);
  }
  public InputStream openStream(String paramString) {
    Entry localEntry = (Entry)this.entryHash.get(paramString);
    if (localEntry == null)
      return null;
    return new InputStreamThread(localEntry);
  }

  public OutputStream createStream(String paramString) throws IOException {
    return new OutputStreamThread(paramString);
  }

  public void getEntryNames(List paramList)
  {
    if (paramList == null) return;
    if (this.entryHash == null) return;
    Map.Entry localEntry = this.entryHash.nextEntry(null);
    while (localEntry != null) {
      paramList.add(localEntry.getKey());
      localEntry = this.entryHash.nextEntry(localEntry);
    }
  }

  private void outputFlush() throws IOException
  {
    Map.Entry localEntry = this.entryNewHash.nextEntry(null);
    while (localEntry != null) {
      localObject1 = (OutputStreamThread)localEntry.getValue();
      ((OutputStreamThread)localObject1)._close();
      localEntry = this.entryNewHash.nextEntry(localEntry);
    }
    this.entryNewHash.clear();

    Object localObject1 = new DataOutputStream(this.outputFile);
    this.tailer = new Tailer();

    int i = this.indexLst.size();
    Object localObject2;
    for (int j = 0; j < i; j++) {
      localObject2 = (IndexEntry)this.indexLst.get(j);
      ((DataOutputStream)localObject1).writeInt(((IndexEntry)localObject2).lenInFile);
    }
    this.tailer.indexesSizeInFile = (i * IndexEntry.sizeInFile());

    this.tailer.entrysSizeInFile = ((DataOutputStream)localObject1).size();
    localEntry = this.entryHash.nextEntry(null);
    while (localEntry != null) {
      localObject2 = (Entry)localEntry.getValue();
      ((DataOutputStream)localObject1).writeUTF(((Entry)localObject2).name);
      ((DataOutputStream)localObject1).writeInt(((Entry)localObject2).len);
      ((DataOutputStream)localObject1).writeInt(((Entry)localObject2).index.length);
      for (int k = 0; k < ((Entry)localObject2).index.length; k++)
        ((DataOutputStream)localObject1).writeInt(localObject2.index[k]);
      localEntry = this.entryHash.nextEntry(localEntry);
    }
    this.tailer.entrysSizeInFile = (((DataOutputStream)localObject1).size() - this.tailer.entrysSizeInFile);

    ((DataOutputStream)localObject1).writeInt(this.tailer.indexesSizeInFile);
    ((DataOutputStream)localObject1).writeInt(this.tailer.entrysSizeInFile);
    ((DataOutputStream)localObject1).flush();
  }

  private void open() throws IOException {
    DataInputStream localDataInputStream = new DataInputStream(this.inputFile);

    this.header = new Header();
    this.header.signature = localDataInputStream.readInt();
    if (this.header.signature != 1245391901)
      throw new IOException("File corrupted: bad signature");
    this.header.version = localDataInputStream.readInt();
    if (this.header.version != 101)
      throw new IOException("File corrupted: unknown version");
    this.header.compressMethod = localDataInputStream.readInt();
    if ((this.header.compressMethod < 0) || (this.header.compressMethod > 2))
      throw new IOException("File corrupted: unknown compression method");
    this.header.blockSize = localDataInputStream.readInt();
    if ((this.header.blockSize < 2048) || (this.header.blockSize > 131072))
      throw new IOException("File corrupted: bad block size");
    this.inputFile.mark(this.inputFile.available());

    this.inputFile.skip(this.inputFile.available() - Tailer.sizeInFile());
    this.tailer = new Tailer();
    this.tailer.indexesSizeInFile = localDataInputStream.readInt();
    this.tailer.entrysSizeInFile = localDataInputStream.readInt();

    this.inputFile.reset(); this.inputFile.mark(this.inputFile.available());
    this.inputFile.skip(this.inputFile.available() - Tailer.sizeInFile() - this.tailer.entrysSizeInFile - this.tailer.indexesSizeInFile);

    int i = this.tailer.indexesSizeInFile / IndexEntry.sizeInFile();
    this.indexLst = new ArrayList(i);
    int j = 0;
    Object localObject;
    for (int k = 0; k < i; k++) {
      localObject = new IndexEntry();
      ((IndexEntry)localObject).offsetInFile = j;
      ((IndexEntry)localObject).lenInFile = localDataInputStream.readInt();
      j += ((IndexEntry)localObject).lenInFile;
      this.indexLst.add(localObject);
    }

    this.entryHash = new HashMapExt();
    while (this.inputFile.available() > Tailer.sizeInFile()) {
      localObject = new Entry();
      ((Entry)localObject).name = localDataInputStream.readUTF();
      ((Entry)localObject).len = localDataInputStream.readInt();
      int m = localDataInputStream.readInt();
      ((Entry)localObject).index = new int[m];
      for (int n = 0; n < m; n++)
        localObject.index[n] = localDataInputStream.readInt();
      this.entryHash.put(((Entry)localObject).name, localObject);
    }
  }

  private void create(int paramInt1, int paramInt2) throws IOException
  {
    if ((paramInt1 < 0) || (paramInt1 > 2))
      throw new IOException("unknown compression method");
    if ((paramInt2 < 2048) || (paramInt2 > 131072))
      throw new IOException("bad block size");
    DataOutputStream localDataOutputStream = new DataOutputStream(this.outputFile);
    this.header = new Header();
    this.header.signature = 1245391901;
    this.header.version = 101;
    this.header.compressMethod = paramInt1;
    this.header.blockSize = paramInt2;
    localDataOutputStream.writeInt(this.header.signature);
    localDataOutputStream.writeInt(this.header.version);
    localDataOutputStream.writeInt(this.header.compressMethod);
    localDataOutputStream.writeInt(this.header.blockSize);
    localDataOutputStream.flush();
    this.outputOffset = 0L;
    this.indexLst = new ArrayList();
    this.entryHash = new HashMapExt();
    this.entryNewHash = new HashMapExt();
  }

  public static boolean isExistAndValid(InputStream paramInputStream) {
    try {
      DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
      Header localHeader = new Header();
      localHeader.signature = localDataInputStream.readInt();
      if (localHeader.signature != 1245391901)
        return false;
      localHeader.version = localDataInputStream.readInt();
      if (localHeader.version != 101)
        return false;
      localHeader.compressMethod = localDataInputStream.readInt();
      if ((localHeader.compressMethod < 0) || (localHeader.compressMethod > 2))
        return false;
      localHeader.blockSize = localDataInputStream.readInt();

      return (localHeader.blockSize >= 2048) && (localHeader.blockSize <= 131072);
    }
    catch (Exception localException) {
    }
    return false;
  }

  public static boolean isExistAndValid(File paramFile) {
    try {
      RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
      boolean bool = isExistAndValid(new InputStreamOfRandomAccessFile(localRandomAccessFile));
      localRandomAccessFile.close();
      return bool; } catch (Exception localException) {
    }
    return false;
  }

  public void open(long paramLong) throws IOException
  {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    this.inputFile = new InputStreamOfInputStream(paramLong);
    open();
  }
  public void open(InOutStreams paramInOutStreams, String paramString) throws IOException {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    this.inputFile = new InputStreamOfInputStream(paramInOutStreams, paramString);
    open();
  }
  public void open(InputStream paramInputStream) throws IOException {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    if (!paramInputStream.markSupported())
      throw new IOException("inputFile mark/reset not supported");
    this.inputFile = paramInputStream;
    open();
  }
  public void create(OutputStream paramOutputStream, int paramInt1, int paramInt2) throws IOException {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    this.outputFile = paramOutputStream;
    create(paramInt1, paramInt2);
  }
  public void create(OutputStream paramOutputStream) throws IOException {
    create(paramOutputStream, 2, 32768);
  }
  public void open(File paramFile, boolean paramBoolean) throws IOException {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    RandomAccessFile localRandomAccessFile;
    Object localObject;
    if (paramBoolean) {
      localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
      if (localRandomAccessFile.length() == 0L) {
        localObject = new OutputStreamOfRandomAccessFile(localRandomAccessFile);
        create((OutputStream)localObject);
      } else {
        localObject = new InputStreamOfRandomAccessFile(localRandomAccessFile);
        open((InputStream)localObject);
        localRandomAccessFile.seek(localRandomAccessFile.length() - Tailer.sizeInFile() - this.tailer.entrysSizeInFile - this.tailer.indexesSizeInFile);

        this.outputFile = new OutputStreamOfRandomAccessFile(localRandomAccessFile);
        this.outputOffset = (localRandomAccessFile.length() - Header.sizeInFile() - Tailer.sizeInFile() - this.tailer.entrysSizeInFile - this.tailer.indexesSizeInFile);

        this.entryNewHash = new HashMapExt();
      }
      this.randomAccessFile = localRandomAccessFile;
    } else {
      localRandomAccessFile = new RandomAccessFile(paramFile, "r");
      localObject = new InputStreamOfRandomAccessFile(localRandomAccessFile);
      open((InputStream)localObject);
      this.randomAccessFile = localRandomAccessFile;
    }
  }

  public void create(File paramFile, int paramInt1, int paramInt2) throws IOException {
    if ((this.randomAccessFile != null) || (this.inputFile != null) || (this.outputFile != null))
      throw new IOException("file alredy opened");
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
    localRandomAccessFile.seek(0L);
    localRandomAccessFile.setLength(0L);
    OutputStreamOfRandomAccessFile localOutputStreamOfRandomAccessFile = new OutputStreamOfRandomAccessFile(localRandomAccessFile);
    create(localOutputStreamOfRandomAccessFile, paramInt1, paramInt2);
    this.randomAccessFile = localRandomAccessFile;
  }
  public void create(File paramFile) throws IOException {
    create(paramFile, 2, 32768);
  }

  public void close() throws IOException {
    if ((this.randomAccessFile == null) && (this.inputFile == null) && (this.outputFile == null))
      return;
    if (this.randomAccessFile != null) {
      if (this.outputFile != null)
        outputFlush();
      this.randomAccessFile.close();
    } else if (this.outputFile != null) {
      outputFlush();
      this.outputFile.close();
    } else {
      this.inputFile.close();
    }
    this.randomAccessFile = null;
    this.inputFile = null;
    this.outputFile = null;
    freeResorces();
  }

  static class OutputStreamOfRandomAccessFile extends OutputStream
  {
    private RandomAccessFile file;
    private long curPos;

    public void write(int paramInt)
      throws IOException
    {
      this.file.seek(this.curPos);
      this.file.write(paramInt);
      this.curPos += 1L;
    }
    public void write(byte[] paramArrayOfByte) throws IOException {
      this.file.seek(this.curPos);
      this.file.write(paramArrayOfByte);
      this.curPos += paramArrayOfByte.length;
    }
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      this.file.seek(this.curPos);
      this.file.write(paramArrayOfByte, paramInt1, paramInt2);
      this.curPos += paramInt2;
    }
    public void flush() throws IOException {
    }
    public void close() throws IOException { this.file.close(); }

    public OutputStreamOfRandomAccessFile(RandomAccessFile paramRandomAccessFile) throws IOException {
      this.file = paramRandomAccessFile;
      this.curPos = paramRandomAccessFile.getFilePointer();
    }
  }

  static class InputStreamOfRandomAccessFile extends InputStream
  {
    private RandomAccessFile file;
    private long curPos;
    private long markPos;

    public int read()
      throws IOException
    {
      this.file.seek(this.curPos);
      int i = this.file.read();
      if (i >= 0)
        this.curPos += 1L;
      return i;
    }
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      this.file.seek(this.curPos);
      int i = this.file.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i >= 0)
        this.curPos += i;
      return i;
    }
    public long skip(long paramLong) throws IOException {
      if (paramLong <= 0L) return 0L;
      long l = this.file.length() - this.curPos;
      if (paramLong > l) paramLong = l;
      this.curPos += paramLong;
      return paramLong;
    }
    public int available() throws IOException {
      return (int)(this.file.length() - this.curPos);
    }
    public void reset() throws IOException {
      if (this.markPos < 0L)
        throw new IOException("Method mark() not invoked");
      this.curPos = this.markPos;
      this.markPos = -1L;
    }
    public void mark(int paramInt) { this.markPos = this.curPos; } 
    public boolean markSupported() { return true; } 
    public void close() throws IOException { this.file.close(); }

    public InputStreamOfRandomAccessFile(RandomAccessFile paramRandomAccessFile) {
      this.file = paramRandomAccessFile;
      this.curPos = 0L;
      this.markPos = -1L;
    }
  }

  static class InputStreamOfInputStream extends InputStream
  {
    private InputStream inStream;
    private long curPos;
    private long markPos;

    public int read()
      throws IOException
    {
      int i = this.inStream.read();
      if (i >= 0)
        this.curPos += 1L;
      return i;
    }
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      int i = this.inStream.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i >= 0)
        this.curPos += i;
      return i;
    }
    public long skip(long paramLong) throws IOException {
      paramLong = this.inStream.skip(paramLong);
      this.curPos += paramLong;
      return paramLong;
    }
    public int available() throws IOException {
      return this.inStream.available();
    }
    public void reset() throws IOException {
      if (this.markPos < 0L)
        throw new IOException("Method mark() not invoked");
      skip(this.markPos - this.curPos);
      this.markPos = -1L;
    }
    public void mark(int paramInt) { this.markPos = this.curPos; } 
    public boolean markSupported() { return true; } 
    public void close() throws IOException {
      if (this.inStream != null) {
        this.inStream.close();
        this.inStream = null;
      }
    }

    public InputStreamOfInputStream(InOutStreams paramInOutStreams, String paramString) throws IOException
    {
      if (paramInOutStreams != null)
        this.inStream = paramInOutStreams.openStream(paramString);
      else
        this.inStream = new SFSInputStream(paramString);
      this.curPos = 0L;
      this.markPos = -1L;
    }

    public InputStreamOfInputStream(long paramLong) throws IOException {
      this.inStream = new SFSInputStream(paramLong);
      this.curPos = 0L;
      this.markPos = -1L;
    }
  }

  class OutputStreamThread extends OutputStream
  {
    public String name;
    public int len;
    public ArrayList index;
    public byte[] buf;

    private void flushBlock(boolean paramBoolean)
      throws IOException
    {
      int i;
      if (paramBoolean) i = InOutStreams.this.header.blockSize; else
        i = this.len % InOutStreams.this.header.blockSize;
      int j = Compress.code(InOutStreams.this.header.compressMethod, this.buf, i);
      InOutStreams.IndexEntry localIndexEntry = new InOutStreams.IndexEntry();
      localIndexEntry.offsetInFile = (int)InOutStreams.this.outputOffset;
      localIndexEntry.lenInFile = j;
      InOutStreams.this.outputFile.write(this.buf, 0, j);
      InOutStreams.access$214(InOutStreams.this, j);
      InOutStreams.this.indexLst.add(localIndexEntry);
      this.index.add(new Integer(InOutStreams.this.indexLst.size() - 1));
    }

    public void write(int paramInt) throws IOException {
      this.buf[(this.len % InOutStreams.this.header.blockSize)] = (byte)paramInt;
      this.len += 1;
      if (this.len % InOutStreams.this.header.blockSize == 0)
        flushBlock(true); 
    }

    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      if (paramArrayOfByte == null)
        throw new NullPointerException();
      if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0))
      {
        throw new IndexOutOfBoundsException();
      }if (paramInt2 == 0) {
        return;
      }
      int i = this.len % InOutStreams.this.header.blockSize;
      while (paramInt2 > 0) {
        int j = paramInt2;
        int k = InOutStreams.this.header.blockSize - i;
        if (j > k)
          j = k;
        System.arraycopy(paramArrayOfByte, paramInt1, this.buf, i, j);
        paramInt2 -= j;
        paramInt1 += j;
        this.len += j;
        i = this.len % InOutStreams.this.header.blockSize;
        if (i == 0)
          flushBlock(true); 
      }
    }
    public void flush() throws IOException {
    }

    public void _close() throws IOException {
      if (this.len % InOutStreams.this.header.blockSize != 0)
        flushBlock(false);
      InOutStreams.Entry localEntry = new InOutStreams.Entry();
      localEntry.name = this.name;
      localEntry.len = this.len;
      int i = this.index.size();
      localEntry.index = new int[i];
      for (int j = 0; j < i; j++)
        localEntry.index[j] = ((Integer)this.index.get(j)).intValue();
      InOutStreams.this.entryHash.put(this.name, localEntry);
    }
    public void close() throws IOException {
      _close();
      InOutStreams.this.entryNewHash.remove(this.name);
      this.buf = null;
    }

    public OutputStreamThread(String arg2)
      throws IOException
    {
      Object localObject;
      if (InOutStreams.this.entryNewHash.containsKey(localObject))
        throw new IOException("Stream '" + localObject + "' alredy creating");
      this.name = localObject;
      this.len = 0;
      this.index = new ArrayList();
      this.buf = new byte[InOutStreams.this.header.blockSize];
      InOutStreams.this.entryNewHash.put(this.name, this);
    }
  }

  class InputStreamThread extends InputStream
  {
    public InOutStreams.Entry entry;
    public int curPos;
    public int markPos;

    public int read()
      throws IOException
    {
      if (this.curPos >= this.entry.len)
        return -1;
      byte[] arrayOfByte = InOutStreams.this.getBlock(this.entry, this.curPos / InOutStreams.this.header.blockSize);
      int i = arrayOfByte[(this.curPos % InOutStreams.this.header.blockSize)];
      this.curPos += 1;
      return i & 0xFF;
    }
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      if (paramArrayOfByte == null)
        throw new NullPointerException();
      if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0))
      {
        throw new IndexOutOfBoundsException();
      }if (paramInt2 == 0) {
        return 0;
      }
      if (this.curPos >= this.entry.len)
        return -1;
      if (paramInt2 > this.entry.len - this.curPos) {
        paramInt2 = this.entry.len - this.curPos;
      }
      int i = paramInt2;
      int j = this.curPos % InOutStreams.this.header.blockSize;
      while (paramInt2 > 0) {
        byte[] arrayOfByte = InOutStreams.this.getBlock(this.entry, this.curPos / InOutStreams.this.header.blockSize);
        int k = paramInt2;
        if (k > InOutStreams.this.header.blockSize - j)
          k = InOutStreams.this.header.blockSize - j;
        System.arraycopy(arrayOfByte, j, paramArrayOfByte, paramInt1, k);
        paramInt1 += k;
        paramInt2 -= k;
        this.curPos += k;
        j = 0;
      }
      return i;
    }
    public long skip(long paramLong) throws IOException {
      if (paramLong <= 0L) return 0L;
      long l = this.entry.len - this.curPos;
      if (paramLong > l) paramLong = l;
      this.curPos += (int)paramLong;
      return paramLong;
    }
    public int available() throws IOException {
      if (this.entry == null) return 0;
      return this.entry.len - this.curPos;
    }
    public void reset() throws IOException {
      if (this.markPos < 0)
        throw new IOException("Method mark() not invoked");
      this.curPos = this.markPos;
      this.markPos = -1;
    }
    public void mark(int paramInt) { this.markPos = this.curPos; } 
    public boolean markSupported() { return true; } 
    public void close() throws IOException { this.entry = null;
    }

    public InputStreamThread(InOutStreams.Entry arg2)
    {
      Object localObject;
      this.entry = localObject;
      this.curPos = 0;
      this.markPos = -1;
    }
  }

  static class Block
  {
    public Block next;
    public InOutStreams.Entry entry;
    public int index = -1;
    public byte[] buf;
  }

  static class Entry
  {
    public String name;
    public int len;
    public int[] index;
  }

  static class IndexEntry
  {
    public int offsetInFile;
    public int lenInFile;

    public static int sizeInFile()
    {
      return 4;
    }
  }

  static class Tailer
  {
    public int indexesSizeInFile;
    public int entrysSizeInFile;

    public static int sizeInFile()
    {
      return 8;
    }
  }

  static class Header
  {
    public int signature;
    public int version;
    public int compressMethod;
    public int blockSize;

    public static int sizeInFile()
    {
      return 16;
    }
  }
}