package ar.com.maol.searchinml.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private double price ;

    @SerializedName("currency_id")
    @Expose
    private String currency_id;

    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("permalink")
    @Expose
    private String permalink;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("accepts_mercadopago")
    @Expose
    private boolean accepts_mercadopago;

    @SerializedName("seller_address")
    @Expose
    private SellerAddress seller_address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isAccepts_mercadopago() {
        return accepts_mercadopago;
    }

    public void setAccepts_mercadopago(boolean accepts_mercadopago) {
        this.accepts_mercadopago = accepts_mercadopago;
    }

    public SellerAddress getSeller_address() {
        return seller_address;
    }

    public void setSeller_address(SellerAddress seller_address) {
        this.seller_address = seller_address;
    }
}
