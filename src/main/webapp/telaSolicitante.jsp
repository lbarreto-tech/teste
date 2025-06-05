<%@page import="java.util.List"%>
<%@page import="model.SolicitacaoModel"%>
<%@page import="model.EspacoModel"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tela Principal - Solicitante</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table-wrapper {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-top: 40px;
        }
        .table-title h2 {
            color: #343a40;
        }
    </style>
</head>
<body class="bg-secondary">
<main class="d-flex">
    <div class="container">
        <div class="table-wrapper">
            <div class="table-title mb-4 d-flex justify-content-between align-items-center">
                <h2>Minhas <b>Solicitações</b></h2>
                <a href="#novaSolicitacao" class="btn btn-primary" data-bs-toggle="modal">
                    <i class="bi bi-plus-circle"></i> Nova Solicitação
                </a>
            </div>

            <%
                List<SolicitacaoModel> solicitacoes = (List<SolicitacaoModel>) request.getAttribute("lista");
                if (solicitacoes == null || solicitacoes.isEmpty()) {
            %>
            <div class="alert alert-light" role="alert">Você não fez nenhuma solicitação!</div>
            <%
                } else {
            %>

            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Espaço</th>
                        <th>Data</th>
                        <th>Horário Início</th>
                        <th>Horário Fim</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (SolicitacaoModel solicitacao : solicitacoes) {
                    %>
                    <tr>
                        <td><%= solicitacao.getIdEspacoFK() %></td>
                        <td><%= solicitacao.getDataReserva() %></td>
                        <td><%= solicitacao.getHorarioInicio() %></td>
                        <td><%= solicitacao.getHorarioFim() %></td>
                        <td><%= solicitacao.getStatus() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <% } %>
        </div>
    </div>
</main>

<!-- Modal Nova Solicitação -->
<div class="modal fade" id="novaSolicitacao" tabindex="-1" aria-labelledby="novaSolicitacaoLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="SolicitacaoServlet" method="POST">
