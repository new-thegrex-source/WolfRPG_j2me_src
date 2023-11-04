/* Decompiler 809ms, total 1110ms, lines 2668 */
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

final class MenuSystem implements Runnable {
   public static int touchMe = 0;
   private static int[] menuData;
   private static int[] menuItems;
   public static short LEVEL_STATS_nextMap;
   public static final int NO = 0;
   public static final int YES = 1;
   public static byte menuParam;
   private static EntityDef detailsDef;
   public static final int INDEX_TREASURE = 0;
   public static final int INDEX_WEAPONS = 1;
   public static final int INDEX_SYRINGES = 2;
   public static final int INDEX_OTHER = 3;
   public static final int INDEX_ITEMS = 4;
   public static final int MAX_SAVED_INDEXES = 5;
   public static int[] indexes = new int[10];
   private static final int BLOCK_LINE_MASK = 63;
   private static final int BLOCK_NUMLINES_SHIFT = 26;
   private static final int BLOCK_CURLINE_SHIFT = 20;
   private static final int BLOCK_OFS_MASK = 1023;
   private static final int BLOCK_CHARS_TO_DRAW_SHIFT = 10;
   public static final int MAX_MENUITEMS = 50;
   public static final int EMPTY_TEXT = Text.STRINGID((short)5, (short)0);
   public static Image imgMainBG;
   public static Image background;
   public static Image imgMedals;
   public static int maxInputDigits = 10;
   public static boolean digitInput;
   public static int lastOffer;
   public static MenuItem[] items = new MenuItem[50];
   public static int numItems;
   public static int menu;
   public static int oldMenu;
   public static int selectedIndex;
   public static int scrollIndex;
   public static int type;
   private static int maxItems;
   private static int cheatCombo;
   public static int startTime;
   private static int MODE_FULLREFRESH = 0;
   private static int MODE_IDLE = 1;
   private static int menuMode;
   private static final int MAXSTACKCOUNT = 10;
   private static int stackCount = 0;
   private static int[] menuStack = new int[10];
   private static byte[] menuIdxStack = new byte[10];
   private static int[] poppedIdx = new int[1];
   public static Text detailsTitle;
   public static Text detailsHelpText;
   public static int medalsMap;
   public static boolean goBackToStation;
   public static final int TOP_Y = 1;
   private static final int MAX_MORE_GAMES = 3;
   private static int moreGamesPage = 0;
   private static final int SM_MEDAL_W = 11;
   private static final int SM_MEDAL_H = 12;
   private static final int SM_MEDAL_PAD = 5;
   private static int[] clipRect = new int[4];
   private static final int RANK1 = 16000;
   private static final int RANK2 = 19000;
   private static final int RANK3 = 24000;
   private static boolean oldVibrateSetting;
   private static boolean oldSoundSetting;
   private static int oldLanguageSetting;

   public static final boolean startup() {
      App.checkPausedState();

      try {
         InputStream var0 = App.getResourceAsStream("/menus.bin");
         Resource.read(var0, 4);
         short var1 = Resource.shortAt(0);
         short var2 = Resource.shortAt(2);
         menuData = new int[var1];
         menuItems = new int[var2];
         int var3 = 0;
         int var4 = 0;
         boolean var5 = false;

         int var6;
         int var9;
         do {
            var9 = (var1 << 2) - var4 > 10240 ? 10240 : (var1 << 2) - var4;
            Resource.read(var0, var9);

            for(var6 = 4; var6 <= var9; var6 += 4) {
               menuData[var3++] = Resource.shiftInt();
            }
         } while((var4 += var9) < var1 << 2);

         App.checkPausedState();
         var3 = 0;
         var4 = 0;

         do {
            var9 = (var2 << 2) - var4 > 10240 ? 10240 : (var2 << 2) - var4;
            Resource.read(var0, var9);

            for(var6 = 4; var6 <= var9; var6 += 4) {
               menuItems[var3++] = Resource.shiftInt();
            }
         } while((var4 += var9) < var2 << 2);
      } catch (IOException var7) {
         App.Error(var7, 21);
      }

      for(int var8 = 0; var8 < items.length; ++var8) {
         items[var8] = new MenuItem();
      }

      numItems = 0;
      background = null;
      menu = 0;
      selectedIndex = 0;
      scrollIndex = 0;
      type = 0;
      maxItems = 0;
      cheatCombo = 0;
      goBackToStation = false;
      return true;
   }

   public static final void buildDivider(Text var0, int var1) {
      int var2 = var0.length();
      int var3 = (var1 - (var2 + 2)) / 2;
      if (var0.length() > 0) {
         var0.insert(' ', 0);
         var0.append(' ');
      } else {
         var0.append('\u0080');
         var0.append('\u0080');
         var0.append('\u0080');
      }

      for(int var4 = 0; var4 < var3; ++var4) {
         var0.insert('\u0080', 0);
         var0.append('\u0080');
      }

   }

   public static boolean enterDigit(int var0) {
      cheatCombo *= 10;
      cheatCombo += var0;
      if (cheatCombo == 3666) {
         cheatCombo = 0;
         selectedIndex = 0;
         scrollIndex = 0;
         gotoMenu(63);
         return true;
      } else if (cheatCombo == 1666) {
         Canvas.loadMap(Canvas.loadMapID, true);
         return true;
      } else {
         if (cheatCombo == 4332) {
            Player.giveAll();
         } else if (cheatCombo == 3366) {
            cheatCombo = 0;
            if (menu >= 28) {
               Canvas.startSpeedTest(false);
            }
         }

         return false;
      }
   }

   public static final void soundClick() {
   }

   public static final void scrollDown() {
      if ((items[selectedIndex].flags & 256) != 0) {
         shiftBlockText(selectedIndex, 1, maxItems);
      } else {
         moveDir(1);
      }

   }

   public static final void scrollUp() {
      if ((items[selectedIndex].flags & 256) != 0) {
         shiftBlockText(selectedIndex, -1, maxItems);
      } else {
         moveDir(-1);
      }

   }

   public static final boolean scrollPageDown() {
      if ((items[selectedIndex].flags & 256) != 0) {
         return shiftBlockText(selectedIndex, maxItems, maxItems);
      } else {
         int var0 = selectedIndex;

         for(int var1 = 0; var1 < maxItems && selectedIndex != numItems - 1; ++var1) {
            moveDir(1);
            if (selectedIndex < var0) {
               moveDir(-1);
               break;
            }
         }

         return var0 != selectedIndex;
      }
   }

   public static final void scrollPageUp() {
      if ((items[selectedIndex].flags & 256) != 0) {
         shiftBlockText(selectedIndex, -maxItems, maxItems);
      } else {
         int var0 = selectedIndex;

         for(int var1 = 0; var1 < maxItems && selectedIndex != 0; ++var1) {
            moveDir(-1);
            if (selectedIndex >= var0) {
               moveDir(1);
               break;
            }
         }
      }

   }

   public static final boolean shiftBlockText(int var0, int var1, int var2) {
      if (var0 <= numItems && (items[var0].flags & 256) != 0) {
         Text var3 = Text.getLargeBuffer();
         Text.composeText(items[var0].textField, var3);
         int var4 = items[var0].param >> 26 & 63;
         int var5 = items[var0].param >> 20 & 63;
         int var6 = items[var0].param & 1023;
         int var7 = var6;
         if (var1 > 0) {
            for(var1 = Math.min(var1, var4 - (var5 + var2)); var1 > 0; --var1) {
               var6 = var3.findFirstOf('|', var6) + 1;
               ++var5;
            }
         } else {
            for(var1 = Math.min(Math.abs(var1), var5); var1 > 0; --var1) {
               var6 = var3.findLastOf('|', var6 - 1) + 1;
               --var5;
            }
         }

         int var8;
         for(var8 = var6; var2 > 0; ++var8) {
            var8 = var3.findFirstOf('|', var8);
            if (var8 == -1) {
               var8 = var3.length();
               break;
            }

            --var2;
         }

         items[var0].param = (var4 & 63) << 26 | (var5 & 63) << 20 | (var8 - var6 & 1023) << 10 | var6 & 1023;
         var3.dispose();
         return var6 != var7;
      } else {
         return false;
      }
   }

   public static final void doDetailsSelect() {
      if (type != 5 && type != 7) {
         cheatCombo = 0;
         if ((items[selectedIndex].flags & 32) == 0) {
            if (items[selectedIndex].action != 0) {
               select();
            }

         } else {
            showDetailsMenu();
         }
      }
   }

   public static final void back() {
      if (menu == 17 || menu == 34) {
         leaveOptionsMenu();
      }

      if (stackCount != 0) {
         int var0 = popMenu(poppedIdx);
         setMenu(var0);
         selectedIndex = poppedIdx[0];
         if (selectedIndex > numItems) {
            moveDir(-1);
         }
      } else if (menu != 28 && menu != 56 && menu != 55 && !goBackToStation && menu != 54 && menu != 40) {
         if (menu == 24) {
            setMenu(3);
         }
      } else {
         returnToGame();
      }

      soundClick();
   }

   private static final void moveDir(int var0) {
      menuMode = MODE_FULLREFRESH;
      if (type != 5 && type != 7) {
         label69:
         while(true) {
            selectedIndex += var0;
            if (selectedIndex < numItems && selectedIndex >= 0) {
               if (items[selectedIndex].textField == EMPTY_TEXT || (items[selectedIndex].flags & '老') != 0) {
                  continue;
               }
               break;
            }

            if (selectedIndex < 0) {
               selectedIndex = numItems - 1;
            } else {
               selectedIndex = 0;
            }

            while(true) {
               if (items[selectedIndex].textField != EMPTY_TEXT && (items[selectedIndex].flags & '老') == 0) {
                  break label69;
               }

               selectedIndex += var0;
            }
         }

         if (maxItems != 0 && var0 < 0) {
            if (selectedIndex - maxItems + 1 > scrollIndex) {
               scrollIndex = selectedIndex - maxItems + 1;
            } else if (selectedIndex < scrollIndex) {
               scrollIndex = selectedIndex;
            }
         } else if (maxItems != 0) {
            if (selectedIndex > scrollIndex + maxItems - 1) {
               scrollIndex = selectedIndex - maxItems + 1;
            } else if (scrollIndex > selectedIndex) {
               scrollIndex = selectedIndex;
            }
         }
      } else {
         if (var0 < 0 && scrollIndex > 0) {
            scrollIndex += var0;
         } else if (var0 > 0 && scrollIndex < numItems - maxItems) {
            scrollIndex += var0;
         }

         selectedIndex = scrollIndex;
      }

   }

   public static final void setMenu(int var0) {
      cheatCombo = 0;
      menuMode = MODE_FULLREFRESH;
      if (var0 == 28 && Canvas.isChickenKicking && Canvas.kickingFromMenu) {
         var0 = 56;
      }

      if (var0 == 3 || var0 == 28 || var0 == 56) {
         clearStack();
      }

      if ((var0 > 2 || var0 == 0) && menu != 0 && menu < 2 && menu >= 1) {
      }

      if (var0 == 20) {
         menuMode = MODE_FULLREFRESH;
      }

      oldMenu = menu;
      menu = var0;
      if (menu != 0) {
         initMenu(menu);
         if (selectedIndex >= numItems) {
            moveDir(-1);
         } else if (items[selectedIndex].textField == EMPTY_TEXT || (items[selectedIndex].flags & '老') != 0) {
            moveDir(1);
         }

         if (Canvas.state != 1) {
            Canvas.setState(1);
         }
      } else {
         returnToGame();
         menu = 0;
      }

   }

   public static final void paint(Graphics var0) {
      int[] var1 = Canvas.menuRect;
      int[] var2 = Canvas.screenRect;
      int[] var3 = clipRect;
      int[] var4 = Canvas.displayRect;
      int var5 = 12;
      Text var6 = Text.getLargeBuffer();
      Text var7 = Text.getLargeBuffer();
      if (menuMode == MODE_IDLE) {
         var0.clipRect(var3[0], var3[1], var3[2], var3[3]);
      }

      MenuItem var8 = items[selectedIndex];
      boolean var9 = (var8.flags & 256) != 0;
      int var10 = 1;
      int var11;
      int var12;
      if (menu >= 28 && menu != 33) {
         Canvas.drawScroll(var0, 0, 0, var1[2], var1[3], 0);
         var5 += 2;
      } else {
         var0.eraseRgn(var1);
         if (background != null) {
            var11 = background.getWidth();
            var12 = background.getHeight();
            var0.drawImage(background, var1[2] / 2, var1[3] / 2, 3);
            var10 += var12 / 2 + 4 - var2[1];
            if (var12 < var1[3]) {
               var10 += (var1[3] - var12) / 2;
            }
         }
      }

      if (menu == 3) {
         var10 -= 12;
      } else if (menu == 25) {
         var10 += 15;
      }

      var11 = var1[3];
      if (menu >= 70 && menu <= 78) {
         var6.setLength(0);
         var6.append('\u008d');
         var6.append(Player.ce.getStat(0));
         var6.append("/");
         var6.append(Player.ce.getStat(1));
         var6.append("   ");
         var6.append('\u008b');
         var6.append(Player.ce.getStat(2));
         var6.append("/");
         var6.append(200);
         var0.drawString(var6, Canvas.SCR_CX, var10, 1);
         var0.setColor(-16777216);
         var0.drawLine(0, var10 + (var5 - 3), var1[2], var10 + (var5 - 3));
         var0.drawLine(0, var10 + (var5 - 2), var1[2], var10 + (var5 - 2));
         var11 -= var5;
         var10 += var5;
      }

      if (type == 2) {
         var12 = 90;
      } else if (type == 1) {
         var12 = 16;
      } else if (type == 5) {
         if (menu >= 28 && menu != 33) {
            var12 = 16;
         } else {
            var12 = 1;
         }

         var10 = -var2[1] + 1;
      } else if (type == 7) {
         var12 = 16;
      } else if (type == 6) {
         var12 = 0;
         var10 = (Canvas.menuRect[3] - numItems * var5) / 2;
      } else if (type == 8) {
         var12 = 13;
      } else if (type == 4) {
         var12 = 0;
      } else {
         var12 = var1[2] / 2;
      }

      boolean var13 = false;
      if (menu != 1 && menu != 17 && selectedIndex < numItems && var8.helpField != EMPTY_TEXT) {
         var13 = true;
      }

      if (var13) {
         maxItems = var11 / var5;
         maxItems -= 2;
      } else {
         maxItems = var11 / var5;
      }

      int var15;
      if (var9) {
         int var14 = items[0].param;
         var15 = var14 >> 20 & 63;
         Canvas.drawScrollBar(var0, var1[2], var10, maxItems * var5, var15, var15 + maxItems, var14 >> 26 & 63, maxItems);
      } else if (maxItems > 0 && numItems > maxItems) {
         Canvas.drawScrollBar(var0, var1[2], var10, maxItems * var5, scrollIndex, scrollIndex + maxItems, numItems, maxItems);
      }

      MenuItem[] var22 = items;

      int var16;
      for(var15 = scrollIndex; var15 < numItems; ++var15) {
         MenuItem var17 = var22[var15];
         int var18 = var1[2] - 2;
         byte var19 = 7;
         if ((var17.flags & '耀') == 0) {
            if (var17.textField != EMPTY_TEXT || (var17.flags & 64) != 0) {
               var6.setLength(0);
               if ((var17.flags & 1024) != 0) {
                  var6.append('\u0087');
                  var6.append(" ");
               }

               Text.composeTextField(var17.textField, var6);
               if ((var17.flags & 2) == 0 && !var9) {
                  var6.dehyphenate();
               }

               int var20;
               if ((var17.flags & 64) != 0) {
                  var20 = (var1[2] - var12) / var19;
                  if (maxItems > 0 && numItems > maxItems) {
                     --var20;
                  }

                  buildDivider(var6, var20);
               }

               if ((var17.flags & 8) != 0) {
                  var20 = var6.getStringWidth(false) >> 1;
                  if (maxItems != 0 && numItems > maxItems) {
                     var16 = Canvas.SCR_CX + (var12 >> 1) - 7 - var20;
                  } else {
                     var16 = Canvas.SCR_CX + (var12 >> 1) - var20;
                  }
               } else {
                  var16 = var12 - var2[0];
                  if (var17.textField2 != EMPTY_TEXT) {
                     if (maxItems > 0 && numItems > maxItems) {
                        var18 -= 7;
                     }

                     var7.setLength(0);
                     Text.composeTextField(var17.textField2, var7);
                     if ((var17.flags & 2) == 0) {
                        var7.dehyphenate();
                     }

                     var0.drawString(var7, var18 - 2, var10, 24);
                  }
               }

               if (type != 5 && type != 7 && var15 == selectedIndex) {
                  var20 = var16 + Canvas.OSC_CYCLE[App.time / 100 % 4];
                  menuMode = MODE_IDLE;
                  var3[0] = var2[0] + var16 - 8;
                  var3[1] = var2[1] + var10 + var5 / 2 - 8;
                  var3[2] = 16;
                  var3[3] = 16;
                  var0.drawCursor(var20, var10, 8);
                  var16 += 4;
               }

               if ((var17.flags & 2048) != 0) {
                  if (var17.param == 0) {
                     var0.drawRegion(imgMedals, 0, 0, 11, 12, var16 + 5, var10);
                     byte var25 = 102;
                     var0.drawRegion(Canvas.imgFont, (var25 & 15) * 9, ((var25 & 240) >> 4) * 12, 9, 12, var16 + 5, var10);
                  } else if (var17.param == 1) {
                     var0.drawRegion(imgMedals, 0, 12, 11, 10, var16 + 5, var10);
                  }
               } else if ((var17.flags & 4096) != 0) {
                  if (var17.param == 0) {
                     var0.drawRegion(imgMedals, 11, 0, 11, 12, var16 + 5, var10);
                  } else if (var17.param == 1) {
                     var0.drawRegion(imgMedals, 11, 12, 11, 10, var16 + 5, var10);
                  }
               }

               if ((var17.flags & 2048) != 0 || (var17.flags & 4096) != 0) {
                  var16 += 20;
               }

               if (var9) {
                  var0.drawString(var6, var16, var10, var5, 0, var17.param & 1023, var17.param >> 10 & 1023);
               } else {
                  var0.drawString(var6, var16, var10, 0);
               }

               if ((var17.flags & 4) != 0) {
                  var20 = var6.length();
                  var6.setLength(0);

                  for(int var21 = 0; var21 < var20; ++var21) {
                     var6.append('\u0089');
                  }

                  var0.drawString(var6, var16, var10, 0);
               }

               if ((var17.flags & 16384) != 0) {
                  var0.drawRegion(Hud.imgAttArrow, 0, 0, 12, 12, var16 - 17, var10, 0, 5);
               }

               if ((var17.flags & 8192) != 0) {
                  var20 = var16 + var6.getStringWidth(false);
                  var0.drawRegion(Hud.imgAttArrow, 0, 0, 12, 12, var20 + 5, var10, 0, 6);
               }
            }

            var10 += var5;
            if (maxItems != 0 && var15 == scrollIndex + maxItems - 1) {
               break;
            }
         }
      }

      if (var13) {
         var16 = var1[3] / var5 - 2;
         int var23 = var16 * var5 + 1;
         var0.setColor(-16777216);
         var0.drawLine(0, var23 - 1, var1[2], var23 - 1);
         var0.drawLine(0, var23, var1[2], var23);
         Text var24 = Text.getLargeBuffer();
         var8.WrapHelpText(var24);
         var0.drawString(var24, var12, var23 + 2, 0);
         var24.dispose();
      }

      if (menuMode == MODE_IDLE) {
         var0.clearClipRect();
      }

      var6.dispose();
      var7.dispose();
   }

   public static final void setItemsFromText(int var0, Text var1, int var2) {
      setItemsFromText(var0, var1, var2, 0, 0);
   }

   public static final void setItemsFromText(int var0, Text var1, int var2, int var3, int var4) {
      numItems = var0;
      var1.wrapText(var2);

      int var5;
      int var6;
      for(var5 = 0; (var6 = var1.findFirstOf('|', var5)) >= 0; var5 = var6 + 1) {
         Text.addTextArg(var1, var5, var6);
         items[var0++].Set(getLastArgString(), EMPTY_TEXT, 2 | var3, 0, var4, EMPTY_TEXT);
      }

      var6 = var1.length();
      Text.addTextArg(var1, var5, var6);
      items[var0++].Set(getLastArgString(), EMPTY_TEXT, 2 | var3, 0, var4, EMPTY_TEXT);
      numItems = var0;
      var1.dispose();
   }

   public static void returnToGame() {
      numItems = 0;
      if (goBackToStation) {
         Canvas.setState(20);
         goBackToStation = false;
      } else {
         App.time = App.lastTime = App.getUpTimeMs();
         Canvas.setState(3);
      }

   }

   public static final void initMenu(int var0) {
      byte var2 = 0;
      MenuItem[] var3 = items;
      if (var0 != oldMenu) {
         scrollIndex = 0;
         selectedIndex = 0;
      }

      Text var4;
      numItems = 0;
      var4 = Text.getLargeBuffer();
      Text.resetTextArgs();
      EntityDef var1;
      int var6;
      int var16;
      int var19;
      int var20;
      short var21;
      label333:
      switch(var0) {
      case 1:
         loadMenuItems(var0);
         loadMedalItems(Canvas.loadMapID - 1, false, true, true);
         Sound.playSound(5);
         Canvas.clearEvents();
         break;
      case 2:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 29:
      case 36:
      case 41:
      case 47:
      case 50:
      case 56:
      case 60:
      case 61:
      case 62:
      case 66:
      case 67:
      case 68:
      default:
         loadMenuItems(var0);
         break;
      case 3:
         if (Game.hasSavedState()) {
            addItem(Text.STRINGID((short)5, (short)65), EMPTY_TEXT, 8, 22, 0, EMPTY_TEXT);
         } else {
            addItem(EMPTY_TEXT, EMPTY_TEXT, 4);
         }

         background = imgMainBG;
         loadMenuItems(var0);
         if (!App.canvas.mgInstance.isAvailable()) {
            int var15 = Text.STRINGID((short)5, (short)72);

            for(var6 = 0; var6 < numItems; ++var6) {
               if (var3[var6].textField == var15) {
                  var3[var6].flags |= 32768;
               }
            }
         }
         break;
      case 4:
      case 16:
         background = null;
         loadMenuItems(var0);
         break;
      case 5:
         type = 5;
         background = null;
         LoadHelpResource((short)6);
         break;
      case 6:
         type = 5;
         background = null;
         LoadHelpResource((short)7);
         break;
      case 7:
         type = 5;
         background = null;
         LoadHelpResource((short)8);
         break;
      case 8:
         type = 5;
         background = null;
         LoadAbout();
         break;
      case 9:
         type = 5;
         background = null;
         LoadHelpResource((short)1);
         break;
      case 10:
         type = 5;
         background = null;
         LoadHelpResource((short)3);
         break;
      case 11:
         type = 5;
         background = null;
         LoadHelpResource((short)5);
         break;
      case 12:
         type = 5;
         background = null;
         LoadHelpResource((short)9);
         break;
      case 13:
         type = 4;
         background = imgMainBG;
         SetYESNO((short)97, 0, 8, 0);
         break;
      case 14:
         type = 6;
         background = null;
         SetYESNO((short)98, 0, 1, 16);
         break;
      case 15:
         type = 6;
         background = null;
         SetYESNO((short)99, 0, 7, 0);
         break;
      case 17:
      case 34:
         loadMenuItems(var0);
         var3[1].textField2 = onOffValue(Sound.isVibrateEnabled());
         var3[2].textField2 = onOffValue(Sound.isSoundEnabled());
         var3[3].textField2 = Text.STRINGID((short)16, (short)0);
         if (var0 == 17) {
            var3[3].param = 19;
         } else {
            var3[3].param = 35;
         }

         oldVibrateSetting = Sound.isVibrateEnabled();
         oldSoundSetting = Sound.isSoundEnabled();
         break;
      case 18:
         type = 5;
         background = null;
         clearStack();
         scrollIndex = 0;
         selectedIndex = 0;
         FillRanking();
         break;
      case 19:
         background = imgMainBG;
         var2 = 8;
      case 35:
         oldLanguageSetting = Text.defaultLanguage;
         type = var0 == 35 ? 1 : 4;
         scrollIndex = 0;
         if (peekMenu() != 20) {
            var4.setLength(0);
            Text.composeText((short)5, (short)75, var4);
            if (type == 4) {
               while(var4.length() < 8) {
                  var4.append(' ');
               }
            }

            Text.addTextArg(var4);
            addItem(getLastArgString(), EMPTY_TEXT, var2, 2, 0, EMPTY_TEXT);
         }

         try {
            Text.beginTextLoading();

            for(var16 = 0; var16 < 5; ++var16) {
               Text.loadTextFromIndex(var16, 16);
               var4.setLength(0);
               Text.composeText((short)16, (short)0, var4);
               if (type == 1) {
                  var4.trim();
               }

               var4.dehyphenate();
               Text.addTextArg(var4);
               addItem(getLastArgString(), EMPTY_TEXT, var2, 19, var16, EMPTY_TEXT);
            }

            Text.finishTextLoading();
            Text.beginTextLoading();
            Text.loadTextFromIndex(Text.defaultLanguage, 16);
            Text.finishTextLoading();
         } catch (IOException var13) {
            App.Error(var13, -1);
         }
         break;
      case 20:
         type = 4;
         background = imgMainBG;
         scrollIndex = 0;
         SetYESNO((short)153, 1, 6, 1, 6, 0);
         clearStack();
         break;
      case 26:
         type = 5;
         background = null;
         LoadHelpResource((short)11);
         break;
      case 27:
         type = 5;
         background = null;
         LoadHelpResource((short)12);
         break;
      case 28:
         selectedIndex = 1;
         loadMenuItems(var0);
         if (Player.inventory[18] == 0) {
            var3[2].flags = 4;
         }

         if (Game.isCameraActive()) {
            var3[3].flags = 4;
            var3[4].flags = 4;
            var3[5].flags = 4;
            var3[10].flags = 4;
            var3[11].action = 1;
            var3[11].param = 57;
         }

         if (Canvas.isChickenKicking) {
            var3[1].flags = 4;
            var3[3].flags = 4;
            var3[4].flags = 4;
            var3[5].flags = 4;
            var3[9].flags = 4;
            var3[10].flags = 4;
            var3[11].action = 1;
            var3[11].param = 57;
         }
         break;
      case 30:
         loadMenuItems(var0);
         var3[4].textField2 = Text.STRINGID((short)5, (short)210);
         var3[5].textField2 = Text.STRINGID((short)5, (short)211);
         var3[6].textField2 = Text.STRINGID((short)5, (short)212);
         var3[7].textField2 = Text.STRINGID((short)5, (short)213);
         var3[8].textField2 = Text.STRINGID((short)5, (short)214);
         var3[9].textField2 = Text.STRINGID((short)5, (short)215);
         var3[10].textField2 = Text.STRINGID((short)5, (short)216);
         var3[11].textField2 = Text.STRINGID((short)5, (short)217);
         var3[12].textField2 = Text.STRINGID((short)5, (short)218);
         var3[13].textField2 = Text.STRINGID((short)5, (short)219);
         var3[14].textField2 = Text.STRINGID((short)5, (short)220);
         var3[15].textField2 = Text.STRINGID((short)5, (short)221);
         var3[16].textField2 = Text.STRINGID((short)5, (short)222);
         var3[17].textField2 = Text.STRINGID((short)5, (short)223);
         var3[18].textField2 = Text.STRINGID((short)5, (short)224);
         var3[19].textField2 = Text.STRINGID((short)5, (short)225);
         var3[20].textField2 = Text.STRINGID((short)5, (short)226);
         var3[21].textField2 = Text.STRINGID((short)5, (short)227);
         fillStatus(false, true, true);
         break;
      case 31:
         loadMenuItems(var0);
         var3[3].textField = Text.STRINGID((short)5, (short)210);
         var3[4].textField2 = Text.STRINGID((short)5, (short)211);
         var3[5].textField2 = Text.STRINGID((short)5, (short)212);
         var3[6].textField2 = Text.STRINGID((short)5, (short)213);
         var3[7].textField2 = Text.STRINGID((short)5, (short)214);
         var3[8].textField2 = Text.STRINGID((short)5, (short)215);
         var3[9].textField2 = Text.STRINGID((short)5, (short)216);
         var3[10].textField2 = Text.STRINGID((short)5, (short)217);
         fillStatus(true, false, false);
         break;
      case 32:
         loadMenuItems(var0);
         byte[] var5 = Player.getLevelMedalCount();
         var6 = 0;
         int var7 = 0;

         for(var16 = 0; var16 < Player.highestMap; ++var16) {
            if (var16 != 4) {
               var6 += var5[var16 << 1];
               var7 += var5[(var16 << 1) + 1];
               buildFraction(var5[var16 << 1], var5[(var16 << 1) + 1], var4);
               Text.addTextArg(var4);
               addItem(Text.STRINGID((short)5, Game.levelNames[var16]), getLastArgString(), var2, 30, var16, Text.STRINGID((short)5, (short)27));
            }
         }

         addItem(EMPTY_TEXT, EMPTY_TEXT, 73, 0, 0, EMPTY_TEXT);
         buildFraction(var6, var7, var4);
         Text.addTextArg(var4);
         addItem(Text.STRINGID((short)5, (short)28), getLastArgString(), var2, 0, 0, Text.STRINGID((short)5, (short)29));
         break;
      case 33:
         type = 5;
         background = null;
         scrollIndex = 0;
         selectedIndex = 0;
         addItem(Text.STRINGID((short)5, (short)210), EMPTY_TEXT, 72, 0, 0, EMPTY_TEXT);
         loadMedalItems(medalsMap, false, false, false);
         break;
      case 37:
         type = 5;
         LoadHelpResource((short)1);
         break;
      case 38:
         type = 5;
         LoadHelpResource((short)3);
         break;
      case 39:
         type = 5;
         LoadHelpResource((short)5);
         break;
      case 40:
         type = 5;
         LoadHelpResource((short)9);
         break;
      case 42:
         type = 5;
         LoadHelpResource((short)6);
         break;
      case 43:
         type = 5;
         LoadHelpResource((short)7);
         break;
      case 44:
         type = 5;
         LoadHelpResource((short)8);
         break;
      case 45:
         LoadNotebook();
         break;
      case 46:
         LoadRecipes();
         break;
      case 48:
         type = 6;
         scrollIndex = 0;
         SetYESNO((short)124, 1, 3, 0);
         break;
      case 49:
         scrollIndex = 0;
         selectedIndex = 2;
         loadMenuItems(var0);
         break;
      case 51:
         type = 6;
         scrollIndex = 0;
         SetYESNO((short)122, 1, 12, 0);
         break;
      case 52:
         type = 6;
         scrollIndex = 0;
         SetYESNO((short)123, 1, 13, 0, 2, 0);
         break;
      case 53:
         loadMenuItems(var0);
         loadBookList();
         break;
      case 54:
         loadBook(menuParam);
         type = 7;
         break;
      case 55:
         loadMenuItems(var0);
         var3[3].textField2 = Text.STRINGID((short)5, (short)206);

         for(var16 = 0; var16 < 5; ++var16) {
            var4.setLength(0);
            var4.append(Canvas.highScoreInitials[var16 * 3 + 0]);
            var4.append(Canvas.highScoreInitials[var16 * 3 + 1]);
            var4.append(Canvas.highScoreInitials[var16 * 3 + 2]);
            Text.addTextArg(var4);
            Text.addTextArg(Canvas.highScores[var16]);
            addItem(Text.STRINGID((short)5, (short)(210 + var16 * 2)), Text.STRINGID((short)5, (short)(210 + var16 * 2 + 1)), 1, 11, 0, EMPTY_TEXT);
         }

         addItem(Text.STRINGID((short)5, (short)127), EMPTY_TEXT, 9);
         addItem(Text.STRINGID((short)1, (short)53), EMPTY_TEXT, 9);
         break;
      case 57:
         type = 6;
         scrollIndex = 0;
         SetYESNO((short)125, 0, 23, 0);
         break;
      case 58:
         type = 5;
         LoadHelpResource((short)11);
         break;
      case 59:
         type = 5;
         LoadHelpResource((short)12);
         break;
      case 63:
         loadMenuItems(var0);
         if (peekMenu() == 3) {
            var3[0].action = 1;
            var3[0].param = 3;
         }

         var3[6].textField2 = onOffValue(Player.noclip);
         var3[7].textField2 = onOffValue(Combat.oneShotCheat);
         var3[8].textField2 = onOffValue(Game.disableAI);
         var3[9].textField2 = onOffValue(Player.enableHelp);
         var3[10].textField2 = onOffValue(Player.god);
         var3[11].textField2 = onOffValue(Canvas.showLocation);
         Text.addTextArg(Canvas.animFrames);
         var3[12].textField2 = Text.STRINGID((short)5, (short)210);
         var3[13].textField2 = onOffValue(Canvas.showSpeeds);
         var3[14].textField2 = onOffValue(Render.skipFlats);
         var3[15].textField2 = onOffValue(Render.skipCull);
         var3[16].textField2 = onOffValue(Render.skipBSP);
         var3[17].textField2 = onOffValue(Render.skipLines);
         var3[18].textField2 = onOffValue(Render.skipSprites);
         var3[19].textField2 = onOffValue(Canvas.renderOnly);
         var3[20].textField2 = onOffValue(Render.skipDecals);
         var3[21].textField2 = onOffValue(Render.skip2DStretch);
         if (Render.renderMode == 0) {
            var3[22].textField2 = Text.STRINGID((short)5, (short)267);
         } else if (Render.renderMode == 63) {
            var3[22].textField2 = Text.STRINGID((short)5, (short)268);
         } else if (Render.renderMode == 31) {
            var3[22].textField2 = Text.STRINGID((short)5, (short)269);
         } else {
            Text var17 = Text.getSmallBuffer();
            var17.setLength(0);
            if ((Render.renderMode & 1) != 0) {
               var17.append('T');
            }

            if ((Render.renderMode & 2) != 0) {
               var17.append('C');
            }

            if ((Render.renderMode & 4) != 0) {
               var17.append('P');
            }

            if ((Render.renderMode & 8) != 0) {
               var17.append('S');
            }

            if ((Render.renderMode & 16) != 0) {
               var17.append('R');
            }

            Text.addTextArg(var17);
            var17.dispose();
            var3[22].textField2 = getLastArgString();
         }
         break;
      case 64:
         type = 1;
         addItem(Text.STRINGID((short)5, (short)75), EMPTY_TEXT, 0, 2, 0, EMPTY_TEXT);
         if (peekMenu() == 75) {
            var3[0].action = 27;
         }

         var16 = 0;

         while(true) {
            if (var16 >= Game.levelNames.length) {
               break label333;
            }

            addItem(Text.STRINGID((short)5, Game.levelNames[var16]), EMPTY_TEXT, 0, 17, var16, EMPTY_TEXT);
            ++var16;
         }
      case 65:
         System.gc();
         Text.resetTextArgs();
         Text.addTextArg((App.getTotalMemory() + 1023) / 1024);
         Text.addTextArg((int)(App.initialMemory + 1023L) / 1024);
         Text.addTextArg((App.getMemFootprint() + 1023) / 1024);
         Text.addTextArg((App.getFreeMemory() + 1023) / 1024);
         Text.addTextArg((App.findLargestMemoryBlock() + 1023) / 1024);
         Text.addTextArg(((int)App.peakMemoryUsage + 1023) / 1024);
         Text.addTextArg((Render.mapMemoryUsage + 1023) / 1024);
         Text.addTextArg((Render.spriteMem + 1023) / 1024);
         Text.addTextArg((Render.lineMem + 1023) / 1024);
         Text.addTextArg((Render.nodeMem + 1023) / 1024);
         var4.setLength(0);
         var4.append((Render.texelMemoryUsage + 1023) / 1024);
         var4.append('/');
         var4.append((Render.paletteMemoryUsage + 1023) / 1024);
         Text.addTextArg(var4);
         Text.addTextArg(App.imageMemory);
         loadMenuItems(var0);
         var3[1].textField2 = Text.STRINGID((short)5, (short)210);
         var3[2].textField2 = Text.STRINGID((short)5, (short)211);
         var3[3].textField2 = Text.STRINGID((short)5, (short)212);
         var3[4].textField2 = Text.STRINGID((short)5, (short)213);
         var3[5].textField2 = Text.STRINGID((short)5, (short)214);
         var3[6].textField2 = Text.STRINGID((short)5, (short)215);
         var3[7].textField2 = Text.STRINGID((short)5, (short)216);
         var3[8].textField2 = Text.STRINGID((short)5, (short)217);
         var3[9].textField2 = Text.STRINGID((short)5, (short)218);
         var3[10].textField2 = Text.STRINGID((short)5, (short)219);
         var3[11].textField2 = Text.STRINGID((short)5, (short)220);
         var3[12].textField2 = Text.STRINGID((short)5, (short)221);
         break;
      case 69:
         Text var23 = null;
         if (detailsDef != null && detailsDef.eType == 6) {
            if (detailsDef.eSubType == 1) {
               var23 = Combat.getWeaponStatStr(detailsDef.parm);
            } else if (detailsDef.eSubType == 6) {
               var23 = Combat.getArmorStatStr(detailsDef.parm);
            }
         }

         Text.resetTextArgs();
         type = 7;
         setItemsFromText(0, detailsTitle, Canvas.ingameScrollWithBarMaxChars);

         for(var20 = 0; var20 < numItems; ++var20) {
            var3[var20].flags |= 8;
         }

         addItem(EMPTY_TEXT, EMPTY_TEXT, 73);
         setItemsFromText(numItems, detailsHelpText, Canvas.ingameScrollWithBarMaxChars);
         if (null != var23) {
            addItem(EMPTY_TEXT, EMPTY_TEXT, 73);
            setItemsFromText(numItems, var23, Canvas.ingameScrollWithBarMaxChars);
            var23.dispose();
         }

         detailsTitle.dispose();
         detailsHelpText.dispose();
         break;
      case 70:
         loadMenuItems(var0, 0, 3);
         boolean var8 = true;

         for(var19 = 16; var19 < 18; ++var19) {
            var21 = Player.inventory[var19 - 0];
            if (var21 > 0) {
               if (var8) {
                  var8 = false;
                  addItem(Text.STRINGID((short)5, (short)103), EMPTY_TEXT, 73, 0, 0, EMPTY_TEXT);
               }

               var4.setLength(0);
               var4.append(var21);
               var1 = EntityDef.find(6, 0, var19);
               Text.addTextArg(var4);
               addItem(Text.STRINGID((short)2, var1.name), getLastArgString(), 32, 24, var19, Text.STRINGID((short)2, var1.description));
            }
         }

         loadMenuItems(var0, 3, -1);
         loadIndexes(4);
         break;
      case 71:
         loadMenuItems(var0);
         loadIndexes(1);
         var19 = 0;

         while(true) {
            if (var19 >= 17) {
               break label333;
            }

            if ((Player.weapons & (long)(1 << var19)) != 0L) {
               int var14 = Player.ce.weapon == 16 && var19 != 16 ? 4 : 0;
               var1 = EntityDef.find(6, 1, (byte)var19);
               if (Player.ce.weapon == var19) {
                  var14 |= 1024;
               }

               var20 = var19 * 9;
               var4.setLength(0);
               if (Combat.weapons[var20 + 4] != 0) {
                  var4.append(Player.ammo[Combat.weapons[var20 + 4]]);
               } else {
                  var4.append('\u0080');
               }

               Text.addTextArg(var4);
               addItem(Text.STRINGID((short)2, var1.name), getLastArgString(), var14 | 32, 18, var19, Text.STRINGID((short)2, var1.description));
            }

            ++var19;
         }
      case 72:
         loadMenuItems(var0);
         loadIndexes(0);
         Text.addTextArg(Player.gold);
         addItem(Text.STRINGID((short)2, (short)190), getLastArgString(), 32, 16, -1, Text.STRINGID((short)2, (short)191));
         var19 = 25;

         while(true) {
            if (var19 >= 28) {
               break label333;
            }

            var21 = Player.inventory[var19 - 0];
            if (var21 > 0) {
               var4.setLength(0);
               var4.append(var21);
               var1 = EntityDef.find(6, 0, var19);
               Text.addTextArg(var4);
               addItem(Text.STRINGID((short)2, var1.name), getLastArgString(), 32, 16, var19, Text.STRINGID((short)2, var1.description));
            }

            ++var19;
         }
      case 73:
         loadMenuItems(var0);
         loadIndexes(2);
         boolean var18 = false;

         for(var20 = 0; var20 < 15; ++var20) {
            short var22 = Player.inventory[var20 - 0];
            if (var22 > 0) {
               var18 = true;
               var4.setLength(0);
               var4.append(var22);
               var1 = EntityDef.find(6, 0, var20);
               Text.addTextArg(var4);
               addItem(Text.STRINGID((short)2, var1.name), getLastArgString(), 32, 24, var20, Text.STRINGID((short)2, var1.description));
            }
         }

         if (!var18) {
            addItem(Text.STRINGID((short)5, (short)1), EMPTY_TEXT, 9, 0, 0, EMPTY_TEXT);
         }
         break;
      case 74:
         loadMenuItems(var0);
         loadIndexes(3);
         boolean var10 = false;

         for(int var11 = 18; var11 < 24; ++var11) {
            short var12 = Player.inventory[var11 - 0];
            if (var11 != 18 && var12 > 0) {
               var1 = EntityDef.find(6, 0, var11);
               Text.addTextArg(var12);
               byte var9 = 16;
               if (var11 == 22) {
                  var9 = 24;
               }

               var10 = true;
               addItem(Text.STRINGID((short)2, var1.name), getLastArgString(), 32, var9, var11, Text.STRINGID((short)2, var1.description));
            }
         }

         if (!var10) {
            addItem(Text.STRINGID((short)5, (short)1), EMPTY_TEXT, 9, 0, 0, EMPTY_TEXT);
         }

         addItem(Text.STRINGID((short)5, (short)202), EMPTY_TEXT, 73, 0, 0, EMPTY_TEXT);
         loadBookList();
         break;
      case 75:
         type = 6;
         scrollIndex = 0;
         var1 = EntityDef.find(6, 0, menuParam);
         var4.setLength(0);
         Text.composeText((short)2, var1.name, var4);
         Text.addTextArg(var4);
         if (peekMenu() == 73) {
            SetYESNO((short)168, 1, 20, menuParam);
         } else {
            SetYESNO((short)168, 1, 21, menuParam);
         }
      }

      var4.dispose();
   }

   public static final void gotoMenu(int var0) {
      if (var0 == 41 && Canvas.isChickenKicking) {
         Canvas.endKickingGame();
         Canvas.backToMain(false);
         setMenu(24);
      } else {
         if (var0 != menu) {
            pushMenu(menu, selectedIndex);
         }

         if (var0 == 25) {
            Canvas.setState(22);
            App.canvas.mgInstance.reset();
         } else {
            setMenu(var0);
         }

      }
   }

   public static final void handleMenuEvents(int var0, int var1) {
      if (var0 < 48 || var0 > 57 || !enterDigit(var0 - 48)) {
         if (var0 != 35 && var0 != 42) {
            if (var1 == 1) {
               if (menu != 25) {
                  scrollUp();
               }
            } else if (var1 == 2) {
               if (menu != 25) {
                  scrollDown();
               }
            } else if (var1 == 3) {
               if (menu != 25) {
                  scrollPageUp();
               }
            } else if (var1 == 4) {
               if (menu != 25) {
                  scrollPageDown();
               }
            } else if (var1 == 6) {
               select();
            } else if (var1 == 14) {
               if (menu == 54) {
                  back();
               }
            } else if (var1 == 7) {
               if (menu == 54 && stackCount == 0) {
                  back();
               } else if (menu != 18 && menu != 1) {
                  if (menu == 69 && goBackToStation) {
                     goBackToStation = false;
                     returnToGame();
                  } else if (menu != 25) {
                     doDetailsSelect();
                  }
               } else {
                  select();
               }
            } else if (var1 == 5) {
               if (menu != 25 && (menu != 54 || stackCount != 0) && (menu != 19 || stackCount >= 2)) {
                  back();
               }
            } else if (var1 == 15 && menu != 20) {
               if (menu == 3) {
                  gotoMenu(13);
               } else if (menu != 1 && menu != 21 && menu != 18 && menu != 22) {
                  if (menu != 25) {
                     back();
                  }
               } else {
                  select();
               }
            }

         }
      }
   }

   public static final void select() {
      select(selectedIndex);
   }

   public static final void select(int var0) {
      cheatCombo = 0;
      menuMode = MODE_FULLREFRESH;
      MenuItem[] var1 = items;
      if (selectedIndex >= numItems || (var1[selectedIndex].flags & 4) == 0) {
         soundClick();
         int var2 = var1[var0].action;
         if (menu == 18) {
            Canvas.setState(15);
         } else {
            if (menu == 1) {
               if (!Canvas.endingGame) {
                  Canvas.loadMap(LEVEL_STATS_nextMap, true);
                  return;
               }

               gotoMenu(18);
            } else if (menu == 55) {
               var2 = 11;
            } else if (menu != 25 && (type == 5 || type == 7)) {
               if (menu == 54) {
                  if (!scrollPageDown()) {
                     byte var3 = Player.bookMap[Player.bookIndex + 2];
                     if (var3 != -1 && stackCount == 0) {
                        Text.resetTextArgs();
                        Text var4 = Text.getSmallBuffer();
                        Text var5 = Text.getSmallBuffer();
                        if (Player.bookMap[Player.bookIndex + 3] >= 0) {
                           var4.append('+');
                        } else {
                           var4.append('-');
                        }

                        Text.addTextArg(Player.bookMap[Player.bookIndex + 3]);
                        Player.statusToString(var3, var5);
                        Text.addTextArg(var5);
                        Text.composeText((short)1, (short)179, var4);
                        Hud.addMessage(var4, 2);
                        var4.dispose();
                        var5.dispose();
                     }

                     back();
                  }
               } else {
                  if (menu == 69 && goBackToStation) {
                     returnToGame();
                     return;
                  }

                  back();
               }

               return;
            }

            int var6;
            switch(var2) {
            case 0:
               return;
            case 1:
               gotoMenu(var1[selectedIndex].param);
               break;
            case 2:
               back();
               break;
            case 3:
               Canvas.loadState(Canvas.getRecentLoadType(), (short)5, (short)177);
               break;
            case 4:
               byte var8 = 3;
               if (var1[selectedIndex].param == 1) {
                  var6 = var8 | 256;
               } else {
                  var6 = var8 | 128;
               }

               Canvas.saveState(var6, (short)5, (short)179);
               break;
            case 5:
               Canvas.backToMain(false);
               break;
            case 6:
               if (var1[selectedIndex].param == 255) {
                  Sound.setSound(!Sound.isSoundEnabled());
               } else {
                  Sound.setSound(var1[selectedIndex].param != 0);
               }

               if (!Sound.isSoundEnabled()) {
                  Sound.soundStop();
               }

               if (menu == 20) {
                  Sound.playSound(6, 1);
                  Canvas.repaintFlags |= 1;
                  App.canvas.flushGraphics();
                  Canvas.setState(2);
               } else {
                  var1[var0].textField2 = onOffValue(Sound.isSoundEnabled());
                  Sound.playSound(8);
               }
               break;
            case 7:
               if (Game.hasSavedState()) {
                  gotoMenu(14);
               } else {
                  gotoMenu(16);
               }
               break;
            case 8:
               App.app.shutdown();
               break;
            case 9:
               Canvas.setState(var1[var0].param);
               break;
            case 10:
               Game.difficulty = (byte)var1[var0].param;
               Canvas.clearSoftKeys();
               startGame(true);
               break;
            case 11:
               returnToGame();
               break;
            case 12:
               Canvas.loadState(2, (short)5, (short)177);
               if (menu != 50) {
                  Hud.addMessage((short)5, (short)178);
               }
               break;
            case 13:
               Canvas.saveState(67, (short)5, (short)179);
            case 14:
               break;
            case 15:
               Sound.setVibrate(!Sound.isVibrateEnabled());
               if (Sound.isVibrateEnabled()) {
                  Canvas.startShake(0, 0, 500);
               }

               var1[var0].textField2 = onOffValue(Sound.isVibrateEnabled());
               break;
            case 16:
               showDetailsMenu();
               break;
            case 17:
               Game.spawnParam = 0;
               Player.removeStatusEffect(12);
               Canvas.loadMap(1 + var1[selectedIndex].param, false);
               break;
            case 18:
               saveIndexes(1);
               if (var0 > 1) {
                  Player.selectWeapon((short)var1[selectedIndex].param);
                  returnToGame();
               }
               break;
            case 19:
               Text.setLanguage(var1[var0].param);
               back();
               break;
            case 20:
            case 21:
               boolean var7 = Player.useItem((short)menuParam);
               if (Game.animatingEffects != 0) {
                  returnToGame();
               } else {
                  Game.snapMonsters(true);
                  Game.snapLerpSprites(-1);
                  if (Canvas.state == 1) {
                     if (!var7) {
                        if (menuParam == 22) {
                           gotoMenu(78);
                        } else if (menuParam >= 16 && menuParam < 18) {
                           gotoMenu(76);
                        } else {
                           gotoMenu(77);
                        }
                     } else {
                        back();
                     }
                  }
               }
               break;
            case 22:
               startGame(false);
               break;
            case 23:
               Canvas.backToMain(false);
               if (Canvas.isChickenKicking) {
                  Canvas.endKickingGame();
                  setMenu(24);
               }
               break;
            case 24:
               if (menu == 73) {
                  saveIndexes(2);
               } else if (menu == 74) {
                  saveIndexes(3);
               } else if (menu == 70) {
                  saveIndexes(4);
               }

               menuParam = (byte)var1[selectedIndex].param;
               gotoMenu(75);
               break;
            case 25:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 113:
            default:
               App.Error(new Exception("Unhandled Menu Action"), var1[selectedIndex].action);
               break;
            case 26:
               Canvas.saveState(7, (short)5, (short)179);
               break;
            case 27:
               popMenu(poppedIdx);
               back();
               break;
            case 28:
               if (var1[var0].param == 0) {
                  Sound.soundStop();
                  CardGames.initGame(var1[var0].param, (ScriptThread)null, false);
               } else if (var1[var0].param == 1) {
                  Canvas.kickingDir = 768;
                  Canvas.loadType = 3;
                  Canvas.loadMap(4, true);
               }
               break;
            case 29:
               menuParam = (byte)var1[selectedIndex].param;
               gotoMenu(54);
               break;
            case 30:
               medalsMap = var1[selectedIndex].param;
               gotoMenu(33);
               break;
            case 102:
               Player.giveAll();
               break;
            case 103:
               Game.givemap(0, 0, 32, 32);
               break;
            case 104:
               Player.noclip = !Player.noclip;
               setMenu(63);
               break;
            case 105:
               Game.disableAI = !Game.disableAI;
               setMenu(63);
               break;
            case 106:
               Player.enableHelp = !Player.enableHelp;
               Game.saveConfig();
               setMenu(63);
               break;
            case 107:
               Player.god = !Player.god;
               setMenu(63);
               break;
            case 108:
               Canvas.showLocation = !Canvas.showLocation;
               setMenu(63);
               break;
            case 109:
               var6 = Canvas.animFrames + 1;
               if (var6 > 15) {
                  var6 = 2;
               }

               Canvas.setAnimFrames(var6);
               Game.saveConfig();
               setMenu(63);
               break;
            case 110:
               Canvas.showSpeeds = !Canvas.showSpeeds;
               setMenu(63);
               break;
            case 111:
               Render.skipFlats = !Render.skipFlats;
               setMenu(63);
               break;
            case 112:
               Render.skipCull = !Render.skipCull;
               setMenu(63);
               break;
            case 114:
               Render.skipBSP = !Render.skipBSP;
               setMenu(63);
               break;
            case 115:
               Render.skipLines = !Render.skipLines;
               setMenu(63);
               break;
            case 116:
               Render.skipSprites = !Render.skipSprites;
               setMenu(63);
               break;
            case 117:
               Canvas.renderOnly = !Canvas.renderOnly;
               setMenu(63);
               break;
            case 118:
               Render.skipDecals = !Render.skipDecals;
               setMenu(63);
               break;
            case 119:
               Render.skip2DStretch = !Render.skip2DStretch;
               setMenu(63);
               break;
            case 120:
               setMenu(63);
               break;
            case 121:
               if (Render.renderMode == 0) {
                  Render.renderMode = 63;
               } else {
                  Render.renderMode >>= 1;
               }

               setMenu(63);
               break;
            case 122:
               Player.equipForLevel(Canvas.loadMapID);
               break;
            case 123:
               Combat.oneShotCheat = !Combat.oneShotCheat;
               setMenu(63);
               break;
            case 124:
               Canvas.enqueueHelpDialog((short)5, (short)311, (byte)-1);
               returnToGame();
               break;
            case 125:
               systemTest(var1[selectedIndex].param + 301);
            }

         }
      }
   }

   public MenuSystem() {
   }

   private static void infiniteLoop() {
      int var0 = 0;

      while(var0 >= 0) {
         ++var0;
         if (var0 < 0) {
            var0 = 0;
         }

         try {
            Thread.sleep(5000L);
         } catch (Exception var2) {
         }
      }

   }

   private static int infiniteRecursion(int[] var0) {
      int var10002 = var0[0]++;
      if (var0[0] > 0) {
         var0[0] = 0;
         return infiniteRecursion(var0);
      } else {
         return var0[0];
      }
   }

   public void run() {
      int var1 = 0;

      while(var1 == 0) {
         int[] var2 = new int[4];
         var1 = var2[8] / 4;

         try {
            Thread.sleep(5000L);
         } catch (Exception var4) {
         }
      }

   }

   private static void systemTest(int var0) {
      switch(var0) {
      case 301:
         infiniteLoop();
         break;
      case 302:
         int[] var4 = new int[1];
         App.time += infiniteRecursion(var4);
         break;
      case 303:
         Thread var1 = new Thread(new MenuSystem());
         var1.start();

         try {
            Thread.sleep(5000L);
            var1.join();
         } catch (Exception var3) {
         }
      }

   }

   private static final void startGame(boolean var0) {
      App.beginImageUnload();
      background = null;
      imgMainBG = null;
      App.endImageUnload();
      System.gc();
      if (var0) {
         Canvas.setLoadingBarText((short)-1, (short)-1);
         Game.removeState(true);
         Game.totalLevelTime = 0;
         Game.activeLoadType = 0;
         Canvas.loadType = 0;
         Canvas.loadMapID = 0;
         Canvas.lastMapID = 0;
         Player.reset();
         Player.totalDeaths = 0;
         Player.helpBitmask = 0;
         Player.invHelpBitmask = 0;
         Player.ammoHelpBitmask = 0;
         Player.weaponHelpBitmask = 0;
         Player.armorHelpBitmask = 0;
         Canvas.clearEvents();
         Canvas.setState(9);
      } else {
         Canvas.loadState(Canvas.getRecentLoadType(), (short)5, (short)177);
      }

   }

   private static final void SetYESNO(short var0, int var1, int var2, int var3) {
      Text var4 = Text.getLargeBuffer();
      Text.composeText((short)5, var0, var4);
      SetYESNO(var4, var1, var2, var3, 2, 0);
      var4.dispose();
   }

   private static final void SetYESNO(short var0, int var1, int var2, int var3, int var4, int var5) {
      Text var6 = Text.getLargeBuffer();
      Text.composeText((short)5, var0, var6);
      SetYESNO(var6, var1, var2, var3, var4, var5);
      var6.dispose();
   }

   private static final void SetYESNO(Text var0, int var1, int var2, int var3, int var4, int var5) {
      Text.resetTextArgs();
      if (var0.findFirstOf('\n', 0) >= 0) {
         int var6;
         int var7;
         for(var6 = 0; (var7 = var0.findFirstOf('\n', var6)) >= 0; var6 = var7 + 1) {
            Text.addTextArg(var0, var6, var7);
            addItem(getLastArgString(), EMPTY_TEXT, 9);
         }

         Text.addTextArg(var0, var6, var0.length());
         addItem(getLastArgString(), EMPTY_TEXT, 9);
      } else {
         Text.addTextArg(var0);
         addItem(getLastArgString(), EMPTY_TEXT, 9);
      }

      addItem(EMPTY_TEXT, EMPTY_TEXT, 9);
      addItem(Text.STRINGID((short)5, (short)180), EMPTY_TEXT, 8, var2, var3, EMPTY_TEXT);
      addItem(Text.STRINGID((short)5, (short)181), EMPTY_TEXT, 8, var4, var5, EMPTY_TEXT);
      if (var1 == 1) {
         selectedIndex = numItems - 2;
      } else {
         selectedIndex = numItems - 1;
      }

   }

   public static void LoadHelpResource(short var0) {
      scrollIndex = 0;
      selectedIndex = 0;
      Text var1 = Text.getLargeBuffer();
      Text.loadText(3);
      Text.composeText((short)3, var0, var1);
      Text.unloadText(3);
      var1.wrapText(menu >= 28 ? Canvas.ingameScrollWithBarMaxChars : Canvas.menuScrollWithBarMaxChars);
      LoadHelpItems(var1);
      var1.dispose();
   }

   private static final void LoadAbout() {
      Text.resetTextArgs();
      addItem(Text.STRINGID((short)5, (short)182), EMPTY_TEXT, 72);
      addItem(Text.STRINGID((short)5, (short)128), EMPTY_TEXT, 0);
      Text var0 = Text.getSmallBuffer();
      Text.composeText((short)5, (short)195, var0);
      Text.addTextArg(var0);
      var0.dispose();
      addItem(getLastArgString(), EMPTY_TEXT, 0);
      String var1 = App.app.getAppProperty("MIDlet-Version");
      if (var1 != null && var1.length() != 0) {
         Text.addTextArg(var1);
      } else {
         Text.addTextArg(0);
      }

      addItem(getLastArgString(), EMPTY_TEXT, 0);
      addItem(EMPTY_TEXT, EMPTY_TEXT, 1);
      LoadHelpResource((short)13);
   }

   private static final void FillRanking() {
      int var2 = Player.calcScore();
      addItem(EMPTY_TEXT, EMPTY_TEXT, 0);
      if (Game.difficulty == 1) {
         addItem(Text.STRINGID((short)5, (short)183), EMPTY_TEXT, 72);
      } else if (Game.difficulty == 2) {
         addItem(Text.STRINGID((short)5, (short)184), EMPTY_TEXT, 72);
         if (var2 < 16000) {
            var2 = 16000;
         } else if (var2 < 19000) {
            var2 = 19000;
         } else if (var2 < 24000) {
            var2 = 24000;
         }
      } else {
         addItem(Text.STRINGID((short)5, (short)185), EMPTY_TEXT, 72);
         if (var2 < 16000) {
            var2 = 19000;
         } else if (var2 < 19000) {
            var2 = 24000;
         }
      }

      byte var0;
      byte var1;
      if (var2 >= 24000) {
         var0 = 22;
         var1 = 23;
      } else if (var2 >= 19000) {
         var0 = 20;
         var1 = 21;
      } else if (var2 >= 16000) {
         var0 = 18;
         var1 = 19;
      } else {
         var0 = 16;
         var1 = 17;
      }

      Text.resetTextArgs();
      addItem(EMPTY_TEXT, EMPTY_TEXT, 1);
      addItem(Text.STRINGID((short)5, (short)186), EMPTY_TEXT, 8);
      Text.addTextArg(var2);
      addItem(getLastArgString(), EMPTY_TEXT, 8);
      addItem(Text.STRINGID((short)5, (short)187), EMPTY_TEXT, 8);
      addItem(Text.STRINGID((short)1, var0), EMPTY_TEXT, 8);
      addItem(EMPTY_TEXT, EMPTY_TEXT, 1);
      Text var3 = Text.getLargeBuffer();
      Text.composeText((short)1, var1, var3);
      var3.wrapText(Canvas.menuScrollWithBarMaxChars);
      LoadHelpItems(var3, 8);
      var3.dispose();
   }

   private static final void LoadRecipes() {
      numItems = 0;
      type = 1;
      items[numItems++].Set(Text.STRINGID((short)5, (short)151), EMPTY_TEXT, 9);
      items[numItems++].Set(EMPTY_TEXT, EMPTY_TEXT, 73);
      Text.resetTextArgs();

      for(int var1 = 5; var1 < 15; ++var1) {
         if ((Player.cocktailDiscoverMask & 1 << var1 - 5) != 0) {
            EntityDef var0 = EntityDef.find(6, 0, var1);
            Text var2 = Text.getSmallBuffer();
            Canvas.recipeToString(var1 - 5, var2);
            Text.addTextArg(var2);
            var2.dispose();
            addItem(Text.STRINGID((short)2, var0.name), getLastArgString(), 32, 11, var1, Text.STRINGID((short)2, var0.description));
         }
      }

      if (numItems == 2) {
         type = 7;
         items[numItems++].Set(Text.STRINGID((short)5, (short)152), EMPTY_TEXT, 9);
      }

   }

   private static final void LoadNotebook() {
      detailsHelpText = Text.getLargeBuffer();
      type = 7;
      boolean var0 = false;
      items[numItems++].Set(Render.mapNameField, EMPTY_TEXT, 8);
      items[numItems++].Set(EMPTY_TEXT, EMPTY_TEXT, 73);

      int var1;
      for(var1 = 0; var1 < Player.numNotebookIndexes; ++var1) {
         detailsHelpText.setLength(0);
         byte var2 = 0;
         Text.composeText(Canvas.loadMapStringID, Player.notebookIndexes[var1], detailsHelpText);
         if (Player.isQuestFailed(var1)) {
            items[numItems++].Set(Text.STRINGID((short)5, (short)193), EMPTY_TEXT, 0);
            var2 = 4;
         } else if (Player.isQuestDone(var1)) {
            detailsHelpText.insert('\u0087', 0);
            detailsHelpText.insert(" ", 1);
         }

         detailsHelpText.wrapText(Canvas.ingameScrollWithBarMaxChars);
         LoadHelpItems(detailsHelpText, var2);
         items[numItems++].Set(EMPTY_TEXT, EMPTY_TEXT, 0);
      }

      var1 = (Canvas.displayRect[3] - 26) / 12;
      selectedIndex = scrollIndex = numItems - var1;
      if (selectedIndex < 0 || scrollIndex < 0) {
         selectedIndex = 0;
         scrollIndex = 0;
      }

      detailsHelpText.dispose();
   }

   private static final void LoadHelpItems(Text var0) {
      LoadHelpItems(var0, 0);
   }

   private static final void LoadHelpItems(Text var0, int var1) {
      int var2;
      int var3;
      for(var2 = 0; (var3 = var0.findFirstOf('|', var2)) >= 0; var2 = var3 + 1) {
         if (var3 > var2 && var0.charAt(var2) == '#') {
            Text.addTextArg(var0, var2 + 1, var3);
            addItem(getLastArgString(), EMPTY_TEXT, 74 | var1);
         } else {
            Text.addTextArg(var0, var2, var3);
            addItem(getLastArgString(), EMPTY_TEXT, 2 | var1);
         }
      }

      var3 = var0.length();
      if (var2 < var3) {
         if (var0.charAt(var2) == '#') {
            Text.addTextArg(var0, var2, var3);
            addItem(getLastArgString(), EMPTY_TEXT, 74 | var1);
         } else {
            Text.addTextArg(var0, var2, var0.length());
            addItem(getLastArgString(), EMPTY_TEXT, 2 | var1);
         }
      }

   }

   public static void buildFraction(int var0, int var1, Text var2) {
      var2.setLength(0);
      if (var0 < 0) {
         var2.append('-');
      }

      var2.append(var0);
      var2.append("/");
      if (var1 < 0) {
         var2.append('-');
      }

      var2.append(var1);
   }

   private static void buildModStat(int var0, int var1, Text var2) {
      var2.setLength(0);
      var2.append(var0);
      if (var1 != 0) {
         var2.append(var1 > 0 ? "(+" : "(-");
         var2.append(var1);
         var2.append(')');
      }
   }

   private static final void fillStatus(boolean var0, boolean var1, boolean var2) {
      Text.resetTextArgs();
      Text var3 = Text.getLargeBuffer();
      if (var1) {
         buildFraction(Player.ce.getStat(0), Player.ce.getStat(1), var3);
         Text.addTextArg(var3);
         buildFraction(Player.ce.getStat(2), 200, var3);
         Text.addTextArg(var3);
         Text.addTextArg(Player.level);
         Text.addTextArg(Player.currentXP);
         Text.addTextArg(Player.nextLevelXP);
         int var4 = Player.ce.getStat(3) - Player.baseCe.getStat(3);
         buildModStat(Player.baseCe.getStat(3), var4, var3);
         Text.addTextArg(var3);
         var4 = Player.ce.getStat(4) - Player.baseCe.getStat(4);
         buildModStat(Player.baseCe.getStat(4), var4, var3);
         Text.addTextArg(var3);
         var4 = Player.ce.getStat(5) - Player.baseCe.getStat(5);
         buildModStat(Player.baseCe.getStat(5), var4, var3);
         Text.addTextArg(var3);
         var4 = Player.ce.getStat(6) - Player.baseCe.getStat(6);
         buildModStat(Player.baseCe.getStat(6), var4, var3);
         Text.addTextArg(var3);
      }

      if (var0) {
         if (var0 && !var1 && !var2) {
            Text.addTextArg((short)5, (short)(Render.mapNameField & 1023));
         }

         Player.formatTime(Game.totalLevelTime + (App.gameTime - Game.curLevelTime), var3);
         Text.addTextArg(var3);
         getMedalFraction(1, false, var3);
         Text.addTextArg(var3);
         getMedalFraction(2, false, var3);
         Text.addTextArg(var3);
         getMedalFraction(0, false, var3);
         Text.addTextArg(var3);
         getMedalFraction(3, false, var3);
         Text.addTextArg(var3);
         getMedalFraction(4, false, var3);
         Text.addTextArg(var3);
         Text.addTextArg(Player.moves);
      }

      if (var2) {
         var3.setLength(0);
         Player.formatTime(Player.totalTime + (App.gameTime - Player.playTime), var3);
         Text.addTextArg(var3);
         Text.addTextArg(Player.totalMoves);
         Text.addTextArg(Player.totalDeaths);
         Text.addTextArg(Player.counters[0]);
         Text.addTextArg(Player.counters[6]);
         Text.addTextArg(Player.counters[7]);
         Text.addTextArg(Player.counters[1]);
         Text.addTextArg(Player.counters[2]);
         Text.addTextArg(Player.counters[3]);
      }

      var3.dispose();
   }

   private static final void saveIndexes(int var0) {
      indexes[var0 * 2] = selectedIndex;
      indexes[var0 * 2 + 1] = scrollIndex;
   }

   private static final void loadIndexes(int var0) {
      selectedIndex = indexes[var0 * 2];
      scrollIndex = indexes[var0 * 2 + 1];
   }

   public static final void showDetailsMenu() {
      MenuItem var0 = items[selectedIndex];
      detailsHelpText = Text.getLargeBuffer();
      detailsTitle = Text.getLargeBuffer();
      detailsDef = null;
      switch(menu) {
      case 70:
         saveIndexes(4);
         detailsDef = EntityDef.find(6, 0, var0.param);
         break;
      case 71:
         saveIndexes(1);
         detailsDef = EntityDef.find(6, 1, var0.param);
         break;
      case 72:
         saveIndexes(0);
         if (var0.param == -1) {
            Text.composeText((short)2, (short)190, detailsTitle);
            Text.composeText((short)2, (short)191, detailsHelpText);
            gotoMenu(69);
            return;
         }

         detailsDef = EntityDef.find(6, 0, var0.param);
         break;
      case 73:
         saveIndexes(2);
      case 46:
         detailsDef = EntityDef.find(6, 0, var0.param);
         break;
      case 74:
         saveIndexes(3);
         detailsDef = EntityDef.find(6, 0, var0.param);
      }

      if (detailsDef != null) {
         Text.composeText((short)2, detailsDef.longName, detailsTitle);
         Text.composeText((short)2, detailsDef.description, detailsHelpText);
         if (menu == 73) {
            int var1 = var0.param - 5;
            if (var1 >= 0 && (Player.cocktailDiscoverMask & 1 << var1) != 0) {
               Text var2 = Text.getSmallBuffer();
               Text.composeText((short)1, (short)50, var2);
               Text.composeText((short)1, (short)138, var2);
               var2.append(' ');
               Canvas.recipeToString(var1, var2);
               detailsHelpText.append(var2);
               var2.dispose();
            }
         }

         gotoMenu(69);
      }
   }

   private static final void addItem(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (numItems == 50) {
         App.Error(100);
      }

      items[numItems++].Set(var0, var1, var2, var3, var4, var5);
   }

   private static final void addItem(int var0, int var1, int var2) {
      addItem(var0, var1, var2, 0, 0, EMPTY_TEXT);
   }

   private static final void loadMenuItems(int var0) {
      loadMenuItems(var0, 0, -1);
   }

   private static final void loadMenuItems(int var0, int var1, int var2) {
      int var3 = menuData.length;

      for(int var6 = 0; var6 < var3; ++var6) {
         if ((menuData[var6] & 255) == var0) {
            int var4 = menuData[var6];
            type = (var4 & -16777216) >> 24;
            int var7 = 0;
            if (var6 != 0) {
               var7 = (menuData[var6 - 1] & 16776960) >> 8;
            }

            if (var2 == -1) {
               var2 = (((var4 & 16776960) >> 8) - var7) / 2;
               var2 -= var1;
            }

            var7 += var1 * 2;

            for(int var8 = 0; var8 < var2; ++var8) {
               var4 = menuItems[var7 + 0];
               int var5 = menuItems[var7 + 1];
               addItem(Text.STRINGID((short)5, (short)(var4 >> 16)), Text.STRINGID((short)5, (short)0), (short)(var4 & '\uffff'), (short)((var5 & '\uff00') >> 8), (short)(var5 & 255), Text.STRINGID((short)5, (short)((var5 & -65536) >> 16)));
               var7 += 2;
            }

            return;
         }
      }

      App.Error(29);
   }

   private static final int onOffValue(boolean var0) {
      return Text.STRINGID((short)5, (short)(var0 ? 129 : 130));
   }

   public static final void leaveOptionsMenu() {
      if (Sound.isVibrateEnabled() != oldVibrateSetting || Sound.isSoundEnabled() != oldSoundSetting || Text.defaultLanguage != oldLanguageSetting) {
         Canvas.pacifierX = Canvas.SCR_CX - 75 + 9;
         Canvas.repaintFlags |= 1;
         Canvas.setLoadingBarText((short)5, (short)194);
         Canvas.updateLoadingBar(true);
         Game.saveConfig();
      }

   }

   public static int getStackCount() {
      return stackCount;
   }

   public static void clearStack() {
      stackCount = 0;
   }

   public static void pushMenu(int var0, int var1) {
      if (stackCount + 1 >= 10) {
         App.Error(new Exception("Menu stack is full."), 0);
      }

      menuIdxStack[stackCount] = (byte)var1;
      menuStack[stackCount++] = var0;
   }

   public static int popMenu(int[] var0) {
      if (stackCount - 1 < 0) {
         App.Error(new Exception("Menu stack is empty"), 0);
      }

      var0[0] = menuIdxStack[stackCount - 1];
      return menuStack[--stackCount];
   }

   public static int peekMenu() {
      return stackCount - 1 < 0 ? -1 : menuStack[stackCount - 1];
   }

   private static int getLastArgString() {
      int var0 = 0;
      if (Text.numTextArgs > 0) {
         var0 = Text.STRINGID((short)5, (short)(210 + (Text.numTextArgs - 1)));
      }

      return var0;
   }

   private static void loadMedalItems(int var0, boolean var1, boolean var2, boolean var3) {
      int var4 = Text.STRINGID((short)4, (short)0);
      Text var5 = Text.getSmallBuffer();
      Text var6 = Text.getSmallBuffer();
      Text.resetTextArgs();
      var5.setLength(0);
      Text.composeText((short)5, Game.levelNames[var0], var5);
      Text.addTextArg(var5);
      byte[] var7 = Player.getLevelMedals(var0 + 1, var1);
      boolean var8 = false;
      int var9 = 0;

      for(boolean var10 = false; var7[var9] != -1; var9 += 2) {
         int var13 = var9 / 2;
         if (var13 >= 5 && !var8) {
            addItem(Text.STRINGID((short)5, (short)199), EMPTY_TEXT, 73, 0, 0, EMPTY_TEXT);
            addItem(EMPTY_TEXT, EMPTY_TEXT, 1);
            var8 = true;
         }

         int var11 = var7[var9 + 1] == 0 ? 4096 : 2048;
         var11 |= 1;
         var5.setLength(0);
         var6.setLength(0);
         Text.composeText((short)4, (short)var7[var9], var6);
         var6.wrapText(20);
         int var12 = var6.findFirstOf('|', 0);
         if (var12 != -1) {
            var5.setLength(0);
            var6.substring(var5, 0, var12);
            Text.addTextArg(var5);
            addItem(getLastArgString(), EMPTY_TEXT, var11, 0, 0, EMPTY_TEXT);
            var5.setLength(0);
            var6.substring(var5, var12 + 1);
            Text.addTextArg(var5);
            addItem(getLastArgString(), EMPTY_TEXT, var11, 0, 1, EMPTY_TEXT);
            if (var13 < 5 && var2) {
               var6.setLength(0);
               Text.composeText((short)5, (short)200, var6);
               getMedalFraction(var9 / 2, var7[var9 + 1] == 1, var5);
               var6.append(var5);
               setItemsFromText(numItems, var6, Canvas.ingameScrollWithBarMaxChars, var11, 3);
            }
         } else {
            addItem(Text.STRINGID((short)4, (short)var7[var9]), EMPTY_TEXT, var11, 0, 0, EMPTY_TEXT);
            var13 = var9 / 2;
            if (var13 < 5 && var2) {
               var6.setLength(0);
               Text.composeText((short)5, (short)200, var6);
               getMedalFraction(var9 / 2, var7[var9 + 1] == 1, var5);
               var6.append(var5);
               setItemsFromText(numItems, var6, Canvas.ingameScrollWithBarMaxChars, var11, 1);
            } else {
               addItem(var4, EMPTY_TEXT, var11, 0, 1, EMPTY_TEXT);
            }
         }

         addItem(EMPTY_TEXT, EMPTY_TEXT, 1);
      }

      if (var3) {
         var5.setLength(0);
         Text.composeText((short)5, (short)201, var5);
         setItemsFromText(numItems, var5, Canvas.ingameScrollWithBarMaxChars, 8, 0);
         addItem(EMPTY_TEXT, EMPTY_TEXT, 1, 0, 1, EMPTY_TEXT);
         if (Text.defaultLanguage == 4) {
            addItem(Text.STRINGID((short)5, (short)65), EMPTY_TEXT, 9);
            addItem(Text.STRINGID((short)5, (short)127), EMPTY_TEXT, 9);
         } else {
            addItem(Text.STRINGID((short)5, (short)127), EMPTY_TEXT, 9);
            addItem(Text.STRINGID((short)5, (short)65), EMPTY_TEXT, 9);
         }
      }

      var5.dispose();
      var6.dispose();
   }

   private static final void getMedalFraction(int var0, boolean var1, Text var2) {
      switch(var0) {
      case 0:
         buildFraction(Game.lootFound, Game.totalLoot, var2);
         break;
      case 1:
         buildFraction(Game.mapSecretsFound, Game.totalSecrets, var2);
         break;
      case 2:
         Player.fillMonsterStats();
         if (var1) {
            buildFraction(Player.monsterStats[1], Player.monsterStats[1], var2);
         } else {
            buildFraction(Player.monsterStats[0], Player.monsterStats[1], var2);
         }
         break;
      case 3:
         buildFraction(Game.destroyedObj, Game.numDestroyableObj, var2);
         break;
      case 4:
         buildFraction(Game.scriptStateVars[9], Game.scriptStateVars[10], var2);
      }

   }

   private static final void loadBookList() {
      boolean var0 = false;
      Text var1 = Text.getLargeBuffer();

      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 8; ++var3) {
            if ((Player.foundBooks[var2] & 1 << var3) != 0) {
               var1.setLength(0);
               int var4 = var2 * 8 + var3;
               Text.composeText((short)0, (short)Player.bookMap[var4 * 4], var1);
               var1.wrapText(Canvas.ingameScrollWithBarMaxChars - 1, 1, '|');
               int var5 = var1.findFirstOf('|', 0);
               if (var5 != -1) {
                  var1.setLength(var5);
                  var1.append('\u0085');
               }

               Text.addTextArg(var1);
               var0 = true;
               addItem(getLastArgString(), EMPTY_TEXT, 0, 29, var4, Text.STRINGID((short)5, (short)203));
            }
         }
      }

      var1.dispose();
      if (!var0) {
         addItem(Text.STRINGID((short)5, (short)1), EMPTY_TEXT, 9, 0, 0, EMPTY_TEXT);
      }

   }

   private static final void loadBook(int var0) {
      int var1 = Player.bookIndex = var0 * 4;
      Text.resetTextArgs();
      Text var2 = Text.getLargeBuffer();
      Text.composeText((short)0, (short)Player.bookMap[var1 + 1], var2);
      if (Player.showBookStat) {
         Text.composeText((short)1, (short)50, var2);
         byte var3 = Player.bookMap[var1 + 2];
         if (var3 != -1) {
            Text var4 = Text.getSmallBuffer();
            Text var5 = Text.getSmallBuffer();
            if (Player.bookMap[var1 + 3] >= 0) {
               var4.append('+');
            } else {
               var4.append('-');
            }

            Text.addTextArg(Player.bookMap[var1 + 3]);
            Player.statusToString(var3, var5);
            Text.addTextArg(var5);
            Text.composeText((short)1, (short)179, var4);
            Text.resetTextArgs();
            Text.addTextArg(var4);
            byte var6 = 30;
            if (var3 == 2) {
               var6 = 20;
            } else if (var3 == 9) {
               var6 = 10;
            } else if (var3 == 1) {
               var6 = 10;
            } else if (var3 == 11) {
               var6 = 6;
            }

            Text.addTextArg(var6);
            Text.composeText((short)1, (short)181, var2);
            Player.showBookStat = false;
            var4.dispose();
            var5.dispose();
         } else {
            Text.composeText((short)1, (short)180, var2);
         }
      }

      var2.wrapText(Canvas.ingameScrollWithBarMaxChars, '|');
      Text.resetTextArgs();
      Text.addTextArg(var2);
      int var7 = var2.getNumLines();
      int var8 = 0;

      for(int var9 = Canvas.menuRect[3] / 14; var9 > 0; ++var8) {
         var8 = var2.findFirstOf('|', var8);
         if (var8 == -1) {
            var8 = var2.length();
            break;
         }

         --var9;
      }

      int var10 = (var7 & 63) << 26 | 0 | (var8 & 1023) << 10 | 0;
      items[numItems++].Set(getLastArgString(), EMPTY_TEXT, 256, 0, var10, EMPTY_TEXT);
      var2.dispose();
      scrollIndex = 0;
   }

   public static final void nextGamePage() {
      if (moreGamesPage + 1 < 3) {
         ++moreGamesPage;
         App.beginImageUnload();
         background = null;
         App.endImageUnload();
         App.beginImageLoading();
         background = App.loadImageFromIndex(23 + moreGamesPage);
         App.endImageLoading();
         items[3].textField = Text.STRINGID((short)5, (short)(207 + moreGamesPage));
         System.gc();
         MenuItem var10000;
         if (moreGamesPage == 2) {
            Canvas.setRightSoftKey((short)1, (short)52);
            var10000 = items[3];
            var10000.flags &= -8193;
         } else {
            if (moreGamesPage != 0) {
               var10000 = items[3];
               var10000.flags |= 16384;
            }

            var10000 = items[3];
            var10000.flags |= 8192;
         }
      }

      Canvas.clearEvents();
   }

   public static final void prevGamePage() {
      if (moreGamesPage - 1 >= 0) {
         --moreGamesPage;
         App.beginImageUnload();
         background = null;
         App.endImageUnload();
         App.beginImageLoading();
         background = App.loadImageFromIndex(23 + moreGamesPage);
         App.endImageLoading();
         items[3].textField = Text.STRINGID((short)5, (short)(207 + moreGamesPage));
         MenuItem var10000;
         if (moreGamesPage == 0) {
            var10000 = items[3];
            var10000.flags &= -16385;
         } else {
            if (moreGamesPage != 2) {
               var10000 = items[3];
               var10000.flags |= 8192;
            }

            var10000 = items[3];
            var10000.flags |= 16384;
         }

         System.gc();
         Canvas.setRightSoftKey((short)1, (short)57);
      }

      Canvas.clearEvents();
   }
}
