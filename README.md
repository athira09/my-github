# my-github
OAuth login, View Repositories and Commits

To begin with, Check out this link : https://developer.github.com/apps/building-oauth-apps/
1. To create OAuth app
2. Get CLIENT_ID & CLIENT_SECRET
3. Set REDIRECT_URI 


Make these changes in your code
1. Replace fields CLIENT_ID, CLIENT_SECRET & REDIRECT_URI with your app values
2. In your Manifest file, modify the intent-filter

              <data
                    android:host="HOST"
                    android:scheme="SCHEME" />

If your REDIRECT_URI is yourapp://yourcallback, 

              <data
                    android:host="yourcallback"
                    android:scheme="yourapp" />
