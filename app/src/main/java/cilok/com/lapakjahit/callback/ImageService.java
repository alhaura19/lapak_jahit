package cilok.com.lapakjahit.callback;

import cilok.com.lapakjahit.entity.UploadImage;
import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
//import retrofit2.Call;
//import retrofit2.http.Header;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.Part;

/**
 * Created by Gilbert on 5/25/2017.
 */
///storage/emulated/0/Pictures/1494739816839.jpg
public interface ImageService {

    @Multipart
    @POST("/images.json")
    void UploadFile(@Part("file")TypedFile file, Callback<UploadImage> callback);
}

//
//    @Multipart
//    @POST("images.json")
////    Call<cilok.com.lapakjahit.entity.UploadImage> postImage(@Part() MultipartBody.Part image, @Part("file") RequestBody name);
////    Call<cilok.com.lapakjahit.entity.UploadImage> postImage(@Part("file") MultipartBody.Part image);
////    @Multipart
////    @POST("/api/Accounts/editaccount")
//    Call<UploadImage> postImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image, @Part("file") RequestBody name);
////    Call<UploadImage> editUser(@Header("Authorization") String authorization, @Part("file\"; filename=\"pp.png\" ") RequestBody file, @Part("FirstName") RequestBody fname, @Part("Id") RequestBody id);
//    @Multipart
//    @POST("images.json")
//    Call<UploadImage> upload(
////            @Header("Authorization") String authorization,
//            @Part MultipartBody.Part filePart
////            @Part("file") RequestBody product
////            @Part MultipartBody.Part file
//    );