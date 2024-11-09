package login_api.controller;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
@RequestMapping(value = "/api")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	EncryptUtils encryptUtils;

	public String retorno;
	
	@RequestMapping(path = "/novo", method = {RequestMethod.POST,RequestMethod.PUT})
	public String cadastrar(UsuarioDTO usuarioDTO) {

		if (validate(usuarioDTO)) {
			try {
				
				Usuario user = new Usuario();
				user.setLogin(usuarioDTO.getLogin());
				user.setPassword(encryptUtils.encryptPassword(usuarioDTO.getPassword()));
				user.setToken(jwtUtils.gerarToken(usuarioDTO));
				
				usuarioRepository.save(user);
				return "Cadastrado com sucesso!";
			} catch (Exception e) {
				System.out.println("Error ao cadastrar");
				return "Error ao cadastrar";
			} 
		}else {
			return retorno;
		}
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public void deletar(@PathVariable int id) {
		usuarioRepository.deleteById(id);
	}

	@GetMapping(path = "/usuarios")
	public List<Usuario> getUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public boolean validate(UsuarioDTO usuarioDTO) {
		
		if(usuarioDTO.getLogin().isEmpty()) {
			retorno = "Prencha o campo login";
			return false;
		}
		
		if(usuarioDTO.getPassword().isEmpty()) {
			retorno = "Preencha o campo senha";
			return false;
		}

		if(usuarioDTO.getPalavraSecreta().isEmpty()) {
			retorno = "Preencha o campo palavra secreta";
			return false;
		}
		
		if(usuarioDTO.getLogin().length() < 8) {
			retorno = "O login precisa ter no minimo 8 caracteres!";
			return false;
		}

		if(usuarioDTO.getPassword().length() < 8) {
			retorno = "A senha precisa ter no minimo 8 caracteres!";
			return false;
		}

		if(usuarioDTO.getPalavraSecreta().length() < 8) {
			retorno = "A palavra secreta precisa ter no minimo 8 caracteres!";
			return false;
		}
		return true;
	}
}
