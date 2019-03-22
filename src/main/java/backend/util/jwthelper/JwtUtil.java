package backend.util.jwthelper;

import io.jsonwebtoken.*;
import org.springframework.context.annotation.Bean;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

//JSON Web Token

public class JwtUtil {

    public static String    sercetKey   = "20190322Friday"  ;

    public static long      keeptime    = 60000;

    //Sample method to construct a JWT
    public static String generateToken(String id, String issuer, String subject){
        long ttlMillis = keeptime ;

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
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public String updateTokenBase64Code(String token){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            token=new String(base64Decoder.decodeBuffer(token),"utf-8");
            Claims claims=parseToken(token);
            String id=claims.getId();
            String subject=claims.getSubject();
            String issuer=claims.getIssuer();
            String newToken = generateToken(id, issuer, subject);
            return base64Encoder.encode(newToken.getBytes());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "0";
    }


    public static Claims parseToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter
                .parseBase64Binary(sercetKey))
                .parseClaimsJws(token)
                .getBody();

        return  claims;
    }
}
