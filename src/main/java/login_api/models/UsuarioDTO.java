package login_api.models;

import lombok.Data;

@Data

public class UsuarioDTO {

	private String login;
	
	private String password;
	
	private String palavraSecreta;
}
