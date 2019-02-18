[![Build status](https://build.appcenter.ms/v0.1/apps/f3ff024a-5968-4ef5-bab6-150c89e4c7af/branches/master/badge)](https://appcenter.ms)

Author: [Carlo Matulessy](http://www.carlomatulessy.com/)
Date: 17 February 2019
Version: 1.0

# VenueFinder
This is an assessment project to demonstrate my skills in Kotlin for Android, Jetpack architecture and testing. The app is a simple search and show app. You have two screens:
1) VenueFinderFragment: This is the screen where the user can enter a city name
2) VenueDetailFragment: This is the screen where the user can read detailed info about the venue they have selected.

## Google Playstore
You can download the showcase app in the Google playstore. Click [here](https://play.google.com/store/apps/details?id=com.carlomatulessy.venuefinder) to visit it

## CodeCoverage
Due to deadline issues, I couldn't make the codecoverage to an acceptable 80%. I have 60% code coverage for now. I saw in my report that Jacoco also covers methods which are not in scope for this report, so for the future I would advice to tag those methods or classes which are not relevant so the codecoverage report is more accurrate.

To run the Jacoco report use the following term in the terminal:
`./gradlew createDebugCoverageReport`

## AppCenter
For my CICD pipeline, I made use of Microsoft AppCenter to build and test the app. I also added analytics in this project to track crashes and user input. For future roll outs, I have linked AppCenter to my Playstore account so I can manage it automatically from there.

## What should I improve if I had more time?
If I had more time, I should have improved the following things:
 * If you reach the max day request count of the Foursquare API, show a message screen which is telling the user it reached it limits for this day. (Because this is an edge case, this had low priority for me, because the cache system is kicking in if you have no internet connection)
 * Add BDD test scenario's with Cucumber. If you would like to see an example of this, definitely check out my other project [here](https://github.com/cmatulessy/Issue-Tracker)
 * Fix CodeCoverage report. I like to keep my code coverage for a minimum of 80%. If I add new code to a project, I always check the code coverage before I commit. Mostly my added new code is already covered due to Test Driven Development, but you have cases where it is impossible to work TDD. And for those cases I always fall back on my code coverage report to validate if all tests are still working and if my code is covered by any tests, if not I will make new testcases for those uncovered code lines.
