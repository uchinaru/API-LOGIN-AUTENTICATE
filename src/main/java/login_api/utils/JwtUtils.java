package login_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import login_api.models.Usuario;
import login_api.models.UsuarioDTO;
import login_api.repository.UsuarioRepository;

@Service
public class JwtUtils {

	@Autowired
	UsuarioRepository userRepo;
	
	String token;

	public String gerarToken(UsuarioDTO usuario) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(usuario.getPalavraSecreta());

			token = JWT.create().withIssuer(usuario.getLogin()).sign(algorithm);

			return token;
		} catch (JWTCreationException exception) {
			throw new JWTCreationException("Erro ao gerar o token: ", exception);
		}
	}

	public String getToken(UsuarioDTO usuarioDTO) {
		Algorithm algorithm = Algorithm.HMAC256(usuarioDTO.getPalavraSecreta());
		
		Usuario user = userRepo.findByLogin(usuarioDTO.getLogin());
		
		if(user != null) {
			
			try {
				token = JWT.require(algorithm)
						.withIssuer(user.getLogin()).build()
						.verify(user.getToken())
						.getSubject();
				
				return token;
			} catch (Exception e) {
				throw new JWTVerificationException("Token inválido ou expirado!");
			}
		}else {
			return null;
		}

	}
}
