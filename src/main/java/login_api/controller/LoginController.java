package login_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login_api.models.Usuario;
import login_api.models.UsuarioDTO;
import login_api.repository.UsuarioRepository;
import login_api.utils.EncryptUtils;
import login_api.utils.JwtUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api")
public class LoginController {
	
	public String retorno;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EncryptUtils encryptUtils;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@RequestMapping(path = "/logar", method = {RequestMethod.POST,RequestMethod.PUT})
	public String logar(UsuarioDTO usuarioDTO) {
		
		Usuario user = usuarioRepository.findByLogin(usuarioDTO.getLogin());
		
		if(user != null) {
			
			if( validate(user, usuarioDTO) ){
				retorno = "Logado com sucesso!";
			}
			
		}else {
			retorno = "Usuário não localizado!";
		}
		
		return retorno;
	}

	private boolean validate(Usuario user, UsuarioDTO usuarioDTO) {
		
		if ( encryptUtils.decryptUserPassword(usuarioDTO) && jwtUtils.gerarToken(usuarioDTO).equals(user.getToken()) ) {
			return true;
		}
		
		return false;
	}
	
}
