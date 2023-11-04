/* Decompiler 8ms, total 169ms, lines 84 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class LerpSprite {
   public static int touchMe = 1;
   public ScriptThread thread;
   public int travelTime;
   public int startTime;
   public int hSprite;
   public int srcX;
   public int srcY;
   public int srcZ;
   public int dstX;
   public int dstY;
   public int dstZ;
   public int height;
   public int dist;
   public int srcScale;
   public int dstScale;
   public int flags;

   public void saveState(DataOutputStream var1) throws IOException {
      if (this.hSprite != 0) {
         var1.writeInt(this.travelTime);
         var1.writeInt(App.gameTime - this.startTime);
         var1.writeShort(this.hSprite);
         var1.writeShort(Render.mapSprites[Render.S_X + this.hSprite - 1]);
         var1.writeShort(Render.mapSprites[Render.S_Y + this.hSprite - 1]);
         var1.writeShort(Render.mapSprites[Render.S_Z + this.hSprite - 1]);
         var1.writeShort(this.srcX);
         var1.writeShort(this.srcY);
         var1.writeShort(this.srcZ);
         var1.writeShort(this.dstX);
         var1.writeShort(this.dstY);
         var1.writeShort(this.dstZ);
         var1.writeShort(this.height);
         var1.writeByte(this.srcScale);
         var1.writeByte(this.dstScale);
         var1.writeShort(this.flags);
         if (this.thread != null) {
            var1.writeByte(this.thread.getIndex());
         } else {
            var1.writeByte(-1);
         }

      }
   }

   public void calcDist() {
      int var1 = (this.dstX - this.srcX) * (this.dstX - this.srcX) + (this.dstY - this.srcY) * (this.dstY - this.srcY);
      this.dist = (int)(Game.FixedSqrt((long)(var1 << 8)) >> 8);
   }

   public void loadState(DataInputStream var1) throws IOException {
      this.travelTime = var1.readInt();
      this.startTime = App.gameTime - var1.readInt();
      this.hSprite = var1.readShort();
      int var2 = this.hSprite - 1;
      Render.mapSprites[Render.S_X + var2] = var1.readShort();
      Render.mapSprites[Render.S_Y + var2] = var1.readShort();
      Render.mapSprites[Render.S_Z + var2] = var1.readShort();
      Render.relinkSprite(var2);
      this.srcX = var1.readShort();
      this.srcY = var1.readShort();
      this.srcZ = var1.readShort();
      this.dstX = var1.readShort();
      this.dstY = var1.readShort();
      this.dstZ = var1.readShort();
      this.height = var1.readShort();
      this.srcScale = var1.readByte();
      this.dstScale = var1.readByte();
      this.flags = var1.readShort();
      byte var3 = var1.readByte();
      if (var3 == -1) {
         this.thread = null;
      } else {
         this.thread = Game.scriptThreads[var3];
      }

      this.calcDist();
   }
}
