<%@page import="java.util.List"%>
<%@page import="model.SolicitacaoModel"%>
<%@page import="model.EspacoModel"%>
<%@page import="dao.EspacoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tela Principal</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
}
.table-wrapper {
	background: white;
	border-radius: 12px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	padding: 30px;
}
.table-title h2 {
	color: #343a40;
}
.btn-icon {
	font-size: 1.2rem;
	margin: 0 5px;
}
</style>
</head>
<body class="bg-secondary">
	<main class="d-flex">
		<div class="container">
			<div class="table-wrapper">
				<div
					class="table-title mb-4 d-flex justify-content-between align-items-center">
					<h2>
						Minhas <b>Solicitações</b>
					</h2>
					<a href="#novaSolicitacao" class="btn btn-primary"
						data-bs-toggle="modal"> <i class="bi bi-plus-circle"></i> Nova
						Solicitação
					</a>
				</div>

				<%	
				List<SolicitacaoModel> solicitacoes = (List<SolicitacaoModel>) request.getAttribute("lista");
				if(solicitacoes == null || solicitacoes.isEmpty()){
				%>
					<div class="alert alert-light" role="alert">
						Você não fez nenhuma solicitação!
					</div>
				<%} else { %>
				<table class="table table-bordered table-hover align-middle">
					<thead class="table-light">
						<tr>
							<th>Data</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
					<% for(SolicitacaoModel solicitacao : solicitacoes) { %>
						<tr>
							<td><%= solicitacao.getDataReserva() %></td>
							<td><%= solicitacao.getStatus() %></td>
						</tr>
					<% } %>
					</tbody>
				</table>
				<% } %>
			</div>
		</div>
	</main>

	<!-- Modal Nova Solicitação -->
	<div class="modal fade" id="novaSolicitacao" tabindex="-1"
		aria-labelledby="novaSolicitacaoLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="SolicitanteServlet" method="POST">
					<div class="modal-header bg-primary text-white">
						<h5 class="modal-title" id="novaSolicitacaoLabel">Nova Solicitação</h5>
						<button type="button" class="btn-close btn-close-white"
							data-bs-dismiss="modal" aria-label="Fechar"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3">
							<label for="sala" class="form-label">Nome do espaço</label>
							<select class="form-select" name="sala" id="sala" required>
								<%
								List<EspacoModel> espacos = (List<EspacoModel>) request.getAttribute("listaEspacos");
								if (espacos != null) {
									for (EspacoModel espaco : espacos) {
								%>
									<option value="<%= espaco.getIdEspaco() %>"><%= espaco.getNome() %></option>
								<%
									}
								}
								%>
							</select>
						</div>

						<div class="mb-3">
							<label for="data" class="form-label">Data</label>
							<input type="date" class="form-control" id="data" name="data" required>
						</div>

						<div class="mb-3">
							<label for="horarioInicio" class="form-label">Horário de início</label>
							<input type="time" class="form-control" id="horarioInicio" name="horarioInicio" required>
						</div>

						<div class="mb-3">
							<label for="horarioFim" class="form-label">Horário de fim</label>
							<input type="time" class="form-control" id="horarioFim" name="horarioFim" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Enviar Solicitação</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
