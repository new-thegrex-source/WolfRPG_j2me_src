/* Decompiler 5ms, total 272ms, lines 48 */
public class TGLEdge {
   int stopY;
   int fracX;
   int fracZ;
   int fracS;
   int fracT;
   int stepX;
   int stepZ;
   int stepS;
   int stepT;
   static final int SCREEN_SHIFT = 3;
   static final int SCREEN_ONE = 8;
   static final int SCREEN_PRESTEP = 7;
   static final int INTERPOLATE_SHIFT = 16;
   static final int INTERPOLATE_TO_PIXELS_SHIFT = 19;
   static final int INTERPOLATE_PRESTEP = 524287;

   public final void setFromVerts(TGLVert var1, TGLVert var2) {
      this.stopY = var2.y + 7 >> 3;
      int var3 = var2.y - var1.y;
      if (var3 != 0) {
         this.stepX = (var2.x - var1.x << 16) / var3;
         this.stepZ = (var2.z - var1.z << 16) / var3;
         this.stepS = (var2.s - var1.s) / var3;
         this.stepT = (var2.t - var1.t) / var3;
         int var4 = 8 - var1.y & 7;
         this.fracX = (var1.x << 16) + var4 * this.stepX;
         this.fracZ = (var1.z << 16) + var4 * this.stepZ;
         this.fracS = var1.s + var4 * this.stepS;
         this.fracT = var1.t + var4 * this.stepT;
         this.stepX <<= 3;
         this.stepZ <<= 3;
         this.stepS <<= 3;
         this.stepT <<= 3;
      } else {
         this.fracX = var1.x << 16;
         this.fracZ = var1.z << 16;
         this.fracS = var1.s;
         this.fracT = var1.t;
         this.stepX = 0;
         this.stepZ = 0;
         this.stepS = 0;
         this.stepT = 0;
      }

   }
}
