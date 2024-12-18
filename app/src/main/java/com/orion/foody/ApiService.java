package com.orion.foody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user") // sesuaikan dengan endpoint API Anda
    Call<RegistrationRequestModel>registerUser(@Body RegistrationRequestModel requestModel);

    @POST("user/login") // sesuaikan dengan endpoint API login
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel requestModel);

    @POST("user/logout") // sesuaikan dengan endpoint API logout
    Call<Void> logoutUser(@Header("Authorization") String authToken); // Sesuaikan dengan respons API logout

    @GET("user")
    Call<UserProfile> getUserProfile(@Header("Authorization") String authToken);

    @PUT("user")
    Call<UpdateProfileResponse> updateProfile(@Header("Authorization") String authToken, @Body UpdateProfileRequest request);

    @GET("makanan")
    Call<ApiResponse<List<MakananModel>>> getMakanan(
            @Header("Authorization") String authToken,
            @Query("kategori")  String kategori
        );

    @GET("makanan/{id}")
    Call<ApiResponse<MakananModel>> getMakananById(@Header("Authorization") String authToken, @Path("id") String id);

    @GET("user/summary")
    Call<ApiResponse<SummaryData>> getUserSummary(@Header("Authorization") String authToken);

    @POST("catatanku")
    Call<ApiResponse<CatatanMakananModel>> simpanCatatanMakanan(@Header("Authorization") String authToken, @Body CatatanMakananModel catatanMakanan);

    @GET("catatanku/daily")
    Call<ApiResponse<List<CatatanMakananModel>>> getCatatanMakananDaily(@Header("Authorization") String authToken);

    @POST("bmi")
    Call<ApiResponse<BmiModel>> hitungBmi(@Header("Authorization") String authToken, @Body BmiModel bmiModel);

    @GET("bmi/recent")
    Call<ApiResponse<List<BmiHistoryModel>>> getRecentBmiData(@Header("Authorization") String authToken);

    @GET("bmi/chart")
    Call<ApiResponse<Void>> getBmiChart(@Header("Authorization") String authToken);

    @DELETE("bmi/{id}")
    Call<ApiResponse<Void>> deleteBmiData(@Header("Authorization") String authToken, @Path("id") String bmiId);

    @DELETE("catatanku/{id}")
    Call<ApiResponse<Void>> hapusCatatanMakanan(@Header("Authorization") String authToken, @Path("id") String catatanId);

    @GET("bmi/history")
    Call<ApiResponse<List<HistoryBmiModel>>> getBmiHistory(@Header("Authorization") String authToken);

    @GET("catatanku/history")
    Call<ApiResponse<List<HistoryResponse>>> getHistory(@Header("Authorization") String authToken);

    @GET("produk")
    Call<ApiResponse<List<Produk>>> getProduk();

    @GET("bmi/chart")
    Call<ApiResponse<ChartData>> getBmiChartData(@Header("Authorization") String authToken);

    @POST("email-verification") // Sesuaikan dengan endpoint API verifikasi OTP
    Call<ApiResponse<Void>> verifikasiOtp(@Header("Authorization") String authToken, @Body VerifikasiOtpModel verifikasiOtpModel);

    @POST("resend-otp")
    Call<ApiResponse<Void>> resendOtp(@Header("Authorization") String authToken);

    @GET("langganan")
    Call<LanggananResponse> getLangganan(@Header("Authorization") String authToken);

    @POST("transaksi")
    Call<ApiResponse<TransaksiResponseModel>> createTransaction(
            @Header("Authorization") String authToken,
            @Body TransaksiResuestModel transaksiResuestModel
    );

    @GET("transaksi")
    Call<ApiResponse<List<TransactionData>>> getTransactions(
            @Header("Authorization") String authToken
    );

    @POST("makanan/generate")
    Call<ApiResponse<MakananModel>> generateMakanan(
            @Header("Authorization") String authToken,
            @Body GenerateMakananRequestModel generateMakananRequestModel
    );

    @POST("makanan/create")
    Call<ApiResponse<MakananModel>> createMakanan(
            @Header("Authorization") String authToken,
            @Body GenerateMakananRequestModel generateMakananRequestModel
    );

    @GET("makanan/rekomendasi")
    Call<ApiResponse<MakananModel>> getRekomendasiMakanan(@Header("Authorization") String authToken);

    @GET("release/latest")
    Call<ApiResponse<NewReleaseModel>> getLatestVersion(@Header("Authorization") String authToken);

    @PUT("user/password")
    Call<ApiResponse<UserData>> changePassword(
            @Header("Authorization")  String authToken,
            @Body ResetPasswordRequest resetPasswordRequest
    );

    @POST("user/report")
    Call<ResponseBody> reportPdf(
            @Header("Authorization")  String authToken,
            @Body ReportPdfRequestModel reportPdfRequestModel
    );

    @Multipart
    @POST("user/image")
    Call<ApiResponse<Void>> updateProfilePicture(
            @Header("Authorization") String authToken,
            @Part MultipartBody.Part image
    );

}

