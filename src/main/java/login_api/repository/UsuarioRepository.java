package login_api.repository;

import org.springframework.data.repository.ListCrudRepository;

import login_api.models.Usuario;

public interface UsuarioRepository extends ListCrudRepository<Usuario, Integer>{
	
	public Usuario findByLogin(String login);

	
}
