package ar.com.maol.searchinml.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ar.com.maol.searchinml.apis.ProductSearchService;
import ar.com.maol.searchinml.models.ResultsResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRepository {
    private static final String PRODUCT_SEARCH_SERVICE_BASE_URL = "https://api.mercadolibre.com/";

    private ProductSearchService productSearchService;
    private MutableLiveData<ResultsResponse> resultsResponseLiveData;

    public ProductRepository() {
        resultsResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        productSearchService = new retrofit2.Retrofit.Builder()
                .baseUrl(PRODUCT_SEARCH_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductSearchService.class);

    }

    public void searchVolumes(String keyword) {
        productSearchService.searchResults(keyword)
                .enqueue(new Callback<ResultsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ResultsResponse> call, @NonNull Response<ResultsResponse> response) {
                        if (response.body() != null) {
                            resultsResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResultsResponse> call, @NonNull Throwable t) {
                        resultsResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<ResultsResponse> getResultsResponseLiveData() {
        return resultsResponseLiveData;
    }
}
