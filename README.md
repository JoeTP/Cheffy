# Cheffy: Your Personal Food Planner 🍽️

Cheffy is a food planner application designed to help you organize your meals, discover your favorite dishes, and manage your meal prep with ease. It allows you to add meals to your app calendar, pick your favorite meals, and view detailed video instructions for meal preparation.

## Features 🎉

- **Meal Planner:** Add meals to your app calendar for easy tracking.
- **Favorite Meals:** Save your favorite meals and easily access them when needed.
- **Authentication System:** Secure login and user registration via Firebase.
- **Meal Preparation Videos:** Watch YouTube videos for step-by-step meal preparation guidance.
  
## Architecture 🏛️

Cheffy is built using the **MVP** (Model-View-Presenter) architecture to separate the logic and view, making the app modular and easier to maintain.

## Dependencies 📦

This project utilizes a variety of libraries and tools to ensure smooth functionality. Below are the key dependencies used in this app:

### RXJava
- `io.reactivex.rxjava3:rxandroid:3.0.2`
- `io.reactivex.rxjava3:rxjava:3.1.5`

### Room Database
- `androidx.room:room-rxjava3:2.6.1`
- `androidx.room:room-runtime:2.6.1`
- `annotationProcessor: androidx.room:room-compiler:2.6.1`

### Retrofit for API Calls
- `com.squareup.retrofit2:retrofit:2.9.0`
- `com.squareup.retrofit2:adapter-rxjava3:2.9.0`
- `com.squareup.retrofit2:converter-gson:2.9.0`

### Firebase for Authentication and Database
- `com.google.firebase:firebase-bom:33.8.0`
- `com.google.firebase:firebase-auth`
- `com.google.firebase:firebase-firestore`
- `com.google.firebase:firebase-database`

### Navigation Component
- `androidx.navigation:navigation-fragment:2.7.7`
- `androidx.navigation:navigation-ui:2.7.7`

### Image and Video Handling
- `com.github.bumptech.glide:glide:4.16.0`
- `com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0`

### UI Components
- `com.google.android.material:material:1.9.0`
- `de.hdodenhof:circleimageview:3.1.0`
- `com.airbnb.android:lottie:6.6.2` for animations

## Installation 🔧

To get started with Cheffy, clone this repository and follow the steps below:

1. **Clone the repo:**

   ```bash
   git clone https://github.com/your-username/cheffy.git
