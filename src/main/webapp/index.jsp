<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
	<link rel="stylesheet" href="css/style.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: auto;
      }
    </style>
  </head>
  <body>
    <main class="form-signin">
      <form action="LoginServlet" method="POST">
         <h1 class="h3 mb-3 fw-normal text-center">Insira seu login</h1>

        <div class="form-floating">
          <input type="email" name="email" class="form-control" id="floatingInput" placeholder="Exemplo@exemplo" required>
          <label for="floatingInput">Email</label>
        </div>
        <div class="form-floating">
          <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Senha" required>
          <label for="floatingPassword">Senha</label>
        </div>

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" name="lembrar" value="lembrar"> Lembre de mim
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">Logar</button>
      </form>
    </main>

    <!-- Bootstrap JS via CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
  </body>
</html>

