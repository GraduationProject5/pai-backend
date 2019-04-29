package backend.util.JWThelper;

import backend.util.config.LoginProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.rmi.runtime.Log;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//JSON Web Token

public class JwtUtil {

    public static String    sercetKey   = LoginProperties.SercetKey ;
//    public static long      keeptime    = LoginProperties.KeepTime ;


    public static List<String> loginID_List = new ArrayList<>();

    public static boolean existToken(String token){
        String userID = "";
        try {
            userID = verify(token).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existUserID(userID);
    }

    public static boolean existUserID(String userID){
       if(loginID_List.contains(userID))
           return true;
       else
           return false;
    }

    public static void removeTokenFromList(String token){
        String userID = "";
        try {
            userID = verify(token).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loginID_List.contains(userID)){
            loginID_List.remove(userID);
        }
    }

    //Sample method to construct a JWT
    public static String generateToken(String id, String issuer, String subject){
//        long ttlMillis = keeptime ;

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(sercetKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder builder = Jwts.builder().setId(id)
                                .setIssuedAt(now)
                                .signWith(signatureAlgorithm, signingKey);
        if(subject!=null){
            builder.setSubject(subject);
        }
        if(issuer!=null){
            builder.setIssuer(issuer);
        }

        //if it has been specified, let's add the expiration
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }

        //Builds the JWT and serializes it to a compact, URL-safe string

        return builder.compact();
    }

    //在拦截器中更新
    public String verifyAndUpdateToken(String token){
        try {
            Claims claims=verify(token);

            String id=claims.getId();
            String subject=claims.getSubject();
            String issuer=claims.getIssuer();

            return generateToken(id, issuer, subject);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "0";
    }

    //在拦截器中更新
    public static Claims verify(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter
                .parseBase64Binary(sercetKey))
                .parseClaimsJws(token)
                .getBody();

        String userid = claims.getId();

        return claims;
    }

    public static String getUserID(String token) throws Exception {
        Claims claims = verify(token);
        return claims.getId();
    }
}
