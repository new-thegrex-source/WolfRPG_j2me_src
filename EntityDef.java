/* Decompiler 13ms, total 306ms, lines 75 */
import java.io.IOException;
import java.io.InputStream;

final class EntityDef {
   public short tileIndex;
   public short name;
   public short longName;
   public short description;
   public byte eType;
   public byte eSubType;
   public byte parm;
   public static int touchMe = 1;
   private static EntityDef[] list;
   public static int numDefs;

   public static final boolean startup() {
      numDefs = 0;
      App.checkPausedState();

      try {
         InputStream var0 = App.getResourceAsStream("/entities.bin");
         Resource.read(var0, 2);
         numDefs = Resource.shiftShort();
         list = new EntityDef[numDefs];

         int var1;
         for(var1 = 0; var1 < numDefs; ++var1) {
            list[var1] = new EntityDef();
         }

         for(var1 = 0; var1 < numDefs; ++var1) {
            EntityDef var2 = list[var1];
            Resource.read(var0, 8);
            var2.tileIndex = Resource.shiftShort();
            var2.eType = Resource.shiftByte();
            var2.eSubType = Resource.shiftByte();
            var2.parm = Resource.shiftByte();
            var2.name = Resource.shiftUByte();
            var2.longName = Resource.shiftUByte();
            var2.description = Resource.shiftUByte();
         }

         return true;
      } catch (IOException var3) {
         App.Error(var3, 18);
         return false;
      }
   }

   public static final EntityDef find(int var0, int var1) {
      return find(var0, var1, -1);
   }

   public static final EntityDef find(int var0, int var1, int var2) {
      for(int var3 = 0; var3 < numDefs; ++var3) {
         if (list[var3].eType == var0 && list[var3].eSubType == var1 && (var2 == -1 || list[var3].parm == var2)) {
            return list[var3];
         }
      }

      return null;
   }

   public static final EntityDef lookup(int var0) {
      for(int var1 = 0; var1 < numDefs; ++var1) {
         short var2 = list[var1].tileIndex;
         if (var2 == var0) {
            return list[var1];
         }
      }

      return null;
   }
}
