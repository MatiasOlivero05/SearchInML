package ar.com.maol.searchinml.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsResponse {

    @SerializedName("query")
    @Expose
    private String query;

    @SerializedName("results")
    @Expose
    List<Result> results = null;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
