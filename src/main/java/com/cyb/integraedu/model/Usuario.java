package com.cyb.integraedu.model;

public class Usuario {

	private final Long id;
	private final PerfilUsuario perfil;
	private final String nome;
	private final String email;
	private final String senha;
	private final String telefone;
	private final String areaAtuacao;
	private boolean emailVerificado;
	private String codigoVerificacao;

	public Usuario(Long id, PerfilUsuario perfil, String nome, String email, String senha, String telefone,
			String areaAtuacao, boolean emailVerificado, String codigoVerificacao) {
		this.id = id;
		this.perfil = perfil;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.areaAtuacao = areaAtuacao;
		this.emailVerificado = emailVerificado;
		this.codigoVerificacao = codigoVerificacao;
	}

	public Long getId() {
		return id;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getAreaAtuacao() {
		return areaAtuacao;
	}

	public boolean isEmailVerificado() {
		return emailVerificado;
	}

	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}

	public void verificarEmail() {
		this.emailVerificado = true;
		this.codigoVerificacao = null;
	}
}
