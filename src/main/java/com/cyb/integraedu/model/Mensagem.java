package com.cyb.integraedu.model;

public record Mensagem(String remetente, String destinatario, String assunto, String conteudo, String horario,
		String canal) {
}
