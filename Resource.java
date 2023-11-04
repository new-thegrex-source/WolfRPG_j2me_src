/* Decompiler 172ms, total 537ms, lines 528 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

final class Resource {
   public static int touchMe = 1;
   public static int cursor = 0;
   public static final int IO_SIZE = 10240;
   public static final byte[] ioBuffer = new byte[10240];
   private static int[] tableOffsets = new int[17];
   private static int prevOffset;
   private static InputStream prevIS;
   static int addRecordStoreError = 0;

   public static void readByteArray(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      int var5;
      for(int var4 = var3; var4 > 0; var4 -= var5) {
         var5 = var0.read(var1, var2 + var3 - var4, var4);
      }

   }

   public static void readUByteArray(InputStream var0, short[] var1, int var2, int var3) throws IOException {
      boolean var4 = false;

      while(var3 > 0) {
         int var5 = 10240 > var3 ? var3 : 10240;
         var3 -= var5;
         read(var0, var5);

         while(true) {
            --var5;
            if (var5 < 0) {
               break;
            }

            var1[var2++] = shiftUByte();
         }
      }

   }

   public static void readCoordArray(InputStream var0, short[] var1, int var2, int var3) throws IOException {
      boolean var4 = false;

      while(var3 > 0) {
         int var5 = 10240 > var3 ? var3 : 10240;
         var3 -= var5;
         read(var0, var5);

         while(true) {
            --var5;
            if (var5 < 0) {
               break;
            }

            var1[var2++] = shiftCoord();
         }
      }

   }

   public static void readShortArray(InputStream var0, short[] var1, int var2, int var3) throws IOException {
      boolean var4 = false;
      short var5 = 5120;

      while(var3 > 0) {
         int var6 = var5 > var3 ? var3 : var5;
         var3 -= var6;
         read(var0, var6 * 2);

         while(true) {
            --var6;
            if (var6 < 0) {
               break;
            }

            var1[var2++] = shiftShort();
         }
      }

   }

   public static void readUShortArray(InputStream var0, int[] var1, int var2, int var3) throws IOException {
      boolean var4 = false;
      short var5 = 5120;

      while(var3 > 0) {
         int var6 = var5 > var3 ? var3 : var5;
         var3 -= var6;
         read(var0, var6 * 2);

         while(true) {
            --var6;
            if (var6 < 0) {
               break;
            }

            var1[var2++] = shiftUShort();
         }
      }

   }

   public static void readIntArray(InputStream var0, int[] var1, int var2, int var3) throws IOException {
      boolean var4 = false;
      short var5 = 2560;

      while(var3 > 0) {
         int var6 = var5 > var3 ? var3 : var5;
         var3 -= var6;
         read(var0, var6 * 4);

         while(true) {
            --var6;
            if (var6 < 0) {
               break;
            }

            var1[var2++] = shiftInt();
         }
      }

   }

   public static final void readMarker(InputStream var0, int var1) throws IOException {
      read(var0, 4);
   }

   public static final void readMarker(InputStream var0) throws IOException {
      readMarker(var0, -889275714);
   }

   public static final void writeMarker(OutputStream var0, int var1) throws IOException {
      ioBuffer[0] = (byte)(var1 & 255);
      ioBuffer[1] = (byte)(var1 >> 8 & 255);
      ioBuffer[2] = (byte)(var1 >> 16 & 255);
      ioBuffer[3] = (byte)(var1 >> 24 & 255);
      var0.write(ioBuffer, 0, 4);
   }

   public static final void writeMarker(OutputStream var0) throws IOException {
      writeMarker(var0, -889275714);
   }

   public static final void read(InputStream var0, int var1) throws IOException {
      cursor = 0;
      readByteArray(var0, ioBuffer, 0, var1);
   }

   public static final void bufSkip(InputStream var0, int var1, boolean var2) throws IOException {
      for(int var3 = 0; var3 < var1; var3 += 10240) {
         int var4 = var3 + 10240 > var1 ? var1 - var3 : 10240;
         read(var0, var4);
         if (var2) {
            Canvas.updateLoadingBar(false);
         }
      }

   }

   public static final byte byteAt(int var0) {
      return ioBuffer[var0];
   }

   public static final byte shiftByte() {
      return ioBuffer[cursor++];
   }

   public static final short UByteAt(int var0) {
      return (short)(ioBuffer[var0] & 255);
   }

   public static final short shiftUByte() {
      return (short)(ioBuffer[cursor++] & 255);
   }

   public static final short shortAt(int var0) {
      return (short)((ioBuffer[var0] & 255) + (ioBuffer[var0 + 1] << 8 & '\uff00'));
   }

   public static final short shiftShort() {
      short var0 = (short)((ioBuffer[cursor] & 255) + (ioBuffer[cursor + 1] << 8 & '\uff00'));
      cursor += 2;
      return var0;
   }

   public static final int shiftUShort() {
      int var0 = (ioBuffer[cursor] & 255) + (ioBuffer[cursor + 1] << 8 & '\uff00');
      cursor += 2;
      return var0;
   }

   public static final int shiftInt() {
      int var0 = ioBuffer[cursor + 3] << 24 & -16777216 | ioBuffer[cursor + 2] << 16 & 16711680 | ioBuffer[cursor + 1] << 8 & '\uff00' | ioBuffer[cursor + 0] & 255;
      cursor += 4;
      return var0;
   }

   public static final short shiftCoord() {
      short var0 = (short)((ioBuffer[cursor] & 255) << 3);
      ++cursor;
      return var0;
   }

   static final int[] readFileIndex(InputStream var0) throws IOException {
      read(var0, 4);
      int var1 = shiftInt();
      int[] var2 = new int[var1];
      read(var0, var1 * 4);

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = shiftInt();
      }

      return var2;
   }

   public static final int[] loadFileIndex(String var0) throws IOException {
      InputStream var1 = App.getResourceAsStream(var0);
      read(var1, 2);
      short var2 = shiftShort();
      byte var3 = 5;
      byte var4 = 3;
      int[] var5 = new int[var2 * var4];
      int var6 = var3 * (10240 / var3);
      int var7 = 0;

      do {
         int var8 = (var2 - var7) * var3;
         var8 = var8 > var6 ? var6 : var8;
         read(var1, var8);

         for(int var9 = 0; var9 < var8; var9 += var3) {
            byte var10 = shiftByte();
            int var11 = shiftInt();
            if (var11 != 0) {
               var5[var7 * var4 - 1] = var11 - var5[var7 * var4 - 2];
            }

            if (var10 != -1) {
               var5[var7 * var4 + 0] = var10;
               var5[var7 * var4 + 1] = var11;
               ++var7;
            }
         }
      } while(var7 != var2);

      read(var1, var3);
      shiftByte();
      var5[var7 * var4 - 1] = shiftInt() - var5[var7 * var4 - 2];
      var1.close();
      return var5;
   }

   public static void initTableLoading() {
      InputStream var0 = App.getResourceAsStream("/tables.bin");

      try {
         read(var0, 68);

         for(int var1 = 0; var1 < 17; ++var1) {
            tableOffsets[var1] = shiftInt();
         }

         var0.close();
         var0 = null;
         System.gc();
      } catch (IOException var2) {
         App.Error(var2, 77);
      }

   }

   public static void beginTableLoading() throws IOException {
      InputStream var0 = App.getResourceAsStream("/tables.bin");
      bufSkip(var0, 68, false);
      prevOffset = 0;
      prevIS = var0;
   }

   public static void seekTable(int var0) throws IOException {
      int var1 = 0;
      if (var0 > 0) {
         var1 = tableOffsets[var0 - 1];
      }

      if (var1 < prevOffset) {
         throw new IOException("seekTable seeking backwards");
      } else {
         bufSkip(prevIS, var1 - prevOffset, false);
         prevOffset = var1;
      }
   }

   public static void finishTableLoading() throws IOException {
      prevIS.close();
      prevIS = null;
      System.gc();
   }

   public static int getNumTableBytes(int var0) {
      int var1 = tableOffsets[var0] - 4;
      return var0 == 0 ? var1 : var1 - tableOffsets[var0 - 1];
   }

   public static int getNumTableShorts(int var0) {
      int var1 = tableOffsets[var0] - 4;
      return var0 == 0 ? var1 / 2 : (var1 - tableOffsets[var0 - 1]) / 2;
   }

   public static int getNumTableInts(int var0) {
      int var1 = tableOffsets[var0] - 4;
      return var0 == 0 ? var1 / 4 : (var1 - tableOffsets[var0 - 1]) / 4;
   }

   public static void loadByteTable(byte[] var0, int var1) {
      try {
         seekTable(var1);
         read(prevIS, 4);
         int var2 = shiftInt();
         prevIS.read(var0);
         prevOffset += 4 + var2;
      } catch (Exception var3) {
         App.Error(var3, 77);
      }

   }

   public static void loadShortTable(short[] var0, int var1) {
      try {
         seekTable(var1);
         read(prevIS, 4);
         int var2 = shiftInt();
         var2 *= 2;
         int var3 = 0;

         for(int var4 = 0; var3 < var2; var3 += 10240) {
            int var5 = var3 + 10240 > var2 ? var2 - var3 : 10240;
            read(prevIS, var5);

            for(int var6 = 0; var6 < var5; var6 += 2) {
               var0[var4++] = shiftShort();
            }
         }

         prevOffset += 4 + var2;
      } catch (Exception var7) {
         App.Error(var7, 77);
      }

   }

   public static void loadIntTable(int[] var0, int var1) {
      try {
         seekTable(var1);
         read(prevIS, 4);
         int var2 = shiftInt();
         var2 *= 4;
         int var3 = 0;

         for(int var4 = 0; var3 < var2; var3 += 10240) {
            int var5 = var3 + 10240 > var2 ? var2 - var3 : 10240;
            read(prevIS, var5);

            for(int var6 = 0; var6 < var5; var6 += 4) {
               var0[var4++] = shiftInt();
            }
         }

         prevOffset += 4 + var2;
      } catch (Exception var7) {
         App.Error(var7, 77);
      }

   }

   public static DataInputStream loadRecord(String var0) throws Exception {
      RecordStore var1 = RecordStore.openRecordStore(var0, false);
      ByteArrayInputStream var2 = null;

      try {
         RecordEnumeration var3 = var1.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);
         var2 = new ByteArrayInputStream(var1.getRecord(var3.nextRecordId()));
      } catch (Exception var11) {
         throw var11;
      } finally {
         var1.closeRecordStore();
         var1 = null;
         System.gc();

         try {
            Thread.sleep(10L);
         } catch (InterruptedException var10) {
         }

      }

      return new DataInputStream(var2);
   }

   public static RecordStore addRecordStore(String var0) {
      addRecordStoreError = 0;

      RecordStore var1;
      for(var1 = null; var1 == null && addRecordStoreError == 0; var1 = openRecord(var0, true)) {
      }

      if (addRecordStoreError != 0) {
         if (addRecordStoreError == 1) {
            App.Error(new RecordStoreFullException("File System Full."), 83);
         } else if (addRecordStoreError == 2) {
            App.Error(new InvalidRecordIDException("Invalid Record ID."), 83);
         } else {
            App.Error(new RecordStoreException("File System Error."), 83);
         }
      }

      return var1;
   }

   public static RecordStore openRecord(String var0, boolean var1) {
      try {
         return RecordStore.openRecordStore(var0, var1);
      } catch (RecordStoreFullException var3) {
         addRecordStoreError = 1;
         return null;
      } catch (InvalidRecordIDException var4) {
         addRecordStoreError = 2;
         return null;
      } catch (Exception var5) {
         addRecordStoreError = 3;
         return null;
      }
   }

   public static void closeRecord(RecordStore var0) {
      try {
         var0.closeRecordStore();
      } catch (Exception var2) {
         App.Error(var2, 82);
      } catch (Error var3) {
         App.Error(82);
      }

   }

   public static void saveRecord(String var0, byte[] var1) {
      deleteRecord(var0);
      RecordStore var2 = addRecordStore(var0);
      if (var2 != null) {
         try {
            var2.addRecord(var1, 0, var1.length);
         } catch (Throwable var12) {
            App.Error(var12, 83);
         } finally {
            closeRecord(var2);
            var2 = null;
            System.gc();

            try {
               Thread.sleep(10L);
            } catch (InterruptedException var11) {
            }

         }
      } else {
         Text.addTextArg(83);
         Canvas.setLoadingBarText((short)1, (short)36);
         Canvas.updateLoadingBar(true);
         App.Error(new RecordStoreFullException("Cannot add RecordStore."), 83);
      }

      System.gc();
   }

   public static boolean recordExists(String var0) {
      String[] var1 = RecordStore.listRecordStores();
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            String var3 = var1[var2];
            if (var3.equals(var0)) {
               return true;
            }
         }
      }

      return false;
   }

   public static void deleteRecord(String var0) {
      while(recordExists(var0)) {
         try {
            RecordStore.deleteRecordStore(var0);
         } catch (RecordStoreNotFoundException var2) {
         } catch (RecordStoreException var3) {
            App.Error(var3, 81);
         }
      }

   }

   public static void touchRecord(String var0) throws RecordStoreException {
      RecordStore var1 = openRecord(var0, true);
      if (var1 != null) {
         closeRecord(var1);
         var1 = null;
         System.gc();

         try {
            Thread.sleep(10L);
         } catch (InterruptedException var3) {
         }
      }

   }
}
