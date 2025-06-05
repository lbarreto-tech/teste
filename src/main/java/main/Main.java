package main;

import dao.CargoDao;
import dao.UsuarioDao;
import dao.EmailDao;
import dao.SolicitacaoDao;
import dao.CriarSolicitacaoDao;
import dao.EspacoDao;
import dao.AvaliacaoDao;
import dao.AuditoriaDao;
import model.UsuarioModel;
import model.EmailModel;
import model.SolicitacaoModel;
import model.EspacoModel;
import model.CargoModel;
import model.AvaliacaoModel;
import model.AuditoriaModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CargoDao cargoDao = new CargoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        EmailDao emailDao = new EmailDao();
        SolicitacaoDao solicitacaoDao = new SolicitacaoDao();
        CriarSolicitacaoDao criarSolicitacaoDao = new CriarSolicitacaoDao();
        EspacoDao espacoDao = new EspacoDao();
        UsuarioModel usuarioLogado = null;
        String emailLogado = null;
        String senhaLogado = null;
        String cargoLogado = null;
        while (true) {
            System.out.println("==== MENU PRINCIPAL ====");
            System.out.println("1. Gerenciar cargos");
            System.out.println("2. Adicionar usuário");
            System.out.println("3. Login");
            if (usuarioLogado != null) {
                System.out.println("4. Adicionar solicitação (solicitante)");
                System.out.println("5. Aceitar/Recusar solicitações (gestor)");
                System.out.println("6. Logout");
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao == 0) break;
            switch (opcao) {
                case 1:
                    while (true) {
                        System.out.println("==== MENU CARGO ====");
                        System.out.println("1. Listar todos os cargos");
                        System.out.println("2. Adicionar novo cargo");
                        System.out.println("3. Buscar cargo por ID");
                        System.out.println("4. Deletar cargo por ID");
                        System.out.println("0. Voltar");
                        System.out.print("Escolha uma opção: ");
                        int opCargo = scanner.nextInt();
                        scanner.nextLine();
                        if (opCargo == 0) break;
                        switch (opCargo) {
                            case 1:
                                List<CargoModel> cargos = cargoDao.listarTodos();
                                for (CargoModel c : cargos) {
                                    System.out.println("ID: " + c.getIdCargo() + ", Nome: " + c.getNomeCargo());
                                }
                                break;
                            case 2:
                                System.out.print("Digite o nome do cargo: ");
                                String nome = scanner.nextLine();
                                CargoModel novoCargo = new CargoModel();
                                novoCargo.setNomeCargo(nome);
                                cargoDao.salvar(novoCargo);
                                break;
                            case 3:
                                System.out.print("Digite o ID do cargo: ");
                                Long idBusca = scanner.nextLong();
                                scanner.nextLine();
                                CargoModel cargo = cargoDao.listarPorId(idBusca);
                                System.out.println("ID: " + cargo.getIdCargo() + ", Nome: " + cargo.getNomeCargo());
                                break;
                            case 4:
                                System.out.print("Digite o ID do cargo para deletar: ");
                                Long idDel = scanner.nextLong();
                                scanner.nextLine();
                                cargoDao.deletar(idDel);
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Digite o nome do usuário: ");
                    String nomeUsuario = scanner.nextLine();
                    System.out.print("Digite o ID do cargo: ");
                    Long idCargo = scanner.nextLong();
                    scanner.nextLine();
                    UsuarioModel novoUsuario = new UsuarioModel();
                    novoUsuario.setNomeUsuario(nomeUsuario);
                    novoUsuario.setIdCargoFK(idCargo);
                    usuarioDao.insert(novoUsuario);
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();
                    EmailModel emailModel = new EmailModel();
                    emailModel.setEnderecoEmail(email);
                    emailModel.setSenha(senha);
                    ArrayList<UsuarioModel> usuarios = usuarioDao.listarTodos();
                    Long idUsuario = usuarios.get(usuarios.size()-1).getIdUsuario();
                    emailModel.setIdUsuarioFk(idUsuario);
                    emailDao.salvar(emailModel);
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o email: ");
                    emailLogado = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    senhaLogado = scanner.nextLine();
                    try {
                        Long idUser = criarSolicitacaoDao.getIdUsuarioPorEmailSenha(emailLogado, senhaLogado);
                        usuarioLogado = usuarioDao.BuscarPorId(idUser);
                        cargoLogado = cargoDao.verificaCargo(emailLogado, senhaLogado);
                        System.out.println("Login realizado como: " + usuarioLogado.getNomeUsuario() + " (" + cargoLogado + ")");
                    } catch (Exception e) {
                        usuarioLogado = null;
                        cargoLogado = null;
                        System.out.println("Email ou senha inválidos!");
                    }
                    break;
                case 4:
                    if (usuarioLogado != null && cargoLogado != null && cargoLogado.trim().toLowerCase().equals("solicitante")) {
                        try {
                            System.out.println("Espaços disponíveis:");
                            List<EspacoModel> espacos = espacoDao.listar();
                            for (EspacoModel esp : espacos) {
                                System.out.println("ID: " + esp.getIdEspaco() + ", Nome: " + esp.getNome());
                            }
                            System.out.print("Digite o id do espaço: ");
                            Long idEspaco = Long.parseLong(scanner.nextLine());
                            System.out.print("Data da reserva (dd/MM/yyyy): ");
                            String dataReserva = scanner.nextLine();
                            System.out.print("Horário início (HH:mm): ");
                            String horarioInicio = scanner.nextLine();
                            System.out.print("Horário fim (HH:mm): ");
                            String horarioFim = scanner.nextLine();
                            criarSolicitacaoDao.cadastrarSolicitacaoPorId(emailLogado, senhaLogado, dataReserva, horarioInicio, horarioFim, idEspaco);
                            System.out.println("Solicitação cadastrada!");
                            // Auditoria do cadastro de solicitação
                            AuditoriaDao auditoriaDao = new AuditoriaDao();
                            AuditoriaModel auditoria = new AuditoriaModel();
                            auditoria.setIdUsuarioFk(usuarioLogado.getIdUsuario());
                            auditoria.setDataAcao(java.sql.Date.valueOf(LocalDate.now()));
                            auditoria.setAcao("Cadastro de solicitação (Espaço ID: " + idEspaco + ")");
                            auditoriaDao.salvar(auditoria);
                            System.out.println("--- Auditoria registrada ---");
                        } catch (Exception e) {
                            System.out.println("Erro ao cadastrar solicitação: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Apenas solicitantes podem adicionar solicitações.");
                    }
                    break;
                case 5:
                    if (usuarioLogado != null && cargoLogado != null && cargoLogado.trim().toLowerCase().equals("gestor")) {
                        try {
                            List<SolicitacaoModel> solicitacoes = solicitacaoDao.listarTodos();
                            for (SolicitacaoModel s : solicitacoes) {
                                UsuarioModel usuario = usuarioDao.BuscarPorId(s.getIdUsuarioFk());
                                String nomeUsuarioSolicitacao = usuario != null ? usuario.getNomeUsuario() : "(desconhecido)";
                                EspacoModel espaco = espacoDao.FindById(s.getIdEspacoFK());
                                String nomeEspaco = espaco != null ? espaco.getNome() : "(desconhecido)";
                                System.out.println("ID: " + s.getIdSolicitacao() + ", Usuário: " + nomeUsuarioSolicitacao + ", Espaço: " + nomeEspaco + ", Status: " + s.getStatus());
                            }
                            System.out.print("Digite o ID da solicitação para avaliar: ");
                            Long idSol = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Digite 'A' para aprovar ou 'R' para rejeitar: ");
                            String acao = scanner.nextLine();
                            System.out.print("Digite a justificativa da avaliação: ");
                            String justificativa = scanner.nextLine();
                            Long idGestor = usuarioLogado.getIdUsuario();
                            String statusAvaliacao;
                            String acaoAuditoria;
                            if (acao.equalsIgnoreCase("A")) {
                                solicitacaoDao.aceitarSolicitacao(idSol);
                                statusAvaliacao = "APROVADO";
                                acaoAuditoria = "Aprovação de solicitação";
                                System.out.println("Solicitação aprovada!");
                            } else if (acao.equalsIgnoreCase("R")) {
                                solicitacaoDao.rejeitarSolicitacao(idSol);
                                statusAvaliacao = "REJEITADO";
                                acaoAuditoria = "Rejeição de solicitação";
                                System.out.println("Solicitação rejeitada!");
                            } else {
                                System.out.println("Ação inválida!");
                                break;
                            }
                            // Salvar avaliação no banco
                            AvaliacaoDao avaliacaoDao = new AvaliacaoDao();
                            AvaliacaoModel avaliacao = new AvaliacaoModel();
                            avaliacao.setIdGestorFk(idGestor);
                            avaliacao.setJustificativa(justificativa);
                            avaliacao.setIdSolicitacaoFk(idSol);
                            avaliacao.setStatus(statusAvaliacao);
                            String dataAvaliacao = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            avaliacao.setDataAvaliacao(dataAvaliacao);
                            avaliacaoDao.salvar(avaliacao);
                            // Auditoria da avaliação
                            AuditoriaDao auditoriaDao = new AuditoriaDao();
                            AuditoriaModel auditoria = new AuditoriaModel();
                            auditoria.setIdUsuarioFk(idGestor);
                            auditoria.setDataAcao(java.sql.Date.valueOf(dataAvaliacao));
                            auditoria.setAcao("Avaliação: " + acaoAuditoria + " (Solicitação ID: " + idSol + ")");
                            auditoriaDao.salvar(auditoria);
                            System.out.println("--- Avaliação registrada ---");
                            System.out.println("ID do gestor: " + idGestor);
                            System.out.println("Justificativa: " + justificativa);
                            System.out.println("ID da solicitação: " + idSol);
                            System.out.println("Status: " + statusAvaliacao);
                        } catch (Exception e) {
                            System.out.println("Erro ao processar solicitação: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Apenas gestores podem aceitar/recusar solicitações.");
                    }
                    break;
                case 6:
                    usuarioLogado = null;
                    emailLogado = null;
                    senhaLogado = null;
                    cargoLogado = null;
                    System.out.println("Logout realizado.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Aplicação encerrada.");
    }
}
