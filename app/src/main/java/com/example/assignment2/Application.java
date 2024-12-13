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
    private static List<VolunteerRegister> userVolunteerRegister;
    private static List<DonateSite> allDonateSite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUserFirebase = auth.getCurrentUser();

    public FirebaseUser getCurrentUserFirebase() {
        return currentUserFirebase;
    }

    public List<DonateRegister> getUserDonateRegister() {
        return userDonateRegister;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        Application.currentUser = currentUser;
    }

    public void setUserDonateRegister(List<DonateRegister> userDonateRegister) {
        Application.userDonateRegister = userDonateRegister;
    }

    public List<VolunteerRegister> getUserVolunteerRegister() {
        return userVolunteerRegister;
    }

    public void setUserVolunteerRegister(List<VolunteerRegister> userVolunteerRegister) {
        Application.userVolunteerRegister = userVolunteerRegister;
    }

    public List<DonateSite> getAllDonateSite() {
        return allDonateSite;
    }

    public static void setAllDonateSite(List<DonateSite> allDonateSite) {
        Application.allDonateSite = allDonateSite;
    }

    //-----------------------GET-------------------------------//
    //FEATCH current user data
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
    public interface UserDataCallback {
        void onSuccess(User userData);
        void onFailure(Exception e);
    }

    //FEATCH donate register
    public void getDonateRegisterData(FirebaseUser user, final UserRegisterDonateCallback callback) {
        if (user != null) {
            db.collection("donate_registers")
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
                                    data.get("siteName").toString(),
                                    documentSnapshot.getId()
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

//    //FEATCH volunteer register
    public void getVolunteerRegisterData(FirebaseUser user, final UserRegisterVolunteerCallback callback){
        if (user != null) {
            db.collection("volunteer_registers")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<VolunteerRegister> volunteerRegisters = new ArrayList<>();
                        queryDocumentSnapshots.forEach(documentSnapshot -> {
                            Map<String, Object> data = documentSnapshot.getData();
                            VolunteerRegister volunteerRegister = new VolunteerRegister(
                                    data.get("userID").toString(),
                                    data.get("donateSiteId").toString(),
                                    data.get("status").toString(),
                                    data.get("dateRegister").toString(),
                                    data.get("timeRegister").toString(),
                                    data.get("ID").toString(),
                                    data.get("firstName").toString(),
                                    data.get("lastName").toString(),
                                    data.get("phone").toString(),
                                    data.get("gID").toString(),
                                    data.get("managerPhone").toString()
                            );
                            volunteerRegisters.add(volunteerRegister);
                        });
                        this.userVolunteerRegister = volunteerRegisters;

                        callback.onSuccess(userVolunteerRegister);
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
    public interface UserRegisterVolunteerCallback{
        void onSuccess(List<VolunteerRegister> volunteerRegisters);
        void onFailure(Exception e);
    }

    //FEATCH site data
    public void getSiteData(FirebaseUser user, final SiteDataCallBack callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (user != null) {
            db.collection("sites")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<DonateSite> donateSites = new ArrayList<>();
                        queryDocumentSnapshots.forEach(documentSnapshot -> {
                            try {
                                Map<String, Object> data = documentSnapshot.getData();
                                DonateSite donateSite = new DonateSite(
                                        data.get("name").toString(),
                                        data.get("address").toString(),
                                        (double) data.get("latitude"),
                                        (double) data.get("longitude"),
                                        data.get("phone").toString(),
                                        data.get("managerID").toString(),
                                        data.get("date").toString(),
                                        data.get("status").toString(),
                                        (List<String>) data.get("currentRegister"),
                                        (List<String>) data.get("currentVolunteer"),
                                        data.get("start").toString(),
                                        data.get("end").toString(),
                                        (double) data.get("amount"),
                                        data.get("bloodType").toString(),
                                        documentSnapshot.getId()
                                );
                                donateSites.add(donateSite);
                            } catch (Exception e) {
                                Log.e("Firestore", "Error parsing document: " + documentSnapshot.getId(), e);
                            }
                        });
                        this.allDonateSite = donateSites;
                        callback.onSuccess(allDonateSite);
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
    public interface SiteDataCallBack {
        void onSuccess(List<DonateSite> donateSites);

        void onFailure(Exception e);
    }


    //---------------PUT---------------------//
    //PUT new site
    public void createNewSite(DonateSite newSite, final CreateSiteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sitesCollection = db.collection("sites");

        DocumentReference newSiteRef = sitesCollection.document();
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
        site.put("siteId", newSiteRef.getId());

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
    public interface CreateSiteCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }

    //PUT new donate register
    public void createNewDonateRegister(DonateRegister donateRegister, final CreateDonateRegisterCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sitesCollection = db.collection("donate_registers");

        DocumentReference newSiteRef = sitesCollection.document();
        // Create a new site object
        Map<String, Object> register = new HashMap<>();
        register.put("bloodType", donateRegister.getBloodType());
        register.put("dateRegister", donateRegister.getDateRegister());
        register.put("dob", donateRegister.getDob());
        register.put("firstName", donateRegister.getFirstName());
        register.put("lastName", donateRegister.getLastName());
        register.put("timeRegister", donateRegister.getTimeRegister());
        register.put("userID", donateRegister.getUserID());
        register.put("siteName", donateRegister.getSiteName());
        register.put("donateSiteId", donateRegister.getDonateSiteId());
        register.put("status", donateRegister.getStatus());
        register.put("donationAmount", donateRegister.getDonationAmount());
        register.put("governmentID", donateRegister.getGovernmentID());
        register.put("registerId", newSiteRef.getId());

        // Add a new document with a generated ID
        sitesCollection.add(register)
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
    public interface CreateDonateRegisterCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }

    //PUT new volunteer register
    public void createNewVolunteerRegister(VolunteerRegister volunteerRegister, final CreateVolunteerRegisterCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference volunteer_registers_clt = db.collection("volunteer_registers");

        DocumentReference newSiteRef = volunteer_registers_clt.document();
        // Create a new site object
        Map<String, Object> register = new HashMap<>();
        register.put("userID", volunteerRegister.getUserID());
        register.put("donateSiteId", volunteerRegister.getDonateSiteId());
        register.put("status", volunteerRegister.getStatus());
        register.put("timeRegister", volunteerRegister.getTimeRegister());
        register.put("dateRegister", volunteerRegister.getDateRegister());
        register.put("registerId", newSiteRef.getId());
        register.put("firstName", volunteerRegister.getFirstName());
        register.put("lastName", volunteerRegister.getLastName());
        register.put("phone", volunteerRegister.getPhone());
        register.put("gID", volunteerRegister.getgID());
        register.put("ID", newSiteRef.getId());

       volunteer_registers_clt.add(register)
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
    };
    public interface CreateVolunteerRegisterCallback {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }




}
