Datastore
=============

Datastore is a Java library which makes life easier when working with [datastore-server][datastore-server].

Adding it to classpath
-------

If you are using maven, just download [datastore][datastore] and then run:

	mvn install

Then just add maven dependency, if you are running it on desktop:

	<dependency>
		<groupId>com.gemserk.datastore</groupId>
		<artifactId>datastore-desktop</artifactId>
		<version>${datastore.version}</version> <!-- the version you want of datastore -->
	</dependency>

Or add tne next dependency if you are running it on Android:

	<dependency>
		<groupId>com.gemserk.datastore</groupId>
		<artifactId>datastore-android</artifactId>
		<version>${datastore.version}</version> <!-- the version you want of datastore -->
	</dependency>

If you don't use maven on your projects, just run:

	mvn dependency:copy-dependencies

to download all depednencies of the project and add all of them to yours to make datastore to work correctly.

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
[datastore]: git://github.com/gemserk/datastore.git
[datastore-server]: https://github.com/gemserk/datastore-server
