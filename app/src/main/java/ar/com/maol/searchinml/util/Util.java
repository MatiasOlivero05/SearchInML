package ar.com.maol.searchinml.util;

import android.content.Context;
import android.content.res.Resources;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ar.com.maol.searchinml.R;

public class Util {

    public static String getStringCondition(Context context, String condition) {
        StringBuilder sbCondition = new StringBuilder().append(context.getResources().getString(R.string.condition)).append(": ");
        if (condition != null && !condition.isEmpty()) {
            if(condition.equals(Constants.CONDITION_NEW)){
                condition = sbCondition.append(context.getResources().getString(R.string.nuevo)).toString();
            }else {
                if(condition.equals(Constants.CONDITION_USED)){
                    condition = sbCondition.append(context.getResources().getString(R.string.used)).toString();
                }else {
                    condition = sbCondition.append(context.getResources().getString(R.string.unknown)).toString();
                }
            }
        }else {
            condition = sbCondition.append(context.getResources().getString(R.string.unknown)).toString();
        }
        return condition;
    }

    public static String getStringCurrencyAndPriceFormated(String currency, double price) {
        if (currency != null && !currency.isEmpty()) {
            currency = currency.equals(Constants.CURRENCY_ID_ARG) ? Constants.CURRENCY_SYMBOL_CURRENCY_ARG : currency;
        }else{
            currency = Resources.getSystem().getString(R.string.not_symbol_currency);
        }
        return new StringBuilder().append(currency).append(" ").append(getStringPriceFormated(price)).toString();
    }

    public static String getStringPriceFormated(double price) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("###,###,###.#", otherSymbols);
        return format.format(price);
    }
}
