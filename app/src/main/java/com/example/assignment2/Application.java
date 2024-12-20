package com.example.assignment2;

import android.media.tv.interactive.AppLinkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2.list_screen.SiteRecord;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
    //FEATCH user by id
    public void getUserByID(String userID, final UserByIDCallback callback) {

            db.collection("users")
                .document(userID)
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
                                userID
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
    }
    public interface UserByIDCallback {
        void onSuccess(User userData);
        void onFailure(Exception e);
    }

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
                                    data.get("ID").toString()
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
                                    data.get("volunteerSiteId").toString(),

                                    data.get("status").toString(),
                                    data.get("dateRegister").toString(),

                                    data.get("timeRegister").toString(),
                                    data.get("ID").toString(),

                                    data.get("firstName").toString(),
                                    data.get("lastName").toString(),

                                    data.get("userPhone").toString(),
                                    data.get("gID").toString(),

                                    data.get("managerPhone").toString(),
                                    data.get("siteName").toString(),

                                    data.get("address").toString()
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
                                        data.get("siteId").toString()
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

    //FEATCH volunteer register by site id
    public void getVolunteerRegisterInSite(String site, final VolunteerFromSite callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteer_registers").whereEqualTo("volunteerSiteId", site).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<VolunteerRegister> volunteerRegisters = new ArrayList<>();
                    queryDocumentSnapshots.forEach( documentSnapshot -> {
                                try {
                                    Map<String, Object> data = documentSnapshot.getData();
                                    VolunteerRegister volunteerRegister = new VolunteerRegister(
                                            data.get("userID").toString(),
                                            data.get("volunteerSiteId").toString(),

                                            data.get("status").toString(),
                                            data.get("dateRegister").toString(),

                                            data.get("timeRegister").toString(),
                                            data.get("ID").toString(),

                                            data.get("firstName").toString(),
                                            data.get("lastName").toString(),

                                            data.get("userPhone").toString(),
                                            data.get("gID").toString(),

                                            data.get("managerPhone").toString(),
                                            data.get("siteName").toString(),

                                            data.get("address").toString()
                                    );
                                    volunteerRegisters.add(volunteerRegister);
                                } catch (Exception e) {
                                    Log.e("Firestore", "Error parsing document: " + documentSnapshot.getId(), e);
                                }
                    });

                    callback.onSuccess(volunteerRegisters);

                }).addOnFailureListener(e -> {
                    Log.w("Firestore", "Error getting donations", e);
                    callback.onFailure(e);  // Notify failure
                });
    }
    public interface VolunteerFromSite{
        void onSuccess(List<VolunteerRegister> volunteerRegisters);
        void onFailure(Exception e);
    }

    //GET donate register by site id
    public void getDonorRegisterInSite(String site, final DonorFromSite callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("donate_registers").whereEqualTo("donateSiteId", site).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DonateRegister> donateRegisters = new ArrayList<>();
                    queryDocumentSnapshots.forEach( documentSnapshot -> {
                        try {
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
                                    data.get("ID").toString()
                            );
                            donateRegisters.add(donateRegister);
                        } catch (Exception e) {
                            Log.e("Firestore", "Error parsing document: " + documentSnapshot.getId(), e);
                        }
                    });

                    callback.onSuccess(donateRegisters);

                }).addOnFailureListener(e -> {
                    Log.w("Firestore", "Error getting donations", e);
                    callback.onFailure(e);  // Notify failure
                });
    }
    public interface DonorFromSite{
        void onSuccess(List<DonateRegister> donateRegisters);
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
        register.put("ID", newSiteRef.getId());

        // Add a new document with a generated ID
        sitesCollection.add(register)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess(documentReference.getId());
                        addToSiteList("currentRegister", donateRegister.getDonateSiteId(), newSiteRef.getId());
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
        register.put("volunteerSiteId", volunteerRegister.getDonateSiteId());

        register.put("status", volunteerRegister.getStatus());
        register.put("timeRegister", volunteerRegister.getTimeRegister());

        register.put("dateRegister", volunteerRegister.getDateRegister());
        register.put("firstName", volunteerRegister.getFirstName());

        register.put("lastName", volunteerRegister.getLastName());
        register.put("userPhone", volunteerRegister.getUserPhone());

        register.put("gID", volunteerRegister.getgID());
        register.put("ID", newSiteRef.getId());

        register.put("managerPhone", volunteerRegister.getManagerPhone());
        register.put("siteName", volunteerRegister.getSiteName());

        register.put("address", volunteerRegister.getAddress());

       volunteer_registers_clt.add(register)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess(documentReference.getId());
                        addToSiteList("currentVolunteer", volunteerRegister.getDonateSiteId(), newSiteRef.getId());
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


    //-------------DELETE-------------------//
    //DELETE donateRegister
    public void deleteDonateRegister(DonateRegister register, final deleteDonationRegisterCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("donate_registers").document(register.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFromSiteList("currentRegister", register.getDonateSiteId(), register.getId());
                        callback.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
    }
    public interface deleteDonationRegisterCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    //DELETE volunteer register
    public void deleteVolunteerRegister(VolunteerRegister register, final deleteVolunteerRegisterCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteer_registers").document(register.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFromSiteList("currentVolunteer", register.getDonateSiteId(), register.getId());
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
    }
    public interface deleteVolunteerRegisterCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    //DELETE manager register volunteer
    public void deleteVolunteerRegFromSite(DonateSite donateSite, String currentManagerID, final deleteVolunteerRegFromSiteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteer_registers")
                .whereEqualTo("userID", currentManagerID)
                .whereEqualTo("volunteerSiteId", donateSite.getSiteId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String documentId = document.getString("ID");
                        document.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "DocumentSnapshot successfully deleted!");

                                    removeFromSiteList("currentVolunteer", donateSite.getSiteId(), documentId);
                                    // Check if all documents are processed
                                    callback.onSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error deleting document", e);
                                    callback.onFailure(e);
                                });
                    }
                    // If no documents were found
                    if (queryDocumentSnapshots.isEmpty()) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error finding document", e);
                    callback.onFailure(e);
                });
    }
    public interface deleteVolunteerRegFromSiteCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    //CLOSE a sites and other information
    public void closeSite(DonateSite site, final closeSiteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sites").whereEqualTo("siteId", site.getSiteId())
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    final List<String> volunteerList = new ArrayList<>();
                    final List<String> leftOverDonor = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Assuming you want to update the first document found
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        DocumentReference documentReference = documentSnapshot.getReference();

                        if (documentSnapshot.contains("currentRegister")) {
                            List<String> currentRegister = (List<String>) documentSnapshot.get("currentRegister");
                            if (currentRegister != null) { // Check for null
                                leftOverDonor.addAll(new ArrayList<>(currentRegister)); // Make a deep copy
                            }
                        }
                        if (documentSnapshot.contains("currentVolunteer")) {
                            List<String> currentVolunteer = (List<String>) documentSnapshot.get("currentVolunteer");
                            if (currentVolunteer != null) {
                                volunteerList.addAll(new ArrayList<>(currentVolunteer));
                            }
                        }
                        documentReference.update("status", "CLOSE")
                                .addOnSuccessListener(aVoid -> {
                                    updateStatusInCollections(db, volunteerList, leftOverDonor, callback);
                                })
                                .addOnFailureListener(e -> {
                                    callback.onFailure(e);
                                });
                    } else {
                        callback.onFailure(new Exception("No documents found"));
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }
    private void updateStatusInCollections(FirebaseFirestore db, List<String> volunteerList, List<String> leftOverDonor, closeSiteCallback callback) {
        // Update status in volunteer_register collection
        for (String id : volunteerList) {
            db.collection("volunteer_registers").whereEqualTo("ID", id)
                    .get().addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            documentSnapshot.getReference().update("status", "COMPLETED")
                                    .addOnFailureListener(callback::onFailure);
                        }
                    }).addOnFailureListener(callback::onFailure);
        }

        // Update status in donate_register collection
        for (String id : leftOverDonor) {
            db.collection("donate_registers").whereEqualTo("ID", id)
                    .get().addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            documentSnapshot.getReference().update("status", "COMPLETED")
                                    .addOnFailureListener(callback::onFailure);
                        }
                    }).addOnFailureListener(callback::onFailure);
        }

        // Call onSuccess after updating all documents
        callback.onSuccess();
    }

    public interface closeSiteCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    //---------------POST------------------------//
    //MODIFY the list volunteer and register in a site
    public void addToSiteList(String field, String siteId, String newString) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sites").whereEqualTo("siteId", siteId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        document.getReference().update(field, FieldValue.arrayUnion(newString))
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "DocumentSnapshot successfully updated!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error updating document", e);

                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error finding document", e);

                });
    }
    public void removeFromSiteList(String field, String siteId, String stringToRemove) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sites").whereEqualTo("siteId", siteId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        document.getReference().update(field, FieldValue.arrayRemove(stringToRemove))
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "DocumentSnapshot successfully updated!");

                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error updating document", e);

                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error finding document", e);

                });
    }
    //UPDATE status of donate register by id
    public void updateStatus(String registerId,double donationAmount, final UpdateStatusCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("donate_registers").whereEqualTo("ID", registerId)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            Double currentDonationAmount = documentSnapshot.getDouble("donationAmount");
                            if (currentDonationAmount == null) {
                                currentDonationAmount = 0.0; // Default to 0 if the field is missing
                            }
                            double newDonationAmount = currentDonationAmount + donationAmount;

                            documentSnapshot.getReference()
                                    .update("status", "COMPLETED", "donationAmount", newDonationAmount)
                                    .addOnSuccessListener(aVoid -> callback.onSuccess())
                                    .addOnFailureListener(callback::onFailure);
                        }
                    } else {
                        callback.onFailure(new Exception("No documents found with ID: " + registerId));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }
    public interface UpdateStatusCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
