Student Information: Mai Dang Khoa
Student ID: s3974876
Course: COSC2657 - Android Development
Lecturer: Mr. Minh Vu Thanh

Assessment: Assignment 2 - Blood Donation Android App
Application name: Blood Donations Station

Feature list:
    Overall:
        Login
        Register
        Logout
        Search bar for list view

    User:
        Register for a blood donating at specific donation site.
        Register for a volunteer at specific donation site.
        Cancel a blood donating register.
        Cancel a volunteer register.
        Map search for existed donation site.
        Search for nearest place.
        Information dashboard.
        Reviewing donation and volunteer record.
        Total amount of blood has been donate from user.

    Manager:
        Search places to create donation site.
        Create donation drive with specific information.
        Checking the list of mange donation site.
        Checking the list of donor and volunteer on that site.
        Checkout a donor when they finished the donation on a site (including the blood have been donated by donor).
        Close a donation site, the late donor will be mark as finished without any credit.
        Volunteer for manger's donation sites.
        Cancel the volunteer request on manger's donation sites.

    Admin:
        Generate PDF report of donation sites
s
Technologies used:
    Android library:
        Necessary library from android studio for building the application.

    Google play services:
        Generating the map.
        Add marker to the location.
        Finding correct location using search place API from Google.
        Get address of location using search place API from Google.

    Firebase
        Authentication: making authorization feature using email.
        Firestore Database: real-time storage no SQL database, store necessary data from the app.

    ItextPDF: Library used for helping generate PDF report.

Drawbacks:
    Absence of special account registration (manger, admin) method, manager and admin account are currently pre-define by modifying database.

