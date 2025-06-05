<%@page import="model.SolicitacaoModel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Página Principal - Gestor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="css/gestor.css" rel="stylesheet">
</head>
<body class="bg-secondary">
    <main class="d-flex">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title mb-4">
                    <h2>
                        Gerenciar <b>Solicitações</b>
                    </h2>
                </div>

                <%
                    List<SolicitacaoModel> solicitacoes = (List<SolicitacaoModel>) request.getAttribute("lista");

                    if (solicitacoes == null || solicitacoes.isEmpty()) {
                %>
                    <div class="alert alert-light" role="alert">
                        Não há nenhuma solicitação 
                    </div>
                <%
                    } else {
                %>

                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>#</th>
                            <th>Espaço</th>
                            <th>Status</th>
                            <th>Data</th>
                            <th>Horário Início</th>
                            <th>Horário Fim</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (SolicitacaoModel solicitacao : solicitacoes) {
                        %>
                        <tr>
                            <td><%= solicitacao.getIdSolicitacao() %></td>
                            <td><%= solicitacao.getIdEspacoFK() %></td>
                            <td><%= solicitacao.getStatus() %></td>
                            <td><%= solicitacao.getDataReserva() %></td>
                            <td><%= solicitacao.getHorarioInicio() %></td>
                            <td><%= solicitacao.getHorarioFim() %></td>
                            <td>
                                <form method="post" action="GestorServlet" style="display: inline;">
                                    <input type="hidden" name="idSolicitacao" value="<%= solicitacao.getIdSolicitacao() %>" />
                                    <input type="hidden" name="acao" value="aceitar" />
                                    <button type="submit" class="btn btn-link text-success btn-icon" title="Aceitar">
                                        <i class="bi bi-check-circle-fill"></i>
                                    </button>
                                </form>

                                <form method="post" action="GestorServlet" style="display: inline;">
                                    <input type="hidden" name="idSolicitacao" value="<%= solicitacao.getIdSolicitacao() %>" />
                                    <input type="hidden" name="acao" value="recusar" />
                                    <button type="submit" class="btn btn-link text-danger btn-icon" title="Recusar"
                                        onclick="return confirm('Tem certeza que deseja recusar esta solicitação?');">
                                        <i class="bi bi-x-lg"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <%
                    }
                %>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
