# Quest Store

The store is the platform where you can use your well earned coins to buy certain events. The store items do not have any material value they represent activities or events that enhance the [Codecool](https://codecool.com/) experience. The store offers two type of items basic and extra (magic). Basic items are meant for individual buyers while extra items are designed to be purchased by a group of students. Assortment and prices can be change at the beginning of week, depending on demand and supply.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing

A step by step series of examples that tell you have to get a development env running (we won't explain how to install & setup postgresql DB)

Clone repository

```
git clone https://github.com/fbrzozowski/quest-store.git
```

Configure database connection in com.codecool.queststore.DAO.UserDAOImpl

```
private static final String DRIVER = "org.postgresql.Driver";
private static final String DB_URL = "jdbc:postgresql://DB_IP:PORT/";
private static final String DB_NAME = "DB_NAME";
private static final String userName = "DB_USER";
private static final String password = "DB_USER_PASSWD";
```

Compile and run
```
mvn compile exec:java -Dexec.mainClass=com.codecool.queststore.App
```
## Built With

* [Java](https://rometools.github.io/rome/) - General-purpose computer-programming language
* [PostgreSql](http://www.dropwizard.io/1.0.2/docs/) - The world's most advanced open source database
* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/fbrzozowski/quest-store/tags). 

## Authors

* **Golec Eliza** - [Elzacodecool](https://github.com/Elzacodecool)
* **Wrona Łukasz** - [wluka85](https://github.com/wluka85)
* **Brzozowski Filip** - [fbrzozowski](https://github.com/fbrzozowski)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
