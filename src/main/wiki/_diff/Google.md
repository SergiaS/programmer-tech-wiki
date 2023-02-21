# Google
Different Google API info.

## Integrating Google Sign-In
* [Integrating Google Sign-In into your web app](https://developers.google.com/identity/sign-in/web/sign-in)

## Google OAuth

***
Spring Boot example to see all fields form Google client account:
```java
@GetMapping("/user")
public Map<String, Object> user(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
  return oAuth2AuthenticationToken.getPrincipal().getAttributes();
}
```

