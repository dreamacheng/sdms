package com.pro.it.common;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
	
	private final static long EXPIRE = 30 * 1000 * 60;
	
	private final static String TOKEN_SECRET = "3273af188536411486ae2639fdcaab12";
	
	public static String sign(String username) {
		//过期时间
		Date expire = new Date(System.currentTimeMillis() + EXPIRE);
		try {
			//加密算法
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			//构建token头部信息
			Map<String, Object> headMap = new HashMap<>(2);
			headMap.put("alg", "HS256");
			headMap.put("typ", "JWT");
			//构建token
			return JWT.create()
					.withHeader(headMap)
					.withClaim("username", username)
					.withExpiresAt(expire)
					.sign(algorithm);
			
		}catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static boolean verify(String token) {
		if(null == token) {
			return false;
		}
		//验证token有效性
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm)
					.build();
			verifier.verify(token);
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getParamInToken(String token, String key) {
		//解析token，获取有效载荷中存储的相关信息
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim(key).asString();
	}

}
