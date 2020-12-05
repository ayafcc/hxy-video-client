//package cn.mahua.vod.ui.widget;
//
//import com.google.android.material.bottomnavigation.BottomNavigationItemView;
//import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.lang.reflect.Field;
//
//public class BottomNavigationViewHelper {
//
//    public static void disableShiftMode(BottomNavigationView navigationView) {
//
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//
//            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//                itemView.setShiftingMode(false);
//                itemView.setChecked(itemView.getItemData().isChecked());
//            }
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
