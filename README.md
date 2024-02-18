# Attendance-Checker
 Simple Android App that Keeps Track of Your Attendance. Built using Jetpack Compose and ROOM database

- [Jetpack Compose :-](https://developer.android.com/jetpack/compose) Jetpack Compose is Android’s recommended modern toolkit for building native UI. It simplifies and accelerates UI development on Android.
- [ROOM :-]() The Room persistence library provides an abstraction layer over SQLite to allow fluent database access

## How the App Works
There are three screens that the user can interact with. "New", "History", and "Setup".
### 'New' Screen
* In the "New" screen, user can mark their "present" or "absent" of the current date of the subjects added to the database.
* In the "New" screen the current percentage, along with number of total and present days of each subject.
* The date is picked automatically from the system.
### 'History' Screen
* Shows a list of all the present or absent entries marked by the user.
* At the top is a search bar, that the user can use to search for all records of a particular subject, or of a particular date.
* By clicking on a single item, the user can delete the particular record
### 'Setup' Screen
* If app does not have any saved subjects, the user will be prompted to enter the number of subjects, and their individual names.
* After app is setup, user can clear all data. This will delete all records, and user will have to add the subjects again,

## Preview Images
When you first launch the app you will be asked to enter number of subjects and name of each subject.

<image src="./images/setup.png" height="700px" />

This is the screen where you will mark the present or absent of subjects that you have entered.

<image src="./images/new.png" height="700px" />

This is the history screen which shows all the marked days.

<image src="./images/history.png" height="700px" />

The search in action.

<image src="./images/search.png" height="700px" />