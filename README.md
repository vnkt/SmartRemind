# SmartRemind
Android application - SmartRemind

The app stores reminders that notifies the user and also alerts people whom the user picks. The user can pick any number of required contacts. All the phone contacts are displayed along with a search box that’s synchronized with the contacts list. Once the user had picked phone number contacts, the app requests user for gmail access. The gmail access is done via OAuth and no user credentials are stored in the app. The app checks for internet connectivity and if there is one, the app proceeds with the access part, else it continues without the option of mailing. If the user is willing to grant access, user can pick emails from the list of mail ids. The user can also deny if needed. Cursors are used to query the db and retrieve filtered contacts from Contacts DB. The remainder info, date time are set. The android inbuilt date picker and time picker are used. As the user saves the reminder, it is stored in db. A particular reminder can be edited after it is set. A pending Intent of that reminder with the specific row id is created. When this PendingIntent is received (ie) @ the time of reminder, the app sends SMS to the contacts picked and emails to the email contacts picked. When the reminder goes the user is shown a notification of reminder along with list of ph num and email ids alerted.
