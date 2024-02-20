# RuskoRadio

![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen?logo=android&style=plastic)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Hilt](https://img.shields.io/badge/Hilt-2.49-red.svg?style=flat&logo=Hilt)](https://dagger.dev/hilt/)
[![KSP](https://img.shields.io/badge/KSP-blueviolet)](https://kotlinlang.org/docs/ksp-overview.html)

<br>
Application for playing streaming online radio for service https://www.ruskoradio.ru/
<br>
It shows the current song info (artist and title), shows notification, and alarms.
<br>

### Stack: 
- [Android Studio](https://developer.android.com/studio/intro)
- Coroutines + Flow
- Arcitecture: MVVM+StateFlow, Single Activity + Android Navigation
- Network: Retrofit2
- Mediaplayer
- DI - Hilt
- Ads - yandex mobileads
- Scheduled notifications

## Screencast 
<img src="./screenshots/000screncast.gif" width="240" height="480"> 

## Getting Started 🛠

1. Clone this repository to your local machine.
    ```text
    git clone https://github.com/GoetzDeBouville/RuskoRadio.git
    ```

2. Open the project in Android Studio.
   
3. Go to folder app/src/main/assets and add `file app_config.properties`. This file should have next contains:
```text
    streamUrl=https://your_stream_url
    websiteUrl=https://your_stream_url
    songInfoUrl=https://songInfoUrl
```
4. Set up your Android device/emulator/simulator.

5. Build and run the project.


## Contributing :writing_hand:

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## Project Roadmap:
- Add dark theme support
- Add alarm manager (screen for alarm settings)
- Add localizations
- Add binding of album cover image (using Itunes search API???) - Open for discussion 😁

## Contributors 📢

<a href="https://github.com/GoetzDeBouville/RuskoRadio/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=GoetzDeBouville/RuskoRadio"/>
</a>

### Contact me  📬

<p align="left">

[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/aleksey-zinchenko-9b3760252/)
[![](https://img.shields.io/badge/Telegram-0077B5?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/heoderer)
[![](https://img.shields.io/badge/Facebook-0077B5?style=for-the-badge&logo=facebook&logoColor=white)](https://www.facebook.com/double.conscience)
</p>
