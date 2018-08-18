# popular-movies


To run code : 
In android Strings XML , change String api_key (xxxxxxxxxxxxxxxxxxxxxxxxxx of themoviedb.org API) by your key (Line 5):

 <string name="api_key" translatable="false" >xxxxxxxxxxxxxxxxxxxxxxxxxx</string>


In this stage you’ll build the core experience of your movies app.

Your app will:

    Upon launch, present the user with an grid arrangement of movie posters.
    Allow your user to change sort order via a setting:

    The sort order can be by most popular, or by top rated

    Allow the user to tap on a movie poster and transition to a details screen with additional information such as:

    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
    release date
