package com.cyb.integraedu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cyb.integraedu.model.Aluno;
import com.cyb.integraedu.model.CadastroForm;
import com.cyb.integraedu.model.LoginForm;
import com.cyb.integraedu.model.PerfilUsuario;
import com.cyb.integraedu.model.Usuario;
import com.cyb.integraedu.service.PortalService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class WebController {

	private final PortalService portalService;

	public WebController(PortalService portalService) {
		this.portalService = portalService;
	}

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("usuario", usuarioLogado(session));
		model.addAttribute("alunos", portalService.listarAlunos());
		model.addAttribute("totalResponsaveis", portalService.totalResponsaveis());
		model.addAttribute("totalProfessores", portalService.totalProfessores());
		model.addAttribute("reunioes", portalService.listarReunioes());
		model.addAttribute("avisos", portalService.listarAvisos());
		model.addAttribute("datasEspeciais", portalService.listarDatasEspeciais());
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String efetuarLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "login";
		}

		return portalService.autenticar(loginForm.getEmail(), loginForm.getSenha()).map(usuario -> {
			session.setAttribute("usuarioId", usuario.getId());
			redirectAttributes.addFlashAttribute("sucesso", "Bem-vindo(a) ao IntegraEDU.");
			return "redirect:/dashboard";
		}).orElseGet(() -> {
			redirectAttributes.addFlashAttribute("erro",
					"Login nao concluido. Verifique seu e-mail, senha ou confirmacao de cadastro.");
			return "redirect:/login";
		});
	}

	@GetMapping("/cadastro/responsavel")
	public String cadastroResponsavel(Model model) {
		model.addAttribute("cadastroForm", new CadastroForm());
		model.addAttribute("perfil", "responsavel");
		model.addAttribute("actionPath", "/cadastro/responsavel");
		return "cadastro";
	}

	@GetMapping("/cadastro/professor")
	public String cadastroProfessor(Model model) {
		CadastroForm form = new CadastroForm();
		form.setAreaAtuacao("Educacao infantil");
		model.addAttribute("cadastroForm", form);
		model.addAttribute("perfil", "professor");
		model.addAttribute("actionPath", "/cadastro/professor");
		return "cadastro";
	}

	@PostMapping("/cadastro/responsavel")
	public String salvarResponsavel(@Valid @ModelAttribute("cadastroForm") CadastroForm cadastroForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("perfil", "responsavel");
			model.addAttribute("actionPath", "/cadastro/responsavel");
			return "cadastro";
		}

		Usuario usuario = portalService.cadastrarUsuario(PerfilUsuario.RESPONSAVEL, cadastroForm.getNome(),
				cadastroForm.getEmail(), cadastroForm.getTelefone(), cadastroForm.getSenha(), "Acompanhamento familiar");
		redirectAttributes.addFlashAttribute("sucesso",
				"Cadastro realizado. Use o codigo " + usuario.getCodigoVerificacao() + " para validar o e-mail.");
		return "redirect:/verificacao-email?email=" + usuario.getEmail();
	}

	@PostMapping("/cadastro/professor")
	public String salvarProfessor(@Valid @ModelAttribute("cadastroForm") CadastroForm cadastroForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("perfil", "professor");
			model.addAttribute("actionPath", "/cadastro/professor");
			return "cadastro";
		}

		Usuario usuario = portalService.cadastrarUsuario(PerfilUsuario.PROFESSOR, cadastroForm.getNome(),
				cadastroForm.getEmail(), cadastroForm.getTelefone(), cadastroForm.getSenha(), cadastroForm.getAreaAtuacao());
		redirectAttributes.addFlashAttribute("sucesso",
				"Cadastro realizado. Use o codigo " + usuario.getCodigoVerificacao() + " para validar o e-mail.");
		return "redirect:/verificacao-email?email=" + usuario.getEmail();
	}

	@GetMapping("/verificacao-email")
	public String verificacaoEmail(@RequestParam(required = false) String email, Model model) {
		model.addAttribute("email", email);
		return "verificacao-email";
	}

	@PostMapping("/verificacao-email")
	public String validarEmail(@RequestParam String email, @RequestParam String codigo,
			RedirectAttributes redirectAttributes) {
		if (portalService.verificarEmail(email, codigo).isEmpty()) {
			redirectAttributes.addFlashAttribute("erro", "Codigo invalido. Confira o e-mail informado e tente novamente.");
			return "redirect:/verificacao-email?email=" + email;
		}

		redirectAttributes.addFlashAttribute("sucesso",
				"E-mail validado com sucesso. Agora voce ja pode acessar a plataforma.");
		return "redirect:/login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		Usuario usuario = usuarioLogado(session);
		if (usuario == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", usuario);
		model.addAttribute("alunos", portalService.listarAlunos());
		model.addAttribute("mensagens", portalService.listarMensagens().stream().limit(3).toList());
		model.addAttribute("reunioes", portalService.listarReunioes());
		model.addAttribute("avisos", portalService.listarAvisos());
		model.addAttribute("datasEspeciais", portalService.listarDatasEspeciais());
		return "dashboard";
	}

	@GetMapping("/alunos")
	public String alunos(Model model, HttpSession session) {
		return carregarAlunos(null, model, session);
	}

	@GetMapping("/alunos/{id}")
	public String alunoDetalhe(@PathVariable Long id, Model model, HttpSession session) {
		return carregarAlunos(id, model, session);
	}

	@GetMapping("/mensagens")
	public String mensagens(Model model, HttpSession session) {
		Usuario usuario = usuarioLogado(session);
		if (usuario == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", usuario);
		model.addAttribute("mensagens", portalService.listarMensagens());
		return "mensagens";
	}

	@GetMapping("/reunioes")
	public String reunioes(Model model, HttpSession session) {
		Usuario usuario = usuarioLogado(session);
		if (usuario == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", usuario);
		model.addAttribute("reunioes", portalService.listarReunioes());
		return "reunioes";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.invalidate();
		redirectAttributes.addFlashAttribute("sucesso", "Sessao encerrada com sucesso.");
		return "redirect:/";
	}

	private String carregarAlunos(Long id, Model model, HttpSession session) {
		Usuario usuario = usuarioLogado(session);
		if (usuario == null) {
			return "redirect:/login";
		}

		List<Aluno> alunos = portalService.listarAlunos();
		Aluno alunoSelecionado = id == null
				? alunos.get(0)
				: portalService.buscarAluno(id).orElse(alunos.get(0));

		model.addAttribute("usuario", usuario);
		model.addAttribute("alunos", alunos);
		model.addAttribute("alunoSelecionado", alunoSelecionado);
		return "alunos";
	}

	private Usuario usuarioLogado(HttpSession session) {
		Object usuarioId = session.getAttribute("usuarioId");
		return usuarioId instanceof Long id ? portalService.buscarUsuario(id).orElse(null) : null;
	}
}
