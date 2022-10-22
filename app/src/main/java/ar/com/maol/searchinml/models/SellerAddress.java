package ar.com.maol.searchinml.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerAddress {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("address_line")
    @Expose
    private String address_line;

    @SerializedName("zip_code")
    @Expose
    private String zip_code;

    @SerializedName("country")
    @Expose
    Country country;

    @SerializedName("state")
    @Expose
    State state;

    @SerializedName("city")
    @Expose
    City city;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    public SellerAddress() {
    }

    public SellerAddress(String id, String comment, String address_line, String zip_code, Country country, State state, City city, String latitude, String longitude) {
        this.id = id;
        this.comment = comment;
        this.address_line = address_line;
        this.zip_code = zip_code;
        this.country = country;
        this.state = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
