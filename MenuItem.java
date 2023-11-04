/* Decompiler 14ms, total 173ms, lines 42 */
final class MenuItem {
   public static int touchMe = 1;
   public int textField;
   public int textField2;
   public int helpField;
   public int flags;
   public int param;
   public int action;
   public static final int HELPTEXT_MAXLINES = 2;
   public static final char[] ellipsesChars = new char[]{' ', '.', ','};

   public void Set(int var1, int var2, int var3) {
      this.Set(var1, var2, var3, 0, 0, MenuSystem.EMPTY_TEXT);
   }

   public void Set(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.textField = var1;
      this.textField2 = var2;
      this.helpField = var6;
      this.flags = var3;
      this.param = var5;
      this.action = var4;
   }

   public void WrapHelpText(Text var1) {
      Text.composeTextField(this.helpField, var1);
      int var2 = var1.length();
      int var3 = var1.wrapText(Canvas.menuHelpMaxChars - 1, 2, '\n');
      if (var3 < var2) {
         var3 = var1.findAnyLastOf(ellipsesChars);
         if (var3 == -1) {
            var1.setLength(0);
            return;
         }

         var1.setLength(var3);
         var1.append('\u0085');
      }

   }
}
