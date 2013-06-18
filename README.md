# Getting Started: Creating a Login Page

What You'll Build
-----------------

This guide walks you through creating a simple web application that has resources that are protected with Spring Security and require login through a login form.

What You'll Need
----------------

- About 15 minutes
- A favorite text editor or IDE
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Maven 3.0][mvn] or later

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[mvn]: http://maven.apache.org/download.cgi

How to complete this guide
--------------------------

Like all Spring's [Getting Started guides](/getting-started), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

To **start from scratch**, move on to [Set up the project](#scratch).

To **skip the basics**, do the following:

 - [Download][zip] and unzip the source repository for this guide, or clone it using [git](/understanding/git):
`git clone https://github.com/springframework-meta/{@project-name}.git`
 - cd into `{@project-name}/initial`
 - Jump ahead to [Create a resource representation class](#initial).

**When you're finished**, you can check your results against the code in `{@project-name}/complete`.

<a name="scratch"></a>
Set up the project
------------------

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Maven](https://maven.apache.org) and [Gradle](http://gradle.org) is included here. If you're not familiar with either, refer to [Getting Started with Maven](../gs-maven/README.md) or [Getting Started with Gradle](../gs-gradle/README.md).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

### Create a Maven POM

`pom.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>org.springframework</groupId>
	<artifactId>gs-springsecurity-login-logout</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<parent>
		<groupId>org.springframework.bootstrap</groupId>
		<artifactId>spring-bootstrap-starters</artifactId>
		<version>0.5.0.BUILD-SNAPSHOT</version>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.bootstrap</groupId>
			<artifactId>spring-bootstrap-web-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-javaconfig</artifactId>
		</dependency>
		<dependency>
			<groupId>org.thymeleaf</groupId>
			<artifactId>thymeleaf-spring3</artifactId>
		</dependency>
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity3</artifactId>
			<version>2.0.0</version>
		</dependency>
	</dependencies>

	<!-- TODO: remove once bootstrap goes GA -->
	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>http://repo.springsource.org/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>http://repo.springsource.org/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>

</project>
```

TODO: mention that we're using Spring Bootstrap's [_starter POMs_](../gs-bootstrap-starter) here.

Note to experienced Maven users who are unaccustomed to using an external parent project: you can take it out later, it's just there to reduce the amount of code you have to write to get started.

<a name="initial"></a>
Setup Spring Security
---------------------
















































=== OLD STUFF ===


First you'll need to set up a basic build script. You can use any build system you like when building apps with Spring, but we've included what you'll need to work with [Maven](https://maven.apache.org) and [Gradle](http://gradle.org) here. If you're not familiar with either of these, you can refer to our [Getting Started with Maven](../gs-maven/README.md) or [Getting Started with Gradle](../gs-gradle/README.md) guides.

### Gradle

Create a Gradle buildfile that looks like this:

`pom.xml`
```xml
apply plugin: 'java'
apply plugin: 'eclipse-wtp'
apply plugin: 'idea'
apply plugin: 'application'

mainClassName = "hello.Main"

repositories {
    mavenCentral()
    maven {
        url "http://repo.springsource.org/snapshot"
    }
}

dependencies {
	compile "org.springframework.bootstrap:spring-bootstrap-web-starter:0.5.0.BUILD-SNAPSHOT"
	compile "org.springframework:spring-webmvc:3.2.0.RELEASE"
    compile "org.springframework.security:spring-security-acl:3.1.3.RELEASE"
    compile "org.mortbay.jetty:servlet-api:3.0.20100224"
    compile "org.springframework:spring-webmvc:3.2.0.RELEASE"
    compile "org.springframework.security:spring-security-javaconfig:1.0.0.CI-SNAPSHOT"
    compile "org.thymeleaf:thymeleaf-spring3:2.0.16"
    compile "org.thymeleaf.extras:thymeleaf-extras-springsecurity3:2.0.0"
}

task wrapper(type: Wrapper) {
	gradleVersion = '1.5'
}

```

> > > TODO: mention that we're using Spring Bootstrap's [_starter POMs_](../gs-bootstrap-starter) here.

Experienced Maven users who feel nervous about using an external parent project: don't panic, you can take it out later, it's just there to reduce the amount of code you have to write to get started.

### Maven

> > > TODO: paste complete pom.xml 

Add the following within the `dependencies { }` section of your build.gradle file:

Creating a Configuration Class for Our Spring MVC Web Application
------------------------------
The first step is to set up a simple Spring configuration class, `hello.WebConfiguration`, for the Spring MVC components in our application. It'll look like this:

`src/main/java/hello/WebConfiguration.java`

```java
package hello;

import org.springframework.bootstrap.context.annotation.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.*;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableWebMvc
@Import(WebSecurityConfiguration.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (Page p : Page.getInsecurePages()) {
            registry.addViewController(p.getUrl()).setViewName(p.getView());
        }
        for (Page p : Page.getSecurePages()) {
            registry.addViewController(p.getUrl()).setViewName(p.getView());
        }
    }

    @Bean
    public SpringTemplateEngine templateEngine(Collection<ITemplateResolver> templateResolvers) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        for (ITemplateResolver templateResolver : templateResolvers) {
            engine.addTemplateResolver(templateResolver);
        }
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

}

```

This class is fairly concise. [`@EnableWebMvc`](http://static.springsource.org/spring/docs/3.2.x/javadoc-api/org/springframework/web/servlet/config/annotation/EnableWebMvc.html) handles the registration of a number of components that enable Spring's support for annotation-based controllers—you'll build one of those in an upcoming step. And we've also annotated the configuration class with [`@ComponentScan`](http://static.springsource.org/spring/docs/3.2.x/javadoc-api/org/springframework/context/annotation/ComponentScan.html) which tells Spring to scan the `hello` package for those controllers (along with any other annotated component classes). 

We've registered a Thymeleaf `SpringTemplateEngine` bean, adding support for the Spring Security namespace support. 

We used a little convention-over-configuration-centric code to automatically map all the pages in our application to URLs in our `addViewControllers` method. A page `/foo` would map to the view called `foo`, for example. We also mapped the URL `/` to show the page `welcome`. To avoid repeating so-called *magic strings* in our code, we'll use an enum - `hello.Page` - to hold constants to represent each page in our application to simplify our security configuration and Spring MVC configuration. Here's the class(`src/main/java/hello/Pages.java`):

```java
package hello;

import java.util.*;

public enum Page {
    WELCOME, // a reception page. Also later mapped to '/'
    HOME(true), //
    LOGOUT,
    LOGIN,
    SLASH_ROOT("/", WELCOME.getView());
    private String url;
    private String view;
    private boolean secured;

    Page() {
        String name = name().toLowerCase();
        setup(name, name, false);
    }

    Page(boolean secured) {
        String name = name().toLowerCase();
        setup(name, name, secured);
    }

    Page(String url, String view) {
        setup(url, view, false);
    }

    public static Set<Page> getSecurePages() {
        Set<Page> pagesSet = new HashSet<Page>();
        for (Page p : Page.values())
            if (p.isSecured()) pagesSet.add(p);
        return pagesSet;
    }

    public static Collection<Page> getInsecurePages() {
        Set<Page> pagesSet = new HashSet<Page>();
        for (Page p : Page.values())
            if (!p.isSecured()) pagesSet.add(p);
        return pagesSet;
    }

    public boolean isSecured() {
        return this.secured;
    }

    public String getView() {
        return this.view;
    }

    public String getUrl() {
        return this.url;
    }

    private void setup(String u, String view, boolean secured) {
        if (!u.startsWith("/")) {
            u = '/' + u;
        }
        this.url = u;
        this.secured = secured;
        this.view = view;
    }
}

```

<a name="initial"></a>
Configuring a Spring Security Login Form 
----------------

Let's look now at our Spring Security configuration class that is `@Import`'ed into the `WebSecurityConfiguration` class. The class has the Spring Security `@EnableWebSecurity` annotation, and extends `WebSecurityConfigurerAdapter`. This base-class provides several no-op base methods and a fluent builder-style API that can be used to configure Spring Security.

We will do three things in this class (`src/main/java/hello/WebSecurityConfiguration`):
 * configure users and roles
 * protect certain resource URLs behind a login form
 * enable logout functionality


```java
package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.AuthenticationRegistry;
import org.springframework.security.config.annotation.web.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    protected void configure(HttpConfigurator http) throws Exception {

        // cordon off resources by URL
        ExpressionUrlAuthorizations expressionUrlAuthorizations = http.authorizeUrls();

        for (Page securePage : Page.getSecurePages())
            expressionUrlAuthorizations.antMatchers(securePage.getUrl()).hasRole(USER_ROLE);

        for (Page insecurePage : Page.getInsecurePages())
            expressionUrlAuthorizations.antMatchers(insecurePage.getUrl()).permitAll();

        // login
        http.formLogin()
                .defaultSuccessUrl(Page.HOME.getUrl())
                .permitAll();  // set permitAll for all URLs associated with Form Login

        // logout
        LogoutHandler logoutHandler = new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                if (null != authentication) {
                    System.out.println(String.format("logging the user ('%s') out!", "" + authentication.getName()));
                }
            }
        };
        http.logout()
                .logoutSuccessUrl(Page.WELCOME.getUrl())
                .addLogoutHandler(logoutHandler);
    }

    @Override
    protected void registerAuthentication(AuthenticationRegistry registry) throws Exception {
        registry.inMemoryAuthentication()
                .withUser("user").password("password").roles(USER_ROLE).and()
                .withUser("admin").password("password").roles(USER_ROLE, ADMIN_ROLE);
    }
}

```

We establish that our *secure* pages should be accessible to anyone, and our *insecure* pages should be accessible only to users of the application that have the role `USER_ROLE`. This check will be performed by consulting the authentication registry that we create in the `registerAuthentication` callback method. 

We also want any user context to be completely and safely destroyed upon logout, so we let Spring Security handle this for us with the `logout()` method on the `http` instance. 

The Login Form View
----
Let's first look at the HTML view for our login form (`src/main/resources/template/login.html`):
```
<!DOCTYPE html>
<html
   xmlns="http://www.w3.org/1999/xhtml" 
   xmlns:th="http://www.thymeleaf.org" 
   xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head><title>Spring Security Example </title></head>
<body>

<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>

</body>
</html>
```

The form is plain but the key ingredients are there: Spring Security is expecting a `username` and a `password` attribute to be `POST`'d to the `/login` endpoint in order to handle authentication requests. We could specify what request parameters are to be expected in our security configuration above. 

Working with the current Authentication
----
When a user is successfully authenticated, he is shown the `src/main/resources/templates/home.html` template, which simply displays some information about the currently authenticated user using the Spring Security *dialect* for Thymeleaf. The dialect installs support for a custom Thymeleaf XML namespace that can be used in our HTML markup, `sec:`. Let's look at the template:

```
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Spring Security Example
    </title>
</head>
<body>


<DIV><a th:href="@{/logout}">Sign Out</a></DIV>

<h1> Ohai!</h1>

<P> Welcome to the secret page of this application. </P>

<div sec:authorize="hasRole('ROLE_ADMIN')">
   <p> This content is only shown to administrators.</p>
</div>

<div sec:authorize="hasRole('ROLE_USER')">
   <p> This content is to shown to *all* authenticated users.</p>
</div>

<div>
    <strong>Logged-in user</strong>: <span sec:authentication="name">Bob</span> <br/>

    <strong>Roles</strong>: <span sec:authentication="principal.authorities">[ROLE_USER, ...]</span>
</div>
</body>
</html>
```

There's a link at the top of the page to the URL `/logout` that is processed by Spring Security and that cleans up any user context associated with the current `Authentication`, effectively signing the user out of the application.

We use the `sec:authorize` tag to evaluate an authorization expression and - if the expression returns true - render the tag's body. We use the `sec:authentication` tag to dereference properties of the current Spring Security `Authentication` object using the Spring Security Expression language and then emit the resulting values into the markup.

Related Resources
-----------------
There's a lot more to working with social APIs than simply fetching a user's name and friends. You can continue your exploration of Twitter, Facebook, and other APIs with the following Getting Started guides:

* [Accessing Facebook Data](../gs-accessing-facebook)
* Authenticating with Twitter
* Authenticating with Facebook

