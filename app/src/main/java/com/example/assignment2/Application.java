package com.example.assignment2;

import android.media.tv.interactive.AppLinkInfo;
import android.util.Log;

import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.User;
import com.example.assignment2.models.VolunteerRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
    private User currentUser;
    private List<DonateRegister> userDonateRegister;
    private List<VolunteerRegister> userVolunteer;
    private List<DonateSite> allDonateSite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUserFirebase = auth.getCurrentUser();

    public FirebaseUser getCurrentUserFirebase() {
        return currentUserFirebase;
    }


    public void getCurrentUserData(FirebaseUser user, final UserDataCallback callback) {
        if (user != null) {
            db.collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> data = documentSnapshot.getData();
                            User retrievedUser = new User(
                                    data.get("firstName").toString(),
                                    data.get("lastName").toString(),
                                    data.get("email").toString(),
                                    data.get("phone").toString(),
                                    data.get("dob").toString(),
                                    data.get("idNumber").toString(),
                                    data.get("bloodType").toString(),
                                    data.get("userType").toString()
                            );

                            this.currentUser = retrievedUser;

                            callback.onSuccess(currentUser);  // Notify that data is ready
                        } else {
                            Log.d("Firestore", "No such document");
                            callback.onFailure(new Exception("No data found"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle error
                        Log.w("Firestore", "Error getting document", e);
                        callback.onFailure(e);  // Notify failure
                    });
        } else {
            // No user is signed in
            Log.d("Firebase", "No user is signed in");
            callback.onFailure(new Exception("No user signed in"));
        }
    }

    public void getUserRegisterData(FirebaseUser user, final UserRegisterDonateCallback callback) {
        if (user != null) {
            db.collection("donations")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<DonateRegister> donateRegisters = new ArrayList<>();
                        queryDocumentSnapshots.forEach(documentSnapshot -> {
                            Map<String, Object> data = documentSnapshot.getData();
                            DonateRegister donateRegister = new DonateRegister(
                                    data.get("donateSiteId").toString(),
                                    data.get("userID").toString(),
                                    data.get("timeRegister").toString(),
                                    data.get("dateRegister").toString(),
                                    data.get("bloodType").toString(),
                                    data.get("lastName").toString(),
                                    data.get("firstName").toString(),
                                    data.get("dob").toString(),
                                    data.get("governmentID").toString(),
                                    documentSnapshot.getDouble("donationAmount"),
                                    data.get("status").toString(),
                                    data.get("siteName").toString()
                            );
                            donateRegisters.add(donateRegister);
                        });
                        this.userDonateRegister = donateRegisters;

                        callback.onSuccess(userDonateRegister);
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error getting donations", e);
                        callback.onFailure(e);  // Notify failure
                    });
        } else {
            // No user is signed in
            Log.d("Firebase", "No user is signed in");
            callback.onFailure(new Exception("No user signed in"));
        }
    }


    public interface UserRegisterDonateCallback{
        void onSuccess(List<DonateRegister> donateRegisters);
        void onFailure(Exception e);
    }
    // Callback interface
    public interface UserDataCallback {
        void onSuccess(User userData);
        void onFailure(Exception e);
    }


}
