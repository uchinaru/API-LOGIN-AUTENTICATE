package login_api.controller;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login_api.models.Usuario;
import login_api.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class UsuarioController {
	
	public String retorno;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@RequestMapping(path = "/novo", method = {RequestMethod.POST,RequestMethod.PUT})
	public String cadastrar(Usuario usuario) {

		if (validate(usuario)) {
			try {
				usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt()));
				usuarioRepository.save(usuario);
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
	
	public boolean validate(Usuario usuario) {
		
		if(usuario.getLogin().isEmpty()) {
			retorno = "Prencha o campo login";
			return false;
		}
		
		if(usuario.getPassword().isEmpty()) {
			retorno = "Preencha o campo senha";
			return false;
		}
		
		if(usuario.getLogin().length() < 8) {
			retorno = "O login precisa ter no minimo 8 caracteres!";
			return false;
		}

		if(usuario.getPassword().length() < 8) {
			retorno = "A senha precisa ter no minimo 8 caracteres!";
			return false;
		}
		
		return true;
	}
}
