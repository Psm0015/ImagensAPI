function carregartb() {
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("GET", "http://localhost:8080/buscarimg/");

    xhr.send();
    xhr.onload = function () {
        tabela = ""
        imagens = JSON.parse(this.responseText);
        for (i = 0; i < imagens.length; i++) {
            console.log(imagens[i])
            tabela += "<tr><th><i style = 'cursor: pointer;' onclick='excluir(" + imagens[i].id + ")' class='fa fa-trash' aria-hidden='true'></i></th><th>" + imagens[i].nome + "</th>";
            tabela += "<th><button onclick='mostrarimg(" + imagens[i].id + ")' type='button' class='btn btn-dark'>Abrir Imagem</button></th></tr>"
        }
        document.getElementById("imagens").innerHTML = tabela;
    }
}

function mostrarimg(id) {
    $('#mostrarimagem').modal('show')
    document.getElementById("showimg").src = "http://" + window.location.hostname + ":8080/buscarimg/" + id
}
function enviarimg() {
    x = document.getElementById("arquivo")
    imagem = new FormData()
    imagem.append("arquivo", x.files[0]);
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("POST", "http://localhost:8080/buscarimg/");

    xhr.send(imagem);
    xhr.onload = function () {
        carregartb()

    }
}
function excluir(id) {

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("DELETE", "http://localhost:8080/buscarimg/"+id);

    xhr.send();

    xhr.onload = function(){
        console.log(this.responseText)
        carregartb()
    }
}
carregartb()
