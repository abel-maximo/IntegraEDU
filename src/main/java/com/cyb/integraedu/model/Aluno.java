package com.cyb.integraedu.model;

import java.util.List;

public class Aluno {

	private final Long id;
	private final String nome;
	private final int idade;
	private final String turma;
	private final String responsavel;
	private final String humorAtual;
	private final String resumoDia;
	private final List<AgendaItem> agenda;
	private final List<ProgressoItem> progresso;
	private final List<DesafioItem> desafios;

	public Aluno(Long id, String nome, int idade, String turma, String responsavel, String humorAtual, String resumoDia,
			List<AgendaItem> agenda, List<ProgressoItem> progresso, List<DesafioItem> desafios) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.turma = turma;
		this.responsavel = responsavel;
		this.humorAtual = humorAtual;
		this.resumoDia = resumoDia;
		this.agenda = agenda;
		this.progresso = progresso;
		this.desafios = desafios;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	public String getTurma() {
		return turma;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public String getHumorAtual() {
		return humorAtual;
	}

	public String getResumoDia() {
		return resumoDia;
	}

	public List<AgendaItem> getAgenda() {
		return agenda;
	}

	public List<ProgressoItem> getProgresso() {
		return progresso;
	}

	public List<DesafioItem> getDesafios() {
		return desafios;
	}
}
