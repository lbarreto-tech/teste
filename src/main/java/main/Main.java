package main;

import dao.CargoDao;
import dao.UsuarioDao;
import dao.EmailDao;
import dao.SolicitacaoDao;
import dao.CriarSolicitacaoDao;
import dao.EspacoDao;
import model.UsuarioModel;
import model.EmailModel;
import model.SolicitacaoModel;
import model.EspacoModel;
import model.CargoModel;
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
                    // Buscar o id do usuário recém cadastrado
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
                    cargoLogado = cargoDao.verificaCargo(emailLogado, senhaLogado);
                    if (!cargoLogado.equals("www")) {
                        try {
                            Long idUser = criarSolicitacaoDao.getIdUsuarioPorEmailSenha(emailLogado, senhaLogado);
                            usuarioLogado = usuarioDao.BuscarPorId(idUser);
                            System.out.println("Login realizado como: " + usuarioLogado.getNomeUsuario() + " (" + cargoLogado + ")");
                        } catch (Exception e) {
                            System.out.println("Erro ao buscar usuário.");
                        }
                    } else {
                        System.out.println("Email ou senha inválidos!");
                    }
                    break;
                case 4:
                    if (usuarioLogado != null && cargoLogado.equalsIgnoreCase("solicitante")) {
                        try {
                            System.out.println("Espaços disponíveis:");
                            List<EspacoModel> espacos = espacoDao.listar();
                            for (EspacoModel esp : espacos) {
                                System.out.println("ID: " + esp.getIdEspaco() + ", Nome: " + esp.getNome());
                            }
                            System.out.print("Digite o nome do espaço: ");
                            String nomeEspaco = scanner.nextLine();
                            System.out.print("Data da reserva (dd/MM/yyyy): ");
                            String dataReserva = scanner.nextLine();
                            System.out.print("Horário início (HH:mm): ");
                            String horarioInicio = scanner.nextLine();
                            System.out.print("Horário fim (HH:mm): ");
                            String horarioFim = scanner.nextLine();
                            criarSolicitacaoDao.cadastrarSolicitacao(emailLogado, senhaLogado, dataReserva, horarioInicio, horarioFim, nomeEspaco);
                            System.out.println("Solicitação cadastrada!");
                        } catch (Exception e) {
                            System.out.println("Erro ao cadastrar solicitação: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Apenas solicitantes podem adicionar solicitações.");
                    }
                    break;
                case 5:
                    if (usuarioLogado != null && cargoLogado.equalsIgnoreCase("gestor")) {
                        try {
                            List<SolicitacaoModel> solicitacoes = solicitacaoDao.listarTodos();
                            for (SolicitacaoModel s : solicitacoes) {
                                System.out.println("ID: " + s.getIdSolicitacao() + ", Usuário: " + s.getIdUsuarioFk() + ", Espaço: " + s.getIdEspacoFK() + ", Status: " + s.getStatus());
                            }
                            System.out.print("Digite o ID da solicitação para aceitar/recusar: ");
                            Long idSol = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Digite 'A' para aceitar ou 'R' para recusar: ");
                            String acao = scanner.nextLine();
                            if (acao.equalsIgnoreCase("A")) {
                                solicitacaoDao.aceitarSolicitacao(idSol);
                                System.out.println("Solicitação aceita!");
                            } else if (acao.equalsIgnoreCase("R")) {
                                solicitacaoDao.delete(idSol);
                                System.out.println("Solicitação recusada e removida!");
                            } else {
                                System.out.println("Ação inválida!");
                            }
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
