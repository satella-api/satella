<!DOCTYPE html>
<html>
<body>
    <a href="https://satella.erllet.com">
        <img align="right" src="https://i.imgur.com/VqXDlaK.png" height="220" width="220">
    </a>
    <h1>Satella-API</h1>
    <p><b>a modular spigot plugin based on [Spring Boot Spigot Starter](hthttps://github.com/Alan-Gomes/mcspring-boottp:// "Spring Boot Spigot Starter")</b></p>
    <hr>
    <h2>Usage</h2>
</body>
</html>




First, make sure the repository is in your  `pom.xml`:

```xml
<repository>
 <id>satella-api</id>
 <url>https://artifactory.erllet.com/artifactory/libs-release-local</url>
</repository>
```

Then, make sure the dependency is in your `pom.xml`:

```xml
<dependency>
   <groupId>dev.leandro.erllet</groupId>
    <artifactId>satella-api</artifactId>
    <version>1.0</version>
    <scope>provided</scope>
</dependency>
```



to create a module define the `module` annotation with the parameter of the module name:

```java
@Module("warp")
public class WarpModule  {

}

```

the system automatically loads and records all events with the `@Component` annotation and all commands that extends `command`


### Creating commands

To create a command, just create a class similar to this example:
```java
@Subcommand
@CommandLine.Command(name = "create", aliases = {"criar", "set", "setar"})
public class WarpCreateCommand implements Callable<String> {

    @Autowired
    private WarpService warpService;

    @CommandLine.Parameters(index = "0", completionCandidates = WarpsSuggestionProvider.class)
    private String warpName;

    @CommandLine.Parameters(index = "1", defaultValue = "PUBLIC")
    private WarpType warpType;

    @Override
    public String call() {
        warpService.createWarp(warpName, warpType);
        return String.format("&aVocê criou a warp &e%s&a!", warpName);
    }

}
```

More examples are available in [Picocli](http://picocli.info/)  (thanks to [picocli-spring-boot-starter](https://github.com/kakawait/picocli-spring-boot-starter))


### Creating listeners

To create a listener, just create a class similar to this example:

```java
@Component
public class FirstLoginListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    // your code here
    }

}
```
full module example here [satella-warp-module](https://https://github.com/satella-api/home-module)
