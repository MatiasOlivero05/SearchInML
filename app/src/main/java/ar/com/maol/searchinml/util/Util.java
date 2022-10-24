package ar.com.maol.searchinml.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.models.SellerAddress;

public class Util {

    /**
     * Retorna un String con la condición del producto formateada para usuario final.
     * @param context necesario para acceder a los recursos String de la App.
     * @param condition como viene en la API.
     * @return String con la condición formateada.
     */
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

    /**
     * Retorna un String con la moneda y el precio del producto formateada para usuario final.
     * @param currency moneda como viene en la API.
     * @param price moneda como viene en la API.
     * @return String con la moneda y precio formateados.
     */
    public static String getStringCurrencyAndPriceFormated(String currency, double price) {
        if (currency != null && !currency.isEmpty()) {
            currency = currency.equals(Constants.CURRENCY_ID_ARG) ? Constants.CURRENCY_SYMBOL_CURRENCY_ARG : currency;
        }else{
            currency = Resources.getSystem().getString(R.string.not_symbol_currency);
        }
        return new StringBuilder().append(currency).append(" ").append(getStringPriceFormated(price)).toString();
    }

    /**
     * Retorna un String con el precio del producto formateada para usuario final con el catacter "," como separador de miles
     * y con el caracter "." como separador de decimales.
     * @param price precio como viene en la API
     * @return String con la moneda formateada.
     */
    public static String getStringPriceFormated(double price) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("###,###,###.#", otherSymbols);
        return format.format(price);
    }

    /**
     * Retorna un String con la localidad, provincia y país del vendedor del producto formateada para usuario final.
     * @param seller_address objeto como viene en la API.
     * @return String con la ubicación del vendedor formateada.
     */
    public static String getStringAdressFormated(SellerAddress seller_address) {
        if (seller_address != null
                && seller_address.getCity() != null && !seller_address.getCity().getName().isEmpty()
                && seller_address.getState() != null && !seller_address.getState().getName().isEmpty()
                && seller_address.getCountry() != null && !seller_address.getCountry().getName().isEmpty()) {

            StringBuilder sbSellerAddress = new StringBuilder().append(seller_address.getCity().getName()).append(" - ").append(seller_address.getState().getName()).append(" - ").append(seller_address.getCountry().getName());
            return sbSellerAddress.toString();
        } else {
            return "";
        }
    }

    /**
     * Retorna un AlertDialog.Builder para ser usado cuando se necesite indicar al usuario que se está realizando una operación de fondo.
     * @param context necesario para poder instanciar al AlertDialog.Builder
     * @return un AlertDialog.Builder.
     */
    public static AlertDialog.Builder getAlertDialogLoading(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progressbar_loading);
        return builder;
    }

    /**
     * Retorna Un AlertDialog con el botón OK, que solamente cierra el mismo. Muestra como título y mensaje los parámetros recibidos. Se debe utilizar para mostrar información simple.
     * @param context necesario para poder instanciar al AlertDialog.Builder
     * @param title título que se va a setear.
     * @param message mensaje que se va a setear.
     * @return AlertDialog.Builder con el título y mensaje recibido por parámetro con el botón OK.
     */
    public static AlertDialog.Builder getAlertDialogOk(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder;
    }

    /**
     * Se valida si es una URL valida
     * @param url la URL que se requiera validar
     * @return true en caso que sea un URL valida o o false en el caso que no lo sea.
     */
    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
