package com.cyb.integraedu.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CadastroForm {

	@NotBlank(message = "Informe o nome completo.")
	private String nome;

	@NotBlank(message = "Informe o e-mail.")
	@Email(message = "Digite um e-mail valido.")
	private String email;

	@NotBlank(message = "Informe o telefone.")
	private String telefone;

	@NotBlank(message = "Informe a senha.")
	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
	private String senha;

	private String areaAtuacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAreaAtuacao() {
		return areaAtuacao;
	}

	public void setAreaAtuacao(String areaAtuacao) {
		this.areaAtuacao = areaAtuacao;
	}
}
