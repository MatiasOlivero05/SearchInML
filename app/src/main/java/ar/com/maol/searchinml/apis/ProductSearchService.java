package ar.com.maol.searchinml.apis;

import ar.com.maol.searchinml.models.ResultsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductSearchService {
    @GET("/sites/MLA/search")
    Call<ResultsResponse> searchResults(
            @Query("q") String query
    );
}