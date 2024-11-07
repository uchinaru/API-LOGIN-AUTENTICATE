package login_api.utils;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import login_api.models.Usuario;

@Service
public class JwtUtils {

	String token;

	public String gerarToken(Usuario usuario) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(usuario.getToken());

			token = JWT.create().withIssuer(usuario.getLogin()).sign(algorithm);

			return token;
		} catch (JWTCreationException exception) {
			throw new JWTCreationException("Erro ao gerar o token: ", exception);
		}
	}

	public String getToken(Usuario usuario) {
		Algorithm algorithm = Algorithm.HMAC256(usuario.getToken());
		try {
			token = JWT.require(algorithm).withIssuer(usuario.getLogin()).build().verify(usuario.getToken())
					.getSubject();
			return token;
		} catch (Exception e) {
			throw new JWTVerificationException("Token inválido ou expirado!");
		}
	}
}
