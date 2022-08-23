# Kudos Cards

## Abstract
Every body appreciates to hear an honest "*Thank you!*" every now and then and if you want to thank somebody go ahead and do it.

*Kudos Cards* is a web application that allows you to display and collect small messages as *Kudos* or *Thank you* cards. 
It is meant to be collected during a week and can than be read out to participants in a weekly gathering.

It is supposed to encourage social interaction with your friends or colleagues.

## Configuration
Kudos Cards is a spring boot application that can be configured using the *application.properties* file, see configuration file for a list of sample properties.

## Setting up your Kudos Corner

### Using images
You can add new images by copying them to the folder that is beeing monitored by the application. You should pre sort the images by kudos type such as
*appreciation*, *thank you* etc. The application scans the folder for new images and will pick them randomly when creating new cards of a specific type.

### Setting up the database
By default Kudos Corner comes with an embedded H2 database, but the database configuration can be changed to other systems as well, check the application.properties file for doing so.


## You are invited to contribute
If you want to contribute to the project feel free to do a pull request or just contact me.

## Planned Features
### Near Future
- duplicate check during import from confluence and run imports on a cron schedule (@Scheduled)
- select week you want to display data for (default last week)
- a few more admin functions
- confluence password should be stored in database and be entered via admin interface on setup 

### Far Future
- more efficient handling of card imports / loading cards
- not only expressing kudos but other emotions

### Not planned because it should not be performance or social media like
- cards are beeing dedicated to user ids not just textual mentioned in the card
- a like system for cards
- reputation high score for people beeing thanked


 


  