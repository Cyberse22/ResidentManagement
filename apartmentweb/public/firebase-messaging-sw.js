importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');

firebase.initializeApp({
    apiKey: "AIzaSyBRPaUddOrWqVjS6aMfpWO0a7BAZOO3QLM",
    authDomain: "residentmanagement-4500e.firebaseapp.com",
    projectId: "residentmanagement-4500e",
    storageBucket: "residentmanagement-4500e.appspot.com",
    messagingSenderId: "199137815708",
    appId: "1:199137815708:web:ae5a0f23cdff38e3191159",
    measurementId: "G-6NL417VTLF"
});

const messaging = firebase.messaging();

self.addEventListener('notificationclick', (event) => {
    event.notification.close();
    event.waitUntil(
      self.clients.openWindow('http://localhost:3000/') // Thay '/your-page' bằng đường dẫn tới trang web của bạn
    );
  });

  messaging.onBackgroundMessage((payload) => {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
  
    // Customize notification here
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
      icon: payload.notification.image
    };
  
  
    self.registration.showNotification(notificationTitle, notificationOptions)
  
  });