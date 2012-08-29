// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   InOutStreams.java

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
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            Compress, SFSInputStream

public class InOutStreams
{
    static class OutputStreamOfRandomAccessFile extends java.io.OutputStream
    {

        public void write(int i)
            throws java.io.IOException
        {
            file.seek(curPos);
            file.write(i);
            curPos++;
        }

        public void write(byte abyte0[])
            throws java.io.IOException
        {
            file.seek(curPos);
            file.write(abyte0);
            curPos += abyte0.length;
        }

        public void write(byte abyte0[], int i, int j)
            throws java.io.IOException
        {
            file.seek(curPos);
            file.write(abyte0, i, j);
            curPos += j;
        }

        public void flush()
            throws java.io.IOException
        {
        }

        public void close()
            throws java.io.IOException
        {
            file.close();
        }

        private java.io.RandomAccessFile file;
        private long curPos;

        public OutputStreamOfRandomAccessFile(java.io.RandomAccessFile randomaccessfile)
            throws java.io.IOException
        {
            file = randomaccessfile;
            curPos = randomaccessfile.getFilePointer();
        }
    }

    static class InputStreamOfRandomAccessFile extends java.io.InputStream
    {

        public int read()
            throws java.io.IOException
        {
            file.seek(curPos);
            int i = file.read();
            if(i >= 0)
                curPos++;
            return i;
        }

        public int read(byte abyte0[], int i, int j)
            throws java.io.IOException
        {
            file.seek(curPos);
            int k = file.read(abyte0, i, j);
            if(k >= 0)
                curPos += k;
            return k;
        }

        public long skip(long l)
            throws java.io.IOException
        {
            if(l <= 0L)
                return 0L;
            long l1 = file.length() - curPos;
            if(l > l1)
                l = l1;
            curPos += l;
            return l;
        }

        public int available()
            throws java.io.IOException
        {
            return (int)(file.length() - curPos);
        }

        public void reset()
            throws java.io.IOException
        {
            if(markPos < 0L)
            {
                throw new IOException("Method mark() not invoked");
            } else
            {
                curPos = markPos;
                markPos = -1L;
                return;
            }
        }

        public void mark(int i)
        {
            markPos = curPos;
        }

        public boolean markSupported()
        {
            return true;
        }

        public void close()
            throws java.io.IOException
        {
            file.close();
        }

        private java.io.RandomAccessFile file;
        private long curPos;
        private long markPos;

        public InputStreamOfRandomAccessFile(java.io.RandomAccessFile randomaccessfile)
        {
            file = randomaccessfile;
            curPos = 0L;
            markPos = -1L;
        }
    }

    static class InputStreamOfInputStream extends java.io.InputStream
    {

        public int read()
            throws java.io.IOException
        {
            int i = inStream.read();
            if(i >= 0)
                curPos++;
            return i;
        }

        public int read(byte abyte0[], int i, int j)
            throws java.io.IOException
        {
            int k = inStream.read(abyte0, i, j);
            if(k >= 0)
                curPos += k;
            return k;
        }

        public long skip(long l)
            throws java.io.IOException
        {
            l = inStream.skip(l);
            curPos += l;
            return l;
        }

        public int available()
            throws java.io.IOException
        {
            return inStream.available();
        }

        public void reset()
            throws java.io.IOException
        {
            if(markPos < 0L)
            {
                throw new IOException("Method mark() not invoked");
            } else
            {
                skip(markPos - curPos);
                markPos = -1L;
                return;
            }
        }

        public void mark(int i)
        {
            markPos = curPos;
        }

        public boolean markSupported()
        {
            return true;
        }

        public void close()
            throws java.io.IOException
        {
            if(inStream != null)
            {
                inStream.close();
                inStream = null;
            }
        }

        private java.io.InputStream inStream;
        private long curPos;
        private long markPos;

        public InputStreamOfInputStream(com.maddox.rts.InOutStreams inoutstreams, java.lang.String s)
            throws java.io.IOException
        {
            if(inoutstreams != null)
                inStream = inoutstreams.openStream(s);
            else
                inStream = new SFSInputStream(s);
            curPos = 0L;
            markPos = -1L;
        }

        public InputStreamOfInputStream(long l)
            throws java.io.IOException
        {
            inStream = new SFSInputStream(l);
            curPos = 0L;
            markPos = -1L;
        }
    }

    class OutputStreamThread extends java.io.OutputStream
    {

        private void flushBlock(boolean flag)
            throws java.io.IOException
        {
            int i;
            if(flag)
                i = header.blockSize;
            else
                i = len % header.blockSize;
            int j = com.maddox.rts.Compress.code(header.compressMethod, buf, i);
            com.maddox.rts.IndexEntry indexentry = new IndexEntry();
            indexentry.offsetInFile = (int)outputOffset;
            indexentry.lenInFile = j;
            outputFile.write(buf, 0, j);
            outputOffset+= = (long)j;
            indexLst.add(indexentry);
            index.add(new Integer(indexLst.size() - 1));
        }

        public void write(int i)
            throws java.io.IOException
        {
            buf[len % header.blockSize] = (byte)i;
            len++;
            if(len % header.blockSize == 0)
                flushBlock(true);
        }

        public void write(byte abyte0[], int i, int j)
            throws java.io.IOException
        {
            if(abyte0 == null)
                throw new NullPointerException();
            if(i < 0 || i > abyte0.length || j < 0 || i + j > abyte0.length || i + j < 0)
                throw new IndexOutOfBoundsException();
            if(j == 0)
                return;
            int k = len % header.blockSize;
            do
            {
                if(j <= 0)
                    break;
                int l = j;
                int i1 = header.blockSize - k;
                if(l > i1)
                    l = i1;
                java.lang.System.arraycopy(abyte0, i, buf, k, l);
                j -= l;
                i += l;
                len += l;
                k = len % header.blockSize;
                if(k == 0)
                    flushBlock(true);
            } while(true);
        }

        public void flush()
            throws java.io.IOException
        {
        }

        public void _close()
            throws java.io.IOException
        {
            if(len % header.blockSize != 0)
                flushBlock(false);
            com.maddox.rts.Entry entry = new Entry();
            entry.name = name;
            entry.len = len;
            int i = index.size();
            entry.index = new int[i];
            for(int j = 0; j < i; j++)
                entry.index[j] = ((java.lang.Integer)index.get(j)).intValue();

            entryHash.put(name, entry);
        }

        public void close()
            throws java.io.IOException
        {
            _close();
            entryNewHash.remove(name);
            buf = null;
        }

        public java.lang.String name;
        public int len;
        public java.util.ArrayList index;
        public byte buf[];

        public OutputStreamThread(java.lang.String s)
            throws java.io.IOException
        {
            if(entryNewHash.containsKey(s))
            {
                throw new IOException("Stream '" + s + "' alredy creating");
            } else
            {
                name = s;
                len = 0;
                index = new ArrayList();
                buf = new byte[header.blockSize];
                entryNewHash.put(name, this);
                return;
            }
        }
    }

    class InputStreamThread extends java.io.InputStream
    {

        public int read()
            throws java.io.IOException
        {
            if(curPos >= entry.len)
            {
                return -1;
            } else
            {
                byte abyte0[] = getBlock(entry, curPos / header.blockSize);
                byte byte0 = abyte0[curPos % header.blockSize];
                curPos++;
                return byte0 & 0xff;
            }
        }

        public int read(byte abyte0[], int i, int j)
            throws java.io.IOException
        {
            if(abyte0 == null)
                throw new NullPointerException();
            if(i < 0 || i > abyte0.length || j < 0 || i + j > abyte0.length || i + j < 0)
                throw new IndexOutOfBoundsException();
            if(j == 0)
                return 0;
            if(curPos >= entry.len)
                return -1;
            if(j > entry.len - curPos)
                j = entry.len - curPos;
            int k = j;
            for(int l = curPos % header.blockSize; j > 0; l = 0)
            {
                byte abyte1[] = getBlock(entry, curPos / header.blockSize);
                int i1 = j;
                if(i1 > header.blockSize - l)
                    i1 = header.blockSize - l;
                java.lang.System.arraycopy(abyte1, l, abyte0, i, i1);
                i += i1;
                j -= i1;
                curPos += i1;
            }

            return k;
        }

        public long skip(long l)
            throws java.io.IOException
        {
            if(l <= 0L)
                return 0L;
            long l1 = entry.len - curPos;
            if(l > l1)
                l = l1;
            curPos += (int)l;
            return l;
        }

        public int available()
            throws java.io.IOException
        {
            if(entry == null)
                return 0;
            else
                return entry.len - curPos;
        }

        public void reset()
            throws java.io.IOException
        {
            if(markPos < 0)
            {
                throw new IOException("Method mark() not invoked");
            } else
            {
                curPos = markPos;
                markPos = -1;
                return;
            }
        }

        public void mark(int i)
        {
            markPos = curPos;
        }

        public boolean markSupported()
        {
            return true;
        }

        public void close()
            throws java.io.IOException
        {
            entry = null;
        }

        public com.maddox.rts.Entry entry;
        public int curPos;
        public int markPos;

        public InputStreamThread(com.maddox.rts.Entry entry1)
        {
            entry = entry1;
            curPos = 0;
            markPos = -1;
        }
    }

    static class Block
    {

        public com.maddox.rts.Block next;
        public com.maddox.rts.Entry entry;
        public int index;
        public byte buf[];

        Block()
        {
            index = -1;
        }
    }

    static class Entry
    {

        public java.lang.String name;
        public int len;
        public int index[];

        Entry()
        {
        }
    }

    static class IndexEntry
    {

        public static int sizeInFile()
        {
            return 4;
        }

        public int offsetInFile;
        public int lenInFile;

        IndexEntry()
        {
        }
    }

    static class Tailer
    {

        public static int sizeInFile()
        {
            return 8;
        }

        public int indexesSizeInFile;
        public int entrysSizeInFile;

        Tailer()
        {
        }
    }

    static class Header
    {

        public static int sizeInFile()
        {
            return 16;
        }

        public int signature;
        public int version;
        public int compressMethod;
        public int blockSize;

        Header()
        {
        }
    }


    private void freeResorces()
    {
        header = null;
        tailer = null;
        indexLst.clear();
        indexLst = null;
        entryHash.clear();
        entryHash = null;
        while(listBlocks != null) 
        {
            com.maddox.rts.Block block = listBlocks;
            listBlocks = block.next;
            block.next = null;
        }
        if(entryNewHash != null)
        {
            entryNewHash.clear();
            entryNewHash = null;
        }
    }

    public int getReadCacheSize()
    {
        return readCacheSize;
    }

    public void setReadCacheSize(int i)
    {
        if(listBlocks == null)
        {
            readCacheSize = i;
        } else
        {
            int j = readCacheSize / header.blockSize;
            if(j < 1)
                j = 1;
            int k = i / header.blockSize;
            if(k < 1)
                k = 1;
            int l = k - j;
            if(l > 0)
            {
                for(int i1 = 0; i1 < l; i1++)
                {
                    com.maddox.rts.Block block1 = new Block();
                    block1.buf = new byte[header.blockSize];
                    block1.next = listBlocks;
                    listBlocks = block1;
                }

            } else
            if(l < 0)
                for(; l < 0; l++)
                {
                    com.maddox.rts.Block block = listBlocks;
                    listBlocks = block.next;
                    block.next = null;
                }

        }
    }

    private void readBlock(com.maddox.rts.Entry entry, int i, byte abyte0[])
        throws java.io.IOException
    {
        inputFile.reset();
        inputFile.mark(inputFile.available());
        com.maddox.rts.IndexEntry indexentry = (com.maddox.rts.IndexEntry)indexLst.get(entry.index[i]);
        inputFile.skip(indexentry.offsetInFile);
        inputFile.read(abyte0, 0, indexentry.lenInFile);
        int j = header.blockSize;
        if(header.blockSize * (i + 1) > entry.len)
            j = entry.len % header.blockSize;
        if(header.compressMethod != 0 && j > indexentry.lenInFile)
            com.maddox.rts.Compress.decode(header.compressMethod, abyte0, indexentry.lenInFile);
    }

    private byte[] getBlock(com.maddox.rts.Entry entry, int i)
        throws java.io.IOException
    {
        com.maddox.rts.Block block = listBlocks;
        com.maddox.rts.Block block1 = null;
        if(block == null)
        {
            int j = readCacheSize / header.blockSize;
            if(j < 1)
                j = 1;
            for(int k = 0; k < j; k++)
            {
                block = new Block();
                block.buf = new byte[header.blockSize];
                block.next = listBlocks;
                listBlocks = block;
            }

        } else
        {
            for(; block.next != null && (block.entry != entry || block.index != i); block = block.next)
                block1 = block;

            if(block1 != null)
            {
                block1.next = block.next;
                block.next = listBlocks;
                listBlocks = block;
            }
        }
        if(block.entry != entry || block.index != i)
        {
            readBlock(entry, i, block.buf);
            block.entry = entry;
            block.index = i;
        }
        return block.buf;
    }

    public boolean isExistStream(java.lang.String s)
    {
        return entryHash.containsKey(s);
    }

    public java.io.InputStream openStream(java.lang.String s)
    {
        com.maddox.rts.Entry entry = (com.maddox.rts.Entry)entryHash.get(s);
        if(entry == null)
            return null;
        else
            return new InputStreamThread(entry);
    }

    public java.io.OutputStream createStream(java.lang.String s)
        throws java.io.IOException
    {
        return new OutputStreamThread(s);
    }

    public void getEntryNames(java.util.List list)
    {
        if(list == null)
            return;
        if(entryHash == null)
            return;
        for(java.util.Map.Entry entry = entryHash.nextEntry(null); entry != null; entry = entryHash.nextEntry(entry))
            list.add(entry.getKey());

    }

    private void outputFlush()
        throws java.io.IOException
    {
        for(java.util.Map.Entry entry = entryNewHash.nextEntry(null); entry != null; entry = entryNewHash.nextEntry(entry))
        {
            com.maddox.rts.OutputStreamThread outputstreamthread = (com.maddox.rts.OutputStreamThread)entry.getValue();
            outputstreamthread._close();
        }

        entryNewHash.clear();
        java.io.DataOutputStream dataoutputstream = new DataOutputStream(outputFile);
        tailer = new Tailer();
        int i = indexLst.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.IndexEntry indexentry = (com.maddox.rts.IndexEntry)indexLst.get(j);
            dataoutputstream.writeInt(indexentry.lenInFile);
        }

        tailer.indexesSizeInFile = i * com.maddox.rts.IndexEntry.sizeInFile();
        tailer.entrysSizeInFile = dataoutputstream.size();
        for(java.util.Map.Entry entry1 = entryHash.nextEntry(null); entry1 != null; entry1 = entryHash.nextEntry(entry1))
        {
            com.maddox.rts.Entry entry2 = (com.maddox.rts.Entry)entry1.getValue();
            dataoutputstream.writeUTF(entry2.name);
            dataoutputstream.writeInt(entry2.len);
            dataoutputstream.writeInt(entry2.index.length);
            for(int k = 0; k < entry2.index.length; k++)
                dataoutputstream.writeInt(entry2.index[k]);

        }

        tailer.entrysSizeInFile = dataoutputstream.size() - tailer.entrysSizeInFile;
        dataoutputstream.writeInt(tailer.indexesSizeInFile);
        dataoutputstream.writeInt(tailer.entrysSizeInFile);
        dataoutputstream.flush();
    }

    private void open()
        throws java.io.IOException
    {
        java.io.DataInputStream datainputstream = new DataInputStream(inputFile);
        header = new Header();
        header.signature = datainputstream.readInt();
        if(header.signature != 0x4a3b2c1d)
            throw new IOException("File corrupted: bad signature");
        header.version = datainputstream.readInt();
        if(header.version != 101)
            throw new IOException("File corrupted: unknown version");
        header.compressMethod = datainputstream.readInt();
        if(header.compressMethod < 0 || header.compressMethod > 2)
            throw new IOException("File corrupted: unknown compression method");
        header.blockSize = datainputstream.readInt();
        if(header.blockSize < 2048 || header.blockSize > 0x20000)
            throw new IOException("File corrupted: bad block size");
        inputFile.mark(inputFile.available());
        inputFile.skip(inputFile.available() - com.maddox.rts.Tailer.sizeInFile());
        tailer = new Tailer();
        tailer.indexesSizeInFile = datainputstream.readInt();
        tailer.entrysSizeInFile = datainputstream.readInt();
        inputFile.reset();
        inputFile.mark(inputFile.available());
        inputFile.skip(inputFile.available() - com.maddox.rts.Tailer.sizeInFile() - tailer.entrysSizeInFile - tailer.indexesSizeInFile);
        int i = tailer.indexesSizeInFile / com.maddox.rts.IndexEntry.sizeInFile();
        indexLst = new ArrayList(i);
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            com.maddox.rts.IndexEntry indexentry = new IndexEntry();
            indexentry.offsetInFile = j;
            indexentry.lenInFile = datainputstream.readInt();
            j += indexentry.lenInFile;
            indexLst.add(indexentry);
        }

        entryHash = new HashMapExt();
        com.maddox.rts.Entry entry;
        for(; inputFile.available() > com.maddox.rts.Tailer.sizeInFile(); entryHash.put(entry.name, entry))
        {
            entry = new Entry();
            entry.name = datainputstream.readUTF();
            entry.len = datainputstream.readInt();
            int l = datainputstream.readInt();
            entry.index = new int[l];
            for(int i1 = 0; i1 < l; i1++)
                entry.index[i1] = datainputstream.readInt();

        }

    }

    private void create(int i, int j)
        throws java.io.IOException
    {
        if(i < 0 || i > 2)
            throw new IOException("unknown compression method");
        if(j < 2048 || j > 0x20000)
        {
            throw new IOException("bad block size");
        } else
        {
            java.io.DataOutputStream dataoutputstream = new DataOutputStream(outputFile);
            header = new Header();
            header.signature = 0x4a3b2c1d;
            header.version = 101;
            header.compressMethod = i;
            header.blockSize = j;
            dataoutputstream.writeInt(header.signature);
            dataoutputstream.writeInt(header.version);
            dataoutputstream.writeInt(header.compressMethod);
            dataoutputstream.writeInt(header.blockSize);
            dataoutputstream.flush();
            outputOffset = 0L;
            indexLst = new ArrayList();
            entryHash = new HashMapExt();
            entryNewHash = new HashMapExt();
            return;
        }
    }

    public static boolean isExistAndValid(java.io.InputStream inputstream)
    {
        java.io.DataInputStream datainputstream;
        com.maddox.rts.Header header1;
        datainputstream = new DataInputStream(inputstream);
        header1 = new Header();
        header1.signature = datainputstream.readInt();
        if(header1.signature != 0x4a3b2c1d)
            return false;
        header1.version = datainputstream.readInt();
        if(header1.version != 101)
            return false;
        header1.compressMethod = datainputstream.readInt();
        if(header1.compressMethod < 0 || header1.compressMethod > 2)
            return false;
        header1.blockSize = datainputstream.readInt();
        if(header1.blockSize < 2048 || header1.blockSize > 0x20000)
            return false;
        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public static boolean isExistAndValid(java.io.File file)
    {
        boolean flag;
        java.io.RandomAccessFile randomaccessfile = new RandomAccessFile(file, "r");
        flag = com.maddox.rts.InOutStreams.isExistAndValid(((java.io.InputStream) (new InputStreamOfRandomAccessFile(randomaccessfile))));
        randomaccessfile.close();
        return flag;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public void open(long l)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
        {
            throw new IOException("file alredy opened");
        } else
        {
            inputFile = new InputStreamOfInputStream(l);
            open();
            return;
        }
    }

    public void open(com.maddox.rts.InOutStreams inoutstreams, java.lang.String s)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
        {
            throw new IOException("file alredy opened");
        } else
        {
            inputFile = new InputStreamOfInputStream(inoutstreams, s);
            open();
            return;
        }
    }

    public void open(java.io.InputStream inputstream)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
            throw new IOException("file alredy opened");
        if(!inputstream.markSupported())
        {
            throw new IOException("inputFile mark/reset not supported");
        } else
        {
            inputFile = inputstream;
            open();
            return;
        }
    }

    public void create(java.io.OutputStream outputstream, int i, int j)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
        {
            throw new IOException("file alredy opened");
        } else
        {
            outputFile = outputstream;
            create(i, j);
            return;
        }
    }

    public void create(java.io.OutputStream outputstream)
        throws java.io.IOException
    {
        create(outputstream, 2, 32768);
    }

    public void open(java.io.File file, boolean flag)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
            throw new IOException("file alredy opened");
        if(flag)
        {
            java.io.RandomAccessFile randomaccessfile = new RandomAccessFile(file, "rw");
            if(randomaccessfile.length() == 0L)
            {
                com.maddox.rts.OutputStreamOfRandomAccessFile outputstreamofrandomaccessfile = new OutputStreamOfRandomAccessFile(randomaccessfile);
                create(outputstreamofrandomaccessfile);
            } else
            {
                com.maddox.rts.InputStreamOfRandomAccessFile inputstreamofrandomaccessfile = new InputStreamOfRandomAccessFile(randomaccessfile);
                open(((java.io.InputStream) (inputstreamofrandomaccessfile)));
                randomaccessfile.seek(randomaccessfile.length() - (long)com.maddox.rts.Tailer.sizeInFile() - (long)tailer.entrysSizeInFile - (long)tailer.indexesSizeInFile);
                outputFile = new OutputStreamOfRandomAccessFile(randomaccessfile);
                outputOffset = randomaccessfile.length() - (long)com.maddox.rts.Header.sizeInFile() - (long)com.maddox.rts.Tailer.sizeInFile() - (long)tailer.entrysSizeInFile - (long)tailer.indexesSizeInFile;
                entryNewHash = new HashMapExt();
            }
            randomAccessFile = randomaccessfile;
        } else
        {
            java.io.RandomAccessFile randomaccessfile1 = new RandomAccessFile(file, "r");
            com.maddox.rts.InputStreamOfRandomAccessFile inputstreamofrandomaccessfile1 = new InputStreamOfRandomAccessFile(randomaccessfile1);
            open(((java.io.InputStream) (inputstreamofrandomaccessfile1)));
            randomAccessFile = randomaccessfile1;
        }
    }

    public void create(java.io.File file, int i, int j)
        throws java.io.IOException
    {
        if(randomAccessFile != null || inputFile != null || outputFile != null)
        {
            throw new IOException("file alredy opened");
        } else
        {
            java.io.RandomAccessFile randomaccessfile = new RandomAccessFile(file, "rw");
            randomaccessfile.seek(0L);
            randomaccessfile.setLength(0L);
            com.maddox.rts.OutputStreamOfRandomAccessFile outputstreamofrandomaccessfile = new OutputStreamOfRandomAccessFile(randomaccessfile);
            create(((java.io.OutputStream) (outputstreamofrandomaccessfile)), i, j);
            randomAccessFile = randomaccessfile;
            return;
        }
    }

    public void create(java.io.File file)
        throws java.io.IOException
    {
        create(file, 2, 32768);
    }

    public void close()
        throws java.io.IOException
    {
        if(randomAccessFile == null && inputFile == null && outputFile == null)
            return;
        if(randomAccessFile != null)
        {
            if(outputFile != null)
                outputFlush();
            randomAccessFile.close();
        } else
        if(outputFile != null)
        {
            outputFlush();
            outputFile.close();
        } else
        {
            inputFile.close();
        }
        randomAccessFile = null;
        inputFile = null;
        outputFile = null;
        freeResorces();
    }

    public InOutStreams()
    {
        listBlocks = null;
        readCacheSize = 32768;
    }

    public static final int SIGNATURE = 0x4a3b2c1d;
    public static final int VERSION = 101;
    public static final int BLOCK_SIZE = 32768;
    public static final int MIN_BLOCK_SIZE = 2048;
    public static final int MAX_BLOCK_SIZE = 0x20000;
    private java.io.RandomAccessFile randomAccessFile;
    private java.io.InputStream inputFile;
    private java.io.OutputStream outputFile;
    private long outputOffset;
    private com.maddox.rts.Header header;
    private com.maddox.rts.Tailer tailer;
    private java.util.ArrayList indexLst;
    private com.maddox.util.HashMapExt entryHash;
    private com.maddox.util.HashMapExt entryNewHash;
    private com.maddox.rts.Block listBlocks;
    private int readCacheSize;








}
