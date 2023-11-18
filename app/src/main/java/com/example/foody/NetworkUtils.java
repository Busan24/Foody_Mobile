//package com.example.foody;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//// NetworkUtils.java
//public class NetworkUtils {
//    public static void loadMakananData(String makananId, Context context) {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<ApiResponse<MakananModel>> call = apiService.getDetailMakanan(makananId);
//        call.enqueue(new Callback<ApiResponse<MakananModel>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<MakananModel>> call, Response<ApiResponse<MakananModel>> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse<MakananModel> apiResponse = response.body();
//                    if (apiResponse != null && "success".equals(apiResponse.getStatus())) {
//                        MakananModel makananModel = apiResponse.getData();
//                        if (makananModel != null) {
//                            // Lakukan sesuatu dengan data makananModel
//                            // Contoh: Setel data ke tampilan
//                            setMakananDataToViews(makananModel, context);
//                        } else {
//                            // Tampilkan pesan bahwa tidak ada data makanan
//                            Toast.makeText(context, "Tidak ada data makanan", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        // Tampilkan pesan bahwa respons tidak sukses
//                        String errorMessage = "Respons tidak sukses: " + response.message();
//                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//                        Log.e("NetworkUtils", errorMessage);
//                    }
//
//                } else {
//                    // Tampilkan pesan bahwa terjadi kesalahan saat mengambil data makanan
//                    Toast.makeText(context, "Gagal mengambil data makanan", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
//                // Tampilkan pesan bahwa terjadi kesalahan jaringan
//                Toast.makeText(context, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
//                Log.e("NetworkUtils", "Kesalahan jaringan", t);
//            }
//        });
//    }
//
//    private static void setMakananDataToViews(MakananModel makananModel, Context context) {
//        // Implementasikan logika setMakananDataToViews di sini
//        // Sesuaikan dengan kebutuhan Anda
//        if (context instanceof DataMakanan) {
//            ((DataMakanan) context).setMakananDataToViews(makananModel);
//        }
//    }
//}
