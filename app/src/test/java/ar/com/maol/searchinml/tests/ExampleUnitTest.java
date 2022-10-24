package ar.com.maol.searchinml.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;

import ar.com.maol.searchinml.models.City;
import ar.com.maol.searchinml.models.Country;
import ar.com.maol.searchinml.models.SellerAddress;
import ar.com.maol.searchinml.models.State;
import ar.com.maol.searchinml.util.Util;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test_funcion_valida_url() {
        assertTrue(Util.isValidURL("https://api.mercadolibre.com/sites/MLA/search?q=zapatillas"));
    }

    @Test
    public void test_formato_precio_pais_argentina_isCorrect() {
        assertEquals("$ 1.987.654,67", Util.getStringCurrencyAndPriceFormated("ARS", 1987654.67));
    }

    @Test
    public void test_formato_precio_otra_moneda_diferente_a_argentina_isCorrect() {
        assertEquals("USD 1.987.654,67", Util.getStringCurrencyAndPriceFormated("USD", 1987654.67));
    }

    @Test
    public void test_formato_adress_isCorrect() {
        String sJsonSellerAddress = "{\"id\":\"\",\"comment\":\"\",\"address_line\":\"\",\"zip_code\":\"\",\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"},\"state\":{\"id\":\"AR-S\",\"name\":\"Santa Fe\"},\"city\":{\"id\":null,\"name\":\"CAPITAN BERMUDEZ\"},\"latitude\":\"\",\"longitude\":\"\"}";
        SellerAddress sellerAddress = new Gson().fromJson(sJsonSellerAddress, SellerAddress.class);
        assertEquals("CAPITAN BERMUDEZ - Santa Fe - Argentina", Util.getStringAdressFormated(sellerAddress));
    }

}