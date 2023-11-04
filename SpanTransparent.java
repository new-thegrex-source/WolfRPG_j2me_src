/* Decompiler 3ms, total 184ms, lines 39 */
public class SpanTransparent extends Span {
   public void span(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      switch(TinyGL.renderMode) {
      case 2:
         Span.spanTransBlend50(var1, var2, var3, var5, var6, var7, var9);
         break;
      case 3:
         Span.spanTransAdd(var1, var2, var3, var5, var6, var7, var9);
         break;
      case 4:
      case 5:
      case 6:
      case 8:
      default:
         Span.spanTrans(var1, var2, var3, var5, var6, var7, var9);
         break;
      case 7:
         Span.spanTexSub(var1, var2, var3, var5, var6, var7, var9);
      case 9:
      }

   }

   public void span(int var1, int var2, int var3, int var4, int var5) {
      switch(TinyGL.renderMode) {
      case 2:
         Span.spanSpriteBlend50(var1, var2, var3, var4, var5);
         break;
      case 3:
         Span.spanSpriteAdd(var1, var2, var3, var4, var5);
      case 9:
         break;
      default:
         Span.spanSprite(var1, var2, var3, var4, var5);
      }

   }
}
