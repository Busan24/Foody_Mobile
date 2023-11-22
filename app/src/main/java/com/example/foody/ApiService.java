package com.example.foody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("users") // sesuaikan dengan endpoint API Anda
    Call<RegistrationRequestModel>registerUser(@Body RegistrationRequestModel requestModel);

    @POST("users/login") // sesuaikan dengan endpoint API login
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel requestModel);

    @POST("users/logout") // sesuaikan dengan endpoint API logout
    Call<Void> logoutUser(); // Sesuaikan dengan respons API logout

    @GET("users/profile")
    Call<UserProfile> getUserProfile(@Header("Authorization") String authToken);

    @PUT("users/update")
    Call<UpdateProfileResponse> updateProfile(@Header("Authorization") String authToken, @Body UpdateProfileRequest request);

    @GET("makanan")
    Call<ApiResponse<List<MakananModel>>> getMakanan();

    @GET("makanan/{id}")
    Call<ApiResponse<MakananModel>> getMakananById(@Path("id") String id);

    @GET("users/summary")
    Call<ApiResponse<SummaryData>> getUserSummary(@Header("Authorization") String authToken);

    @POST("catatanku/store")
    Call<ApiResponse<CatatanMakananModel>> simpanCatatanMakanan(@Header("Authorization") String authToken, @Body CatatanMakananModel catatanMakanan);

    @GET("catatanku/daily")
    Call<ApiResponse<List<CatatanMakananModel>>> getCatatanMakananDaily(@Header("Authorization") String authToken);

    @POST("bmi")
    Call<ApiResponse<BmiModel>> hitungBmi(@Header("Authorization") String authToken, @Body BmiModel bmiModel);

    @GET("bmi/recent")
    Call<ApiResponse<List<BmiHistoryModel>>> getRecentBmiData(@Header("Authorization") String authToken);

    @DELETE("bmi/delete/{id}")
    Call<ApiResponse<Void>> deleteBmiData(@Header("Authorization") String authToken, @Path("id") String bmiId);

    @DELETE("catatanku/delete/{id}")
    Call<ApiResponse<Void>> hapusCatatanMakanan(@Header("Authorization") String authToken, @Path("id") String catatanId);

    @GET("bmi/history")
    Call<ApiResponse<List<HistoryBmiModel>>> getBmiHistory(@Header("Authorization") String authToken);

    @GET("catatanku/history")
    Call<ApiResponse<List<HistoryResponse>>> getHistory(@Header("Authorization") String authToken);

    @GET("produk")
    Call<ApiResponse<List<Produk>>> getProduk();

    @GET("/bmi/chart")
    Call<ApiResponse<ChartData>> getBmiChartData(@Header("Authorization") String authToken);

}

