package com.openxu.core.utils.toasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.openxu.core.R;
import com.openxu.core.base.BaseApplication;


/**
 * This file is part of FToast.
 * <p>
 * FToast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * FToast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with FToast.  If not, see <http://www.gnu.org/licenses/>.
 */

@SuppressLint("InflateParams")
public class FToast {

    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;

    private static final int textSizeDef = 14; // in SP
    private static final boolean tintIconDef = true;
    private static final boolean allowQueueDef = false;

    private static int textSize = textSizeDef; // in SP

    private static boolean tintIcon = tintIconDef;
    private static boolean allowQueue = allowQueueDef;

    private static Toast lastToast = null;

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private FToast() {
        // avoiding instantiation
    }

    private static Context context;
    static {
        context = BaseApplication.getApplication().getApplicationContext();
    }
   
    public static void normal(@StringRes int message) {
        normal(context.getString(message), Toast.LENGTH_SHORT, null, false);
    }
    public static void normal(@NonNull CharSequence message) {
        normal(message, Toast.LENGTH_SHORT, null, false);
    }
    public static void normal(@StringRes int message, Drawable icon) {
        normal(context.getString(message), Toast.LENGTH_SHORT, icon, true);
    }
    public static void normal(@NonNull CharSequence message, Drawable icon) {
        normal(message, Toast.LENGTH_SHORT, icon, true);
    }
    public static void normal(@StringRes int message, int duration) {
        normal(context.getString(message), duration, null, false);
    }
    public static void normal(@NonNull CharSequence message, int duration) {
        normal(message, duration, null, false);
    }
    public static void normal(@StringRes int message, int duration,
                               Drawable icon) {
        normal(context.getString(message), duration, icon, true);
    }
    public static void normal(@NonNull CharSequence message, int duration,
                               Drawable icon) {
        normal(message, duration, icon, true);
    }
    public static void normal(@StringRes int message, int duration,
                               Drawable icon, boolean withIcon) {
        custom(context.getString(message), icon, ToastyUtils.getColor(context, R.color.toast_normalColor),
                ToastyUtils.getColor(context, R.color.toast_defaultTextColor), duration, withIcon, true);
    }
    public static void normal(@NonNull CharSequence message, int duration,
                               Drawable icon, boolean withIcon) {
        custom(message, icon, ToastyUtils.getColor(context, R.color.toast_normalColor),
                ToastyUtils.getColor(context, R.color.toast_defaultTextColor), duration, withIcon, true);
    }
   
    
    public static void warning(@StringRes int message) {
        warning(context.getString(message), Toast.LENGTH_SHORT, true);
    }
    public static void warning(@NonNull CharSequence message) {
        warning(message, Toast.LENGTH_SHORT, true);
    }
    public static void warning(@StringRes int message, int duration) {
        warning(context.getString(message), duration, true);
    }
    public static void warning(@NonNull CharSequence message, int duration) {
        warning(message, duration, true);
    }
    public static void warning(@StringRes int message, int duration, boolean withIcon) {
        custom(context.getString(message), ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_error_outline_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_warningColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }
    public static void warning(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_error_outline_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_warningColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }

    
    public static void info(@StringRes int message) {
        info(context.getString(message), Toast.LENGTH_SHORT, true);
    }
    public static void info(@NonNull CharSequence message) {
        info(message, Toast.LENGTH_SHORT, true);
    }
    public static void info(@StringRes int message, int duration) {
        info(context.getString(message), duration, true);
    }
    public static void info(@NonNull CharSequence message, int duration) {
        info(message, duration, true);
    }
    public static void info(@StringRes int message, int duration, boolean withIcon) {
        custom(context.getString(message), ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_info_outline_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_infoColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }
    public static void info(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_info_outline_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_infoColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }

   
    public static void success(@StringRes int message) {
        success(context.getString(message), Toast.LENGTH_SHORT, true);
    }
    public static void success(@NonNull CharSequence message) {
        success(message, Toast.LENGTH_SHORT, true);
    }
    public static void success(@StringRes int message, int duration) {
        success(context.getString(message), duration, true);
    }
    public static void success(@NonNull CharSequence message, int duration) {
        success(message, duration, true);
    }
    public static void success(@StringRes int message, int duration, boolean withIcon) {
        custom(context.getString(message), ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_check_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_successColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }
    public static void success(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_check_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_successColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }

   
    public static void error(@StringRes int message) {
        error(context.getString(message), Toast.LENGTH_SHORT, true);
    }
    public static void error(@NonNull CharSequence message) {
        error(message, Toast.LENGTH_SHORT, true);
    }
    public static void error(@StringRes int message, int duration) {
        error(context.getString(message), duration, true);
    }
    public static void error(@NonNull CharSequence message, int duration) {
        error(message, duration, true);
    }
    public static void error(@StringRes int message, int duration, boolean withIcon) {
        custom(context.getString(message), ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_clear_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_errorColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }
    public static void error(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_clear_white_24dp),
                ToastyUtils.getColor(context, R.color.toast_errorColor), ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, true);
    }

   
    public static void custom(@StringRes int message, Drawable icon,
                               int duration, boolean withIcon) {
        custom(context.getString(message), icon, -1, ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, false);
    }
    public static void custom(@NonNull CharSequence message, Drawable icon,
                               int duration, boolean withIcon) {
        custom(message, icon, -1, ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, false);
    }
    public static void custom(@StringRes int message, @DrawableRes int iconRes,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        custom(context.getString(message), ToastyUtils.getDrawable(context, iconRes),
                tintColor, ToastyUtils.getColor(context, R.color.toast_defaultTextColor), duration, withIcon, shouldTint);
    }
    public static void custom(@NonNull CharSequence message, @DrawableRes int iconRes,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        custom(message, ToastyUtils.getDrawable(context, iconRes),
                tintColor, ToastyUtils.getColor(context, R.color.toast_defaultTextColor), duration, withIcon, shouldTint);
    }
    public static void custom(@StringRes int message, Drawable icon,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        custom(context.getString(message), icon, tintColor, ToastyUtils.getColor(context, R.color.toast_defaultTextColor),
                duration, withIcon, shouldTint);
    }
    public static void custom(@StringRes int message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        custom(context.getString(message), icon, tintColor, textColor,
                duration, withIcon, shouldTint);
    }

    /**
     * @param message Toast文字内容
     * @param icon icon图片
     * @param tintColor 背景色
     * @param textColor 文字颜色
     * @param duration 显示时间
     * @param withIcon 是否显示icon
     * @param shouldTint
     * @return
     */
    @SuppressLint("ShowToast")
    public static void custom(@NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.core_toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.lib_toast_ic_frame);
        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            ToastyUtils.setBackground(toastIcon, tintIcon ? ToastyUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);

        if (!allowQueue){
            if (lastToast != null) {
                lastToast.cancel();
            }
            lastToast = currentToast;
        }
        currentToast.show();
    }

    public static class Config {
        private Typeface typeface = FToast.currentTypeface;
        private int textSize = FToast.textSize;

        private boolean tintIcon = FToast.tintIcon;
        //是否允许排队，true:先后一个个展示   false：前一个需要展示2秒，但在1s的时候有新的Toast将会覆盖前一个
        private boolean allowQueue = true;

        private Config() {
            // avoiding instantiation
        }

       
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            FToast.currentTypeface = LOADED_TOAST_TYPEFACE;
            FToast.textSize = textSizeDef;
            FToast.tintIcon = tintIconDef;
            FToast.allowQueue = allowQueueDef;
        }

       
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

       
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

       
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

       
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public void apply() {
            FToast.currentTypeface = typeface;
            FToast.textSize = textSize;
            FToast.tintIcon = tintIcon;
            FToast.allowQueue = allowQueue;
        }
    }
}
