package com.cyb.integraedu.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.cyb.integraedu.model.AgendaItem;
import com.cyb.integraedu.model.Aluno;
import com.cyb.integraedu.model.AvisoInstitucional;
import com.cyb.integraedu.model.DataEspecial;
import com.cyb.integraedu.model.DesafioItem;
import com.cyb.integraedu.model.Mensagem;
import com.cyb.integraedu.model.PerfilUsuario;
import com.cyb.integraedu.model.ProgressoItem;
import com.cyb.integraedu.model.ReuniaoOnline;
import com.cyb.integraedu.model.Usuario;

import jakarta.annotation.PostConstruct;

@Service
public class PortalService {

	private final Map<Long, Usuario> usuarios = new LinkedHashMap<>();
	private final Map<Long, Aluno> alunos = new LinkedHashMap<>();
	private final List<Mensagem> mensagens = new ArrayList<>();
	private final List<ReuniaoOnline> reunioes = new ArrayList<>();
	private final List<AvisoInstitucional> avisos = new ArrayList<>();
	private final List<DataEspecial> datasEspeciais = new ArrayList<>();
	private final AtomicLong usuarioId = new AtomicLong(10);

	@PostConstruct
	void init() {
		usuarios.put(1L, new Usuario(1L, PerfilUsuario.RESPONSAVEL, "Mariana Souza", "mariana@integraedu.com",
				"123456", "(27) 99999-1234", "Acompanhamento familiar", true, null));
		usuarios.put(2L, new Usuario(2L, PerfilUsuario.PROFESSOR, "Prof. Lucas Almeida", "lucas@integraedu.com",
				"123456", "(27) 98888-4321", "Educacao infantil e alfabetizacao", true, null));

		alunos.put(1L, new Aluno(1L, "Miguel Ferreira", 4, "Maternal III - Turma Estrela", "Mariana Souza",
				"Calmo e concentrado",
				"Comunicado aos pais: Miguel teve boa participacao nas atividades motoras e respondeu bem aos combinados da rotina.",
				List.of(new AgendaItem("08:00", "Boas-vindas", "Chegada com acolhimento individual.", "Acolhimento"),
						new AgendaItem("09:30", "Circuito motor", "Saltos, equilibrio e coordenacao ampla.", "Movimento"),
						new AgendaItem("11:00", "Lanche e higiene", "Pais informaram que Miguel dormiu pouco; turma manteve rotina mais tranquila.", "Particularidade"),
						new AgendaItem("14:00", "Jogo cooperativo", "Atividade em dupla com blocos e cores.", "Rotina")),
				List.of(new ProgressoItem("Coordenacao motora", "Mais equilibrio e seguranca nos deslocamentos.", 79,
						"Em crescimento"),
						new ProgressoItem("Atencao compartilhada", "Mantem foco por mais tempo em propostas guiadas.", 71,
								"Precisa continuidade"),
						new ProgressoItem("Participacao", "Responde bem a convites e combinados simples.", 84,
								"Bom engajamento")),
				List.of(new DesafioItem("Inicio de tarefa",
						"Quando a sala esta mais agitada, demora a iniciar as propostas sozinho.",
						"Reduzir distracoes e reforcar a primeira etapa da atividade."),
						new DesafioItem("Comunicacao de frustracao",
								"Em alguns momentos, se afasta sem verbalizar o que sentiu.",
								"Usar painel de humor com opcoes visuais no inicio e fim das aulas."))));

		alunos.put(2L, new Aluno(2L, "Sofia Souza", 5, "Jardim II - Turma Girassol", "Mariana Souza", "Feliz e curiosa",
				"Comunicado aos pais: Sofia participou da roda de leitura, concluiu a atividade de pintura e interagiu com autonomia no lanche.",
				List.of(new AgendaItem("07:30", "Recepcao acolhedora", "Entrada com musica e combinados do dia.", "Acolhimento"),
						new AgendaItem("09:00", "Contacao de historia", "Leitura do livro do dia.", "Pedagogico"),
						new AgendaItem("10:45", "Momento do lanche", "Incentivo a autonomia no cuidado pessoal.", "Rotina"),
						new AgendaItem("13:30", "Observacao da professora", "Foi necessario informar a familia sobre sensibilidade na transicao apos o descanso.", "Comportamento")),
				List.of(new ProgressoItem("Comunicacao", "Expande frases e compartilha sentimentos com mais clareza.", 82,
						"Evolucao consistente"),
						new ProgressoItem("Interacao social", "Procura colegas para brincar e respeita turnos.", 76,
								"Boa adaptacao"),
						new ProgressoItem("Autonomia", "Organiza mochila e participa da higiene com supervisao leve.", 88,
								"Ponto forte")),
				List.of(new DesafioItem("Transicao entre atividades",
						"Ainda precisa de avisos visuais curtos para trocar de proposta sem frustracao.",
						"Antecipar a proxima atividade com cartoes e contagem regressiva."),
						new DesafioItem("Expressao de cansaco",
								"Em dias mais intensos, demonstra irritacao antes de pedir pausa.",
								"Oferecer canto tranquilo e reforcar o pedido verbal de descanso."))));

		mensagens.add(new Mensagem("Prof. Lucas Almeida", "Mariana Souza", "Desenvolvimento da Sofia",
				"Sofia participou bem das atividades coletivas. O relatorio completo segue restrito ao professor e a instituicao; compartilhamos com a familia os pontos necessarios para acompanhamento.",
				"Hoje, 15:20", "Professor > Responsavel"));
		mensagens.add(new Mensagem("Mariana Souza", "Prof. Lucas Almeida", "Acontecimento especifico",
				"Sofia acordou durante a madrugada e pode ficar mais sensivel no periodo da tarde. Se necessario, pode oferecer um momento mais tranquilo.",
				"Hoje, 16:05", "Responsavel > Professor"));
		mensagens.add(new Mensagem("Coordenacao IntegraEDU", "Familias", "Autorizacao para profissional de saude",
				"O acesso ao relatorio de desenvolvimento por profissionais de saude depende de autorizacao dos pais e da instituicao. Questionarios serao incluidos em uma proxima versao.",
				"Ontem, 18:40", "Institucional"));

		reunioes.add(new ReuniaoOnline("Reuniao de alinhamento com a familia", "24/03/2026", "19:00",
				"Prof. Lucas Almeida", "https://meet.google.com/integraedu-familias",
				"Rotina da semana, comunicacao familia-escola e particularidades do aluno.",
				"Pais podem solicitar uma nova data pelo portal caso nao consigam participar."));
		reunioes.add(new ReuniaoOnline("Plantao pedagogico online", "26/03/2026", "18:30", "Coordenacao Pedagogica",
				"https://meet.google.com/integraedu-plantao",
				"Tira-duvidas com pais e professores.",
				"Ha opcoes em 27/03 as 18:30 e 28/03 as 09:00."));
		reunioes.add(new ReuniaoOnline("Formacao docente sobre humor e autorregulacao", "28/03/2026", "14:00",
				"Equipe IntegraEDU", "https://meet.google.com/integraedu-formacao",
				"Boas praticas para registro de humor e acolhimento.",
				"Visivel para professores e instituicao."));

		avisos.add(new AvisoInstitucional("Entrega de autorizacoes", "Enviar autorizacao assinada para passeio pedagogico ate sexta-feira.",
				"20/03/2026", "Alta"));
		avisos.add(new AvisoInstitucional("Agenda individual", "Pais podem informar particularidades do aluno antes do inicio da aula.",
				"Diariamente", "Rotina"));
		avisos.add(new AvisoInstitucional("Saude e desenvolvimento",
				"Profissionais de saude so acessam relatorios apos autorizacao dos pais e da instituicao.",
				"Em analise", "Privacidade"));

		datasEspeciais.add(new DataEspecial("22/03/2026", "Dia da agua", "Atividade ludica com garrafinha identificada.", "Data especial"));
		datasEspeciais.add(new DataEspecial("29/03/2026", "Reuniao de familias", "Encontro por turma com possibilidade de escolha de nova data.", "Reuniao"));
		datasEspeciais.add(new DataEspecial("02/04/2026", "Mostra de trabalhos", "Exposicao interna acompanhada pelos professores.", "Institucional"));
	}

	public Usuario cadastrarUsuario(PerfilUsuario perfil, String nome, String email, String telefone, String senha,
			String areaAtuacao) {
		Long id = usuarioId.incrementAndGet();
		Usuario usuario = new Usuario(id, perfil, nome, email, senha, telefone, areaAtuacao, false,
				"EDU-" + id + "2026");
		usuarios.put(id, usuario);
		return usuario;
	}

	public Optional<Usuario> autenticar(String email, String senha) {
		return usuarios.values().stream()
			.filter(usuario -> usuario.getEmail().equalsIgnoreCase(email) && usuario.getSenha().equals(senha))
			.findFirst()
			.filter(Usuario::isEmailVerificado);
	}

	public Optional<Usuario> buscarUsuario(Long id) {
		return Optional.ofNullable(usuarios.get(id));
	}

	public Optional<Usuario> buscarPorEmail(String email) {
		return usuarios.values().stream().filter(usuario -> usuario.getEmail().equalsIgnoreCase(email)).findFirst();
	}

	public Optional<Usuario> verificarEmail(String email, String codigo) {
		return buscarPorEmail(email).filter(usuario -> codigo.equalsIgnoreCase(usuario.getCodigoVerificacao())).map(usuario -> {
			usuario.verificarEmail();
			return usuario;
		});
	}

	public List<Aluno> listarAlunos() {
		return alunos.values().stream().sorted(Comparator.comparing(Aluno::getNome)).toList();
	}

	public Optional<Aluno> buscarAluno(Long id) {
		return Optional.ofNullable(alunos.get(id));
	}

	public List<Mensagem> listarMensagens() {
		return List.copyOf(mensagens);
	}

	public List<ReuniaoOnline> listarReunioes() {
		return List.copyOf(reunioes);
	}

	public List<AvisoInstitucional> listarAvisos() {
		return List.copyOf(avisos);
	}

	public List<DataEspecial> listarDatasEspeciais() {
		return List.copyOf(datasEspeciais);
	}

	public long totalResponsaveis() {
		return usuarios.values().stream().filter(usuario -> usuario.getPerfil() == PerfilUsuario.RESPONSAVEL).count();
	}

	public long totalProfessores() {
		return usuarios.values().stream().filter(usuario -> usuario.getPerfil() == PerfilUsuario.PROFESSOR).count();
	}
}
