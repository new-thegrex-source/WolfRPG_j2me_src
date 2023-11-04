/* Decompiler 104ms, total 274ms, lines 399 */
import javax.microedition.lcdui.Image;

public class ParticleSystem {
   public static int touchMe = 1;
   private static final int MAX_SYSTEMS = 4;
   private static final int MAX_PARTICLES = 64;
   private static final int FREE_PARTICLE = 63;
   private static final int INVALID_PARTICLE = 255;
   private static final int GIB_FLAG = 8;
   private static final int SIZE_MASK = 7;
   private static final int SYSTEM_SIZE = 64;
   public static final int GIBTYPE_NONE = -1;
   public static final int GIBTYPE_WOOD = 0;
   public static final int GIBTYPE_STONE = 1;
   public static final int GIBTYPE_SKELETON = 2;
   public static final int GIBTYPE_MAX = 3;
   private static int systemIdx = 0;
   private static ParticleSystem[] systems;
   private static final int NUM_GIBS = 4;
   private static final int GIBSIZE = 24;
   public static Image[] imgGibs = new Image[3];
   public static final int COLOR_WOOD = -8826361;
   public static final int COLOR_SPARKS = -3027183;
   public static final int COLOR_CHICKEN = -1;
   public static final int COLOR_TOILET = -1;
   public static final int COLOR_FLAG = -7267291;
   public static final int COLOR_EXPLOSION = -161512;
   public static byte[] monsterColors = null;
   private short[] pos = new short[3];
   private int gravity;
   private byte gibType;
   private int color;
   private int startTime;
   private static final byte[] particleNext = new byte[64];
   private static final byte[] particleStartX = new byte[64];
   private static final byte[] particleStartY = new byte[64];
   private static final short[] particleVelX = new short[64];
   private static final short[] particleVelY = new short[64];
   private static final byte[] particleSize = new byte[32];
   public static final int[] levelColors = new int[]{-1, -12963026, -10329502, -8826361, -3027183, -161512, -14634995};
   private static int[] rotationSequence = new int[]{0, 5, 3, 6};
   private static final int INTERPOLATION_SHIFT = 10;
   static int[] clipRect = new int[4];
   public static final int SYSTEM_DEBUG = 0;
   public static final int SYSTEM_MONSTER_BLOOD = 1;
   public static final int SYSTEM_WOOD_GIBS = 2;
   public static final int SYSTEM_STONE_GIBS = 3;
   public static final int SYSTEM_BONE_GIBS = 4;
   public static final int SYSTEM_SPARKS = 5;
   public static final int SYSTEM_FALLING_DEBRIS = 6;
   public static final int SYSTEM_EXPLOSION = 7;
   public static final int GIB_BONE_MASK = 33040;

   public static final void startup() {
      monsterColors = App.TBL_MONSTER_COLORS;
      systems = new ParticleSystem[4];

      for(int var0 = 0; var0 < systems.length; ++var0) {
         systems[var0] = new ParticleSystem();
      }

      freeAllParticles();
   }

   public static final int unlinkParticle(int var0) {
      int var1 = particleNext[var0] & 255;
      if (var1 != 255) {
         particleNext[var0] = particleNext[var1];
         particleNext[var1] = -1;
      }

      return var1;
   }

   public static final void linkParticle(int var0, int var1) {
      particleNext[var1] = particleNext[var0];
      particleNext[var0] = (byte)var1;
   }

   public static final void freeAllParticles() {
      particleNext[63] = -1;

      for(int var0 = 0; var0 < 63; ++var0) {
         if (var0 < 4) {
            particleNext[var0] = -1;
         } else {
            linkParticle(63, var0);
         }
      }

   }

   public static final void freeSystem(int var0) {
      for(int var1 = unlinkParticle(var0); var1 != 255; var1 = unlinkParticle(var0)) {
         linkParticle(63, var1);
      }

   }

   public final void render(Graphics var1, int var2) {
      int var3 = var2;
      int var4 = particleNext[var2] & 255;
      if (var4 != 255) {
         int var5 = (App.gameTime - this.startTime << 10) / 1000;
         int var6 = var5 * var5 >> 10;
         int var8 = 64 * Render.sinTable[Render.viewAngle & 1023] >> 12;
         int var9 = 64 * Render.sinTable[Render.viewAngle + 256 & 1023] >> 12;
         TGLVert var10 = TinyGL.mv[0];
         var10.x = this.pos[0] + var8;
         var10.y = this.pos[1] + var9;
         var10.z = this.pos[2] + 1024;
         TGLVert var11 = TinyGL.mv[1];
         var11.x = var10.x;
         var11.y = var10.y;
         var11.z = var10.z - 2048;
         var11 = TinyGL.mv[2];
         var11.x = var10.x - (var8 << 1);
         var11.y = var10.y - (var9 << 1);
         var11.z = var10.z;
         TGLVert[] var12 = TinyGL.transform3DVerts(TinyGL.mv, 3);
         if (var12[0].w + var12[0].z < 0 || var12[1].w + var12[1].z < 0 || var12[2].w + var12[2].z < 0) {
            return;
         }

         TinyGL.projectVerts(var12, 3);
         int var13 = var12[0].x;
         int var14 = var12[2].x;
         var13 >>= 3;
         var14 >>= 3;
         int var15 = 8388607 / var12[0].z;
         int var16 = var13 + var14 >> 1;
         if (var16 < 0 || var16 >= TinyGL.screenWidth || var15 > TinyGL.columnScale[var16]) {
            return;
         }

         int var17 = var12[1].x - var12[2].x;
         int var18 = var12[1].y - var12[0].y;
         byte var19 = 0;
         byte var20 = 0;
         int var21 = (var17 << 7) / 128;
         int var22 = (var18 << 7) / 128;
         var17 >>= 3;
         var18 >>= 3;
         int var23 = var19 + (var12[2].x >> 3);
         int var24 = var20 + (var12[0].y >> 3);
         int[] var25 = Canvas.viewRect;
         if (var23 < 0) {
            var17 -= -var23;
            var23 = 0;
         }

         if (var24 < 0) {
            var18 -= -var24;
            var24 = 0;
         }

         if (var17 + var23 > var25[2]) {
            var17 -= var23 + var17 - var25[2];
         }

         if (var18 + var24 > var25[3]) {
            var18 -= var24 + var18 - var25[3];
         }

         if (var17 < 0 || var18 < 0) {
            return;
         }

         var1.clipRect(var23, var24, var17, var18);
         int var32 = var19 + (var12[1].x + var12[2].x >> 4);
         int var33 = var20 + (var12[0].y + var12[1].y >> 4);
         var1.setColor(this.color);

         while(var4 != 255) {
            int var26 = particleSize[var4 >> 1] >> ((var4 & 1) << 2);
            boolean var27 = (var26 & 8) != 0;
            var26 &= 7;
            int var28 = (particleStartX[var4] << 10) + particleVelX[var4] * var5;
            int var29 = (particleStartY[var4] << 10) - (particleVelY[var4] * var5 + var6 * this.gravity);
            if (var28 >= -65536 && var28 <= 65536 && var29 <= 65536 && var29 >= -65536) {
               var28 = var32 + (var28 * var21 >> 20);
               var29 = var33 + (var29 * var22 >> 20);
               if (!var27) {
                  var26 = var26 * Math.min(var21, 1024) >> 10;
                  var1.fillCircle(var28, var29, var26);
               } else {
                  int var30 = Math.min(var21 * 3 >> 10, 3) - 1;
                  if (var30 >= 0) {
                     int var31 = (var4 << 6) + var5 >> 8 & 3;
                     if (particleVelX[var4] < 0) {
                        var31 = 3 - var31;
                     }

                     var1.drawRegion(imgGibs[this.gibType], (2 - var30) * 24, var26 * 24, 24, 24, var28, var29, 3, rotationSequence[var31]);
                  }
               }
            } else {
               linkParticle(63, unlinkParticle(var3));
               var4 = var3;
            }

            var3 = var4;
            var4 = particleNext[var4] & 255;
         }
      }

   }

   public static final void renderSystems(Graphics var0) {
      System.arraycopy(Canvas.viewRect, 0, clipRect, 0, 4);
      int[] var10000;
      if (Canvas.state == 18) {
         var10000 = clipRect;
         var10000[0] += Canvas.cinRect[0];
         var10000 = clipRect;
         var10000[1] += Canvas.cinRect[1];
         clipRect[2] = Canvas.cinRect[2];
         clipRect[3] = Canvas.cinRect[3];
      }

      int var10002 = clipRect[0]++;
      var10002 = clipRect[1]++;
      var10000 = clipRect;
      var10000[2] -= 2;
      var10000 = clipRect;
      var10000[3] -= 2;

      for(int var1 = 0; var1 < 4; ++var1) {
         var0.setScreenSpace(clipRect);
         systems[var1].render(var0, var1);
         var0.resetScreenSpace();
      }

   }

   public static final void spawnMonsterBlood(Entity var0, boolean var1) {
      int var2 = var0.getSprite();
      int var3 = Render.getPalette(Render.mapSpriteInfo[var2] & 255, 0, 0)[monsterColors[var0.def.eSubType] & 255];
      int var4 = 0;
      if (var0.def.eType == 9) {
         var4 -= 26;
      }

      if (var1 && ('脐' & 1 << var0.def.eSubType) != 0) {
         spawnParticles(4, var3, Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], Render.mapSprites[Render.S_Z + var2] + var4);
      } else {
         spawnParticles(1, var3, Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], Render.mapSprites[Render.S_Z + var2] + var4);
      }

   }

   public static final void spawnParticles(int var0, int var1, int var2) {
      spawnParticles(var0, var1, Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], Render.mapSprites[Render.S_Z + var2]);
   }

   public static final void spawnParticles(int var0, int var1, int var2, int var3, int var4) {
      int var5 = -64;
      int var6 = 64;
      int var7 = -64;
      int var8 = 64;
      byte var9 = -128;
      short var10 = 128;
      byte var11 = -32;
      short var12 = 32;
      byte var13 = 4;
      byte var14 = 7;
      short var15 = -196;
      int var16 = 15;
      int var17 = 0;
      byte var18 = -1;
      switch(var0) {
      case 0:
         var9 = 0;
         var10 = 0;
         var11 = 0;
         var12 = 0;
         var15 = 0;
         var18 = 0;
         var17 = 5;
         break;
      case 1:
         var5 >>= 2;
         var6 >>= 2;
         var7 >>= 2;
         var8 >>= 2;
         var11 = 96;
         var12 = 110;
         break;
      case 2:
         var5 >>= 2;
         var6 >>= 2;
         var7 >>= 2;
         var8 >>= 2;
         var9 = -32;
         var10 = 32;
         var11 = 64;
         var12 = 78;
         var17 = 5;
         var18 = 0;
         break;
      case 3:
         var5 >>= 2;
         var6 >>= 2;
         var7 >>= 2;
         var8 >>= 2;
         var9 = -32;
         var10 = 32;
         var11 = 118;
         var12 = 128;
         var17 = 5;
         var18 = 1;
         break;
      case 4:
         var5 >>= 2;
         var6 >>= 2;
         var7 >>= 2;
         var8 >>= 2;
         boolean var23 = false;
         var9 = -32;
         var10 = 32;
         var11 = 64;
         var12 = 78;
         var17 = 5;
         var18 = 2;
         break;
      case 5:
         var5 >>= 3;
         var6 >>= 3;
         var7 >>= 3;
         var8 >>= 3;
         var16 = 8;
         var9 = -128;
         var10 = 128;
         var11 = -128;
         var12 = 128;
         break;
      case 6:
         var5 >>= 1;
         var6 >>= 1;
         var7 = -64;
         var8 = -40;
         var17 = 0;
         var9 = 0;
         var10 = 0;
         var11 = 0;
         var12 = -128;
         break;
      case 7:
         var5 >>= 3;
         var6 >>= 3;
         var7 >>= 3;
         var8 >>= 3;
         var9 = -128;
         var10 = 128;
         var11 = -128;
         var12 = 128;
      }

      int var19 = systemIdx;
      systemIdx = (systemIdx + 1) % 4;
      ParticleSystem var20 = systems[var19];
      freeSystem(var19);
      var20.color = var1;
      var20.gravity = var15;
      var20.gibType = var18;
      var20.startTime = App.gameTime;
      var20.pos[0] = (short)(var2 << 4);
      var20.pos[1] = (short)(var3 << 4);
      var20.pos[2] = (short)(var4 << 4);

      while(var17 > 0 || var16 > 0) {
         int var21 = unlinkParticle(63);
         if (var21 == 255) {
            return;
         }

         int var22 = 0;
         if (var16 > 0) {
            var22 = var13 + ((var14 - var13) * App.nextByte() >> 8) & 7;
            --var16;
         } else if (var17 > 0) {
            var22 = App.nextByte() % 4 | 8;
            --var17;
         }

         particleStartX[var21] = (byte)(var5 + ((var6 - var5) * App.nextByte() >> 8));
         particleStartY[var21] = (byte)(var7 + ((var8 - var7) * App.nextByte() >> 8));
         particleVelX[var21] = (short)(var9 + ((var10 - var9) * App.nextByte() >> 8));
         particleVelY[var21] = (short)(var11 + ((var12 - var11) * App.nextByte() >> 8));
         byte[] var10000 = particleSize;
         var10000[var21 >> 1] = (byte)(var10000[var21 >> 1] & ~(15 << ((var21 & 1) << 2)));
         var10000 = particleSize;
         var10000[var21 >> 1] = (byte)(var10000[var21 >> 1] | var22 << ((var21 & 1) << 2));
         linkParticle(var19, var21);
      }

   }
}
