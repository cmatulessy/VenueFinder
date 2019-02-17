[![Build status](https://build.appcenter.ms/v0.1/apps/f3ff024a-5968-4ef5-bab6-150c89e4c7af/branches/master/badge)](https://appcenter.ms)

# VenueFinder
This is an assessment project to demonstrate my skills in Kotlin for Android, Jetpack architecture and testing. The app is a simple search and show app. You have two screens:
1) VenueFinderFragment: This is the screen where the user can enter a city name
2) VenueDetailFragment: This is the screen where the user can read detailed info about the venue they have selected.

## CodeCoverage
Due to deadline issues, I couldn't make the codecoverage to an acceptable 80%. I have 60% code coverage for now. I saw in my report that Jacoco also covers methods which are not in scope of the report. For the future I would advice to tag those methods or classes which are not relevant so the codecoverage report is more accurrate.

To run the Jacoco report use the following term in the terminal:
`./gradlew createDebugCoverageReport`

## AppCenter
For my CICD pipeline, I made use of Microsoft AppCenter to build and test the app. I also added analytics in this project to track crashes and user input.