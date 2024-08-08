# Playlist App

## Personalizing your music

This application aims to provide users with a comprehensive tool to manage and track their personal music.

**Who will use it?**

Any individual who loves listening to music!

**Why is this project of interest to you?**

As a young individual who loves music and spends a lot of time listening to music, I would love to create an app that personalizes my music the way that I want!

## User Stories

1. **As a user, I want to be able to add a new songs to my playlist**

2. **As a user, I want to be able to view a list of songs on my playlist**

3. **As a user, I want to be able to categorize my songs**

4. **As a user, I want to be able to search for songs or artists in my playlist**
5. **As a user, I want to be able to save my playlist**
6. **As a user, I want to be able to load my playlist**
Phase 4 :Task 3: 
After reviewing the UML class diagram and the current design, one area that could benefit from refactoring is the handling of playlist and song management. Currently, the Playlist class manages both the collection of Song objects and the logic for searching and updating the playlist. To improve this, it would be beneficial to introduce a separate PlaylistManager class that encapsulates playlist operations such as adding, removing, and searching for songs. This separation would help to isolate playlist management logic from the core Playlist data structure, making it easier to manage and extend the functionality in the future.
Phase 4 : Task 2:
   Playlist created: summer
   Song added: summertime by lana to playlist summer
   Song added: summertime
   Song added: video games by lana to playlist summer
   Song added: video games

