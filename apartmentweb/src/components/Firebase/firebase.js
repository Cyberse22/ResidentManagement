import { initializeApp } from "firebase/app";
import { getMessaging, getToken } from "firebase/messaging";

const firebaseConfig = {
    apiKey: "AIzaSyBRPaUddOrWqVjS6aMfpWO0a7BAZOO3QLM",
    authDomain: "residentmanagement-4500e.firebaseapp.com",
    projectId: "residentmanagement-4500e",
    storageBucket: "residentmanagement-4500e.appspot.com",
    messagingSenderId: "199137815708",
    appId: "1:199137815708:web:ae5a0f23cdff38e3191159",
    measurementId: "G-6NL417VTLF"
};

const app = initializeApp(firebaseConfig);

export const messaging = getMessaging(app);

export const generateToken = async () => {
    const permission = await Notification.requestPermission() 
    console.log("permission: ",permission)

    if (permission === "granted") {
        const token = await getToken(messaging, {
            vapidKey: "BDZiVVnAPhhHHa8TwQSoRmS3TSL_OkFGTI1CvVdwwRcSD0vKsY5cneWtZrAV8G2if6slfvCudWuNpUpwqKbbGUg"
        })
        console.log("token: ",token)
        return token
    }

}