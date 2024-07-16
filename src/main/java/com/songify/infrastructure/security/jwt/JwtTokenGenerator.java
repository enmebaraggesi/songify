//package com.songify.infrastructure.security.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.songify.infrastructure.security.SecurityUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.security.KeyPair;
//import java.security.interfaces.RSAPrivateKey;
//import java.time.Clock;
//import java.time.Duration;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//@Component
//@RequiredArgsConstructor
//class JwtTokenGenerator {
//
//    public static final String ROLES_CLAIM_NAME = "roles";
//
//    private final AuthenticationManager authenticationManager;
//    private final Clock clock;
//    private final JwtConfigurationProperties properties;
//    private final KeyPair keyPair;
//
//    public String authenticateAndGenerateToken(final String username, final String password) {
//        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(authenticate);
//        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//
//        Instant issued = LocalDateTime.now(clock)
//                                      .toInstant(ZoneOffset.UTC);
//        Instant expires = issued.plus(Duration.ofMinutes(properties.expirationMinutes()));
////        Algorithm algorithm = Algorithm.HMAC256(properties.secret());
//        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) keyPair.getPrivate());
//        return JWT.create()
//                  .withSubject(securityUser.getUsername())
//                  .withIssuedAt(issued)
//                  .withExpiresAt(expires)
//                  .withIssuer(properties.issuer())
//                  .withClaim(ROLES_CLAIM_NAME, securityUser.getAuthoritiesAsString())
//                  .sign(algorithm);
//    }
//}
