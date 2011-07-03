Datastore
=============

Datastore is a Java library which makes life easier when working with [datastore-server][datastore-server]. Just add it to your Java classpath.

Instantiate scores main classes:
-------

	String gameKey = "..."; // your game key
	String appUrl = "http://yourapplicationid.appspot.com"; // the url of the datastore-server instance
	Scores scores = new ScoresHttpImpl(gameKey, appUrl);
	Profiles profiles = new ProfilesHttpImpl(appUrl);

Submit a new score:
-------

	boolean guest = true;
	Profile profile = profiles.register("new player", guest);
	Score score = new Score(...);
	scores.submit(profile.getPrivatekey(), score);

Request top 20 daily scores:
-------

	Set<String> tags = new HashSet<String>();
	Collection<Score> dailyScores = scores.getOrderedByPoints(tags, 20, false, Range.Day);

Finally
-------

That's all for now, explore the API by yourself and propose improvements by suggesting new stuff on [Issues][issues] page. 

[issues]: https://github.com/gemserk/datastore/issues
[datastore-server]: https://github.com/gemserk/datastore-server
