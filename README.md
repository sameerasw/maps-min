# maps-min
Google Maps power saving mode for any device with Shizuku

### Requirements
- Shizuku - for executing the min mode activity
- Notification listener - to detect the Google Map's "Navigation" category notification

> There is no UI in the application, Just add the QS tile and you should be able to check status and toggle the behavior with it.
> The Power saving mode will be enabled when there is an ongoign navigation, the QS tile is enabled and when the user turns off the screen. You will have to turn back on and you will be prompted with the same navigation view but in power saving mode.

<p align="center">
  <img src="https://github.com/user-attachments/assets/4275ebc0-2043-49dc-861d-8ccc7e403b2d" width="48%" />
  <img src="https://github.com/user-attachments/assets/343dd14b-6190-47d3-af3a-342d4cc2a134" width="48%" />
</p>

> Currently the power saving mode for Google Maps is only available on the latest Pixel devices. The activity is present in the app but is not accessible with regular privilages so with elevated shizuku priv, we can launch it on any device. This will not show on the AOD, rather it will replace the navigation view that usually appear over the lock screen in min mode. And with a tap, it will switch to the regular view and allow you to interact with the map and UI.

> To uninstall, either long press the QS tile or find the app in all apps list in settings and uninstall from the app info page as it does not have a launchable activity cluttering the apps list. 

## Setup
https://github.com/user-attachments/assets/7e8a03de-efaf-4e42-b463-ab6f34f31077
