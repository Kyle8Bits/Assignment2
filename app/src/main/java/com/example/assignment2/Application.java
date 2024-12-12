package com.example.assignment2;

import android.media.tv.interactive.AppLinkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2.main_screen.DonateMapScreen;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.User;
import com.example.assignment2.models.VolunteerRegister;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private static User currentUser;
    private static List<DonateRegister> userDonateRegister;
    private static List<VolunteerRegister> userVolunteer;
    private static List<DonateSite> allDonateSite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    FirebaseUser currentUserFirebase = auth.getCurrentUser();

    public FirebaseUser getCurrentUserFirebase() {
        return currentUserFirebase;
    }

    public List<DonateRegister> getUserDonateRegister() {
        return userDonateRegister;
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
                                    data.get("userType").toString(),
                                    user.getUid()
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

    public interface UserRegisterDonateCallback{
        void onSuccess(List<DonateRegister> donateRegisters);
        void onFailure(Exception e);
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
    // Callback interface
    public interface UserDataCallback {
        void onSuccess(User userData);
        void onFailure(Exception e);
    }

    public void createNewSite(DonateSite newSite, final CreateSiteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sitesCollection = db.collection("sites");

        // Create a new site object
        Map<String, Object> site = new HashMap<>();
        site.put("name", newSite.getName());
        site.put("address", newSite.getAddress());
        site.put("amount", newSite.getAmountOfBlood());
        site.put(("status"), newSite.getStatus());

        site.put("date", newSite.getDate());
        site.put("start", newSite.getDonationStartTime( ));
        site.put("end", newSite.getDonationEndTime());
        site.put("bloodType", newSite.getBloodCollectType());

        site.put("latitude", newSite.getLatitude());
        site.put("longitude", newSite.getLongitude());
        site.put("managerID", newSite.getManagerUID());
        site.put("phone", newSite.getPhone());

        site.put("currentRegister", newSite.getDonationRegisterIds());
        site.put("currentVolunteer", newSite.getVolunteerRegisterIds());

        // Add a new document with a generated ID
        sitesCollection.add(site)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
    }

    // Callback interface
    public interface CreateSiteCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }
}
