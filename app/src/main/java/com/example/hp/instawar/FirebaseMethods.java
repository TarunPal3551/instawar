package com.example.hp.instawar;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.Order_Book.OrderDetails;
import com.example.hp.instawar.modeldatabase.Album;
import com.example.hp.instawar.modeldatabase.FilePaths;
import com.example.hp.instawar.modeldatabase.Profile;
import com.example.hp.instawar.modeldatabase.User;
import com.example.hp.instawar.modeldatabase.User_account_setting;
import com.example.hp.instawar.modeldatabase.Usersetting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FirebaseMethods {


    private static final String TAG = "FirebaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Context mContext;
    private static String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    private StorageReference mStorageReference;
    private double mPhotouploadProgress = 0;
    public int count = 0;


    public FirebaseMethods(Context context) {

        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {

            userID = mAuth.getCurrentUser().getUid();

        }

    }
    public void getOrderDetails(DataSnapshot dataSnapshot,TextView name,TextView address,TextView mobileNumber){
        OrderDetails orderDetails=new OrderDetails();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getKey().equals("order_detail")) {
                try {


                    Log.d(TAG, "getOrderDetails: datasnapshot" + ds);

                    orderDetails.setName(
                            ds.child(userID)
                                    .getValue(OrderDetails.class)
                                    .getName()
                    );
                    orderDetails.setAddress(

                            ds.child(userID)
                                    .getValue(OrderDetails.class)
                                    .getAddress()
                    );

                    orderDetails.setMobilenumber(

                            ds.child(userID)
                                    .getValue(OrderDetails.class)
                                    .getMobilenumber()
                    );
                    name.setText(orderDetails.getName());
                    address.setText(orderDetails.getAddress());
                    mobileNumber.setText(orderDetails.getMobilenumber());


                    Log.d(TAG, "getOrderDetails:  retrived informationorder details" + orderDetails.toString());


                } catch (NullPointerException e) {
                    Log.d(TAG, "getOrderDetails: datasnapshot" + e.getCause());
                }
            }

        }
    }

    public int getPostCount(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.child(mContext.getString(R.string.user_photo))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getChildren()) {
            count++;

        }
        return count;

    }

    public int getAlbumCount(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.child("album")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getChildren()) {
            count++;

        }
        return count;
    }

    public void uploadNewphoto(String phototype, final String caption, int count, final Uri imgUrl) {
        Log.d(TAG, "uploadNewphoto: attempting to upload new photo");
        FilePaths filePaths = new FilePaths();

        if (phototype.equals(mContext.getString(R.string.new_photo))) {
            Log.d(TAG, "uploadNewphoto: uploading new photo");
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference.child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/" + "user_posted_photo" + "/photo" + (count + 1));
//            Bitmap bitmap=ImageManager.getBitmap(imgUrl);
//            byte[] bytes=ImageManager.getBytesFromBitmap(bitmap,100);
            UploadTask uploadTask = null;
            uploadTask = storageReference.putFile(imgUrl);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseuri = taskSnapshot.getDownloadUrl();
                    Toast.makeText(mContext, "photo upload succesful", Toast.LENGTH_SHORT).show();
                    addPhototodatabase(caption, firebaseuri.toString());


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: uploading is failed");
                    Toast.makeText(mContext, "photo upload progress: uploading failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if (progress - 15 > mPhotouploadProgress) {
                        Toast.makeText(mContext, "photo upload progress:" + String.format("%.0f", progress), Toast.LENGTH_SHORT).show();
                        mPhotouploadProgress = progress;

                    }
                    Log.d(TAG, "onProgress: upload progress:" + progress + "% done");
                }
            });


        } else if (phototype.equals(mContext.getString(R.string.profile_photo))) {
            Log.d(TAG, "uploadNewphoto: uploading new  propfile photo");
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference.child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/" + "user_profile_photo" + "/profie_photo" + (count + 1));
//            Bitmap bitmap=ImageManager.getBitmap(imgUrl);
//            byte[] bytes=ImageManager.getBytesFromBitmap(bitmap,100);
            UploadTask uploadTask = null;
            uploadTask = storageReference.putFile(imgUrl);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseuri = taskSnapshot.getDownloadUrl();
                    Toast.makeText(mContext, "photo upload succesful", Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: uploading is failed");
                    Toast.makeText(mContext, "photo upload progress: uploading failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if (progress - 15 > mPhotouploadProgress) {
                        Toast.makeText(mContext, "photo upload progress:" + String.format("%.0f", progress), Toast.LENGTH_SHORT).show();
                        mPhotouploadProgress = progress;

                    }
                    Log.d(TAG, "onProgress: upload progress:" + progress + "% done");
                }
            });
        }


    }

    private String getTimeStamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());

    }

    public void addPhototodatabase(String caption, String url) {
        Log.d(TAG, "addPhototodatabase: adding photo to database");
        String tags = "#bakchodi  # bhand #morning ";
        String newphotoKey = mReference.child(mContext.getString(R.string.new_photo)).push().getKey();
        Profile profile = new Profile();
        profile.setCaption(caption);
        profile.setData_created(getTimeStamp());
        profile.setImage_path(url);
        profile.setTag(tags);
        profile.setPhoto_id(newphotoKey);
        profile.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        profile.setUser_id(newphotoKey);
        mReference.child(mContext.getString(R.string.user_photo)).child(userID).child(newphotoKey).setValue(profile);
        mReference.child(mContext.getString(R.string.profile_photo)).child(userID).child(newphotoKey).setValue(profile);

    }

    public void uploadAlbum(String url) {
        String key = mReference.child("new_photo").push().getKey();
        Album album = new Album();
        album.setImageUrl(url);
        album.setImage_path(key);
        mReference.child("album").child(userID).child(key).setValue(album);

    }

    public void orderDetail(String name, String address, String mobile, String size, String albumtype, String price, String albumname) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        OrderDetails orderDetails = new OrderDetails(name, address, size, price, albumtype, mobile, albumname);
        Log.d(TAG, "orderDetail: uploading details" + orderDetails);
        mReference.child("order_detail").child(userID).setValue(orderDetails);


    }


    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot) {

        Log.d(TAG, "checking if User is exists");
        User user = new User();
        for (DataSnapshot ds : dataSnapshot.child(userID).getChildren()) {
            user.setUsername(ds.getValue(User.class).getUsername());
            if (user.getUsername().equals(username)) {
                username.replace("", ".");
                Log.d(TAG, "checkifusernameexists:FOUND A MATCH:" + user.getUsername());


            } else
                username.replace(".", " ");


        }

        return false;
    }

    public void registerNewemail(final String email, final String password, final String username) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in User's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(mContext, "Authentication Succes.", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete:Authstate changed:" + userID);
                            sendVerificationEmail();


                        } else {
                            // If sign in fails, display a message to the User.
                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void addNewUser(String email, String username, long phone_number, String state, String address, String profile_photo, String display_name, long post) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        User user = new User(userID, email, phone_number, username);
        mReference.child(mContext.getString(R.string.user))
                .child(userID)
                .setValue(user);

        User_account_setting setting = new User_account_setting(address, display_name, post, profile_photo, state, email, phone_number, username);
        mReference.child(mContext.getString(R.string.dataaccountsetting))
                .child(userID)
                .setValue(setting);

    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {


                            } else {


                                Toast.makeText(mContext, "couldn't Send verificationEmail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public Usersetting getUser_settings(DataSnapshot dataSnapshot) {

        Log.d(TAG, "getUser_account_settings: retrive user account setting from firebase");
        User_account_setting setting = new User_account_setting();
        User user = new User();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getKey().equals(mContext.getString(R.string.dataaccountsetting))) {
                try {


                    Log.d(TAG, "getUser_account_settings: datasnapshot" + ds);

                    setting.setDisplay_name(
                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getDisplay_name()
                    );
                    setting.setAddress(

                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getAddress()
                    );

                    setting.setPost(

                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getPost()
                    );

                    setting.setProfile_photo(

                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getProfile_photo()
                    );
                    setting.setState(
                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getState()
                    );
                    setting.setEmail(
                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getEmail()
                    );
                    setting.setPhone_number(
                            ds.child(userID)
                                    .getValue(User_account_setting.class)
                                    .getPhone_number()
                    );
                    setting.setUsername(
                            ds.child(userID).
                                    getValue(User_account_setting.class)
                                    .getUsername());

                    Log.d(TAG, "getUser_account_settings:  retrived information useraccountsetting" + setting.toString());


                } catch (NullPointerException e) {
                    Log.d(TAG, "getUser_account_settings: datasnapshot" + e.getCause());
                }
            }


            Log.d(TAG, "getUser_setting: retrive user setting from firebase");
            if (ds.getKey().equals(mContext.getString(R.string.dataUser_setting))) {

                Log.d(TAG, "getUser_account_settings: datasnapshot" + ds);
                user.setUser_id(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUser_id()
                );
                user.setEmail(
                        ds.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );
                user.setUsername(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );
                user.setPhone_number(
                        ds.child(userID)
                                .getValue(User.class)
                                .getPhone_number()

                );

                Log.d(TAG, "getUser_account_settings:  retrived information user" + setting.toString());

            }


        }

        return new Usersetting(user, setting);
    }

    public void updateUsername(String username) {
        mReference.child(mContext.getString(R.string.user))
                .child(userID)
                .child(mContext.getString(R.string.username))
                .setValue(username);
        mReference.child(mContext.getString(R.string.dataaccountsetting))
                .child(userID)
                .child(mContext.getString(R.string.username))
                .setValue(username);

    }

    public void updateUserprofile(String imgurl) {
        mReference.child("user")
                .child(userID)
                .child("profile_photo")
                .setValue(imgurl);
        mReference.child(mContext.getString(R.string.dataaccountsetting))
                .child(userID)
                .child("profile_photo")
                .setValue(imgurl);


    }

    public void UpdatePost(int postcount) {
        mReference.child(mContext.getString(R.string.dataaccountsetting))
                .child(userID)
                .child("post")
                .setValue(count);
    }

    public void updateUserAccountSetting(String username, String email, long phone_number, String address, String state, String profile_photo) {
        Log.d(TAG, "updateUserAccountSetting: update edit user setting");
        if (username != null) {

            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.username))
                    .setValue(username);


        }
        if (email != null) {
            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.email))
                    .setValue(email);
        }
        if (phone_number != 0) {

            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.phone_number))
                    .setValue(phone_number);
        }
        if (address != null) {
            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.address))
                    .setValue(address);
        }
        if (state != null) {
            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.state))
                    .setValue(state);
        }
        if (profile_photo != null) {
            mReference.child(mContext.getString(R.string.dataaccountsetting))
                    .child(userID)
                    .child(mContext.getString(R.string.profile_photo))
                    .setValue(address);
        }
    }


}

