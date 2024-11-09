package login_api.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login_api.models.Usuario;
import login_api.models.UsuarioDTO;
import login_api.repository.UsuarioRepository;

@Service
public class EncryptUtils {
	
	@Autowired
	UsuarioRepository UsuarioRepository;
	
	public String encryptPassword(String password) {
		try {
			return BCrypt.hashpw(password, BCrypt.gensalt());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean decryptUserPassword(UsuarioDTO usuarioDTO) {

		try {
			Usuario user = UsuarioRepository.findByLogin(usuarioDTO.getLogin());

			if (user != null) {
				boolean retorno = BCrypt.checkpw(usuarioDTO.getPassword(), user.getPassword());
				
				return retorno;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
