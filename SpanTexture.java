/* Decompiler 9ms, total 221ms, lines 29 */
public class SpanTexture extends Span {
   public void span(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      switch(TinyGL.renderMode) {
      case 1:
         if (var6 == 0) {
            Span.spanTexDTBlend25(var1, var2, var3, var5, var7, var9);
         } else if (var7 == 0) {
            Span.spanTexDSBlend25(var1, var2, var3, var5, var6, var9);
         } else {
            Span.spanTexBlend25(var1, var2, var3, var5, var6, var7, var9);
         }
         break;
      case 7:
         Span.spanTexSub(var1, var2, var3, var5, var6, var7, var9);
      case 9:
         break;
      default:
         if (var6 == 0) {
            Span.spanTexDT(var1, var2, var3, var5, var7, var9);
         } else if (var7 == 0) {
            Span.spanTexDS(var1, var2, var3, var5, var6, var9);
         } else {
            Span.spanTex(var1, var2, var3, var5, var6, var7, var9);
         }
      }

   }
}
