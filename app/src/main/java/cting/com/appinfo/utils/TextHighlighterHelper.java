package cting.com.appinfo.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Highlights the text in a text field.
 */
public class TextHighlighterHelper {
    private final static boolean DEBUG = false;

    private CharacterStyle[] mStyle;

    public TextHighlighterHelper() {
        initDefaultStyles();
    }

    public TextHighlighterHelper(CharacterStyle[] characterStyles) {
        if (characterStyles == null) {
            initDefaultStyles();
        } else {
            mStyle = characterStyles;
        }
    }

    private void initDefaultStyles() {
        mStyle = new CharacterStyle[]{
                new StyleSpan(Typeface.BOLD),
                new ForegroundColorSpan(Color.RED)
        };
    }

    public static boolean containKeyword(@NonNull String text, @NonNull String keyword) {
        return !TextUtils.isEmpty(text)
                && !TextUtils.isEmpty(keyword)
                && text.toLowerCase().contains(keyword);
    }

    public void updateHighliteInText(@NonNull TextView view, @NonNull String text, @NonNull String keyword) {
        view.setText(applyHighlight(text, keyword));
    }

    public SpannableString applyHighlight(@NonNull String text, @NonNull String keyword) {
        SpannableString highlightStr = new SpannableString(text);

        Pattern p = Pattern.compile(keyword.toLowerCase());
        Matcher m = p.matcher(new SpannableString(text.toLowerCase()));
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            for (CharacterStyle style : mStyle) {
                highlightStr.setSpan(style, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return highlightStr;
    }

    public SpannableString applyHighlights(String text, String[] keyword) {
        SpannableString spannableString = new SpannableString(text);
        for (String s : keyword) {
            applyHighlight(text, s);
        }
        return spannableString;
    }


}